package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.List;

public class SQL implements ISource{

	
	
	
	
	private ColumnSequence select;
	
	private TableSequence from=new TableSequence();
	
	private List<Condition> joinConditions=new ArrayList<Condition>();
	
	private IPredicate where;
	
	private IPredicate having;
	
	private ColumnSequence groupBy=new ColumnSequence();
	
	private ColumnSequence orderBy=new ColumnSequence();
	
	
	
	public static List<String> getSQLKeywords(){
		
		List<String> keywords = new ArrayList<String>();
		keywords.add("select");
		keywords.add("from");
		keywords.add("group_by");
		keywords.add("pre_group_by");
		keywords.add("order_by");
		keywords.add("where");
		keywords.add("sort");
		keywords.add("having");
		keywords.add("as");
		keywords.add("on");
		keywords.add("inner_join");
		keywords.add("and");
		keywords.add("or");
		keywords.add("asc");
		keywords.add("desc");

		return keywords;
	}
	
	public static boolean isSQLKeyword(String string){
		
		return getSQLKeywords().contains(string);
		
	}
	

	public ColumnSequence getSelect() {
		return select;
	}

	public void setSelect(ColumnSequence select) {
		this.select = select;
	}

	public TableSequence getFrom() {
		return from;
	}

	public void setFrom(TableSequence from) {
		this.from = from;
	}

	public List<Condition> getJoinConditions() {
		return joinConditions;
	}

	public void setJoinConditions(List<Condition> joinConditions) {
		this.joinConditions = joinConditions;
	}

	public IPredicate getWhere() {
		return where;
	}

	public void setWhere(IPredicate where) {
		this.where = where;
	}

	public ColumnSequence getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(ColumnSequence groupBy) {
		this.groupBy = groupBy;
	}

	public ColumnSequence getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(ColumnSequence orderBy) {
		this.orderBy = orderBy;
	}

	public IPredicate getHaving() {
		return having;
	}

	public void setHaving(IPredicate having) {
		this.having = having;
	}

	
	
	
	
	
	
	
	
	
	
	
}
