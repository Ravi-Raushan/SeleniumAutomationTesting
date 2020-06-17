package testutility;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ExcelFileWrite {

    public static void putData(int sheetNumber,int row,int column,String data) {
        XSSFWorkbook workbook = null;
        File src = null;
        try{
            src = new File(Constant.EXCEL_OUTPUT_FILE_PATH);
            FileInputStream fileInputStream = new FileInputStream(src);
            workbook = new  XSSFWorkbook(fileInputStream);
        }catch(Exception e){
            e.printStackTrace();
        }
        XSSFSheet firstSheet = workbook.getSheetAt(sheetNumber);
        firstSheet.getRow(row).createCell(column).setCellValue(data);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(src);
            workbook.write(fileOutputStream);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
