package de.webdataplatform.client;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Pair;

import de.webdataplatform.log.Log;
import de.webdataplatform.log.StatisticLog;
import de.webdataplatform.settings.ColumnDefinition;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.settings.TableConfig;
import de.webdataplatform.settings.TableDefinition;
import de.webdataplatform.test.ZipfGenerator;

public class HbaseClient{


	
	
	private Configuration conf;
	
	private Log log;
	
	
	
	public HbaseClient(Log log){
		
		conf = NetworkConfig.getHBaseConfiguration(log);
		
		this.log = log;

	}

	

	

	
	
	public void deleteTable(String tableName) throws IOException {
		
		log.info(HbaseClient.class,"-----------------------");
		log.info(HbaseClient.class,"delete table "+tableName);
		log.info(HbaseClient.class,"-----------------------");
		
		
		HBaseAdmin admin = new HBaseAdmin(conf);
	
		if(admin.tableExists(tableName)){
			try {
			
				admin.disableTable(Bytes.toBytes(tableName));
				boolean isDisabled = admin.isTableDisabled(Bytes.toBytes(tableName));
				log.info(HbaseClient.class,"Table is disabled: " + isDisabled);
				
				boolean avail1 = admin.isTableAvailable(Bytes.toBytes(tableName));
				log.info(HbaseClient.class,"Table available: " + avail1);
			
				
				admin.deleteTable(Bytes.toBytes(tableName));
			
			} catch (Exception e) {
				
				log.error(HbaseClient.class, e);
			
			}
		}
		admin.close();

		
		

		

	}
	
	
	

	
	public void recreateTableFromSchema(String tableName, String schemaName) throws IOException{
		
		try {
			deleteTable(tableName);
		} catch (IOException e) {
			log.error(this.getClass(), e);
		}
		
		createTableFromSchema(tableName, schemaName);
		
		
	}

	
	public void createTableFromSchema(String tableName, String schemaName) throws IOException {

		
		log.info(HbaseClient.class,"-----------------------");
		log.info(HbaseClient.class,"create table:"+tableName+" from schema:"+schemaName);
		log.info(HbaseClient.class,"-----------------------");
		
		HBaseAdmin admin = new HBaseAdmin(conf);
		
		try {
			
			
			TableDefinition tableDefinition = TableConfig.getTableDefinition(schemaName);
			
			
			HTableDescriptor desc = new HTableDescriptor(Bytes.toBytes(tableName));
			

			HColumnDescriptor coldef = new HColumnDescriptor(Bytes.toBytes("colfam1"));
			desc.addFamily(coldef);
			

			
			admin.createTable(desc);
			
			boolean avail = admin.isTableAvailable(Bytes.toBytes(tableName));
			
			
			log.info(HbaseClient.class,"Table available: " + avail);
			log.info(HbaseClient.class,"Table enabled: " + admin.isTableEnabled(Bytes.toBytes(schemaName)));
			
		} catch (Exception e) {
			
			log.error(HbaseClient.class, e);
		
		}
		
		admin.close();
		

	}
	
	public void createTable(String tableName) throws IOException {
		
		List<String> strlst1 = new ArrayList<String>(Arrays.asList("colfam1"));
		createTable(tableName, strlst1);
		
	}
//	List<String> strlst1 = new ArrayList<String>(Arrays.asList("bla1","bla2","bla3","bla4"));

	
	public void createTable(String tableName, List<String> colFams) throws IOException {

		
		log.info(HbaseClient.class,"-----------------------");
		log.info(HbaseClient.class,"create table "+tableName);
		log.info(HbaseClient.class,"-----------------------");
		
		HBaseAdmin admin = new HBaseAdmin(conf);

		
		if(!admin.tableExists(tableName)){
			
			try {
				
				
				
				
				HTableDescriptor desc = new HTableDescriptor(Bytes.toBytes(tableName));
				
				
				
				for (String colFamName : colFams) {
					
					HColumnDescriptor coldef = new HColumnDescriptor(Bytes.toBytes(colFamName));
					desc.addFamily(coldef);
				}
				
				
				
				admin.createTable(desc);
				
				boolean avail = admin.isTableAvailable(Bytes.toBytes(tableName));
				
		//		admin.enableTable(Bytes.toBytes(tableName));
				
				log.info(HbaseClient.class,"Table available: " + avail);
				log.info(HbaseClient.class,"Table enabled: " + admin.isTableEnabled(Bytes.toBytes(tableName)));
				
			} catch (Exception e) {
				
				log.error(HbaseClient.class, e);
			
			}
		}
		
		admin.close();
		

	}
	
	public void recreateTable(String tableName, List<String> colfams) throws IOException{
		
		try {
			deleteTable(tableName);
		} catch (IOException e) {
			log.error(this.getClass(), e);
		}
		
		createTable(tableName, colfams);
		
		
	}
	
