package de.webdataplatform.query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.webdataplatform.client.HbaseClient;
import de.webdataplatform.graph.Graph;
import de.webdataplatform.log.Log;
import de.webdataplatform.parser.Parser;
import de.webdataplatform.parser.Planner;
import de.webdataplatform.parser.SQL;
import de.webdataplatform.settings.TableConfig;
import de.webdataplatform.settings.TableDefinition;
import de.webdataplatform.system.Table;
import de.webdataplatform.view.ViewTable;

public class QueryManager {



	private Log log;
	
	private QueryTable queryTable;

	private List<Query> queries;
	
	private HbaseClient hbaseClient;
	

	
	
	public QueryManager(Log log) {
		super();
		this.log = log;
		
        
		queryTable = new QueryTable(log);
		

	}
	
	public boolean addQuery(String queryName, String queryString){
		
		try {
			queryTable.addQuery(queryName, queryString);
		} catch (Exception e) {

			log.error(this.getClass(), e);
			return false;
		}
		
		Query query = parseQuery(queryName, queryString);
		
		log.info(this.getClass(), "Adding query: "+query.toString());
		
		queries.add(query);
		
		createViewTables(query);

		return true;
	}
	
	public boolean deleteQuery(String queryName){
		
		try {
			queryTable.removeQuery(queryName);
		} catch (Exception e) {
			
			log.error(this.getClass(), e);
			return false;
		}

		Query query = getQuery(queryName);
		
		deleteViewTables(query);

		queries.remove(query);
		
		return true;
		
	}
	
	public boolean reloadQuery(String queryName){
		

		Query query = getQuery(queryName);
		
		recreateViewTables(query);
		
		return true;
		
	}

	public Query getQuery(String queryName){
		
		for (Query query : queries) {
			if(query.getQueryName().equals(queryName))return query;
		}
		
		return null;
		
	}
	
	public void loadQueries(){
		
		queryTable.loadQueries();
		queries=new ArrayList<Query>();
		queries.addAll(parseQueries(queryTable.getRawQueries()));
	}
	


	private List<Query> parseQueries(Map<String, String> rawQueries) {
		
		
		log.info(this.getClass(), "--------------------");
		log.info(this.getClass(), "Parse queries");
		log.info(this.getClass(), "--------------------");		
		
		List<Query> result = new ArrayList<Query>();

		
		for (String name : rawQueries.keySet()) {
			
			Query query = parseQuery(name, rawQueries.get(name));
			result.add(query);
			
		}

		return result;
		
		
	}
	
	
	
	
	private Query parseQuery(String queryName, String queryString) {
		
		
		log.info(this.getClass(), "--------------------");
		log.info(this.getClass(), "Parse query");
		log.info(this.getClass(), "--------------------");		
	
		Parser.log = log;
		Planner.log = log;
		
		SQL sql = Parser.parse(queryName, queryString);
		
		Planner.initialize(queryName);
		Planner.plan(queryName, sql);
		
		Graph queryDAG = Planner.getQueryDAG();
		
		Query query = new Query(queryName, queryDAG);
		
		log.info(this.getClass(), query.getQueryDAG().toString());

		return query;
		
		
	}
	
	public boolean isQueryAffected(String baseTableName){
		
		
		for (Query query : queries) {

			if(isTableTracked(baseTableName, query))return true;
		}
		return false;
	}

	
	public List<Query> getAffectedQueries(String tableName, boolean baseTableOnly){
		
		List<Query> result = new ArrayList<Query>();
		
		for (Query query : queries) {
			
			if(baseTableOnly){
				if(isBaseTableTracked(tableName, query))result.add(query);
			}else{
				if(isTableTracked(tableName, query))result.add(query);
			}
		}
		return result;
	}
	

	
	public boolean isTableTracked(String tableName, Query query){
		
		
		List<Table> tables = query.getQueryDAG().getTrackedTables();
		
		for (Table table : tables) {
			if(table.getTableName().equals(tableName))return true;
		}
		
		return false;
		
	}
	
	
	
	public boolean isBaseTableTracked(String baseTableName, Query query){
		
		
		List<Table> tables = query.getQueryDAG().getBaseTables();
		
		for (Table table : tables) {
			if(table.getTableName().equals(baseTableName))return true;
		}
		
		return false;
		
	}
	
	
	


	
	
	
	private void createViewTables(Query query){

		
			if(hbaseClient == null){
				
				hbaseClient = new HbaseClient(log);
			}
		
			List<ViewTable> tables = query.getQueryDAG().getViewTables();
			
			log.info(this.getClass(), "Creating view table: "+tables);
			
			for (ViewTable table : tables) {
				
				
				TableDefinition tableDefinition = table.getTableDefinition();
				
				try {
					
					if(tableDefinition == null)throw new Exception("Table Definition of view table: "+table+" is null");
					
					hbaseClient.createRangeSplitTable(tableDefinition, tableDefinition.getNumRegions());
					
				} catch (IOException e) {
			
					log.error(this.getClass(), e);
				} catch (Exception e) {
	
					log.error(this.getClass(), e);
				}
			}


	}	
	
	private void recreateViewTables(Query query){

		
		if(hbaseClient == null){
			
			hbaseClient = new HbaseClient(log);
		}
	
		List<ViewTable> tables = query.getQueryDAG().getViewTables();
		
		log.info(this.getClass(), "Recreating view table: "+tables);
		
		for (ViewTable table : tables) {
			
			TableDefinition tableDefinition = table.getTableDefinition();
			try {
				hbaseClient.recreateRangeSplitTable(tableDefinition, tableDefinition.getNumRegions());
			} catch (IOException e) {
		
				e.printStackTrace();
			}
		}

	}
	
	private void deleteViewTables(Query query){

		
		if(hbaseClient == null){
			
			hbaseClient = new HbaseClient(log);
		}
	
		List<ViewTable> tables = query.getQueryDAG().getViewTables();
		
		log.info(this.getClass(), "Deleting view table: "+tables);
		
		for (Table table : tables) {
			
			try {
				hbaseClient.deleteTable(table.getTableName());
			} catch (IOException e) {
		
				e.printStackTrace();
			}
		}

	}	
	
//	private void createViewTables(){
//		
//		if(hbaseClient == null){
//			
//			hbaseClient = new HbaseClient(log);
//		}
//		for (Query query : queries) {
//			
//			List<ViewTable> tables = query.getQueryDAG().getViewTables();
//			
//			for (ViewTable table : tables) {
//				
//				try {
//					hbaseClient.createTable(table.getTableName(), table.getColFams());
//				} catch (IOException e) {
//			
//					e.printStackTrace();
//				}
//			}
//			
//		}
//
//	}	
		
	


	public QueryTable getQueryTable() {
		return queryTable;
	}

	public void setQueryTable(QueryTable queryTable) {
		this.queryTable = queryTable;
	}

	public List<Query> getQueries() {
		return queries;
	}


	public void setQueries(List<Query> queries) {
		this.queries = queries;
	}		
	
	

	
	

}
