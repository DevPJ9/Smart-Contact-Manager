package com.trantor.phonebookapi.controller;

import com.trantor.phonebookapi.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class ExcelController {

    @Autowired
    ExcelService excelService;


    @GetMapping("/export/downloadDataAsExcel")
    public void exportToExcel(HttpServletResponse response) {

        excelService.listAll(response);
    }

    @PostMapping("/uploadDataFromExcelTODB")
    public void uploadDataFromExcel(){

        excelService.uploadAll();
    }
}
