package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.webdataplatform.basetable.BaseTable;
import de.webdataplatform.graph.Graph;
import de.webdataplatform.graph.MaintenancePlanUtil;
import de.webdataplatform.graph.Node;
import de.webdataplatform.graph.TableNode;
import de.webdataplatform.log.Log;
import de.webdataplatform.query.Query;
import de.webdataplatform.settings.TableConfig;
import de.webdataplatform.settings.TableDefinition;
import de.webdataplatform.view.operation.Delta;
import de.webdataplatform.view.operation.PreAggregation;
import de.webdataplatform.view.operation.Projection;
import de.webdataplatform.view.operation.ReverseJoin;
import de.webdataplatform.view.operation.Selection;
import de.webdataplatform.view.operation.Sort;
import de.webdataplatform.view.operation.ViewOperation;

public class Planner {


	public static Log log;
	
	
	private static Graph queryDAG;
	private static int numViews;
	private static String queryName;
	

	
	


	public static Graph getQueryDAG() {
		return queryDAG;
	}

	public static void setQueryDAG(Graph queryDAG) {
		Planner.queryDAG = queryDAG;
	}

	public static String generateViewName(){
		
		String result = queryName+"_"+numViews;
		numViews++;
		
		return result;
		
		
	}
	
//	private static NodeMap nodeMap;
	
	public static void initialize(String name){
		queryName = name;
		numViews = 0;
		queryDAG = new Graph();
	}
	
	public static NodeMap plan(String name, SQL sql){
//		
		NodeMap nodeMap = new NodeMap();
		
		log.info(Planner.class, "-----------------------------");
		log.info(Planner.class, "Planning query");
		log.info(Planner.class, "-----------------------------");
		
//		Query query = null;



		
		//create BASE NODES  
		log.info(Planner.class, "Creating base nodes");
		try {
			createBaseNodes(nodeMap, sql.getFrom());
		} catch (Exception e) {

			log.error(Planner.class, e);
		}
		log.info(Planner.class, "result: "+nodeMap);
		log.info(Planner.class, "------------------------");
		
		if(sql.getWhere() != null){

			log.info(Planner.class, "Creating selection and join nodes");
			Map<IPredicate, Set<String>> selectionMap = sql.getWhere().getEvaluationMap();
			
			//create SELECTION NODES
			createSelectionNodes(nodeMap, selectionMap);	
			log.info(Planner.class, "result: "+nodeMap);
			log.info(Planner.class, "------------------------");
		
			// create JOIN NODES
			List<Condition> joinConditions = sql.getWhere().getJoinConditions();
			if(joinConditions.size() > 0){
				log.info(Planner.class, "Creating join nodes");
				createJoinNodes(nodeMap, sql.getSelect(), joinConditions, selectionMap);
				log.info(Planner.class, "------------------------");
				
			}	
		}	

		
	
//		// CREATE SELECTION NODES
//		log.info(Planner.class, "Creating join nodes");
//		if(sql.getJoinConditions().size() > 0){
//			tableNode = createJoinNodes(sql.getJoinConditions());
//		}			
		
	
		// CREATE AGGREGATION NODES
		if(sql.getGroupBy().hasColumns()){
			log.info(Planner.class, "Creating aggregation nodes");
			createGroupByNode(nodeMap, sql.getSelect(), sql.getGroupBy());
			log.info(Planner.class, "------------------------");
		}
		
//		
//		//Parsing HAVING
		if(sql.getHaving()!=null){
			
			
			createSelectionNodes(nodeMap, sql.getHaving().getEvaluationMap());	
			log.info(Planner.class, "result: "+nodeMap);
			log.info(Planner.class, "------------------------");
			
//			List<String[]> predicateTokens =  new ArrayList<String[]>();
//			predicateTokens.add(having);
//			Selection selection = new Selection(predicateTokens, false);
//			selection.setSelectivity(0.7f);
//			tableNode = MaintenancePlanUtil.createView(queryDAG, tableNode, generateViewName(), selection, Arrays.asList(new String[]{"colfam1"}));
			
			
		}	
		
		//Parsing ORDER BY
		
		if(sql.getOrderBy().hasColumns()){
			
			log.info(Planner.class, "Creating order by nodes");
			createOrderByNodes(nodeMap, sql.getOrderBy());
			log.info(Planner.class, "------------------------");
			
			
		}	
		//Parsing SELECT
		if(sql.getSelect().hasColumns()){
			
			log.info(Planner.class, "Creating projection nodes");
			createProjectionNodes(nodeMap, sql.getSelect(), sql.getFrom());
			log.info(Planner.class, "------------------------");
			
		}	
//		log.info(Planner.class, "Graph: "+queryDAG);
		return nodeMap;
		
//		return query;
	}
	

