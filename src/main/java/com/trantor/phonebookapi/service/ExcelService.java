package com.trantor.phonebookapi.service;

import com.trantor.phonebookapi.model.ContactModel;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ExcelService {
    public void exportData(ResponseEntity responseEntity) throws IOException;

    List<ContactModel> listAll(HttpServletResponse response);
    public List<ContactModel> uploadAll();
}
