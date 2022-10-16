package com.trantor.phonebookapi.service;

import com.trantor.phonebookapi.dto.ContactDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ContactService {

    public List<ContactDTO> findAllContact(boolean flag);

    public ResponseEntity<ContactDTO> saveContact(boolean flag, ContactDTO contactDTO);

    public List<ContactDTO> findByfirstName(boolean flag, String name);

    public ResponseEntity<ContactDTO> deleteByID(boolean flag ,Integer ID);

}