	public static void createSelectionNodes(NodeMap nodeMap, Map<IPredicate, Set<String>> predicates){
		
		System.out.println("predicates: "+predicates);
		System.out.println("-----------");
		List<IPredicate> processedPredicates = new ArrayList<IPredicate>();
		
		for (IPredicate predicate : predicates.keySet()) {
			
			Set<String> tables = predicates.get(predicate);
			Set<String> nodeMapTables = nodeMap.getTables(tables);
			
			if(nodeMapTables != null){
			
				Node node = nodeMap.getNode(tables);
				
				boolean rowKeyChanged = (((TableNode)node).getTable() instanceof BaseTable);
				
				if(node != null){
					
					// create SELECTION view 		
					String viewName = generateViewName();
					Selection selection = new Selection(predicate, rowKeyChanged);
					selection.setSelectivity(0.5f);
					
					TableDefinition tableDefinition = ((TableNode)node).getTable().getTableDefinition();
					if(tableDefinition!= null){
						tableDefinition = ((TableNode)node).getTable().getTableDefinition().copy();
						tableDefinition.setName(viewName);
						
					}
					
					
					node = MaintenancePlanUtil.createView(queryDAG, node, viewName, selection,  Arrays.asList(new String[]{"colfam1"}), tableDefinition);
					nodeMap.putNode(tables, node);
					System.out.println("Tables: "+tables);
					
					processedPredicates.add(predicate);
				}
			}
			
		}
		for (IPredicate iPredicate : processedPredicates) {
			predicates.remove(iPredicate);
		}
		System.out.println("predicates-after: "+predicates);
		
		
		
	}
	
	
	
	public static void createBaseNodes(NodeMap nodeMap, TableSequence from) throws Exception{
		
		
		for (Table table : from.getTables()) {
			
			ISource source = table.getSource();
			
			
			
			if(source instanceof TableName){
				
				if(table.getAlias() != null){
					
					TableDefinition tableDefinition = TableConfig.getTableDefinition(table.getAlias());
					TableNode tableNode = MaintenancePlanUtil.createBaseTable(queryDAG, table.getAlias(), tableDefinition);
					nodeMap.putNode(table.getAlias(), tableNode);
				}else{
					TableName tableName = (TableName)source;
					TableDefinition tableDefinition = TableConfig.getTableDefinition(tableName.getName());
					TableNode tableNode = MaintenancePlanUtil.createBaseTable(queryDAG, tableName.getName(), tableDefinition);
					nodeMap.putNode(tableName.getName(), tableNode);
				}
			}
			
			if(source instanceof SQL){
				
				SQL sql = (SQL)source;
				NodeMap subNodeMap = Planner.plan(table.getAlias(), sql);
				
				for(Set<String> tables : subNodeMap.getMap().keySet()){
					
					Set<String> newTables = new HashSet<String>();
					newTables.add(table.getAlias());
					nodeMap.getMap().put(newTables, subNodeMap.getMap().get(tables));
				}
//				nodeMap.putAll(subNodeMap);
				System.out.println("NodeMap: "+nodeMap);
			}
			
			
		}	
		

	}
	
	
	public static List<ColumnName> addAllDistinct(List<ColumnName> columns, List<ColumnName> columnsToAdd){
		
		
		for (ColumnName columnName : columnsToAdd) {
			if(!columns.contains(columnName))columns.add(columnName);
		}
		return columns;
		
	}
	
