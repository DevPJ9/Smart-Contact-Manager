package com.trantor.phonebookapi.service.impl;

import com.trantor.phonebookapi.dto.ContactDTO;
import com.trantor.phonebookapi.exception.DataIsEmptyException;
import com.trantor.phonebookapi.exception.InvalidFlagValueException;
import com.trantor.phonebookapi.exception.NameNotFoundException;
import com.trantor.phonebookapi.exception.ResourceNotFoundException;
import com.trantor.phonebookapi.model.ContactModel;
import com.trantor.phonebookapi.repo.ContactRepo;
import com.trantor.phonebookapi.service.ContactService;
import com.trantor.phonebookapi.service.extraservice.ExternalApiConnector;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    ExternalApiConnector externalApiConnector;

    @Autowired
    ExcelServiceImpl excelService;

    /*
     * flag = false means connecting to the external API
     * flag = true means connecting to my API
     */


    @Override
    public List<ContactDTO> findAllContact(boolean flag) {
        if (flag == true) {

            List<ContactDTO> collect = contactRepo.findAll().stream().map(contact -> modelMapper.map(contact, ContactDTO.class))
                    .collect(Collectors.toList());

            if (collect.isEmpty() || collect == null) {
                throw new DataIsEmptyException();
            } else {
                try {
                    excelService.exportData(new ResponseEntity(collect, HttpStatus.OK));

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return collect;
            }
        }
        else if(flag == false){
            return externalApiConnector.getAllProducts();
        }
        else {
            throw new InvalidFlagValueException();
        }

    }

    @Override
    public ResponseEntity<ContactDTO> saveContact(boolean flag,ContactDTO contactDTO) {

        if(flag == true){
            ContactModel contactModel = modelMapper.map(contactDTO, ContactModel.class);

            System.out.println(" save :" + contactModel);

            contactRepo.save(contactModel);

            ContactDTO mDto = modelMapper.map(contactModel, ContactDTO.class);

            return new ResponseEntity<ContactDTO>(mDto, HttpStatus.OK);
        }
        else
        {
            return externalApiConnector.createProducts(contactDTO);
        }
    }

    @Override
    public List<ContactDTO> findByfirstName(boolean flag, String name) {
        if (flag == true) {

            List<ContactDTO> collect = contactRepo.findByfirstName(name).stream().map(contact -> modelMapper.map(contact, ContactDTO.class))
                    .collect(Collectors.toList());

            if(collect.isEmpty() || collect == null){
                throw new NameNotFoundException();
            }
            else {
                return collect;
            }

        }
        else if(flag == false) {
            return externalApiConnector.getByName(name);
        }
        else {
            throw new InvalidFlagValueException();
        }
    }

    @Override
    public ResponseEntity<ContactDTO> deleteByID(boolean flag ,Integer ID) {

        if (flag == true) {

            ContactModel contactModel = contactRepo.findById(ID).orElseThrow(()
                    -> new ResourceNotFoundException("____ * * Value Not FOUND * * ____"));

            contactModel.setIsActive(false);

            ContactModel savedModel = contactRepo.save(contactModel);

            ContactDTO map = modelMapper.map(savedModel, ContactDTO.class);
            return new ResponseEntity<ContactDTO>(map,HttpStatus.OK);
        }
        else if(flag == false) {
            ContactDTO contactDTO = externalApiConnector.deleteProduct(ID);
            return new ResponseEntity<ContactDTO>(contactDTO,HttpStatus.OK);
        }
        else {
            throw new InvalidFlagValueException();
        }
    }
}
