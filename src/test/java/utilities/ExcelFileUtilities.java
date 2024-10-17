package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFileUtilities {

	public XSSFWorkbook wb;

	public ExcelFileUtilities(String filePath) throws Throwable {
		FileInputStream fi = new FileInputStream(filePath);
		wb = new XSSFWorkbook(fi);

	}

	public int rowCount(String sheetName) {

		return wb.getSheet(sheetName).getLastRowNum();
	}

	public String getCellData(String sheetName, int rowNum, int cellNum) {

		String cellData;
		if (wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getCellType() == CellType.STRING) {
			cellData = wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getStringCellValue();
		} else {
			int cellNumericData = (int) wb.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getNumericCellValue();

			cellData = String.valueOf(cellNumericData);

		}

		return cellData;
	}

	public void setCellData(String sheetName, int rowNum, int cellNum, String status, String writeExcel)
			throws Throwable {

		XSSFSheet sheet = wb.getSheet(sheetName);

		XSSFRow row = sheet.getRow(rowNum);

		XSSFCell cell = row.createCell(cellNum);

		cell.setCellValue(status);

		if (status.equalsIgnoreCase("pass")) {
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setBold(true);
			font.setColor(IndexedColors.GREEN.getIndex());
			style.setFont(font);

		} else if (status.equalsIgnoreCase("fail")) {
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setBold(true);
			font.setColor(IndexedColors.RED.getIndex());
			style.setFont(font);

		} else if (status.equalsIgnoreCase("blocked")) {
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setBold(true);
			font.setColor(IndexedColors.BLUE.getIndex());
			style.setFont(font);

		}
		FileOutputStream fo = new FileOutputStream(writeExcel);
		wb.write(fo);
	}

}