	public static List<ColumnName> addDistinct(List<ColumnName> columns, ColumnName columnToAdd){
		
		
		if(!columns.contains(columnToAdd))columns.add(columnToAdd);
		return columns;
		
	}
	
	
	public static void createJoinNode(NodeMap nodeMap, ColumnSequence select, List<ColumnName> joinColumns){
		
		int i = 0;
		
		log.info(Planner.class, "Join columns: "+joinColumns);

		List<Node> inputNodes = new ArrayList<Node>();
		List<String> colfams = new ArrayList<String>();
		Set<String> joinTables = new HashSet<String>();
		
		log.info(Planner.class, "nodeMap: "+nodeMap);
		List<ColumnName> updatedJoinColumns = new ArrayList<ColumnName>();
		
		for (ColumnName joinColumn : joinColumns) {
			
			Set<String> tables = nodeMap.getTables(joinColumn.getTable());
//			log.info(Planner.class, "tablesxx: "+joinColumn.getTable());
//			log.info(Planner.class, "tablesxx: "+tables);
			//	CREATE DELTA VIEW
			if(tables.size() == 1){
				
				List<ColumnName> columns = new ArrayList<ColumnName>();
				columns.add(joinColumn);
				columns = addAllDistinct(columns, select.getInputColumns(joinColumn.getTable()));
				
				select.getInputColumns();
				
				createDeltaNode(nodeMap, tables, columns);
			}

			Node node = nodeMap.getNode(joinColumn.getTable());
			log.info(Planner.class, "Node for "+joinColumn.getTable());
			log.info(Planner.class, "tables: "+tables+", node: "+node);
			
			
			joinTables.addAll(tables);
			inputNodes.add(node);
			colfams.add("colfam_"+((TableNode)node).getTable().getTableName());
			updatedJoinColumns.add(new ColumnName(((TableNode)node).getTable().getTableName(), joinColumn.getName()));
			nodeMap.removeNode(tables);
		
		}
		
		//	CREATE REVERSE JOIN VIEW
		log.info(Planner.class, "Join column fams: "+colfams);
		log.info(Planner.class, "UpdatedJoinColumns: "+updatedJoinColumns);
		
		String viewName = generateViewName();
		ReverseJoin reverseJoin = new ReverseJoin(updatedJoinColumns, true);
		
		TableDefinition tableDefinition = reverseJoin.createTableDefinition(viewName, ((TableNode)inputNodes.get(0)).getTable().getTableDefinition());
		if(tableDefinition != null){
			tableDefinition.setName(viewName);
		}
		
		TableNode tableNode = MaintenancePlanUtil.createView(queryDAG, inputNodes, viewName, reverseJoin, colfams, tableDefinition);
		
		nodeMap.putNode(joinTables, tableNode);
		
		
		
	}
	
	
	