	public void recreateRangeSplitTable(TableDefinition tableDefinition, int numRegions) throws IOException{
		
		try {
			deleteTable(tableDefinition.getName());
		} catch (IOException e) {
			log.error(this.getClass(), e);
		}
		
		try {
			createRangeSplitTable(tableDefinition, tableDefinition.getNumRegions());
		} catch (Exception e) {
			log.error(this.getClass(), e);
		}
		
		
	}
	

	
	public void createRangeSplitTable(TableDefinition tableDefinition, int numRegions)throws Exception{
	
	
		if(tableDefinition == null){
			throw new Exception("table definition not found for table: ");
		}
		
		
		HBaseAdmin admin=null;
		try {
			admin = new HBaseAdmin(conf);
		} catch (MasterNotRunningException e) {
	
			log.error(HbaseClient.class, e);
		} catch (ZooKeeperConnectionException e) {
	
			log.error(HbaseClient.class, e);
		}
		
		if(!admin.tableExists(tableDefinition.getName())){
			
			HTableDescriptor desc = new HTableDescriptor(Bytes.toBytes(tableDefinition.getName()));
	
			
			for (String colFam : tableDefinition.getColumnFamilies()) {
				
				HColumnDescriptor coldef = new HColumnDescriptor(Bytes.toBytes(colFam));
				desc.addFamily(coldef);
			}
			

//			
			long numOfPrimaryKeys = tableDefinition.getPrimaryKey().getNumOfValues();
	
			if(numOfPrimaryKeys == 0){
				createTable(tableDefinition.getName());
				return;
			}
			
			byte[][] regions = createRegionArray(tableDefinition.getName(), numRegions, tableDefinition.getPrimaryKey().getPrefix(), numOfPrimaryKeys);
	
	
	
			try {
				admin.createTable(desc, regions);
			} catch (IOException e) {
			
				log.error(HbaseClient.class, e);
			}
			printTableRegions(conf, tableDefinition.getName());
		}else{
			log.info(HbaseClient.class, "Table "+tableDefinition.getName()+" already exists");
		}
	
	
}




	public byte[][] createRegionArray(String tableName, int regCount, String prefix, long numOfPrimaryKeys)throws Exception {

		
//		if(tableDefinition == null)throw new Exception("table definition not found for table: "+tableName+", cannot split keyrange");
//		
//		long numOfPrimaryKeys = tableDefinition.getPrimaryKey().getNumOfValues();
		
		long recordsPerRegion = numOfPrimaryKeys/regCount;
//		log.info(Client.class,"recordsPerRegion: "+recordsPerRegion);
		
		int digits = String.valueOf(numOfPrimaryKeys).length();
		
		byte[][] regions = new byte[regCount-1][];
		
		for (int i = 1; i < regCount; i++) {
			
		
			String rowKey=""; 
//			String prefix = tableDefinition.getPrimaryKey().getPrefix();	
			
			if(prefix != null && !prefix.equals(""))rowKey = prefix;
			
			

			for(int x = 0; x < (digits - String.valueOf((i*recordsPerRegion)).length());x++)rowKey+="0";
			rowKey += (i*recordsPerRegion);
				

			regions[i-1] = Bytes.toBytes(rowKey);
			
		}
		return regions;
	}
	

	
	
	private void printTableRegions(Configuration conf, String tableName){
	
	
		log.info(HbaseClient.class,"Printing regions of table: " + tableName);
	
	HTable table=null;
	try {
		table = new HTable(conf, Bytes.toBytes(tableName));
	} catch (IOException e) {
		
		log.error(HbaseClient.class, e);
	}
	
	Pair<byte[][], byte[][]> pair=null;
	try {
		pair = table.getStartEndKeys();
	} catch (IOException e) {

		log.error(HbaseClient.class, e);
	}
	
	for (int n = 0; n < pair.getFirst().length; n++) {
		
	byte[] sk = pair.getFirst()[n];
	
	byte[] ek = pair.getSecond()[n];
	
	log.info(HbaseClient.class,"[" + (n + 1) + "]" +
	" start key: " +
	(sk.length == 8 ? Bytes.toLong(sk) : Bytes.toStringBinary(sk)) +
	", end key: " +
	(ek.length == 8 ? Bytes.toLong(ek) : Bytes.toStringBinary(ek)));
	}
	
	try {
		table.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		log.error(HbaseClient.class, e);
	}
}	
	
	


	Random random;
	ZipfGenerator zipf;

	
	
