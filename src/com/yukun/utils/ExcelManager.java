package com.yukun.utils;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Arcane on 2016/10/13.
 */
public class ExcelManager {
    public ArrayList<String[]> getData(String filePath, int sheetIndex) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        File file = new File(filePath);
        Printer p = new Printer();

        //打开HSSFWorkbook
        XSSFWorkbook workBook;
        try {
            workBook = new XSSFWorkbook(file);
        } catch (InvalidFormatException e) {
            p.out("打开HSSFWorkbook失败！");
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            p.out("打开HSSFWorkbook失败！");
            e.printStackTrace();
            return null;
        }

        if (sheetIndex < workBook.getNumberOfSheets()) {
            XSSFSheet sheet = workBook.getSheetAt(sheetIndex); //获取Sheet对象
            XSSFRow firstRow = sheet.getRow(0);

            int lastRowNumber = sheet.getLastRowNum() + 1;

            for (int rowIndex = 0; rowIndex < lastRowNumber; rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);
                int lastCellNumber = row.getLastCellNum();

                //避免读到无效行
                if (row == null)
                    continue;

                String[] str = new String[lastCellNumber];
                for (int cellIndex = 0; cellIndex < lastCellNumber; cellIndex++) {
                    XSSFCell cell = row.getCell(cellIndex);
                    //避免读到无效单元
                    if (cell == null)
                        continue;

                    str[cellIndex] = row.getCell(cellIndex).getStringCellValue();
                }
                result.add(str);
            }
        }

        try {
            workBook.close();
        } catch (IOException e) {
            p.out("关闭HSSFWorkbook失败！");
            e.printStackTrace();
            return null;
        }

        return result;
    }
}
