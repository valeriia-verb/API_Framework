package com.restful_booker.utilities;

import org.apache.poi.ss.usermodel.*; //Provides classes for working with Excel workbooks, sheets, rows, and cells.
import org.apache.poi.xssf.usermodel.XSSFWorkbook; //Represents an Excel workbook for .xlsx files.

import java.io.FileInputStream; //Used to read the Excel file.
import java.io.IOException;
import java.util.*;


    public class ExcelUtil {

        /*
        This method reads data from an Excel file at the given file path and returns a list of Object[],
        where each Object[] represents a row of data.
         */
        public static List<Object[]> readExcelData(String filePath) throws IOException {
            List<Object[]> data = new ArrayList<>();

            try (FileInputStream fileInputStream = new FileInputStream(filePath);
                 Workbook workbook = new XSSFWorkbook(fileInputStream)) { //This ensures that the FileInputStream and Workbook are closed properly after use.

                Sheet sheet = workbook.getSheetAt(0);  //This gets the first sheet in the workbook.
                Iterator<Row> rows = sheet.iterator(); //This gets an iterator for the rows and skips the header row.
                rows.next(); // Skip header row

                while (rows.hasNext()) {  //Iterates through each row in the sheet.
                    Row row = rows.next();
                    Iterator<Cell> cells = row.iterator(); //Gets an iterator for the cells in the current row.

                    //Reading cell values:
                    String firstname = cells.next().getStringCellValue();
                    String lastname = cells.next().getStringCellValue();
                    int totalprice = (int) cells.next().getNumericCellValue();
                    boolean depositpaid = cells.next().getBooleanCellValue();
                    String checkin = String.valueOf(cells.next().getLocalDateTimeCellValue()).substring(0, 10);
                    String checkout = String.valueOf(cells.next().getLocalDateTimeCellValue()).substring(0,10);
                    String additionalneeds = cells.next().getStringCellValue();

                    data.add(new Object[]{firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds});
                    //Adds the cell values as an Object[] to the data list.
                }
            }

            return data;
            //Returns the list of rows, where each row is represented as an Object[].
        }


    }

