package de.webdataplatform.settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import de.webdataplatform.log.Log;

public class TaskConfig {


	
	
	public static List<Task> TASKS;
	
	public static Task getTask(String name){
		
		Task result=null;
		
		for (Task task : TASKS) {
			
			
			if(task.getName().equals(name))result=task;
			
		}
		return result;
	}
	
	public static Map<String, List<Task>> getTaskGroups(){
		
		Map<String, List<Task>> taskGroups = new HashMap<String, List<Task>>();
		
		for (Task task : TaskConfig.TASKS) {
	
			if(!taskGroups.containsKey(task.getName()))taskGroups.put(task.getName(), new ArrayList<Task>());
			taskGroups.get(task.getName()).add(task);
			
		}
		return taskGroups;
	}

	public static void load(Log log) {	
		
		try
		{
			
//			log.info(TaskConfig.class, "Loading task config:");
			
			XMLConfiguration config = new XMLConfiguration();
			config.setDelimiterParsingDisabled(true);
			config.load("VMTaskConfig.xml");
   

		    TaskConfig.TASKS = new ArrayList<Task>();
		    List<String> tabledefs = config.getList("taskconfig.task.name");

//		    log.info(TaskConfig.class, tabledefs.size()+" tasks found");
		    
		    for (int i = 0; i < tabledefs.size(); i++) {
			

		    	Task task = null;
		    	
			    String name = config.getString("taskconfig.task("+i+").name");
			    String table = config.getString("taskconfig.task("+i+").table");
			    String type = config.getString("taskconfig.task("+i+").type");

			    if(type.equals("create")){
			    	
			    	String numRegions = config.getString("taskconfig.task("+i+").numRegions");
			    	task = new TaskCreate(name, table, type, Integer.parseInt(numRegions));
			    	
			    }
			    if(type.equals("delete")){
			    	
			    	task = new TaskDelete(name, table, type);
			    	
			    }
			    if(type.equals("fill")){
			    	
			    	String numOperations = config.getString("taskconfig.task("+i+").numOperations");
			    	String distribution = config.getString("taskconfig.task("+i+").distribution");
			    	
			    	task = new TaskFill(name, table, type,  Integer.parseInt(numOperations), distribution);
			    	
			    }	
			    if(type.equals("file")){
			    	
			    	String fileName = config.getString("taskconfig.task("+i+").fileName");
			    	String count = config.getString("taskconfig.task("+i+").count");
			    	String start = config.getString("taskconfig.task("+i+").start");
			    	String end = config.getString("taskconfig.task("+i+").end");
			    	
			    	
			    	task = new TaskFile(name, table, type, fileName, Integer.parseInt(count), Integer.parseInt(start), Integer.parseInt(end));
			    	
			    }	
			    
			    
//			    log.info(TaskConfig.class, task.toString());
			    
			    TaskConfig.TASKS.add(task);
			    
			    

			}
		    
		}
		catch(ConfigurationException cex)
		{
			
			log.error(TaskConfig.class, cex);
			cex.printStackTrace();
			System.exit(0);
		} 
		

//		log.info(TaskConfig.class, "--------------------------------------------------------------");
  



	}
	
	public static void main(String[] args){
//		 load();
	}
	

}
