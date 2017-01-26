package de.webdataplatform.settings;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import de.webdataplatform.log.Log;

public class EvaluationConfig {


	
	public static List<String> BASE_TABLES;
	
	public static List<String> TASKS;
	
	public static List<String> QUERIES;
	


	public static void load(Log log) {	
		
		try
		{
			
//			log.info(QueryConfig.class, "Loading query config:");
			
			XMLConfiguration config = new XMLConfiguration();
			config.setDelimiterParsingDisabled(true);
			config.load("VMEvaluationConfig.xml");
   

		    EvaluationConfig.BASE_TABLES = config.getList("evalconfig.basetables.table");
//		    for (String baseTable : EvaluationConfig.BASE_TABLES) {
//	    	 log.info(EvaluationConfig.class, baseTable);
//			}
		    
		    EvaluationConfig.TASKS = config.getList("evalconfig.tasks.task");
//		    for (String task : EvaluationConfig.TASKS) {
//		    	 log.info(EvaluationConfig.class, task);
//			}
		    
		    EvaluationConfig.QUERIES = config.getList("evalconfig.queries.query");
//		    for (String query : EvaluationConfig.QUERIES) {
//		    	 log.info(EvaluationConfig.class, query);
//			}	    
		    
		    
//		    log.info(QueryConfig.class, QueryConfig.QUERIES.size()+" queries found");

		    
		}
		catch(ConfigurationException cex)
		{
			
			log.error(EvaluationConfig.class, cex);
			cex.printStackTrace();
			System.exit(0);
		} 
		

//		log.info(QueryConfig.class, "--------------------------------------------------------------");
  



	}
	
	public static void main(String[] args){
//		 load();
	}
	

}