	public static TableNode createJoinNodes(NodeMap nodeMap, ColumnSequence select, List<Condition> joinConditions, Map<IPredicate, Set<String>> predicates){
		
		
			
			List<String[]> mergedPredicates = Predicate.mergeAll(Predicate.getPredicateStrings(joinConditions));
			
			for (String[] mergedPredicate : mergedPredicates) {
				
				List<ColumnName> joinColumns = new ArrayList<ColumnName>();
				for (String string : mergedPredicate) {
					
					joinColumns.add(new ColumnName(string));
				}
				createJoinNode(nodeMap, select, joinColumns);
				
				
				if(!predicates.isEmpty()){
					System.out.println("predicates: "+predicates);
					createSelectionNodes(nodeMap, predicates);
					
				}
				log.info(Planner.class, "------------------------");

				
			}
			
			return null;		

		
	
	}
	
//	
	public static void createDeltaNode(NodeMap nodeMap, Set<String> tables, List<ColumnName> columns){
		
//		Set<String> tables = nodeMap.getTables(select.getColumns().get(0).getColumnName().getTable());
		log.info(Planner.class, "tables: "+tables);
		Node inputNode = nodeMap.getNode(tables);
		log.info(Planner.class, "node: "+inputNode);
		
		
		String viewName=generateViewName();
		
		boolean rowKeyChanged = (((TableNode)inputNode).getTable() instanceof BaseTable);
		
		TableDefinition tableDefinition = ((TableNode)inputNode).getTable().getTableDefinition();
		if(tableDefinition != null){
			tableDefinition=tableDefinition.copy();
			tableDefinition.setName(viewName);
		}
		
		log.info(Planner.class, "tableDefinition: "+tableDefinition);
		
		ViewOperation delta = new Delta(rowKeyChanged, columns);
		TableNode tableNode = MaintenancePlanUtil.createView(queryDAG, inputNode, viewName, delta, Arrays.asList(new String[]{"colfam1"}), tableDefinition);
		
		nodeMap.putNode(tables, tableNode);
		
	}

	
	public static void createGroupByNode(NodeMap nodeMap, ColumnSequence select, ColumnSequence groupBy){
		
		
		Set<String> tables = nodeMap.getTables(select.getTables());
		
		if(tables.size() == 1){
			
			List<ColumnName> columns = new ArrayList<ColumnName>();
			columns.addAll(groupBy.getInputColumns());
			columns = addAllDistinct(columns, select.getInputColumns());
			createDeltaNode(nodeMap, tables, columns);
		
		}	
			
		Node inputNode = nodeMap.getNode(tables);
		log.info(Planner.class, "tables: "+tables+", node: "+inputNode);
		log.info(Planner.class, "Node: "+((TableNode)inputNode).getTable().getTableDefinition());
		
		PreAggregation preAggregation = new PreAggregation(select, groupBy, true);
		
		String viewName = generateViewName();
		
		TableDefinition tableDefinition = preAggregation.createTableDefinition(viewName, ((TableNode)inputNode).getTable().getTableDefinition());
		if(tableDefinition != null)tableDefinition.setName(viewName);
		
		TableNode tableNode = MaintenancePlanUtil.createView(queryDAG, inputNode, viewName, preAggregation, Arrays.asList(new String[]{"colfam1"}), tableDefinition);
		
		nodeMap.putNode(tables, tableNode);
		
	}
	
	
	public static void createOrderByNodes(NodeMap nodeMap, ColumnSequence orderBy){
		
		Set<String> tables = nodeMap.getTables(orderBy.getColumns().get(0).getColumnName().getTable());
		Node inputNode = nodeMap.getNode(tables);
		log.info(Planner.class, "tables: "+tables+", node: "+inputNode);
		
		String viewName= generateViewName();
		
		TableDefinition tableDefinition = ((TableNode)inputNode).getTable().getTableDefinition();
		if(tableDefinition != null){
			tableDefinition=tableDefinition.copy();
			tableDefinition.setName(viewName);
		}
		
		Sort sort = new Sort(orderBy, true);
		TableNode tableNode = MaintenancePlanUtil.createView(queryDAG, inputNode, viewName, sort,  Arrays.asList(new String[]{"colfam1"}), tableDefinition);
		
		nodeMap.putNode(tables, tableNode);
	}

	
	
	
	public static void createProjectionNodes(NodeMap nodeMap, ColumnSequence select, TableSequence from){
		
//		System.out.println("nodemap: "+nodeMap);
		Set<String> tables = select.getTables();
//		System.out.println("tables: "+tables);
		
		if(tables.contains("*")){
			tables = new HashSet<String>();
			for (TableName tableName : from.getTableNames()) {
				
				tables.add(tableName.getName());
			}
		}
		
		Node inputNode = nodeMap.getNode(tables);
		log.info(Planner.class, "tables: "+tables+", node: "+inputNode);
		
		boolean isTracked=false;
		if(inputNode instanceof TableNode){
			if(((TableNode)inputNode).getTable() instanceof BaseTable)isTracked=true;
		}
		
		ViewOperation projection = new Projection(select, isTracked);
		
		String viewName = generateViewName();
		TableDefinition tableDefinition = ((TableNode)inputNode).getTable().getTableDefinition();
		if(tableDefinition != null){
			tableDefinition=tableDefinition.copy();
			tableDefinition.setName(viewName);
		}
		
		TableNode tableNode = MaintenancePlanUtil.createView(queryDAG, inputNode, viewName, projection, Arrays.asList(new String[]{"colfam1"}), tableDefinition);
		
		nodeMap.removeNode(nodeMap.getTables(tables));
		nodeMap.putNode(tables, tableNode);
	}
	
	
	

}