	private String generateRowKey(String distribution, TableDefinition tableDefinition) {
		String rowKey="";

		long k = tableDefinition.getPrimaryKey().getStartRange();
		
		
		if(distribution.equals("uniform")){
			k+=ThreadLocalRandom.current().nextInt(new Long(tableDefinition.getPrimaryKey().getNumOfValues()).intValue());
		}
		if(distribution.equals("zipf")){
			
			k+=(zipf.next()*10+(ThreadLocalRandom.current().nextInt(10)));
			k+=zipf.next();
		}
		
		int digits = String.valueOf(tableDefinition.getPrimaryKey().getEndRange()).length();
		
		
		if(tableDefinition.getPrimaryKey().getPrefix() != null)rowKey = tableDefinition.getPrimaryKey().getPrefix();
		
		for(int x = 0; x < (digits - String.valueOf(k).length());x++)rowKey+="0";
		rowKey += k;
		return rowKey;
	}
	
	
	
	
	
//    int start = computeStart(getOrdersCount(), iteration, iterationNum)+computeStart(getOrdersCount()/iterationNum, client, clientNum);
//    int end = computeStart(getOrdersCount(), iteration, iterationNum)+computeEnd(getOrdersCount()/iterationNum, client, clientNum);
	
	
	public void fillBaseTableByFile(String tableName, String fileName, int start, int end)throws Exception{

		
		
			HTable baseTable = new HTable(conf, tableName);

			TableDefinition tableDefinition = TableConfig.getTableDefinition(tableName);
			
			
			if(tableDefinition == null){
				baseTable.close();
				throw new Exception("table definition not found for schema: "+tableName);
			}		
		
		    String csvFile = SystemConfig.CLIENT_TPCH_DIRECTORY +"/"+fileName;
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = "\\|";
	        int x =0;
	        
	        System.out.println("csvFile: "+csvFile);
	        
	        
	        System.out.println("start: "+start);
	        System.out.println("end: "+end);
	        
	        try {
	            br = new BufferedReader(new FileReader(csvFile));
	            while ((line = br.readLine()) != null) {

	            	if(x >= start && x <= end ){
	            	
		                // use comma as separator
		                String[] country = line.split(cvsSplitBy);
	
		                Put put = generatePutFromFile(tableDefinition.getColumns(), country);
		                
		                baseTable.put(put);
		                
	            	}
	                x++;

	                
	                if(x%10000 == 0)
	                	System.out.println("x: "+x);
	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            baseTable.close();
	        }
		
		
	}
	

	
	private Put generatePutFromFile(List<ColumnDefinition> columns, String[] values) {

		String rowKey="";
		
		Integer index=0,x=0;
		
		for (ColumnDefinition colDef : columns) {
			
			if(colDef.isPrimaryKey()){
				if(x==0)rowKey += values[index];
				else rowKey += "_"+values[index];
				x++;
			}
			index++;
		}
		
		Put put = new Put(Bytes.toBytes(rowKey));
		
		index=0;
		for (ColumnDefinition colDef : columns) {
			
			if(!colDef.isPrimaryKey()){
				put.add(Bytes.toBytes("colfam1"), Bytes.toBytes(colDef.getName()), Bytes.toBytes(values[index]));	
			}
			index++;
		}

		return put;
	}
	
	
	public void fillBaseTable(String baseTableName, int numOfOperations, String distribution)throws Exception{
		
		log.info(HbaseClient.class,"-----------------------");
		log.info(HbaseClient.class,"fill table:"+baseTableName);
		log.info(HbaseClient.class,"-----------------------");
		
		log.info(HbaseClient.class, "distribution: "+distribution);
		log.info(HbaseClient.class, "numOfOperations: "+numOfOperations);
		
		
		HTable baseTable = new HTable(conf, baseTableName);
//		baseTable.setAutoFlushTo(false);

		TableDefinition tableDefinition = TableConfig.getTableDefinition(baseTableName);
		
		if(tableDefinition == null){
			baseTable.close();
			throw new Exception("table definition not found for schema: "+baseTableName);
		}
		
		

		
		int sysoutCount=0;
		long startingTime = new Date().getTime();
		long creationTime=0;
		
		long numOfKeys = tableDefinition.getPrimaryKey().getNumOfValues();
		
		if(distribution.equals("uniform"))random = new Random(new Long(numOfKeys).intValue());
		if(distribution.equals("zipf")){
//			zipf = new ZipfGenerator(new Long(numOfKeys).intValue(),1);
			zipf = new ZipfGenerator(1000,1);
		}
		
		for (long i = 0; i < numOfOperations; i++) {
			
			
			sysoutCount++;	

//			long startCreationTime = new Date().getTime();
			
			String rowKey = generateRowKey(distribution, tableDefinition);
			
//			creationTime += new Date().getTime()-startCreationTime;
			
			Get get = new Get(Bytes.toBytes(rowKey));
			boolean exists=false;
			try {
				
//				exists = baseTable.exists(get);
				
				Result result = baseTable.get(get);
				String val = Bytes.toString(result.getValue(Bytes.toBytes(tableDefinition.getColumns().get(0).getFamily()), Bytes.toBytes(tableDefinition.getColumns().get(0).getName())));
				exists = (val != null);

				
				if(sysoutCount == SystemConfig.CLIENT_LOGINTERVAL){
					log.info(this.getClass(), "iteration: "+i+", key: "+rowKey+", exists:"+exists);
					log.info(this.getClass(), "took: "+(new Date().getTime()-startingTime)+" ms");
//					log.info(this.getClass(), "generation: "+(creationTime/SystemConfig.CLIENT_LOGINTERVAL)+" ms");
					startingTime = new Date().getTime();
					sysoutCount = 0;
					creationTime = 0;
				}

				if(!exists){
					
					Put put = generatePut(tableDefinition, rowKey);
//					baseTable.put(put);
					
					boolean check = baseTable.checkAndPut(Bytes.toBytes(rowKey), Bytes.toBytes(tableDefinition.getColumns().get(0).getFamily()), Bytes.toBytes(tableDefinition.getColumns().get(0).getName()), null, put);
					if(!check)i--;
					
				}else{
					
					int zahl = (int)(Math.random() * 2);
					switch(zahl){ 
						case 0: 
							Put update = generatePut(tableDefinition, rowKey);
//							baseTable.put(update);
							boolean check = baseTable.checkAndPut(Bytes.toBytes(rowKey), Bytes.toBytes(tableDefinition.getColumns().get(0).getFamily()), Bytes.toBytes(tableDefinition.getColumns().get(0).getName()), Bytes.toBytes(val), update);
							if(!check)i--;	
							
						break;
						case 1: 
							Delete delete = generateDelete(rowKey);
//							baseTable.delete(delete);
							check = baseTable.checkAndDelete(Bytes.toBytes(rowKey), Bytes.toBytes(tableDefinition.getColumns().get(0).getFamily()), Bytes.toBytes(tableDefinition.getColumns().get(0).getName()), Bytes.toBytes(val), delete);
							if(!check)i--;	
						break;
					}
					
					
				}
			} catch (IOException e) {
			
				log.error(this.getClass(), e);
				i--;
			}
			


		}

		
		baseTable.close();
		
	

	}









	public void executePut(String baseTableName)throws Exception{
		
		
		log.info(HbaseClient.class,"-----------------------");
		log.info(HbaseClient.class,"execute insert on:"+baseTableName);
		log.info(HbaseClient.class,"-----------------------");
		
		
		HTable baseTable = new HTable(conf, baseTableName);
		TableDefinition tableDefinition = TableConfig.getTableDefinition(baseTableName);
		
		if(tableDefinition == null)throw new Exception("table definition not found for schema: "+baseTableName);
		

		
		random = new Random();


		String rowKey = generateRowKey("uniform", tableDefinition);
			
		Get get = new Get(Bytes.toBytes(rowKey));
		
		boolean exists=true;
		int stop=0;
		
		while(exists){	
			try {
				exists = baseTable.exists(get);

				
				if(!exists){
					
					Put put = generatePut(tableDefinition, rowKey);
					baseTable.put(put);
					
					
				}
			} catch (IOException e) {

				log.error(this.getClass(), e);
			}
			stop++;
			if(stop==1000)break;

		}
	
		baseTable.close();


	}
	
	public void executeUpdate(String baseTableName)throws Exception{
		
		
		log.info(HbaseClient.class,"-----------------------");
		log.info(HbaseClient.class,"execute update on :"+baseTableName);
		log.info(HbaseClient.class,"-----------------------");
		
		
		HTable baseTable = new HTable(conf, baseTableName);
		TableDefinition tableDefinition = TableConfig.getTableDefinition(baseTableName);
		
		if(tableDefinition == null){
			baseTable.close();
			throw new Exception("table definition not found for schema: "+baseTableName);
		}
		

		Scan scan1 = new Scan();
		
		ResultScanner scanner1 = baseTable.getScanner(scan1);
		
		
		Result res = scanner1.next();
		
		if(res.isEmpty()){
			baseTable.close();
			throw new Exception("nothing to update: "+baseTableName);
		}
		else{
			
			Put put = generatePut(tableDefinition, Bytes.toString(res.getRow()));
			baseTable.put(put);
		}
			
	
		scanner1.close();	
		baseTable.close();


	}
	public void executeDelete(String baseTableName)throws Exception{
		
		log.info(HbaseClient.class,"-----------------------");
		log.info(HbaseClient.class,"execute delete on :"+baseTableName);
		log.info(HbaseClient.class,"-----------------------");
		
		
		HTable baseTable = new HTable(conf, baseTableName);
		TableDefinition tableDefinition = TableConfig.getTableDefinition(baseTableName);
		
		if(tableDefinition == null){
			baseTable.close();
			throw new Exception("table definition not found for schema: "+baseTableName);
		}
		

		Scan scan1 = new Scan();
		
		ResultScanner scanner1 = baseTable.getScanner(scan1);
		
		
		Result res = scanner1.next();
		
		if(res.isEmpty()){
			baseTable.close();
			throw new Exception("nothing to delete: "+baseTableName);
		}
		else{
			
			Delete delete = generateDelete(Bytes.toString(res.getRow()));
			baseTable.delete(delete);
		}
			
	
		scanner1.close();	
		baseTable.close();		
		
	}
	




	private Put generatePut(TableDefinition tableDefinition, String rowKey) {

		Put put = new Put(Bytes.toBytes(rowKey));
		
		for (ColumnDefinition columnDefinition : tableDefinition.getColumns()) {
			
			
			int digits = String.valueOf(columnDefinition.getEndRange()).length();
			
			int zahl = (int)(columnDefinition.getStartRange()+(Math.random() * columnDefinition.getNumOfValues() + 1));
			
			String value="";
			if(columnDefinition.getPrefix() != null)value = columnDefinition.getPrefix();
			
			for(int x = 0; x < (digits - String.valueOf(zahl).length());x++)value+="0";
			value+=zahl;

			
			put.add(Bytes.toBytes(columnDefinition.getFamily()), Bytes.toBytes(columnDefinition.getName()), Bytes.toBytes(value));	
			
		}
		

		return put;
	}
	
	


	private Delete generateDelete(String rowKey) {
		
		
		
		Delete delete = new Delete(Bytes.toBytes(rowKey));

		

		return delete;
	}	

	
	
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	/*
	private void fillJoinTables(String joinTableLeft, String joinTableRight, Configuration conf) throws IOException {
		
		log.info(Client.class,"-----------------------");
		log.info(Client.class,"fill join tables: joinTableLeft:"+joinTableLeft+", joinTableRight"+joinTableRight);
		log.info(Client.class,"-----------------------");
		
		HTable baseTableLeft = new HTable(conf, joinTableLeft);
		
		HTable baseTableRight = new HTable(conf, joinTableRight);
		
		long operationsPerClient = numOfOperations/numOfClients;
		
		log.info(this.getClass(), "operationsPerClient: "+operationsPerClient);

		
		Random random = new Random();
		ZipfDistribution zd = new ZipfDistribution(new Long(numOfPrimaryKeys).intValue(), 1); 
		ZipfDistribution zdx = new ZipfDistribution(new Long(numOfAggKeys).intValue(), 1); 
		
		int sysoutCount=0;
		for (long i = 0; i < operationsPerClient; i++) {
			sysoutCount++;	

			long k = 0;
			if(distribution.equals(UNIFORM))k=random.nextInt(new Long(numOfPrimaryKeys).intValue());
			if(distribution.equals(ZIPF))k=zd.sample();
			
			int digits = String.valueOf(numOfPrimaryKeys).length();
			String rowKey = "k";
			for(int j = 0; j < (digits - String.valueOf(k).length());j++)rowKey+="0";
			rowKey += k;
			
			
			long x = 0;
			if(distribution.equals(UNIFORM))k=random.nextInt(new Long(numOfAggKeys).intValue());
			if(distribution.equals(ZIPF))k=zdx.sample();
			
			int digitsAggKeys = String.valueOf(numOfAggKeys).length();
			String aggregationKey = "x";
			for(int j = 0; j < (digitsAggKeys - String.valueOf(x).length());j++)aggregationKey+="0";
			aggregationKey+=x;
			
			
			Get get = new Get(Bytes.toBytes(rowKey));
			boolean exists = baseTableLeft.exists(get);
			if(sysoutCount == 10000){
				log.info(this.getClass(), "iteration: "+i+", key: "+rowKey+", exists:"+exists);
				sysoutCount = 0;
			}
			
			boolean insertUpdate=true;
			if(!exists){
				
				
				Put put = generateInsertJoinLeft(rowKey, aggregationKey);
				baseTableLeft.checkAndPut(Bytes.toBytes(rowKey), Bytes.toBytes("colfam1"), Bytes.toBytes("aggregationKey"), null, put);
				
				
			}else{
				
				int zahl = (int)(Math.random() * 2);
				switch(zahl){ 
					case 0: Put update = generateUpdateJoinLeft(rowKey, aggregationKey);baseTableLeft.put(update);
						break;
					case 1: Delete delete = generateDeleteJoinLeft(rowKey);baseTableLeft.delete(delete);insertUpdate=false;
						break;
				}
				
				
			}
			
			if(insertUpdate){
				
				get = new Get(Bytes.toBytes(aggregationKey));
				exists = baseTableRight.exists(get);
				if(sysoutCount == 10000){
					log.info(this.getClass(), "iteration: "+i+", key: "+rowKey+", exists:"+exists);
					sysoutCount = 0;
				}
				
				if(!exists){
					
					
					Put put = generateInsertJoinRight(rowKey);
					baseTableRight.checkAndPut(Bytes.toBytes(rowKey), Bytes.toBytes("colfam1"), Bytes.toBytes("aggregationValue"), null, put);
					
					
				}else{
					
					Put put = generateUpdateJoinRight(rowKey);
					baseTableRight.checkAndPut(Bytes.toBytes(rowKey), Bytes.toBytes("colfam1"), Bytes.toBytes("aggregationValue"), null, put);
					
				}
				i++;
			}


		}

		
		baseTableLeft.close();
		baseTableRight.close();
	

	}
	
	

	
	
	
	private Put generateInsertJoinLeft(String rowKey, String aggregationKey) {
		
		int aggregationValue = (int)(Math.random() * numOfAggValues + 1);
		
		log.info(this.getClass(), "putting key left: "+rowKey);
		
		Put put = new Put(Bytes.toBytes(rowKey));
		put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("aggregationKey"), Bytes.toBytes(aggregationKey));	
		put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("aggregationValue"), Bytes.toBytes(aggregationValue+""));
		return put;
	}
	
	private Put generateUpdateJoinLeft(String rowKey, String aggregationKey) {
		
		int aggregationValue = (int)(Math.random() * numOfAggValues + 1);
		
		log.info(this.getClass(), "updating key left: "+rowKey);
		
		Put put = new Put(Bytes.toBytes(rowKey));
		put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("aggregationKey"), Bytes.toBytes(aggregationKey));	
		put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("aggregationValue"), Bytes.toBytes(aggregationValue+""));
		return put;
	}


	private Delete generateDeleteJoinLeft(String rowKey) {
		
		Delete delete = new Delete(Bytes.toBytes(rowKey));
		log.info(this.getClass(), "deleting key left: "+rowKey);

		return delete;
	}	

	private Put generateInsertJoinRight(String aggregationKey){
		
		Put put=null;

		
		int aggregationValue = (int)(Math.random() * numOfAggValues + 1);
		
		log.info(this.getClass(), "putting key right: "+aggregationKey);
		
		put = new Put(Bytes.toBytes(aggregationKey));
		put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("aggVal"), Bytes.toBytes(aggregationValue+""));
		
		
		
		return put;
	}
	
	private Put generateUpdateJoinRight(String rowKey) {
		
		
		log.info(this.getClass(), "updating key right: "+rowKey);
		int aggregationValue = (int)(Math.random() * numOfAggValues + 1);
		
		
		Put put = new Put(Bytes.toBytes(rowKey));
		put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("aggVal"), Bytes.toBytes(aggregationValue+""));
		return put;
	}

	
	
	
//	private void fillBaseTableViewDefinitions(String tableName, Configuration conf) throws IOException {
//		
//		log.info(Client.class,"-----------------------");
//		log.info(Client.class,"fill view definition table");
//		log.info(Client.class,"-----------------------");
//		
//		HTable table = new HTable(conf, tableName);
//
//		Put put = new Put(Bytes.toBytes("basetable"));
//		Put put2 = new Put(Bytes.toBytes("basetable_joinleft"));
//		Put put3 = new Put(Bytes.toBytes("basetable_joinright"));
//
//		
//		
//		log.info(Client.class, "creating view tables");
//
//		for (int i = 0; i < viewTableCount; i++) {
//			String viewTableType = viewTableTypes.get(i%viewTableTypes.size());
//
//			if(viewTableType.equals("count")){
//				put.add(Bytes.toBytes("colfam1"), Bytes.toBytes(viewTableType+(i/viewTableTypes.size())), Bytes.toBytes( ViewMode.AGGREGATION_COUNT.toString()+","+"aggregationKey"+","+"aggregationValue"));
//			}
//			if(viewTableType.equals("sum")){
//				put.add(Bytes.toBytes("colfam1"), Bytes.toBytes(viewTableType+(i/viewTableTypes.size())), Bytes.toBytes( ViewMode.AGGREGATION_SUM.toString()+","+"aggregationKey"+","+"aggregationValue"));
//			}
//			if(viewTableType.equals("min")){
//				put.add(Bytes.toBytes("colfam1"), Bytes.toBytes(viewTableType+(i/viewTableTypes.size())), Bytes.toBytes( ViewMode.AGGREGATION_MIN.toString()+","+"aggregationKey"+","+"aggregationValue"));
//			}
//			if(viewTableType.equals("max")){
//				put.add(Bytes.toBytes("colfam1"), Bytes.toBytes(viewTableType+(i/viewTableTypes.size())), Bytes.toBytes( ViewMode.AGGREGATION_MAX.toString()+","+"aggregationKey"+","+"aggregationValue"));
//			}
//			if(viewTableType.equals("selection")){
//				put.add(Bytes.toBytes("colfam1"), Bytes.toBytes(viewTableType+(i/viewTableTypes.size())), Bytes.toBytes(ViewMode.SELECTION.toString()+","+"aggregationValue,<,5"));
//			}
//			if(viewTableType.equals("join")){
//				put2.add(Bytes.toBytes("colfam1"), Bytes.toBytes(viewTableType+(i/viewTableTypes.size())), Bytes.toBytes(ViewMode.JOIN.toString()+",basetable_joinleft,basetable_joinright,aggregationKey"));
//				put3.add(Bytes.toBytes("colfam1"), Bytes.toBytes(viewTableType+(i/viewTableTypes.size())), Bytes.toBytes(ViewMode.JOIN.toString()+",basetable_joinleft,basetable_joinright,aggregationKey"));
//			}
//			
//		}
//		
//		if(viewTableTypes.contains("count") || viewTableTypes.contains("sum") || viewTableTypes.contains("min") || viewTableTypes.contains("max")|| viewTableTypes.contains("selection")){
//			
//			table.put(put);
//		}
//		if(viewTableTypes.contains("join")){
//			
//			table.put(put2);
//			table.put(put3);
//		}
//		table.close();
//
//	}	
	*/
	
	private Map<String, Integer> aggregationCountMap;
	
	private Map<String, Integer> aggregationSumMap;	
	
	private Map<String, Integer> aggregationMinMap;
	
	private Map<String, Integer> aggregationMaxMap;
	

	
	
	
	private void scanBaseTable(String tableName, Configuration conf) throws IOException {
		
		
		aggregationCountMap = new HashMap<String, Integer>();
		
		aggregationSumMap = new HashMap<String, Integer>();
		
		aggregationMinMap = new HashMap<String, Integer>();
		
		aggregationMaxMap = new HashMap<String, Integer>();
		
		long start = System.currentTimeMillis();
		
		HTable table = new HTable(conf, tableName);	
	
		System.out.println("Duration client setup in ms: " + (System.currentTimeMillis() - start));
		
		Scan scan1 = new Scan();
		
		start = System.currentTimeMillis();
		ResultScanner scanner1 = table.getScanner(scan1);
		
		
		
//		for (Result res : scanner1) {
//			System.out.print("Key: "+Bytes.toString(res.getRow())+",  ");
//			System.out.print("AggKey: "+Bytes.toString(res.getValue(Bytes.toBytes("colfam1"), Bytes.toBytes("aggregationKey")))+",  ");
//			System.out.print("AggValue: "+Bytes.toInt(res.getValue(Bytes.toBytes("colfam1"), Bytes.toBytes("aggregationValue"))));
//			System.out.println();
////			System.out.println(res);
//		}
//		scanner1.close();
//		
//		
		
		
		
//		InternalScanner scanner = environment.getRegion().getScanner(scan);
	
		
		int result = 0;
		try {
			
			List<KeyValue> curVals;
			Result done;
			do {
				
				curVals = new ArrayList<KeyValue>();
//				curVals.clear();
				done = scanner1.next();
				
				if(done != null){
				
					curVals = done.list();
//					System.out.println(curVals);
					
					Integer updateSumValue = 0;
					Integer updateCountValue = 0;
					Integer updateMinValue = Integer.MAX_VALUE;
					Integer updateMaxValue = Integer.MIN_VALUE;
					String aggregationKey="";
					
					boolean printKey=false;
					
					for(KeyValue keyValue : curVals){
						

						String keyString = Bytes.toString(keyValue.getKey());
						
						if(!printKey && !keyString.contains("rs")){
							System.out.println();
							System.out.print(Bytes.toString(keyValue.getRow())+"; ");
							printKey=true;
						}
						
						if(!keyString.contains("rs"))System.out.print(Bytes.toString(keyValue.getValue())+"; ");
						
						if(keyString.contains("aggregationKey")){
							
							
							aggregationKey = Bytes.toString(keyValue.getValue());
							
//							System.out.println("Key: "+aggregationKey);
							
							if(aggregationSumMap.containsKey(aggregationKey)){
								
								updateSumValue = aggregationSumMap.get(aggregationKey);
							}
							if(aggregationCountMap.containsKey(aggregationKey)){
								
								updateCountValue = aggregationCountMap.get(aggregationKey);
							}	
							if(aggregationMinMap.containsKey(aggregationKey)){
								
								updateMinValue = aggregationMinMap.get(aggregationKey);
							}	
							if(aggregationMaxMap.containsKey(aggregationKey)){
								
								updateMaxValue = aggregationMaxMap.get(aggregationKey);
							}
							
						}
						if(keyString.contains("aggregationValue") && !aggregationKey.equals("")){
	
//							System.out.println("Value:"+keyValue.getValue());
							
							Integer newValue=0;
							try{
								newValue = Integer.parseInt(Bytes.toString(keyValue.getValue()));
//								System.out.println("Value: "+Bytes.toString(keyValue.getValue()));
								
							}catch(Exception e){
								log.error(HbaseClient.class, e);
							}
								
								updateSumValue += newValue;
								
								updateCountValue += 1;
	
								
								aggregationCountMap.put(aggregationKey, updateCountValue);
								
								aggregationSumMap.put(aggregationKey, updateSumValue);
								
								if(newValue < updateMinValue)aggregationMinMap.put(aggregationKey, newValue);
								
								if(newValue > updateMaxValue)aggregationMaxMap.put(aggregationKey, newValue);
							
						}
						
					}
					
				
				
				}
				
//				result += countKeyValues ? curVals.size() : 1;
			} while (done != null);
			
			log.info(HbaseClient.class,"Duration scan in ms: " + (System.currentTimeMillis() - start));
		} finally {
			scanner1.close();
		}
		

		log.info(HbaseClient.class,"--------------Scan of basetable--------------");
		
		
		StatisticLog.direct("viewtablecount");
		
		
//		for (String key : aggregationCountMap.keySet()) {
//			
//			List<String> writeToLog = new ArrayList<String>();
//			writeToLog.add(key);
//			writeToLog.add(aggregationCountMap.get(key)+"");
//			writeToLog.add(aggregationSumMap.get(key)+"");
//			writeToLog.add(aggregationMinMap.get(key)+"");
//			writeToLog.add(aggregationMaxMap.get(key)+"");
//			
//			StatisticLog.info(writeToLog);
//			
//			
//		}
		log.info(HbaseClient.class,"--------------Result count view--------------");
		log.info(HbaseClient.class,"countresult: "+aggregationCountMap+"");
		log.info(HbaseClient.class,"  ");
		log.info(HbaseClient.class,"  ");
		
		log.info(HbaseClient.class,"--------------Result sum view--------------");
		log.info(HbaseClient.class,"sumresult: "+aggregationSumMap+"");
		log.info(HbaseClient.class,"  ");
		log.info(HbaseClient.class,"  ");
		
		log.info(HbaseClient.class,"--------------Result min view--------------");
		log.info(HbaseClient.class,"minresult: "+aggregationMinMap+"");
		log.info(HbaseClient.class,"  ");
		log.info(HbaseClient.class,"  ");
		
		log.info(HbaseClient.class,"--------------Result max view--------------");
		log.info(HbaseClient.class,"maxresult: "+aggregationMaxMap+"");
		log.info(HbaseClient.class,"  ");
		log.info(HbaseClient.class,"  ");
		
	}
	
	
	/*
private void scanBaseTableJoin(String tableLeft, String tableRight, Configuration conf) throws IOException {
		
		
		Map<String, List<String>> joinMap = new HashMap<String, List<String>>();
		

		
		long start = System.currentTimeMillis();
		
		HTable table = new HTable(conf, tableLeft);	
//		BaseTableService baseTableService = new BaseTableService(log);
		
		System.out.println("Duration client setup in ms: " + (System.currentTimeMillis() - start));
		
		Scan scan1 = new Scan();
		
		start = System.currentTimeMillis();
		ResultScanner scanner1 = table.getScanner(scan1);
	
		
		int result = 0;
		try {
			
			List<KeyValue> curVals;
			Result done;
			do {
				
				curVals = new ArrayList<KeyValue>();
				done = scanner1.next();
				
				if(done != null){
				
					curVals = done.list();
//					System.out.println(curVals);
					

					String aggregationKey="";
					
					String joinKey = null;
					List<String> joinValues = new ArrayList<String>();
					boolean printKey=false;
					
					
					for(KeyValue keyValue : curVals){
						

						String keyString = Bytes.toString(keyValue.getKey());
						
						if(!printKey && !keyString.contains("rs")){
							System.out.println();
							System.out.print(Bytes.toString(keyValue.getRow())+"; ");
							joinKey = Bytes.toString(keyValue.getRow());
							printKey=true;
							
							
						}
						
						if(!keyString.contains("rs"))System.out.print(Bytes.toString(keyValue.getValue())+"; ");
						
						if(keyString.contains("aggregationKey")){
							
							
							aggregationKey = Bytes.toString(keyValue.getValue());
							joinValues.add(aggregationKey);
							
							Result temp = baseTableService.get(tableRight, aggregationKey);

							if(temp != null){
								for (KeyValue keyValueTemp : temp.list()){
									
//									if(!Bytes.toString(keyValueTemp.getQualifier()).equals("aggregationKey")){
										joinValues.add(Bytes.toString(keyValueTemp.getValue()));
										System.out.print(Bytes.toString(keyValueTemp.getValue())+"; ");
//									}
								}
							}
						
						}
						if(keyString.contains("aggregationValue") && !aggregationKey.equals("")){
	
							
							Integer newValue=0;
							try{
								newValue = Integer.parseInt(Bytes.toString(keyValue.getValue()));
								joinValues.add(newValue+"");
//								System.out.println("Value: "+Bytes.toString(keyValue.getValue()));
								
							}catch(Exception e){
								log.error(Client.class, e);
							}
								
							
						}
						
					}
					joinMap.put(joinKey, joinValues);
					
				
				
				}
				
//				result += countKeyValues ? curVals.size() : 1;
			} while (done != null);
			
			log.info(Client.class,"Duration scan in ms: " + (System.currentTimeMillis() - start));
		} finally {
			scanner1.close();
		}
		

		log.info(Client.class,"--------------Scan of basetable--------------");
		
		
		StatisticLog.direct("viewtablecount");
		
		
//		for (String key : aggregationCountMap.keySet()) {
//			
//			List<String> writeToLog = new ArrayList<String>();
//			writeToLog.add(key);
//			writeToLog.add(aggregationCountMap.get(key)+"");
//			writeToLog.add(aggregationSumMap.get(key)+"");
//			writeToLog.add(aggregationMinMap.get(key)+"");
//			writeToLog.add(aggregationMaxMap.get(key)+"");
//			
//			StatisticLog.info(writeToLog);
//			
//			
//		}
		log.info(Client.class,"--------------Result join view--------------");
		log.info(Client.class,joinMap+"");
		log.info(Client.class,"  ");
		log.info(Client.class,"  ");
		

		
	}
	
	*/
	
	private void testPut(Configuration conf) throws IOException {
		
		
		HTable table=null;
		try {
			table = new HTable(conf, Bytes.toBytes("viewtable"));
		} catch (IOException e) {
			
			log.error(HbaseClient.class, e);
		}
		
		Put put = new Put(Bytes.toBytes("x1"));
		put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("aggregatedValue"), Bytes.toBytes(200));

		table.put(put);
		
		table.close();
		

		

	}
	
	
	private void testGet(Configuration conf) throws IOException {
		
		
		HTable table=null;
		try {
			table = new HTable(conf, Bytes.toBytes("viewtable"));
		} catch (IOException e) {
			
			log.error(HbaseClient.class, e);
		}
		
		Get get = new Get(Bytes.toBytes("x2"));

		get.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("aggregatedValue"));	
		
