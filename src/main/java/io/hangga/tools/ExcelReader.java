package io.hangga.tools;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelReader extends SwingWorker<Void, Void> {

    private String filePath;
    private ExcelListener excelListener;

    private List<String> names;

    public ExcelReader setFilePath(String filePath, ExcelListener excelListener) {
        this.filePath = filePath;
        this.excelListener = excelListener;
        return this;
    }

    @Override
    protected Void doInBackground() {
        FileInputStream fis;
        try {
            fis = new FileInputStream(filePath);

            String extensi = filePath.substring(filePath.lastIndexOf("."));
            StringBuffer stringBuffer = new StringBuffer();
            if (extensi.equalsIgnoreCase(".xls")) {
                HSSFWorkbook wb = new HSSFWorkbook(fis);
                //creating a Sheet object to retrieve the object
                HSSFSheet sheet = wb.getSheetAt(0);
                //evaluating cell type
                //FormulaEvaluator formulaEvaluator = wb.getCreationHelper().createFormulaEvaluator();
                names = new ArrayList<>();
                //StringBuffer stringBuffer = new StringBuffer();
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        //System.out.print(cell.getStringCellValue() + "\n\t");
                        names.add(cell.getStringCellValue().replace("/","-"));
                        stringBuffer.append(cell.getStringCellValue()).append(", ");
                        excelListener.OnGetNameAt(cell.getStringCellValue());
                    }
                    //System.out.println();
                }
                if (names.size() > 0) excelListener.OnGetNames(names, String.valueOf(stringBuffer));
            } else {
                XSSFWorkbook workbook = new XSSFWorkbook(fis);

                // Get first/desired sheet from the workbook
                XSSFSheet sheet = workbook.getSheetAt(0);

                // Iterate through each rows one by one

                // Till there is an element condition holds true
               /* for (Row row : sheet) {

                    Iterator<Cell> cellIterator
                            = row.cellIterator();

                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();
                        System.out.print(cell.getStringCellValue() + "\n\t");
                        names.add(cell.getStringCellValue());
                        stringBuffer.append(cell.getStringCellValue()).append(", ");
                        excelListener.OnGetNameAt(cell.getStringCellValue());
                    }
                }*/

                for (Row row : sheet) {
                    for (Cell cell : row) {
                        //System.out.print(cell.getStringCellValue() + "\n\t");
                        names.add(cell.getStringCellValue().replace("/","-"));
                        stringBuffer.append(cell.getStringCellValue()).append(", ");
                        excelListener.OnGetNameAt(cell.getStringCellValue());
                    }
                    //System.out.println();
                }
                if (names.size() > 0) excelListener.OnGetNames(names, String.valueOf(stringBuffer));
            }

            fis.close();

        } catch (IOException e) {
            System.out.println("ERROR disini :  " + e.getMessage());
            throw new RuntimeException(e);
        }

        return null;
    }
}
