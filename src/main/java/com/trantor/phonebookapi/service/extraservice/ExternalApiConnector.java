package com.trantor.phonebookapi.service.extraservice;

import com.trantor.phonebookapi.dto.ContactDTO;
import com.trantor.phonebookapi.model.ContactModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@PropertySource("classpath:config.properties")
public class ExternalApiConnector {

    @Value("${baseUri}")
    String uri = "";

    @Value("${getAllAddonUri}")
    String getAllAddonUri = "";

    @Value("${getByNameAddonUri}")
    String getByNameAddonUri = "";

    @Value("${saveAddonUri}")
    String saveAddonUri = "";

    @Value("${deleteAddonUri}")
    String deleteAddonUri = "";

    @Value("${excelConvertor}")
    String excelConvertor = "";


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ModelMapper modelMapper;

    //get
    public List<ContactDTO> getAllProducts(){

        String getAll = uri + getAllAddonUri;

        ContactDTO[] forObject = restTemplate.getForObject(getAll, ContactDTO[].class);
        return Arrays.asList(forObject);
    }

    //getByName
    public List<ContactDTO> getByName(String name){

        String getByNameURI = uri + getByNameAddonUri + name;

        ContactDTO[] forObject = restTemplate.getForObject(getByNameURI, ContactDTO[].class);
        return Arrays.asList(forObject);
    }

    //save
    public ResponseEntity<ContactDTO> createProducts(ContactDTO contactDTO){

        String save = uri + saveAddonUri;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        ContactModel contactModel = modelMapper.map(contactDTO, ContactModel.class);

        HttpEntity<ContactModel> entity = new HttpEntity<>(contactModel,httpHeaders);
        return restTemplate.exchange(save, HttpMethod.POST, entity, ContactDTO.class);
    }

    //delete
    public ContactDTO deleteProduct(Integer id) {

        String delete = uri + deleteAddonUri + id;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<ContactDTO> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(delete, HttpMethod.DELETE, httpEntity, ContactDTO.class).getBody();
    }
}
