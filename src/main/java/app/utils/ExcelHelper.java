package app.utils;

import app.entity.TaskResult;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = { "Name", "Amazon", "Google" };
    static String SHEET = "Workbook";

    public static ByteArrayInputStream export(List<TaskResult> tutorials) {

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERs[col]);
            }

            int rowIdx = 1;
            for (TaskResult taskResult : tutorials) {
                Row row = sheet.createRow(rowIdx++);
                Hyperlink amazon = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
                Hyperlink google = workbook.getCreationHelper().createHyperlink(HyperlinkType.URL);
                amazon.setAddress(taskResult.getAmazonUrl());
                amazon.setLabel("amazon search");
                row.createCell(0).setCellValue(taskResult.getName());
                row.createCell(1).setHyperlink(amazon);
                row.createCell(1).setCellValue("amazon link");
                google.setAddress(taskResult.getGoogleUrl());
                google.setLabel("google search");
                row.createCell(2).setHyperlink(google);
                row.createCell(2).setCellValue("google link");
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
