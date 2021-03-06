package de.webdataplatform.parser;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.webdataplatform.log.Log;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.TypeChecker;

public class Parser {

	/**
	 * @param args
	 */

	
	public static Log log;
	

	public static SQL parse(String queryName, String queryString){
		
		queryString = queryString.replace("pre group by", "pre_group_by");
		queryString = queryString.replace("group by", "group_by");
		queryString = queryString.replace("order by", "order_by");
		queryString = queryString.replace("inner join", "inner_join");
		
		List<String> tokens = Tokenizer.tokenize(queryString);
		log.info(Parser.class, "tokens: "+tokens);
		TokenStream tokenStream = new TokenStream(tokens);
		
		SQL sql = parseSQLPattern(tokenStream);
	
		
		if(tokenStream.hasNext()){
			
			log.info(Parser.class, "Couldn't identify token: "+tokenStream.consumeToken());
			
		}else{
			log.info(Parser.class, "Parsing successful");
		}
		
		
		return sql;
	}

	
	public static SQL parseSQLPattern(TokenStream tokenStream){
		
		
		SQL sql = new SQL();
		
		while(tokenStream.hasNext()){
		
			String token =  tokenStream.consumeToken();
			
			switch(token){
			
				case "select" :
					ColumnSequence select = parseSelect(tokenStream);
					sql.setSelect(select);
					
					log.info(Parser.class, "select: ");
					for (Column column : select.getColumns()) {
						
						log.info(Parser.class, "column: "+column);
					}
				break;	
				case "from" :
					TableSequence from = parseFrom(tokenStream);
					sql.setFrom(from);
					for (Table iTable : from.getTables()) {
						
						log.info(Parser.class, "from: "+iTable);
					}
				break;	
				case "where" :
					IPredicate where = parsePredicate(tokenStream);
					sql.setWhere(where);
					log.info(Parser.class, "where: "+where);
					log.info(Parser.class, "----------------");
					log.info(Parser.class, "join conditions: ");
					for (Condition condition : where.getJoinConditions()) {
						log.info(Parser.class, "condition:"+condition);
					}
					log.info(Parser.class, "----------------");
					log.info(Parser.class, "column conditions: ");
					for (Condition condition : where.getColumnConditions()) {
						log.info(Parser.class, "condition:"+condition);
					}
					log.info(Parser.class, "----------------");
					log.info(Parser.class, "evaluation map: ");
					
					Map<IPredicate, Set<String>> map = where.getEvaluationMap();
					
					for (IPredicate predicate : map.keySet()) {
						log.info(Parser.class, map.get(predicate)+": "+predicate);
					}
					log.info(Parser.class, "----------------");
//					for (Condition condition : sql.getJoinConditions()) {
//						
//						log.info(Parser.class, "condition: "+condition);
//						
//					}
//					String query1 = "select A.c1 as c1, A.c2 as c2, B.c1 as c3 from A where A.c1 < 50 or A.c2 < 20 and A.c1 < 10";
					/**Map<String, String> map = new HashMap<String, String>();
					map.put("A.c1", "50");
					map.put("A.c2", "10");
					map.put("A.c3", "10");
					try {
						log.info(Parser.class, "eval where: "+where.eval(map));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
				break;
				case "having" :
					IPredicate having = parsePredicate(tokenStream);
					sql.setHaving(having);
					log.info(Parser.class, "having: "+having);
				break;	
				case "group_by" :
					ColumnSequence groupBy = parseGroupBy(tokenStream);
					sql.setGroupBy(groupBy);
					log.info(Parser.class, "groupBy: "+groupBy);
				break;
				case "pre_group_by" :
					groupBy = parseGroupBy(tokenStream);
					sql.setGroupBy(groupBy);
					log.info(Parser.class, "groupBy: "+groupBy);
				break;	
				case "order_by" :
					ColumnSequence orderBy = parseOrderBy(tokenStream);
					sql.setOrderBy(orderBy);
					log.info(Parser.class, "orderBy: "+orderBy);
				break;	
				case ";" :
				break;
				default :
					tokenStream.pushToken(token);
					return sql;
		
			}
		}
		return sql;
		
	}
	
	
	
	public static ColumnSequence parseSelect(TokenStream tokenStream){
		
		ColumnSequence columnSequence = new ColumnSequence();
		Column column=null;
		
		while(tokenStream.hasNext()){
		

			ITerm term = parseTerm(tokenStream);
			
			if(term != null){
				column=new Column(term);
				columnSequence.addColumn(column);
			}else{

				String token = tokenStream.consumeToken();

				switch(token){	
				
					case "," :
						 
					break;	
					case "as" :
						column.setAlias(tokenStream.consumeToken());
					break;
					default :
//						log.info(Parser.class, "Select end:"+token);
						tokenStream.pushToken(token);
						return columnSequence;
						
		
				}
			}
			
			
		}
		
		return columnSequence;
		
	}
	
	public static ITerm parseTerm(TokenStream tokenStream){
	
		ITerm term=null;

			while(tokenStream.hasNext()){ 	
				
				String token =  tokenStream.consumeToken();
				
//				log.info(Parser.class, "token: "+token);
				
				// Check if column name
				if(ColumnName.isColumnName(token)){
					
					ColumnName columnName = new ColumnName(token);
					term = columnName;
					
				// Check if integer Number 	
				}else if(TypeChecker.isInteger(token)){
					
					Constant constant = new Constant(Integer.parseInt(token));
					term = constant;

					// Check if float Number 	
				}else if(TypeChecker.isFloat(token)){
					
					Constant constant = new Constant(Float.parseFloat(token));
					term = constant;
				
				// Check for operators	
				}else{
				
					switch(token){
					
						case "(" :
							term = parseTerm(tokenStream);
						break;	
						case ")" :
							return term;
						case "*" :
							Multiplication multiplication;
						
							if(term == null){
								ColumnName columnName = new ColumnName();
								columnName.setTable(token);
								columnName.setName(token);
								term = columnName;
								return term;
							}
							if(ColumnName.isColumnName(tokenStream.nextToken())){
								
								token = tokenStream.consumeToken();
								ColumnName columnName = new ColumnName(token);
								multiplication = new Multiplication(term, columnName);
							}else{
								
								multiplication = new Multiplication(term, parseTerm(tokenStream));
							}
							term = multiplication;
						break;	
						case "+" :
							Addition addition = new Addition(term, parseTerm(tokenStream));
							term = addition;
						break;	
						case "-" :
							Substraction substraction = new Substraction(term, parseTerm(tokenStream));
							term = substraction;
						break;	
						case "sum" :
							tokenStream.consumeToken();
							Function function = new Function("sum", parseTerm(tokenStream));
							term = function;
						break;	
						case "min" :
							tokenStream.consumeToken();
							function = new Function("min", parseTerm(tokenStream));
							term = function;
						break;	
						case "max" :
							tokenStream.consumeToken();
							function = new Function("max", parseTerm(tokenStream));
							term = function;
						break;	
						case "avg" :
							tokenStream.consumeToken();
							function = new Function("avg", parseTerm(tokenStream));
							term = function;
						break;							
						case "count" :
							tokenStream.consumeToken();
							function = new Function("count", parseTerm(tokenStream));
							term = function;
						break;	
						// Don't know token. Push back and return
						default :
//							log.info(Parser.class, "Term end:"+token);
							tokenStream.pushToken(token);
							return term;
					}
				}
		
		}
		return term;
		
	}
		
	
	
	
	public static TableSequence parseFrom(TokenStream tokenStream){
		
		TableSequence tableSequence = new TableSequence();
		Table table=null;
		
		
		while(tokenStream.hasNext()){
		
				String token =  tokenStream.consumeToken();
		
				if(TableName.isTableName(token)){
					
//					log.info(Parser.class, "tokentt:"+token);
					table = new Table(new TableName(token));
					tableSequence.addTable(table);
					
				}else {
					switch(token){
					
						case "," :
							
						break;	
						case "(" :
							log.info(Parser.class, "----------------------------------");
							log.info(Parser.class, "Parsing subquery");
							table = new Table(parseSQLPattern(tokenStream));
							tableSequence.addTable(table);
						break;	
						case ")" :
							log.info(Parser.class, "----------------------------------");
						break;
						case "as" :
							table.setAlias(tokenStream.consumeToken());
						break;
						case "inner_join" :
							
							token = tokenStream.consumeToken();
							table = new Table(new TableName(token));
							tableSequence.addTable(table);
							token = tokenStream.consumeToken();
							Condition condition = parseCondition(tokenStream);
							
							
						break;
						default :
							tokenStream.pushToken(token);
							return tableSequence;
					}
				}

		}
		return tableSequence;
		
	}
	

	public static IPredicate parsePredicate(TokenStream tokenStream){
		

		IPredicate predicate=null;
		boolean isOpenBracket=false;
		
		while(tokenStream.hasNext()){
		
		
			if(ColumnName.isColumnName(tokenStream.nextToken())){
				
				Condition condition = parseCondition(tokenStream);
				predicate = condition;
				
				
			}else{
		
				String token =  tokenStream.consumeToken();
				switch(token){
					case "(" :
						isOpenBracket=true;
						predicate = parsePredicate(tokenStream);
					break;	
					case ")" :
						if(!isOpenBracket){
							tokenStream.pushToken(token);
							return predicate;
						}else{
							isOpenBracket=false;
						}
					break;	
					case "and" :
						Conjunction conjunction;
						
						if(ColumnName.isColumnName(tokenStream.nextToken())){
							Condition condition2 = parseCondition(tokenStream);
							conjunction = new Conjunction(predicate, condition2);
						}else{
							
							conjunction = new Conjunction(predicate, parsePredicate(tokenStream));
						}
						predicate = conjunction;
					break;	
					case "or" :
						Disjunction disjunction = new Disjunction(predicate, parsePredicate(tokenStream));
						predicate = disjunction;
					break;
					default :
						tokenStream.pushToken(token);
						return predicate;
				
				}
			}
		}
		return predicate;
		
	}
	
	public static ColumnSequence parseGroupBy(TokenStream tokenStream){
		
		ColumnSequence columnSequence = new ColumnSequence();
		Column column=null;
		
		while(tokenStream.hasNext()){
		
			String token =  tokenStream.consumeToken();
		
			
			if(ColumnName.isColumnName(token)){
				
				column = new Column(new ColumnName(token)); 
				columnSequence.addColumn(column);
				
			}else{
		
				switch(token){
				
					case "," :
						
					break;	
					default :
						tokenStream.pushToken(token);
						return columnSequence;
				}
			}
		}
		
		return columnSequence;
		
	}
	
	public static ColumnSequence parseOrderBy(TokenStream tokenStream){
		
		ColumnSequence columnSequence = new ColumnSequence();
		Column column=null;
		
		while(tokenStream.hasNext()){
		
			String token =  tokenStream.consumeToken();
		
			if(ColumnName.isColumnName(token)){
				
				column = new Column(new ColumnName(token)); 
				columnSequence.addColumn(column);
				
			}else{
		
				switch(token){
				
					case "desc" :
					break;	
					case "asc" :
					break;	
					case "," :
					
					break;
					default :
						tokenStream.pushToken(token);
						return columnSequence;
				
				}
			}
		}
		
		return columnSequence;
		
	}

	
	public static Condition parseCondition(TokenStream tokenStream){
		
		Condition condition = new Condition();
		
		condition.setColumnName(new ColumnName(tokenStream.consumeToken()));
		
		String token = tokenStream.consumeToken();
		
		
		if(token.equals("<")|| token.equals("<=")||token.equals(">")||token.equals(">=")||token.equals("=")||token.equals("like")){
			
			condition.setOperator(token);
			token = tokenStream.consumeToken();
			
			if(ColumnName.isColumnName(token)){
				
				condition.setValue(new ColumnName(token)); 
				
			}else{ 
			
				
				
				if(TypeChecker.isInteger(token)){
				
					condition.setValue(new Constant(Integer.parseInt(token)));
				} else
				if(TypeChecker.isFloat(token)){
						
					condition.setValue(new Constant(Float.parseFloat(token)));
				} else
				if(TypeChecker.isDate(token)){
						
					SimpleDateFormat format = new SimpleDateFormat(SystemConfig.RECORDS_DATEFORMAT);
					try {
						condition.setValue(new Constant(format.parse(token)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else
					
					condition.setValue(new Constant(token));
				}
				
			
		}else
			if(token.equals("between")){
			
			condition.setOperator(token);
			
			token = tokenStream.consumeToken();
			
			Constant start=null; 
			if(TypeChecker.isInteger(token)){
				
				start= new Constant(Integer.parseInt(token));
			}
			else if(TypeChecker.isFloat(token)){
				
				start= new Constant(Float.parseFloat(token));
			}			
			else if(TypeChecker.isDate(token)){
				
				SimpleDateFormat format = new SimpleDateFormat(SystemConfig.RECORDS_DATEFORMAT);
				try {
					start= new Constant(format.parse(token));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}	
			else start= new Constant(token);
			
			
			token = tokenStream.consumeToken();
			token = tokenStream.consumeToken();
			Constant end=null;
			
			if(TypeChecker.isInteger(token)){
				
				end= new Constant(Integer.parseInt(token));
			}
			else if(TypeChecker.isFloat(token)){
				
				end= new Constant(Float.parseFloat(token));
			}			
			else if(TypeChecker.isDate(token)){
				
				SimpleDateFormat format = new SimpleDateFormat(SystemConfig.RECORDS_DATEFORMAT);
				try {
					end= new Constant(format.parse(token));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}	
			else end= new Constant(token);
			
			if(start==null || end==null)log.info("Malformed between");
			condition.setValue(new Range(start, end));
			
		}else 
			if(token.equals("in")){
			
			condition.setOperator(token);
			Enumeration enumeration = parseEnumeration(tokenStream);
			
			condition.setValue(enumeration);
			
		}else{
			log.info(Parser.class, "Invalid operator for condition: "+token);
		}
		

		return condition;
		
	}	
	
	public static Enumeration parseEnumeration(TokenStream tokenStream){
		
		Enumeration enumeration = new Enumeration();
		boolean endOfList=false;
		while(tokenStream.hasNext()&& !endOfList){
		
			String token =  tokenStream.consumeToken();
			
			if(TypeChecker.isInteger(token)){
				enumeration.addElement(new Constant(Integer.parseInt(token)));
			}else if(TypeChecker.isString(token)){
				enumeration.addElement(new Constant(token));
			}else{
		
				switch(token){
				
					case "(" :
					break;	
					case ")" :
						return enumeration;
					case "," :
					break;	
	
					default :
						tokenStream.pushToken(token);
						return enumeration;
				}
			}
			
		}

		return enumeration;
		
	}
	
	
//	
//	public Query parse(String queryName, TokenStream tokenStream){
//		
		
		
//		log.info(Parser.class, "-----------------------------");
//		
//		Query query = null;
////		queryString = queryString.replace(",", " ");
//		
//		
//		log.info(this.getClass(), "tokens: "+tokens);
//		
//
//
//		
//		log.info(this.getClass(), "token: "+token);
//		log.info(this.getClass(), "tokens: "+tokens);
		
		
//		switch(token){
//		
//			case "select " :
//				log.info(Parser.class, "bracket");
//			break;
//			
//		
//		
//		}
				
			
		
		
		
//		
//		ColumnSequence from = new ColumnSequence(TokenUtil.consumeToNextKeyword(tokens, "from", getSQLKeywords()));
//		ColumnSequence select = new ColumnSequence(TokenUtil.consumeToNextKeyword(tokens, "select", getSQLKeywords()));
//		ColumnSequence groupBy = new ColumnSequence(TokenUtil.consumeToNextKeyword(tokens, "group_by", getSQLKeywords()));
//		ColumnSequence orderBy = new ColumnSequence(TokenUtil.consumeToNextKeyword(tokens, "order_by", getSQLKeywords()));
//		
//		
//		Predicate where = new Predicate(TokenUtil.consumeToNextKeyword(tokens, "where", getSQLKeywords()));
//		Predicate having = new Predicate(TokenUtil.consumeToNextKeyword(tokens, "having", getSQLKeywords()));
//		
//		log.info(this.getClass(), "from: "+from);
//		
//		log.info(this.getClass(), "select: "+select);
//		log.info(this.getClass(), "select: "+select.getColumnMapping());
//		log.info(this.getClass(), "select: "+select.getDistinctColumns());
//		
//		
//		log.info(this.getClass(), "groupBy: "+groupBy);
//		log.info(this.getClass(), "orderBy: "+orderBy);
//		
//		log.info(this.getClass(), "predicate: "+where);
//		log.info(this.getClass(), "predicate: "+where.getDistinctColumns());
//		log.info(this.getClass(), "predicate: "+where.getTerms(where.getColumns()));
//		
//		log.info(this.getClass(), "having: "+having);
		
		
//		log.info(this.getClass(), "select: "+select);
//		log.info(this.getClass(), "predicate: "+predicate.getTerms());
//		
//		log.info(this.getClass(), "from: "+from);
//		log.info(this.getClass(), "relations: ");
//		log.info(this.getClass(), "----------");
//		int x=0;
////		for (String[] relation : relations) {
////			log.info(this.getClass(), "relation "+x+":"+Arrays.toString(relation));
////		}
//		log.info(this.getClass(), "----------");

		
/**
		
		String rowKey=null;
		
		TableNode tableNode=null;
		
		Graph queryDAG = new Graph();

		Map<String, Node> baseTables = new HashMap<String, Node>();
		
		numViews = 0;
		this.queryName = queryName;
		
		
		//Parsing FROM	
		
		if(from != null){
			
			
//			create BASE tables  
			
			for (String[] relation : relations) {
				
				
				log.info(Parser.class, "RELATION: "+Arrays.toString(relation));
				
				tableNode = MaintenancePlanUtil.createBaseTable(queryDAG, relation[0]);
				
				baseTables.put(relation[0], tableNode);
				
				//Parsing WHERE		
				
				
//				if(where != null){
//					
//
//						List<String[]> predicate = ParserUtil.tokenizePredicate(where);
//						for (String[] predicateToken : predicate) {
//							
//							
//							if(ParserUtil.containsArrayKey(predicateToken, relation[0])){
//								
//								List<String[]> predicateTokens =  new ArrayList<String[]>();
//								predicateTokens.add(predicateToken);
//							
//								// create SELECTION view 		
//								Selection selection = new Selection(predicateTokens, isNewZone(tableNode));
//								selection.setSelectivity(0.5f);
//								
//								tableNode = MaintenancePlanUtil.createView(queryDAG, tableNode, generateViewName(), selection,  Arrays.asList(new String[]{"colfam1"}));
//								baseTables.put(relation[0], tableNode);
//								
//								
//							}
//
//						}
//					
//				}		
				
				
				
			}
			
			
			// CREATE JOIN TABLES
			
			if(relations.size() > 1){
	//			extract predicates
				List<String[]> predicates = extractPredicates(relations);
				
				List<String[]> mergedPredicates = ParserUtil.mergeAllPredicates(predicates);

				String lastRJName = null;
				TableNode lastRJNode = null;
				String[] lastPredicate = null;
				String matching = null;
				
				for (String[] mergedPredicate : mergedPredicates) {
					

					log.info(Parser.class, "-------------");
					log.info(Parser.class, "mergedPred:"+Arrays.toString(mergedPredicate));
					if(lastPredicate != null){
						
						
						matching = ParserUtil.searchTableMatching(lastPredicate, mergedPredicate);
						
						log.info(Parser.class, "Matching: "+matching);
						
					}
					
					
					List<Node> inputNodes = new ArrayList<Node>();	
					
					String[] joinColumns = new String[mergedPredicate.length];
					String[] colfams = new String[mergedPredicate.length];
					
					

//					int i = 0;
//					for (String predicate : mergedPredicate) {
//
//						
//						if(matching != null && ParserUtil.getTable(predicate).equals(matching)){
//							
//							
//
////							CREATE JOIN COLUMN
//							joinColumns[i]=lastRJName+"."+ParserUtil.getColumn(predicate);
//							colfams[i]="colfam_"+lastRJName;
//							inputNodes.add(lastRJNode);
//							
//							
//							
//						}else{	
//							
////							CREATE DELTA VIEW
//							String viewName = generateViewName();
//							TableNode baseNode = (TableNode)baseTables.get(ParserUtil.getTable(predicate));
//							
//							ViewOperation delta = new Delta(isNewZone(baseNode));
//							tableNode = MaintenancePlanUtil.createView(queryDAG, baseTables.get(ParserUtil.getTable(predicate)), viewName, delta, Arrays.asList(new String[]{"colfam1"}));
//
//							
////							CREATE JOIN COLUMN
//							joinColumns[i]=viewName+"."+ParserUtil.getColumn(predicate);
//							colfams[i]="colfam_"+viewName;
//							inputNodes.add(tableNode);
//							
//						}
//						
//						i++;
//						
//						
//					}
					

//					CREATE REVERSE JOIN VIEW
					String viewName = generateViewName();
					ViewOperation reverseJoin = new ReverseJoin(joinColumns, select.getColumns(), true);
//					reverseJoin
					tableNode = MaintenancePlanUtil.createView(queryDAG, inputNodes, viewName, reverseJoin, Arrays.asList(colfams));
					
//					CREATE PROJECTION
					if(mergedPredicates.indexOf(mergedPredicate) != mergedPredicates.size()-1){
						viewName = generateViewName();
						ViewOperation projection = new Projection(new String[]{"*"}, false);
						tableNode = MaintenancePlanUtil.createView(queryDAG, tableNode, viewName, projection,  Arrays.asList(new String[]{"colfam1"}));
					}
					
					lastRJName = viewName;
					lastRJNode = tableNode;
					lastPredicate = mergedPredicate;
					matching = null;
					
				}

				
						
			
			}
			
			
		}
		
		
//		ViewOperation delta = new Delta(new String[]{relation[0]+".*"}, true);
//		tableNode = createView(queryDAG, tableNode, queryName+"_V"+numViews, delta, viewExpression);
//		numViews++;
//		deltaTables.put(relation[0], tableNode);

		
		//Parsing PRE GROUP BY


		if(preGroupBy != null){
			
//			boolean keyChange;
//			if(tableNode.getTable() instanceof BaseTable)keyChange=true;
//			else keyChange=false;
			
			ViewOperation delta = new Delta(isNewZone(tableNode));
			tableNode = MaintenancePlanUtil.createView(queryDAG, tableNode, generateViewName(), delta, Arrays.asList(new String[]{"colfam1"}));
//			deltaTables.put(relation[0], tableNode);
			
			
			ViewOperation preAggregation = new PreAggregation(ParserUtil.splitTokens(preGroupBy,","), select.getColumnNames(), select.getColumnMapping(), true);
			tableNode = MaintenancePlanUtil.createView(queryDAG, tableNode, generateViewName(), preAggregation, Arrays.asList(new String[]{"colfam1"}));
			
			
			
//			ViewOperation aggregation = new Null(queryName+"_V"+numViews, false);
//			tableNode = queryDAG.createView(tableNode, queryName+"_V"+numViews, aggregation, viewExpression);
//			numViews++;
		}		
		
		//Parsing GROUP BY


//		if(groupBy != null){
//			
//			boolean keyChange;
//			if(tableNode.getTable() instanceof BaseTable)keyChange=true;
//			else keyChange=false;
//			
//			ViewOperation delta = new Delta(tableNode.getTable().getTableName(), keyChange);
//			tableNode = queryDAG.createView(tableNode, generateViewName(), delta, viewExpression, Arrays.asList(new String[]{"colfam1"}));
////			deltaTables.put(relation[0], tableNode);
//			
//			
//			ViewOperation aggregation = new Aggregation(groupBy, columnNames, columnMapping,  true);
//			tableNode = queryDAG.createView(tableNode, generateViewName(), aggregation, viewExpression, Arrays.asList(new String[]{"colfam1"}));
//			
//		}			
		
		//Parsing HAVING
		

		if(having != null){
			
			List<String[]> predicateTokens =  new ArrayList<String[]>();
			predicateTokens.add(having);
			Selection selection = new Selection(predicateTokens, false);
			selection.setSelectivity(0.7f);
			tableNode = MaintenancePlanUtil.createView(queryDAG, tableNode, generateViewName(), selection, Arrays.asList(new String[]{"colfam1"}));
			
			
		}	
		
		//Parsing ORDER BY
		

		if(orderBy != null){
			
			Sort sort = new Sort(ParserUtil.splitTokens(orderBy,","), false);
			tableNode = MaintenancePlanUtil.createView(queryDAG, tableNode, generateViewName(), sort,  Arrays.asList(new String[]{"colfam1"}));
			
			
		}	
		
		//Parsing SELECT
		if(select != null){
			

			
			// CREATE PROJECTION
			ViewOperation projection = new Projection(select.getColumnNames(), isNewZone(tableNode));
			tableNode = MaintenancePlanUtil.createView(queryDAG, tableNode, generateViewName(), projection, Arrays.asList(new String[]{"colfam1"}));
			
			
		}	
		
		
		query.setQueryDAG(queryDAG);*/
		
//		return query;
//	}
	/**
	public void createReverseJoinViews(List<String[]> mergedPredicates, int numViews){
		
		String matching=null;
		
		int startElement=0;
		int matchingPos=0;


		for (int i = 0; i < predicates.size(); i++) {
			for (int j = i+1; j < predicates.size(); j++) {
	
//					log.info(Parser.class, "------");
//					log.info(Parser.class, "merging: i:"+Arrays.toString(predicates.get(i))+", j:"+Arrays.toString(predicates.get(j)));

					matching = searchColumnMatching(predicates.get(i), predicates.get(j));
//					log.info(Parser.class, "Matching: "+matching);
					if(matching != null){
						startElement = i;
						matchingPos = j;
						break;
					}

			}
			if(matching != null){
				break;
			}
		}
		
		
		if(matching != null){
			return mergeAllPredicates(mergePredicates(predicates, startElement, matchingPos));
		}else{
			log.info(Parser.class, "------");
			return predicates;
		}
		
		for (String[] mergedPredicate : mergedPredicates) {
			
			
			log.info(Parser.class, "mergedPred:"+Arrays.toString(mergedPredicate));
			List<Node> inputNodes = new ArrayList<Node>();	
			
			String[] joinColumns = new String[mergedPredicate.length];
			String[] colfams = new String[mergedPredicate.length];
			int i = 0;
			for (String predicate : mergedPredicate) {
//				log.info(Parser.class, "predicate.split():"+Arrays.toString(predicate.split(".")));
				
				
				boolean isRowKeyChange=false;
				if(((TableNode)baseTables.get(predicate.split("\\.")[0])).getTable() instanceof BaseTable)isRowKeyChange=true;
				
				joinColumns[i]=queryName+"_V"+numViews+"."+predicate.split("\\.")[1];
				colfams[i]="colfam_"+queryName+"_V"+numViews;
				ViewOperation delta = new Delta(predicate.split("\\.")[0], isRowKeyChange);
				tableNode = queryDAG.createView(baseTables.get(predicate.split("\\.")[0]), queryName+"_V"+numViews, delta, viewExpression, Arrays.asList(new String[]{"colfam1"}));
				numViews++;
//				deltaTables.put(relation[0], tableNode);
				
				inputNodes.add(tableNode);
				
				i++;
				
				
			}
			
//			log.info(Parser.class, "join tables:"+inputNodes);
			ViewOperation reverseJoin = new ReverseJoin(joinColumns, select, true);
			tableNode = queryDAG.createView(inputNodes, queryName+"_V"+numViews, reverseJoin, viewExpression, Arrays.asList(colfams));
			numViews++;
			
		}
	}*/
	
//
//	private List<String[]> extractPredicates(List<String[]> relations) {
//		int count=0;
//		
//		List<String[]> predicates = new ArrayList<String[]>();
//		
//		for (String[] relation : relations) {
//			if(count > 0){
//				
//				String[] predicate = Arrays.copyOfRange(relation, 2, relation.length);
//				
//				String[] pred = ParserUtil.removeElement(predicate, "=");
//				
//				log.info(this.getClass(), "predicate:"+Arrays.toString(pred));
//				predicates.add(pred);
//				
//			}
//			count++;
//		}
//		return predicates;
//	}
//	


	
	
}
