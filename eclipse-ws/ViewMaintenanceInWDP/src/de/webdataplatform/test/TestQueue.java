package de.webdataplatform.test;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.regionserver.wal.HLog;

import de.webdataplatform.basetable.BaseTableOperation;
import de.webdataplatform.log.Log;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.regionserver.WALReader;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.Component;

public class TestQueue{

	
	

	
//	private static final String[] DATA = {
//		"jetzt","testen","wir","das","wal","von",
//		"hadoop"
//	};
	
	public static void main(String[] args) throws IOException {
		

		
		Queue<BaseTableOperation> incomingUpdates = new ConcurrentLinkedQueue<BaseTableOperation>();
		

		
		
		long start = System.nanoTime();
		incomingUpdates.poll();
		
		System.out.println("Time: "+(System.nanoTime()-start)/1000000);
		
		
		
	}
	
	



	
	
	
	
	
	
	
	public static BaseTableOperation generateBtu(String table, String key, String col1, String val1, String col2,  String val2, String col3, String val3){
		
		Map<String, String> cols = new HashMap<String, String>();
		Map<String, String> colFams = new HashMap<String, String>();
		
		cols.put(col1, val1);
		cols.put(col2, val2);
		cols.put(col3, val3);
		colFams.put(col1, "colfam1");
		colFams.put(col2, "colfam1");
		colFams.put(col3, "colfam1");
		
		
		BaseTableOperation btu = new BaseTableOperation(table, "rs1", "regionxy", 100+"",new Date().getTime()+"", key, "Put", cols, colFams);
		
		return btu;
		
	}










	
}
