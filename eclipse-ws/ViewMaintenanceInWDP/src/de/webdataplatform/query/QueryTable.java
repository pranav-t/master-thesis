package de.webdataplatform.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import de.webdataplatform.log.Log;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.view.TableService;

public class QueryTable {


	
	private TableService tableService;
	
	
	private Log log; 
	

	private Map<String, String> rawQueries= new HashMap<String, String>();
	
	
	private static String QUERY_TABLE_NAME="query_table";
	
	
	public QueryTable(Log log) {

		this.log = log;
		
		
	}
	

	
	public void generateQueryTable() throws Exception{
		
		
		Configuration conf = NetworkConfig.getHBaseConfiguration(log);

		HBaseAdmin admin = new HBaseAdmin(conf);
		
		if(!admin.tableExists(QUERY_TABLE_NAME)){
			
			log.info(this.getClass(),"-----------------------");
			log.info(this.getClass(),"creating query table");
			log.info(this.getClass(),"-----------------------");
			
			
			HTableDescriptor desc = new HTableDescriptor(Bytes.toBytes(QUERY_TABLE_NAME));
			
			HColumnDescriptor coldef = new HColumnDescriptor(Bytes.toBytes("colfam1"));
			desc.addFamily(coldef);
			
			admin.createTable(desc);
		}

		admin.close();
	}
	
	
	public void addQuery(String queryName, String query) throws Exception{
		
		log.info(this.getClass(),"-----------------------");
		log.info(this.getClass(),"add query to table");
		log.info(this.getClass(),"-----------------------");
		
		log.info(this.getClass(),"queryName: "+queryName);
		log.info(this.getClass(),"query: "+query);
//		Put put2 = new Put(Bytes.toBytes("basetable_joinleft"));
//		Put put3 = new Put(Bytes.toBytes("basetable_joinright"));

		rawQueries.put(queryName, query);
		
		Configuration conf = NetworkConfig.getHBaseConfiguration(log);
		
		HTable table = new HTable(conf, QUERY_TABLE_NAME);						
			
		Put put = new Put(Bytes.toBytes(queryName));
			
		put.add(Bytes.toBytes("colfam1"), Bytes.toBytes("c1"), Bytes.toBytes(query));
		
		table.put(put);
				
		table.close();

	}
	
	public void removeQuery(String queryName) throws Exception{
		
		log.info(this.getClass(),"-----------------------");
		log.info(this.getClass(),"remove query from table");
		log.info(this.getClass(),"-----------------------");
		
		log.info(this.getClass(),"queryName: "+queryName);

		rawQueries.remove(queryName);
		
		Configuration conf = NetworkConfig.getHBaseConfiguration(log);
		
		HTable table = new HTable(conf, QUERY_TABLE_NAME);						
			
		Delete delete = new Delete(Bytes.toBytes(queryName));
			
		table.delete(delete);
				
		table.close();

	}
	
	
	public void loadQueries(){


		log.info(this.getClass(),"--------------------");
		log.info(this.getClass()," loading query table ");
		log.info(this.getClass(),"---------------------");
		
		rawQueries = new HashMap<String, String>();
		if(tableService == null)tableService = new TableService(log);
		List<Result> results = tableService.scan(QUERY_TABLE_NAME);
	
		for(Result result : results){
			
			String key =Bytes.toString(result.getRow());
			
			for(KeyValue keyValue : result.list()){
				
				rawQueries.put(key, Bytes.toString(keyValue.getValue()));
				log.info(this.getClass(),"query:"+key+", sql:"+Bytes.toString(keyValue.getValue()));
			}
		}
		if(results == null || results.size()==0){
			
		}
			
//		tableService.close();
		
		
	}



	public Map<String, String> getRawQueries() {
		return rawQueries;
	}



	public void setRawQueries(Map<String, String> queries) {
		this.rawQueries = queries;
	}





	

}
