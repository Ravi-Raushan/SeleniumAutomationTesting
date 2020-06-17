package testutility;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class ExcelFileRead {
    XSSFWorkbook workbook;
    XSSFSheet firstSheet;
    File src;
    public ExcelFileRead(String excelPath){
        try{
             src = new File(excelPath);
            FileInputStream fileInputStream = new FileInputStream(src);
            workbook = new  XSSFWorkbook(fileInputStream);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getData(int sheetNumber,int row,int column){
        firstSheet = workbook.getSheetAt(sheetNumber);
        firstSheet.getRow(row).getCell(column).setCellType(Cell.CELL_TYPE_STRING);
        String data = firstSheet.getRow(row).getCell(column).getStringCellValue();
        return data;
    }

    public int getRowCount(int sheetNumber){
       return workbook.getSheetAt(sheetNumber).getLastRowNum()+1;
    }
    public int getColumnCount(int sheetNumber){
        return workbook.getSheetAt(sheetNumber).getRow(0).getLastCellNum();
    }

}
