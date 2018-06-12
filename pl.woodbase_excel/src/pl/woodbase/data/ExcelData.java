package pl.woodbase.data;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import pl.woodbase.dao.SqlConn;

public class ExcelData {
	
	private int id;
	private String path;
	private SqlConn sqlConn = new SqlConn();
	public void readExcelFile(String fileName) {
		
		try {
			
			FileInputStream excelFile = new FileInputStream(new File(fileName));
			Workbook workbook = new XSSFWorkbook(excelFile);
			org.apache.poi.ss.usermodel.Sheet datatypeSheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = datatypeSheet.iterator();
			
			while(iterator.hasNext()) {
				
				Row currentRow = iterator.next();
				Iterator<Cell> cellIterator = currentRow.iterator();
					
					while(cellIterator.hasNext()) {
						
						Cell currentCell = cellIterator.next();
						
						if(currentCell.getCellTypeEnum() == CellType.STRING) {
							path = currentCell.getStringCellValue();
							//System.out.println(currentCell.getStringCellValue() + "--");
						}else if(currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							id = (int) currentCell.getNumericCellValue();
							//System.out.println(currentCell.getNumericCellValue() +"--");
						}
						
					}
					System.out.println("Wczytujê do bazy: " + id + " " + path);
					sqlConn.addRecord(id, path);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
