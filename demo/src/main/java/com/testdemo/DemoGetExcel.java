package com.testdemo;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import io.opentelemetry.sdk.metrics.data.Data;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.NumberToTextConverter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DemoGetExcel {

    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir") + "DemoPVT");
        FileInputStream file = new FileInputStream(
                System.getProperty("user.dir") + "/demo/src/test/java/data/testdemo.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        int sheets = workbook.getNumberOfSheets();
        for (int i = 0; i < sheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase("test")) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rows = sheet.iterator();
                Row firstrow = rows.next();
                Iterator<Cell> cell = firstrow.cellIterator();
                int k = 0;
                int column = -1;
                while (cell.hasNext()) {
                    Cell value = cell.next();
                    if (value.getStringCellValue().equalsIgnoreCase("Test1")) {
                        column = k;
                        System.out.println("Column: " + column);
                    }
                    k++;
                }
            }
        }
    }

    public Object[][] getDataExcel(String filePath, String sheetName) throws IOException {
        Object[][] data = null;
        DataFormatter formatter = new DataFormatter();
        // fileInputStream argument
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        int sheets = workbook.getNumberOfSheets();
        for (int i = 0; i < sheets; i++) {
            if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
                XSSFSheet sheet = workbook.getSheetAt(i);
                // Identify Testcases coloum by scanning the entire 1st row
                XSSFRow row = sheet.getRow(0);
                int rowCount = sheet.getPhysicalNumberOfRows();
                int columnCount = row.getLastCellNum();
                data = new Object[rowCount - 1][columnCount];
                for (int j = 0; j < rowCount - 1; j++) {
                    row = sheet.getRow(j + 1);
                    for (int k = 0; k < columnCount; k++) {
                        XSSFCell c = row.getCell(k);
                        data[j][k] = formatter.formatCellValue(c);
                    }
                }
            }
        }
        return data;
    }
}
