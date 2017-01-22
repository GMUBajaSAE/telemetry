
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;





import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


//This class utilizes the Apache POI library to write data streamed from Serial
//and writes the data into an excel file
//The constructor initializes the excel file path while the add() adds data to excel
public class ExcelWriter {
	
	private static String FILE_PATH = "C:/Users/p/Desktop/data.xls";  //excel writes to path
	HSSFWorkbook workbook;
	HSSFSheet sheet;
	int nextRow = 0;
	
	
	//constructors sets up excel file
	public ExcelWriter(){
		
		 workbook = new HSSFWorkbook();
		 sheet = workbook.createSheet("FirstExcelSheet");	
	}
			
	
		
	//adds data to an excel spreadsheet	
	//@param data: data inputed from serial data stream
	//@param return:  void
	void addData(int data) throws FileNotFoundException, IOException{
				
		//creates next row as long as its empty
		Row r = sheet.getRow(nextRow);
		if (r == null) {
		    r = sheet.createRow(nextRow);
		}
		//writes to cell then increments the row
		@SuppressWarnings("deprecation")
		Cell c = r.getCell(0, Row.CREATE_NULL_AS_BLANK);
		c.setCellValue(data);
		nextRow++;
			
		//closes iostream and saves to excel file
		workbook.write(new FileOutputStream(FILE_PATH));
		workbook.close();
		
	    }
		
}


