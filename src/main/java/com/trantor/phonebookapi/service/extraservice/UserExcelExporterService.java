package com.trantor.phonebookapi.service.extraservice;

import com.trantor.phonebookapi.model.ContactModel;
import com.trantor.phonebookapi.model.MobileModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Service
public class UserExcelExporterService {
        private XSSFWorkbook workbook;
        private XSSFSheet sheet;
        private List<ContactModel> listUsers;

        public UserExcelExporterService(List<ContactModel> listUsers) {
            this.listUsers = listUsers;
            workbook = new XSSFWorkbook();
        }


        private void writeHeaderLine() {
            sheet = workbook.createSheet("Users");

            Row row = sheet.createRow(0);

            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            font.setFontHeight(16);
            style.setFont(font);
            style.setFillBackgroundColor(IndexedColors.BLUE_GREY.getIndex());
            style.setFillPattern(FillPatternType.BIG_SPOTS);

            createCell(row, 0, "Contact ID", style);
            createCell(row, 1, "First Name", style);
            createCell(row, 2, "Last Name", style);
            createCell(row, 3, "Email Address", style);
            createCell(row, 4, "Is Active", style);
            createCell(row, 5, "Created On", style);
            createCell(row, 6, "Created By", style);

            //mobile

            createCell(row, 7, "Mobile ID", style);
            createCell(row, 8, "created By ", style);
            createCell(row, 9, "Mobile Number ", style);

        }

        private void createCell(Row row, int columnCount, Object value, CellStyle style) {
            sheet.autoSizeColumn(columnCount);
            Cell cell = row.createCell(columnCount);
            if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else if (value instanceof Boolean) {
                cell.setCellValue((Boolean) value);
            }else {
                cell.setCellValue((String) value);
            }
            cell.setCellStyle(style);
        }

        private void writeDataLines() {
            int rowCount = 1;

            CellStyle style = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setFontHeight(14);
            style.setFont(font);

            for (ContactModel user : listUsers) {

                // setting color of row according to odd or even
                CellStyle cellStyle = setColor(user);
                style = cellStyle;

                Row row = sheet.createRow(rowCount++);
                int columnCount = 0;

                createCell(row, columnCount++, user.getContactId(), style);
                createCell(row, columnCount++, user.getFirstName(), style);
                createCell(row, columnCount++, user.getLastName(), style);
                createCell(row, columnCount++, user.getEmailAddress().toString(), style);
                createCell(row, columnCount++, user.getIsActive(), style);
                createCell(row, columnCount++, user.getCreatedDate().toString(), style);
                createCell(row, columnCount++, user.getCreatedBy(), style);

                List<MobileModel> mobile = user.getMobile();

                for (MobileModel mobileModel: mobile) {
                    createCell(row, columnCount++, mobileModel.getMobileId(), style);
                    createCell(row, columnCount++, mobileModel.getCreatedBy(), style);
                    createCell(row, columnCount++, mobileModel.getMobileNumber().toString(), style);
                }

            }
        }

        CellStyle setColor(ContactModel contactModel){
            CellStyle cellStyle = workbook.createCellStyle();

            if(contactModel.getContactId() % 2 == 0){
                cellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());
                cellStyle.setFillPattern(FillPatternType.BIG_SPOTS);
            }
            else
            {
                cellStyle.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
                cellStyle.setFillPattern(FillPatternType.BIG_SPOTS);
            }

            return cellStyle;
        }

        public void export(HttpServletResponse response) throws IOException {
            writeHeaderLine();
            writeDataLines();

            System.out.println(response);

            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();

            outputStream.close();

        }
    }
