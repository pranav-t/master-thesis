package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.webdataplatform.system.TableRecord;

public class ColumnSequence {


	
	
	private List<Column> columns=new ArrayList<Column>();

	
	public ColumnSequence() {
		super();
		
		this.columns = new ArrayList<Column>();
		
		
		
	}

	
	public void addColumn(Column column){
		
		this.columns.add(column);
		
	}
	
	

	@Override
	public String toString() {
		return "ColumnSequence{" + columns + "}";
	}


	
	public boolean hasColumns(){
		
		
		return columns.size() > 0;
		
	}
	
	public List<ColumnName> getOutputColumns(){
		
		List<ColumnName> result = new ArrayList<ColumnName>();
		
		for (Column column : columns) {
			
			if(column.getTerm() instanceof ColumnName){
				
				result.add((ColumnName)column.getTerm());
				
			}else{
				result.add(new ColumnName(column.getAlias()));
			}
			
		}
		return result;
		
	}
	
	public boolean isOutputColumn(String colName){
		
		
		for (ColumnName columnName : getOutputColumns()) {
			
			if(columnName.getName().equals(colName))return true;
			
		}
		return false;
		
	}
	

	public List<ColumnName> getInputColumns() {
		List<ColumnName> result = new ArrayList<ColumnName>();
		for (Column column : columns) {
			
			result.addAll(column.getTerm().getBaseColumns());
		}
		return result;
	}
	
	public List<ColumnName> getInputColumns(String tableName) {
		List<ColumnName> result = new ArrayList<ColumnName>();
		for (Column column : columns) {
			
//			if(column.equals(tableName))
			List<ColumnName> columns = column.getTerm().getBaseColumns();
		
			for (ColumnName columnName : columns) {
				if(columnName.getTable().equals(tableName))result.add(columnName);	
			}
				
		}
		return result;
	}	
	
	
	
	public List<ColumnName> getDistinctBaseColumns(){
		
		List<ColumnName> result = new ArrayList<ColumnName>();
		
		List<ColumnName> inputColumns=getInputColumns();
		
		for (ColumnName columnName : inputColumns) {
			if(!result.contains(columnName))result.add(columnName);
		}
		return result;
	}

	
	public Set<String> getTables(){
		
		Set<String> result = new HashSet<String>();
		List<ColumnName> baseColumns = getInputColumns();
		
		for (ColumnName columnName : baseColumns) {
			
			result.add(columnName.getTable());
			
		}
		return result;
	}

	public TableRecord computeColumns(TableRecord tableRecord)throws Exception{
		
		for (Column column : columns) {
			
			if(!(column.getTerm() instanceof ColumnName)&& !tableRecord.getColumns().containsKey(column.getAlias())){
				
				tableRecord.putValue("colfam1", column.getAlias(), String.valueOf(column.getTerm().eval(tableRecord)));
				
			}
			
		}
		return tableRecord;
		
	}
	

	
	public List<Column> getColumns() {
		

		return columns;
	}
	
	

	
	
	

}
