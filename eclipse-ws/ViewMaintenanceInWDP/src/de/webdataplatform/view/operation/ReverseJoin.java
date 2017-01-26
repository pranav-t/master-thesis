package de.webdataplatform.view.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.webdataplatform.basetable.BaseTableUpdate;
import de.webdataplatform.graph.CostModel;
import de.webdataplatform.graph.Node;
import de.webdataplatform.parser.ColumnName;
import de.webdataplatform.parser.IPredicate;
import de.webdataplatform.settings.ColumnDefinition;
import de.webdataplatform.settings.KeyDefinition;
import de.webdataplatform.settings.TableDefinition;
import de.webdataplatform.system.TableRecord;



public class ReverseJoin extends ViewOperation{

	private List<ColumnName> joinColumns;
	
//	private IPredicate predicate;

	private float matchingProbability;
	
	
	public ReverseJoin(List<ColumnName> joinColumns, boolean rowKeyChanged) {
		super("\u22c8", rowKeyChanged);
		this.joinColumns = joinColumns;
//		this.columns = columns;
	}
	
	public String getJoinColumn(String tableName){
		
		for (ColumnName columName : joinColumns) {
			
			if(columName.getTable().equals(tableName))return columName.getName();
			
		}
		return null;
		
	}
	
	
	public TableDefinition createTableDefinition(String tableName, TableDefinition inputDefinition){
		
		if(inputDefinition==null)return null;
		
		TableDefinition result = inputDefinition.copy();
		

		for (ColumnDefinition colDef : result.getColumns()) {
			
			if(colDef.getName().equals(joinColumns.get(0).getName())){
				
				colDef.setPrimaryKey(true);
				
			}else{
				colDef.setPrimaryKey(false);
			}
			
		}
		
		return result;
		
		
	}
	
//	public List<String> getColumnFamilies(){
//		
//		List<String> result = new ArrayList<String>();
//		
////		for (ColumnName columnName : joinColumns) {
//			
//		result.add("colfam1");
//			
////		}
//		return result;
//		
//	}
	
	

	
	public List<ColumnName> getJoinColumns() {
		return joinColumns;
	}

	public void setJoinColumns(List<ColumnName> joinColumns) {
		this.joinColumns = joinColumns;
	}

	
	
	public List<String> getJoinTables() {
		
		List<String> tables= new ArrayList<String>();
		
		for (ColumnName columnName : joinColumns) {
			
			tables.add(columnName.getTable());
			
		}
		
		return tables;
	}
	
	/**
	public List<String> getColumns() {
		
		List<String> cols= new ArrayList<String>();
		
		for (int i = 0; i < columns.length; i++) {
			
			cols.add(columns[i].split("\\.")[1]);
			
			
		}
		
		return cols;
	}
	public List<String> getColumns(String tableName) {
		
		List<String> result = new ArrayList<String>();
		
		for (int i = 0; i < columns.length; i++) {
			
			if(columns[i].split("\\.")[0].equals(tableName))
					result.add(columns[i].split("\\.")[1]);
			
			
		}
		
		return result;
	}
	
	public List<String> getColumnsWithoutJoinColumn(String tableName) {
		
		List<String> result = new ArrayList<String>();
		
		for (int i = 0; i < columns.length; i++) {
			
			if(columns[i].split("\\.")[0].equals(tableName))
				if(!columns[i].split("\\.")[1].equals(getJoinColumn(tableName)))
					result.add(columns[i].split("\\.")[1]);
			
			
		}
		
		return result;
	}*/



	@Override
	public int computeFlow(int inputFlow) {
				
		float result = inputFlow;
		
		return (int)result;
	}

	@Override
	public int computeCost(int outputFlow) {
		
		float cost = outputFlow*CostModel.PUT_WEIGHT;
		
		return (int)cost;
	}

	
	

	@Override
	public String toString() {
		
		return this.symbol+"["+joinColumns+"]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + joinColumns.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReverseJoin other = (ReverseJoin) obj;
		if (!joinColumns.equals(other.joinColumns))
			return false;
		return true;
	}

	
	@Override
	public String getViewRecordKey(TableRecord baseRecord) {

		return baseRecord.getColumns().get(getJoinColumn(baseRecord.getTableName()));
	}

	
	
	public String getViewRecordKey(BaseTableUpdate btu) {

		return getViewRecordKey(btu.getRecord());
		
	}
	
//	public List<String> getViewRecordColFams(BaseTableUpdate btu) {
//		
//		List<String> result = new ArrayList<String>();
//		
//		for(String colFam : getColumnFamilies()){
//			
//			if(!(colFam.contains(btu.getRecord().getTableName()))){
//				result.add(colFam);
//			}
//		}
//		
//		return result;
//		
//	}
	
	
	

	@Override
	public boolean similar(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Node getSplitOperation() {
		// TODO Auto-generated method stub
		return null;
	}


	


	
	

}
