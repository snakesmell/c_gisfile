package test.java.utils;

import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.List;
import java.util.Map;
/**
 * Author: 灵枢
 * Date: 2018/12/05
 * Time: 17:21
 * Description:读取Excel数据
 */
public class ExcelData {
	private  XSSFSheet sheet;

	public XSSFSheet getSheet() {
		return sheet;
	}
	/**
	 * 构造函数，初始化excel数据
	 * @param filePath  excel路径
	 * @param sheetName sheet表名
	 */
	ExcelData(String filePath,String sheetName){
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(filePath);
			XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
			//获取sheet
			sheet = sheets.getSheet(sheetName);
			/*//行数
			int rownum = sheet.getLastRowNum();
			System.out.println(rownum);
			//列数
			sheet.getDefaultColumnWidth();
			XSSFRow row = sheet.getRow(1);
			short cn = row.getLastCellNum();
			System.out.println(cn);*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 根据行和列的索引获取单元格的数据
	 * @param row
	 * @param column
	 * @return
	 */
	public String getExcelDateByIndex(int row,int column){
		XSSFRow row1 = sheet.getRow(row);
		String cell = row1.getCell(column).toString();
		return cell;
	}

	/**
	 * 根据某一列值为“******”的这一行，来获取该行第x列的值
	 * @param caseName
	 * @param currentColumn 当前单元格列的索引
	 * @param targetColumn 目标单元格列的索引
	 * @return
	 */
	public String getCellByCaseName(String caseName,int currentColumn,int targetColumn){
		String operateSteps="";
			//获取行数
			int rows = sheet.getPhysicalNumberOfRows();
			for(int i=0;i<rows;i++){
				XSSFRow row = sheet.getRow(i);
				String cell = row.getCell(currentColumn).toString();
				if(cell.equals(caseName)){
					operateSteps = row.getCell(targetColumn).toString();
					break;
				}
			}
		return operateSteps;
	}

	//打印excel数据
	public void readExcelData(){
		//获取行数
		int rows = sheet.getPhysicalNumberOfRows();
		for(int i=0;i<rows;i++){
			//获取列数
			XSSFRow row = sheet.getRow(i);
			int columns = row.getPhysicalNumberOfCells();
			for(int j=0;j<columns;j++){
				String cell = row.getCell(j).toString();
				System.out.println(cell);
			}
		}
	}

	//测试方法
	public static void main(String[] args) throws Exception{
		
		FileWriter writer = new FileWriter(new File("E:\\2temp\\zhibiao.xml"));
		
		
		
		ExcelData sheet1 = new ExcelData("E:\\1华高工作\\2海绵项目\\文件汇总\\西海岸海绵专项规划修编CGCS2000坐标\\指标数据.xlsx", "Sheet1");
		
		XSSFSheet tag = sheet1.getSheet();
		//行数
		int rownum = tag.getLastRowNum();
//		System.out.println(rownum);
		//列数
//		tag.getDefaultColumnWidth();
		
		String sql="";
		String area_id="";
		for(int j=1;j<=rownum;j++) {
			
			XSSFRow row = tag.getRow(j);
			short column = row.getLastCellNum();
			for(int i=0;i<column;i++) {
				switch(i) {
					case 0:{
						XSSFCell val = row.getCell(i);
						area_id = val.getRawValue();
						System.out.println("areaid:"+area_id);
						
						break;
					}
					case 5:{
						XSSFCell val = row.getCell(i);
						String raw = val.getRawValue();
						System.out.println("10:"+raw);
						
						sql=" INSERT INTO basic_area_ass_index (area_id,index_id,index_value,create_by,create_time,index_code) ";
						sql+=" VALUES("+area_id+",10,"+raw+",'admin',NOW(),10); ";
						System.out.println(sql);
						writer.write("\n");
						writer.write(sql);
						break;
					}
					case 6:{
						XSSFCell val = row.getCell(i);
						String raw = val.getRawValue();
						System.out.println("1:"+raw);	
						
						sql=" INSERT INTO basic_area_ass_index (area_id,index_id,index_value,create_by,create_time,index_code) ";
						sql+=" VALUES("+area_id+",1,"+raw+",'admin',NOW(),1); ";
						System.out.println(sql);
						writer.write("\n");
						writer.write(sql);
						break;
					}
					case 7:{
						XSSFCell val = row.getCell(i);
						String raw = val.getRawValue();
						System.out.println("6:"+raw);
						
						sql=" INSERT INTO basic_area_ass_index (area_id,index_id,index_value,create_by,create_time,index_code) ";
						sql+=" VALUES("+area_id+",6,"+raw+",'admin',NOW(),6); ";
						System.out.println(sql);
						writer.write("\n");
						writer.write(sql);
						break;
					}
					case 8:{
						XSSFCell val = row.getCell(i);
						String raw = val.getRawValue();
						System.out.println("12:"+raw);
						
						sql=" INSERT INTO basic_area_ass_index (area_id,index_id,index_value,create_by,create_time,index_code) ";
						sql+=" VALUES("+area_id+",12,"+raw+",'admin',NOW(),12); ";
						System.out.println(sql);
						writer.write("\n");
						writer.write(sql);
						break;
					}
				}
				
			}
		}
		
		writer.close();
		//获取第二行第4列
//		String cell2 = sheet1.getExcelDateByIndex(1, 3);
		//根据第3列值为“customer23”的这一行，来获取该行第2列的值
//		String cell3 = sheet1.getCellByCaseName("customer23", 2,1);
//		System.out.println(cell2);
//		System.out.println(cell3);
	}
}

