package com.kml.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.UUID;

public class Phase {

	
	
	public static void main(String[] args) {
		try {
			@SuppressWarnings("resource")
			FileReader reader = new FileReader("E:\\2temp\\bigmap.kml");
			BufferedReader buf = new BufferedReader(reader);
			StringBuilder kml=new StringBuilder();
			String line = buf.readLine();
			while(line !=null) {
				kml.append(line);
//				System.out.print(line);
				line=buf.readLine();
			}
			System.out.println("所有："+kml.toString());
			
			int i=0;
			FileWriter writer = new FileWriter(new File("E:\\2temp\\ss.xml"));
			split(kml.toString(),i,writer);
			
			writer.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void split(String kml,int i,FileWriter writer) throws Exception{
		int start=0;
		int end=0;
		//STEP1
		///////Folder/////////
		String Folder_begin="<Folder>";
		String Folder_end="</Folder>";
		start = kml.indexOf(Folder_begin);
		end=kml.indexOf(Folder_end)+Folder_end.length();
		
		if(start==-1||end==-1) {
			return;
		}
		String val=kml.substring(start, end);
//		writer.write("\n");
		///////Folder/////////
		
		//STEP2
		///////name//////////
		String name_begin="<name>";
		String name_end="</name>";
		start=val.indexOf(name_begin)+name_begin.length();
		end=val.indexOf(name_end);
		
		String val_name=val.substring(start,end);
		System.out.println("val_name:"+val_name);
//		writer.write(val_name);
//		writer.write("\n");
		///////name//////////
		
		//STEP2-1
		///////<Placemark>//////////
		String Placemark_begin="<Placemark>";
		String Placemark_end="</Placemark>";
		start=val.indexOf(Placemark_begin)+Placemark_begin.length();
		end=val.indexOf(Placemark_end);
		
		String val_Placemark=val.substring(start,end);
//		System.out.println("val_Placemark:"+val_Placemark);
		
		String name_begin2="<name>";
		String name_end2="</name>";
		start=val_Placemark.indexOf(name_begin2)+name_begin2.length();
		end=val_Placemark.indexOf(name_end2);
		
		String val_name2=val_Placemark.substring(start,end);
		double f = Double.parseDouble(val_name2);
		BigDecimal   b   =   new   BigDecimal(f);  
		double   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue();  
		val_name2=String.valueOf(f1);
		System.out.println("val_name:"+val_name2);
		
		
//			writer.write(val_name);
//			writer.write("\n");
		///////<Placemark>//////////
		
		
		//STEP3
		///////description//////////
		String description_begin="<description>";
		String description_end="</description>";
		start=val.indexOf(description_begin)+description_begin.length();
		end=val.indexOf(description_end);
		
		String val_description=val.substring(start,end);
		System.out.println("val_description:"+val_description);
//		writer.write(val_description);
//		writer.write("\n");
		////////description/////////
		
		//STEP4
		////////coordinates/////////
		String coordinates_begin="<coordinates>";
		String coordinates_end="</coordinates>";
		start=val.indexOf(coordinates_begin)+coordinates_begin.length();
		end=val.indexOf(coordinates_end);
		
		String val_coordinates=val.substring(start,end);
		
		String[] sp = val_coordinates.split(" ");
		String max_lng="0";
		String max_lat="0";
		
		String min_lng="0";
		String min_lat="0";
		for (String st : sp) {
			String[] sub = st.split(",");
			min_lng=sub[0];
			min_lat=sub[1];
			
			if(Double.parseDouble(sub[0])>Double.parseDouble(max_lng)) {
				max_lng=sub[0];
			}
			if(Double.parseDouble(sub[1])>Double.parseDouble(max_lat)) {
				max_lat=sub[1];
			}
			if(Double.parseDouble(sub[0])<Double.parseDouble(min_lng)) {
				min_lng=sub[0];
			}
			if(Double.parseDouble(sub[1])<Double.parseDouble(min_lat)) {
				min_lat=sub[1];
			}
		}
		
		String lng=String.valueOf( (Double.parseDouble(max_lng)+Double.parseDouble(min_lng)) / 2 );
		String lat=String.valueOf( (Double.parseDouble(max_lat)+Double.parseDouble(min_lat)) / 2 );
		
		val_coordinates=val_coordinates.replace(",","|");
		val_coordinates=val_coordinates.replace(" ",",");
		System.out.println("val_coordinates:"+val_coordinates);
//		writer.write(val_coordinates);
		////////coordinates/////////
		i++;
		String sql=" INSERT INTO basic_area (id,code,file_code,NAME,acreage,area_coordinate,lat,lng,area_type,area_level,create_time,create_by,update_time,update_by,del_flag,is_config,parent_id,dept_id,partition_no,private_no)  VALUES ";
		sql+= " ("+(300+i)+",'','24','"+val_description+"-"+val_name+"',"+val_name2+",'"+val_coordinates+"','"+lat+"','"+lng+"','100','02',NOW(),'admin',NOW(),'admin','0','N','',120,'"+val_name+"','"+UUID.randomUUID().toString()+"');";
		
		System.out.println(sql);
		writer.write(sql);
		writer.write("\n");
		
		kml=kml.replace(val,"");
		split(kml,i,writer);
		
	}
	
}
