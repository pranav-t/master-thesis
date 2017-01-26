package de.webdataplatform.test;

import org.apache.hadoop.hbase.util.Bytes;

import de.webdataplatform.client.HbaseClient;
import de.webdataplatform.log.Log;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.settings.TableConfig;

public class TestHClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Log log = new Log("evaluation.log");

		SystemConfig.load(log);
		NetworkConfig.load(log);
		TableConfig.load(log);
		
		HbaseClient hbaseClient = new HbaseClient(log);
		
		
		try {
//			for (byte[] region : hbaseClient.createRegionArray("bt1", 10)){
//				
//				System.out.println(Bytes.toString(region));
//			}
		
//			hbaseClient.recreateTableFromSchema("bt1", "bt1");
			
//			hbaseClient.fillBaseTable("bt1", 20000, "uniform");
			
//			hbaseClient.fillBaseTableTPCH("orders.tbl", 2, 10, 1, 10);
//			
			
//			HTable baseTable = new HTable( NetworkConfig.getHBaseConfiguration(log), "bt1");
//			
//			Put put = new Put(Bytes.toBytes("k2"));
//			
//			put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("c1"), Bytes.toBytes("xxx"));	
//			
//				
//			boolean check =	baseTable.checkAndPut(Bytes.toBytes("k2"), Bytes.toBytes("colfam1"), Bytes.toBytes("c1"), null, put);
//			
//			System.out.println("check: "+check);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

	}

}
