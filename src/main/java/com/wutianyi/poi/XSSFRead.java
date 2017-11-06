package com.wutianyi.poi;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hanjiewu on 2016/6/29.
 */
public class XSSFRead {
	public static void main(String[] args) throws IOException, InvalidFormatException {
		Workbook workbook = new XSSFWorkbook(new File("F:\\work\\情景短信\\需求\\附件b：小源号码库_v1.xlsx"));
		PrintWriter pw = new PrintWriter("F:\\work\\情景短信\\需求\\phone.txt");
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);
			System.out.println(sheet.getSheetName());
			int rows = sheet.getPhysicalNumberOfRows();
			for (int r = 0; r < rows; r++) {
				Row row = sheet.getRow(r);
				if (null == row) {
					continue;
				}
				Cell cell = row.getCell(0);
				if (null == cell) {
					continue;
				}
				pw.println(cell.getStringCellValue());
			}
		}
		pw.close();
		workbook.close();
	}
}
