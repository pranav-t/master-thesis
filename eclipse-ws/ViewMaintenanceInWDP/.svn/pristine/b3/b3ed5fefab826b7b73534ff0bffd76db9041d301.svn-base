package de.webdataplatform.settings;

import java.util.ArrayList;
import java.util.List;



public class TableDefinition {


	private String name; 
//	private KeyDefinition primaryKey;
	private List<ColumnDefinition> columns;
	private int numRegions;
	
	


	public TableDefinition() {
		super();
	}
	public TableDefinition(String name, List<ColumnDefinition> columns, int numRegions) {
		super();
		this.name = name;
		this.columns = columns;
		this.numRegions = numRegions;
	}
	public ColumnDefinition getPrimaryKey() {
	
		for (ColumnDefinition colDef : columns) {
			if(colDef.isPrimaryKey())return colDef;
		}
		
		return null;
	}
//	public void setPrimaryKey(KeyDefinition primaryKey) {
//		this.primaryKey = primaryKey;
//	}
	public List<ColumnDefinition> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnDefinition> columns) {
		this.columns = columns;
	}
	
	public List<String> getColumnNames() {
		
		List<String> result = new ArrayList<String>();
		for (ColumnDefinition colDef : columns) {
			
			result.add(colDef.getName());
			
		}
		
		
		return result;
	}
	
	public List<String> getColumnFamilies(){
		
		List<String> result = new ArrayList<String>();
		
		for (ColumnDefinition coumnDefinition : this.getColumns()) {
		
			
			if(coumnDefinition.getFamily()!= null && !result.contains(coumnDefinition.getFamily()))result.add(coumnDefinition.getFamily());
		}
		
		return result;
	}
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getNumRegions() {
		return numRegions;
	}
	public void setNumRegions(int numRegions) {
		this.numRegions = numRegions;
	}
	
	
	
	@Override
	public String toString() {
		return "TableDefinition [name=" + name + ", columns=" + columns + ", numRegions=" + numRegions + "]";
	}



	public TableDefinition copy(){
		
		
		TableDefinition tableDefinition = new TableDefinition();
		
		tableDefinition.setName(this.name);
//		tableDefinition.setPrimaryKey(primaryKey.copy());
		
		List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
				
		for (ColumnDefinition colDef : this.columns) {
			
			columns.add(colDef.copy());
			
		}		
		
		
		tableDefinition.setColumns(columns);
		tableDefinition.setNumRegions(this.numRegions);
		
		return tableDefinition;
	}


	
	
	

}
