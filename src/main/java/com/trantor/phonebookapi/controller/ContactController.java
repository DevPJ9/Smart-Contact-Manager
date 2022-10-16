package com.trantor.phonebookapi.controller;

import com.trantor.phonebookapi.dto.ContactDTO;
import com.trantor.phonebookapi.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContactController {
    
    @Autowired
    private ContactService contactService;

    // for getting all the data
    @GetMapping("/get/{connectToMySystem}")
    public List<ContactDTO> findAll(@PathVariable(name = "connectToMySystem") boolean flag){
        return contactService.findAllContact(flag);
    }

    // for saving all the data
    @PostMapping("/save/{connectToMySystem}")
    public ResponseEntity<ContactDTO> saveData(@PathVariable(name = "connectToMySystem") boolean flag, @RequestBody ContactDTO contactDTO){
        return contactService.saveContact(flag,contactDTO);
    }

    // for getting data by name
    @GetMapping("/get/byName/{connectToMySystem}/{name}")
    public List<ContactDTO> getByName(@PathVariable(name = "connectToMySystem") boolean flag,@PathVariable(name = "name") String name){
        return contactService.findByfirstName(flag,name);
    }

    // for deleting a data i.e. setting isActive = false
    @DeleteMapping("/delete/{connectToMySystem}/{id}")
    public ResponseEntity<ContactDTO> deleteByID(@PathVariable(name = "connectToMySystem") boolean flag , @PathVariable(name = "id") Integer ID){
        return contactService.deleteByID(flag,ID);
    }
}