		Result result = table.get(get);
		
		byte[] val = result.getValue(Bytes.toBytes("colfam1"), Bytes.toBytes("aggregatedValue"));

		if(val != null){

			System.out.println("Value: " + Bytes.toInt(val));
		}
		
		table.close();

	}	
	
	private void scanViewTable(String tableName, Configuration conf) throws IOException {
		
		System.out.println("----------------------------------------------");
		System.out.println(tableName);
		System.out.println("----------------------------------------------");

		long start = System.currentTimeMillis();
		
		HTable table = new HTable(conf, tableName);	
	
		System.out.println("Duration client setup in ms: " + (System.currentTimeMillis() - start));
		
		Scan scan1 = new Scan();
		
		start = System.currentTimeMillis();
		ResultScanner scanner1 = table.getScanner(scan1);
		
		
	
		
		int result = 0;
	
			
			List<KeyValue> curVals;
			Result done;
			do {
				
				curVals = new ArrayList<KeyValue>();
//				curVals.clear();
				done = scanner1.next();
				
				if(done != null){
				
					curVals = done.list();
//					System.out.println(curVals);

					for(KeyValue keyValue : curVals){
						
						String keyString = Bytes.toString(keyValue.getKey());
//						System.out.println(keyString);

						
						if(keyString.contains("aggregationValue")){
							
							System.out.println("Key: "+Bytes.toString(keyValue.getRow())+", Value: "+Bytes.toInt(keyValue.getValue()));
//							System.out.println("Value: "+Bytes.toInt(keyValue.getValue()));
							
							
							
							
							
							
						}	
					}
				}
			} while (done != null);	
	}







}
