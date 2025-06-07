package Utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {
    public String filePath;
    public FileInputStream fis;
    public FileOutputStream fos;
    public Workbook workbook;
    public Sheet sheet;
    public Row row;
    public Cell cell;

    public ExcelUtility(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            workbook = WorkbookFactory.create(fis);
        } catch (Exception e) {
            System.out.println("Error loading Excel file: " + e.getMessage());
            workbook = null;
        }
    }

    public int getRowCount(String sheetName) {
        if (workbook == null) {
            throw new RuntimeException("Workbook not initialized. Check file path.");
        }
        Sheet sheet = workbook.getSheet(sheetName);
        return sheet.getLastRowNum();
    }


    public int getCellCount(String sheetName, int rowNum) {
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        return row.getLastCellNum();
    }

    public String getCellData(String sheetName, int rowNum, int colNum) {
        sheet = workbook.getSheet(sheetName);
        row = sheet.getRow(rowNum);
        cell = row.getCell(colNum);
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING: return cell.getStringCellValue();
            case NUMERIC: return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN: return String.valueOf(cell.getBooleanCellValue());
            case FORMULA: return cell.getCellFormula();
            default: return "";
        }
    }

    public void setCellData(String sheetName, int rowNum, int colNum, String data) {
        try {
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(rowNum);
            if (row == null) row = sheet.createRow(rowNum);
            cell = row.createCell(colNum);
            cell.setCellValue(data);
            fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillGreenColor(String sheetName, int rowNum, int colNum) {
        fillCellColor(sheetName, rowNum, colNum, IndexedColors.BRIGHT_GREEN);
    }

    public void fillRedColor(String sheetName, int rowNum, int colNum) {
        fillCellColor(sheetName, rowNum, colNum, IndexedColors.RED);
    }

    private void fillCellColor(String sheetName, int rowNum, int colNum, IndexedColors color) {
        try {
            sheet = workbook.getSheet(sheetName);
            row = sheet.getRow(rowNum);
            if (row == null) row = sheet.createRow(rowNum);
            cell = row.getCell(colNum);
            if (cell == null) cell = row.createCell(colNum);
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(color.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            fos = new FileOutputStream(filePath);
            workbook.write(fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
