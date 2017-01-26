package de.webdataplatform.tpch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import de.webdataplatform.settings.ColumnDefinition;
import de.webdataplatform.settings.TableDefinition;

public class TPCHFileReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String rowKey = "c1";
		
		List<String> columns = new ArrayList<String>();
		columns.add("c1");
		columns.add("c2");
		columns.add("c3");
		columns.add("c4");
		columns.add("c5");
		columns.add("c6");
		columns.add("c7");
		columns.add("c8");
		columns.add("c9");
		columns.add("c10");
		columns.add("c11");
		columns.add("c12");
		columns.add("c13");
		columns.add("c14");
		columns.add("c15");
		columns.add("c16");

		File file = new File(".//src//lineitem.tbl");
		
		try {
			   BufferedReader in=new BufferedReader(new InputStreamReader(new FileInputStream(file)));

			   String msg=null;
			   int x = 0;
			   while((msg=in.readLine())!=null && x < 10){
				   generatePut(rowKey, columns, msg.split(separator));
				   x++;
			   }
			
			
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}
	
	public static String separator="\\|";
	
	

	
	
	public static void generatePut(String rowKey, List<String> columns,  String[] values){
		

			String rowKeyValue = findRowKeyValue(rowKey, columns, values);
		
			System.out.println("rowkey-value:"+rowKeyValue);
			
			Put put = new Put(Bytes.toBytes(rowKeyValue));

			
			for (int i = 0; i < values.length; i++) {
				
				put.add(Bytes.toBytes("colfam1"), Bytes.toBytes(columns.get(i)), Bytes.toBytes(values[i]));			
				
			}
			
			System.out.println("put: "+put);

		
	}
	
	public static String findRowKeyValue(String rowKey, List<String> columns,  String[] values){
		
		
		for (int i = 0; i < values.length; i++) {
			
			if(rowKey.equals(columns.get(i)))return values[i];
			
		}
		return null;
		
	}

}
