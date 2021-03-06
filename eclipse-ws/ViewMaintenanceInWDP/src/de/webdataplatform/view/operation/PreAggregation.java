package de.webdataplatform.view.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mortbay.log.Log;

import de.webdataplatform.basetable.BaseTableUpdate;
import de.webdataplatform.graph.Node;
import de.webdataplatform.parser.Column;
import de.webdataplatform.parser.ColumnName;
import de.webdataplatform.parser.ColumnSequence;
import de.webdataplatform.settings.ColumnDefinition;
import de.webdataplatform.settings.KeyDefinition;
import de.webdataplatform.settings.TableDefinition;
import de.webdataplatform.system.TableRecord;




public class PreAggregation extends ViewOperation{


	private ColumnSequence groupingKeys;
	private ColumnSequence select;
	
//	private List<String> aggregationKeys=new ArrayList<String>();
//	private List<String> aggregationColumns=new ArrayList<String>();
//	private List<String> aggregationFunctions=new ArrayList<String>();
//	private List<String> aggregationExpressions=new ArrayList<String>();
	
	
	
	public PreAggregation(ColumnSequence select, ColumnSequence groupingKeys, boolean rowKeyChanged) {
		super("pre-\u03B3", rowKeyChanged);
		this.groupingKeys = groupingKeys;
		this.select = select;
	
	}
	
	
	
	
	
	
	public List<String> getDefinedFunctions(){
		
		List<String> keywords = new ArrayList<String>();
		keywords.add("sum");
		keywords.add("count");
		keywords.add("min");
		keywords.add("max");
		keywords.add("avg");

		return keywords;
	}
	

	public ColumnSequence getGroupingKeys() {
		return groupingKeys;
	}


	public void setGroupingKeys(ColumnSequence groupingKeys) {
		this.groupingKeys = groupingKeys;
	}


	public ColumnSequence getSelect() {
		return select;
	}


	public void setSelect(ColumnSequence select) {
		this.select = select;
	}






	public boolean containsDefinedFunction(String expression){
		
		boolean result = false;
		
		for (String function : getDefinedFunctions()) {
			if(expression.contains(function))result=true;
		}
		
		return result;
		
	}





	@Override
	public int computeFlow(int inputFlow) {

		
		return inputFlow;
	}
	
	@Override
	public String toString() {
		
		return this.symbol+"["+groupingKeys+"]";
	}

//
	public List<String> getAggregationValueColumns() {
		List<String> result = new ArrayList<String>();
		
//		for (String expr : aggregationExpressions) {
//
//
//			result.addAll(extractColumns(expr));
//			
//
//		}
		return result;
	}
	
	
	public TableDefinition createTableDefinition(String tableName, TableDefinition inputDefinition){
		
		if(inputDefinition==null)return null;
		
		TableDefinition result = inputDefinition.copy();
		

		for (ColumnDefinition colDef : result.getColumns()) {
			
			if(colDef.getName().equals(groupingKeys.getOutputColumns().get(0).getName())){
				
				colDef.setPrimaryKey(true);
				
			}else{
				colDef.setPrimaryKey(false);
			}
			
			
		}
		return result;
		
		
	}
	

	/**
	 * Computes the key of the view record given a base table entry
	 * @param tableRecord
	 * @return
	 */
	public String getViewRecordKey(TableRecord baseRecord) {
		
		String key = "";
		int i = 0;
		for (ColumnName columnName : groupingKeys.getOutputColumns()) {
			
			try {
				key += baseRecord.resolveValue(columnName.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(i != groupingKeys.getColumns().size()-1)key += "_";
			i++;
		}
		
		
		return key;
	}
	
	
	public String getViewRecordKey(BaseTableUpdate btu) {
		
		String key = "";

		
//		if(btu.getOperationType().equals("Put")){
			
			return getViewRecordKey(btu.getRecord());
//		}
			
//			
//		if(btu.getOperationType().equals("Delete") || btu.getOperationType().equals("DeleteColumn") || btu.getOperationType().equals("DeleteFamily")){
//			
//			return getViewRecordKey(btu.getOldBaseRecord());
//		}
//		
//		return key;
//		
		
	}	

	
	
	

//	public void setAggregationValues(List<String> aggregationValues) {
//		this.aggregationValues = aggregationValues;
//	}

	
	
	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime
//				* result
//				+ ((aggregationColumns == null) ? 0 : aggregationColumns
//						.hashCode());
//		result = prime
//				* result
//				+ ((aggregationExpressions == null) ? 0
//						: aggregationExpressions.hashCode());
//		result = prime
//				* result
//				+ ((aggregationFunctions == null) ? 0 : aggregationFunctions
//						.hashCode());
//		result = prime * result
//				+ ((aggregationKeys == null) ? 0 : aggregationKeys.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		PreAggregation other = (PreAggregation) obj;
//		
//		if(aggregationKeys.size()!= other.getAggregationKeys().size())return false;
//		int i = 0;
//		for (String aggKey : aggregationKeys) {
//			if(!aggKey.equals(other.getAggregationKeys().get(i)))return false;
//		i++;
//		}
//		
//		if(aggregationExpressions.size()!= other.getAggregationExpressions().size())return false;
//		i = 0;
//		for (String aggKey : aggregationExpressions) {
//			if(!aggKey.equals(other.getAggregationExpressions().get(i)))return false;
//		i++;
//		}
//		
//		if(aggregationFunctions.size()!= other.getAggregationFunctions().size())return false;
//		i = 0;
//		for (String aggKey : aggregationFunctions) {
//			if(!aggKey.equals(other.getAggregationFunctions().get(i)))return false;
//		i++;
//		}
//		return true;
//	}
//
//	@Override
//	public boolean similar(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		PreAggregation other = (PreAggregation) obj;
//
//		if(aggregationKeys.size()!= other.getAggregationKeys().size())return false;
//		int i = 0;
//		for (String aggKey : aggregationKeys) {
//			if(!aggKey.equals(other.getAggregationKeys().get(i)))return false;
//		i++;
//		}
//		
//		
//		return true;
//	}
	
	
	@Override
	public int computeCost(int outputFlow) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Node getSplitOperation() {
		// TODO Auto-generated method stub
		return null;
	}






	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}






	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}






	@Override
	public boolean similar(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}


	

	


	
	

}
