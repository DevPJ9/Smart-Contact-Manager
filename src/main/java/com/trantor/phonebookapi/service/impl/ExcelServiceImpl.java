package com.trantor.phonebookapi.service.impl;

import com.trantor.phonebookapi.model.ContactModel;
import com.trantor.phonebookapi.repo.ContactRepo;
import com.trantor.phonebookapi.service.ExcelService;
import com.trantor.phonebookapi.service.extraservice.UserExcelExporterService;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

@PropertySource("classpath:application.properties")
@Service
@Transactional
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    HikariDataSource hikariDataSource;

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;


    private static final Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);
    private static final String CSV_FILE_LOCATION = "C:/Users/pranjal.joshi/Downloads/Contacts_DataExcel.xlsx";

    @Autowired
    private UserExcelExporterService userExcelExporterService;

    @Autowired
    private ContactRepo contactRepo;

    public void exportData(ResponseEntity responseEntity) throws IOException {
        userExcelExporterService.export((HttpServletResponse) responseEntity);
    }

    public List<ContactModel> listAll(HttpServletResponse response) {

        response.setContentType("application/octet-stream");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Contacts_" + "DataExcel" + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<ContactModel> all = contactRepo.findAll();

        UserExcelExporterService excelExporter = new UserExcelExporterService(all);

        try {
            excelExporter.export(response);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return all;
    }

    @Transactional
    public List<ContactModel> uploadAll(){

        List<ContactModel> courses = new ArrayList<>();

        Workbook workbook =null;
        try {
            // Creating a Workbook from an Excel file (.xls or .xlsx)
            workbook = WorkbookFactory.create(new File(CSV_FILE_LOCATION));

            // Print all sheets name
            workbook.forEach(sheet -> {
                logger.info(" => " + sheet.getSheetName());

                // Create a DataFormatter to format and get each cell's value as String
                DataFormatter dataFormatter = new DataFormatter();

                //loop through all rows and columns and create Course object
                int index = 0;

                for(Row row : sheet) {
                    if(index++ == 0) continue;

                    ContactModel course = new ContactModel();

                    course.setFirstName(dataFormatter.formatCellValue(row.getCell(1)));
                    course.setLastName(dataFormatter.formatCellValue(row.getCell(2)));
                    course.setEmailAddress(dataFormatter.formatCellValue(row.getCell(3)));
                    course.setIsActive(Boolean.valueOf(dataFormatter.formatCellValue(row.getCell(4))));
                    course.setCreatedBy(dataFormatter.formatCellValue(row.getCell(6)));

                    courses.add(course);
                }

                saveAllJdbcBatch(courses);

            });
        } catch (EncryptedDocumentException | IOException e) {
            logger.error(e.getMessage(), e);
        }finally {
            try {
                if(workbook != null) workbook.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

        return courses;
    }

    public void saveAllJdbcBatch(List<ContactModel> productData){
        String sql = String.format(
                "INSERT INTO %s (first_Name, last_Name, email_Address, is_Active,created_By) " +
                        "VALUES (?, ?, ?, ?, ?)",
                ContactModel.class.getAnnotation(Table.class).name()
        );
        try (Connection connection = hikariDataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)
        ){
            int counter = 0;
            for (ContactModel product : productData) {
                statement.clearParameters();
                statement.setString(1, product.getFirstName());
                statement.setString(2, product.getLastName());
                statement.setString(3, product.getEmailAddress());
                statement.setBoolean(4, product.getIsActive());
                statement.setString(5, product.getCreatedBy());
                statement.addBatch();
                if ((counter + 1) % batchSize == 0 || (counter + 1) == productData.size()) {
                    statement.executeBatch();
                    statement.clearBatch();
                }
                counter++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
