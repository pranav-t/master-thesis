package de.webdataplatform.settings;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import de.webdataplatform.log.Log;

public class TableConfig {


	
	
	public static List<TableDefinition> TABLEDEFINITIONS;
	
	public static TableDefinition getTableDefinition(String name){
		
		if(TABLEDEFINITIONS==null)return null;
		for (TableDefinition tableDefinition : TABLEDEFINITIONS) {
			
			
			if(tableDefinition.getName().equals(name))return tableDefinition;
			
		}
		return null;
	}
	
	public static void load(Log log) {
	
		
		
		
		try
		{
			
//			log.info(TableConfig.class, "Loading table config:");
			
			XMLConfiguration config = new XMLConfiguration();
			config.setDelimiterParsingDisabled(true);
			config.load("VMTableConfig.xml");
   

		    TableConfig.TABLEDEFINITIONS = new ArrayList<TableDefinition>();
		    List<String> tabledefs = config.getList("tableconfig.table.name");

		    
//		    log.info(TableConfig.class, tabledefs.size()+" tables found");
		    
		    for (int i = 0; i < tabledefs.size(); i++) {
			

		   
		    	
			    String name = config.getString("tableconfig.table("+i+").name");
			    
			    
			    
//			    String keyName = config.getString("tableconfig.table("+i+").primaryKey.name");
//			    String keyPrefix = config.getString("tableconfig.table("+i+").primaryKey.prefix");
//			    String keyStartRange = config.getString("tableconfig.table("+i+").primaryKey.startRange");
//			    String keyEndRange = config.getString("tableconfig.table("+i+").primaryKey.endRange");
//			    
//			    KeyDefinition keyDef = new KeyDefinition(keyName, keyPrefix, Long.parseLong(keyStartRange), Long.parseLong(keyEndRange));
			    
			    
			    String nRegions = config.getString("tableconfig.table("+i+").numRegions");
			    int numRegions=0;
			    if(nRegions != null && !nRegions.equals(""))numRegions=Integer.parseInt(nRegions);
			    
			    			    
			    List<String> coldefs = config.getList("tableconfig.table("+i+").column.name");
			    			    
			    List<ColumnDefinition> colDefs = new ArrayList<ColumnDefinition>();
			    
			    for (int x = 0; x < coldefs.size(); x++) {
			    	
				    String colName = config.getString("tableconfig.table("+i+").column("+x+").name");
				    String colFamily = config.getString("tableconfig.table("+i+").column("+x+").family");
				    String colPrefix = config.getString("tableconfig.table("+i+").column("+x+").prefix");
				    String colStartRange = config.getString("tableconfig.table("+i+").column("+x+").startRange");
				    String colEndRange = config.getString("tableconfig.table("+i+").column("+x+").endRange");
				    String colPrimaryKey = config.getString("tableconfig.table("+i+").column("+x+").primaryKey");
				    
				    ColumnDefinition colDef = new ColumnDefinition(colName, colFamily, colPrefix, (colStartRange!=null)?Long.parseLong(colStartRange):null, (colEndRange!=null)?Long.parseLong(colEndRange):null, (colPrimaryKey!=null)?Boolean.parseBoolean(colPrimaryKey):null);
				    
				    colDefs.add(colDef);
		    
				    
			    }
			    
			    TableDefinition tableDefinition = new TableDefinition(name, colDefs, numRegions);
			    
			    log.info(TableConfig.class, tableDefinition.toString());
			    log.info(TableConfig.class, "Key: "+tableDefinition.getPrimaryKey().toString());
			    
			    TableConfig.TABLEDEFINITIONS.add(tableDefinition);
			    
			    

			}

		    
//		    
		    
		    
		}
		catch(ConfigurationException cex)
		{
			
			log.error(TableConfig.class, cex);
			cex.printStackTrace();
			System.exit(0);
		} 
		

//		log.info(TableConfig.class, "--------------------------------------------------------------");
  



	}
	
	public static void main(String[] args){
//		 load();
	}
	

}
