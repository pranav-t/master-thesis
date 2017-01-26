package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TableSequence {


	
	
	private List<Table> tables=new ArrayList<Table>();

	
	public TableSequence() {
		super();
		
		this.tables = new ArrayList<Table>();
		
		
		
	}

	
	public void addTable(Table column){
		
		this.tables.add(column);
		
	}
	
	

	@Override
	public String toString() {
		return "TableSequence{" + tables + "}";
	}


	
	public boolean hasColumns(){
		
		
		return tables.size() > 0;
		
	}
	
	public List<TableName> getTableNames(){
		
		List<TableName> result = new ArrayList<TableName>();
		
		for (Table table : tables) {
			
			if(table.getSource() instanceof TableName){
				
				result.add((TableName)table.getSource());
				
			}else{
				result.add(new TableName(table.getAlias()));
			}
			
		}
		return result;
		
	}

	
	
	

	public List<Table> getTables() {
		return tables;
	}


	public void setTables(List<Table> tables) {
		this.tables = tables;
	}


	
	
	
	

	
	

	
	
	

}
