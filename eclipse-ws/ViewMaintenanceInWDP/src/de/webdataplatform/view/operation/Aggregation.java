package de.webdataplatform.view.operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.util.Bytes;
import org.mortbay.log.Log;

import de.webdataplatform.basetable.BaseTableUpdate;
import de.webdataplatform.graph.Node;
import de.webdataplatform.system.TableRecord;




public class Aggregation extends ViewOperation{


	private List<String> aggregationKeys=new ArrayList<String>();
	private List<String> aggregationColumns=new ArrayList<String>();
	private List<String> aggregationFunctions=new ArrayList<String>();
	private List<String> aggregationExpressions=new ArrayList<String>();
	
	
	public List<String> getDefinedFunctions(){
		
		List<String> keywords = new ArrayList<String>();
		keywords.add("sum");
		keywords.add("count");
		keywords.add("min");
		keywords.add("max");
		keywords.add("avg");

		return keywords;
	}
	
	public boolean containsDefinedFunction(String expression){
		
		boolean result = false;
		
		for (String function : getDefinedFunctions()) {
			if(expression.contains(function))result=true;
		}
		
		return result;
		
	}
	
	public String extractFunction(String expression){
		
			
		String[] split = expression.split("\\(");
			

		return split[0];
		
	}
	
	public String extractArithmeticExpression(String expression){
		
//		System.out.println("expression: "+expression);
		String result="";
		
		
		int firstInd = expression.indexOf("(");
//		System.out.println("firsInd: "+firstInd);

		
		int lastInd = expression.lastIndexOf(")");
//		System.out.println("lastInd: "+lastInd);

		result=expression.substring(firstInd+1, lastInd);
//		System.out.println("result: "+result);
		
		return result;
		
	}


	public Aggregation(String[] groupBy, String[] columns, Map<String, String> columnMapping, boolean rowKeyChanged) {
		super("\u03B3", rowKeyChanged);
		
		aggregationKeys = Arrays.asList(groupBy);
		for (String col : columns) {
			
			String expression = columnMapping.get(col);

			if(containsDefinedFunction(expression)){
				
				
				aggregationColumns.add(col);
				
				aggregationFunctions.add(extractFunction(expression));
				
				aggregationExpressions.add(extractArithmeticExpression(expression));

				

			}
			
		}
		
		System.out.println("aggregationKeys:"+aggregationKeys);
		System.out.println("aggregationColumns:"+aggregationColumns);
		System.out.println("aggregationFunctions:"+aggregationFunctions);
		System.out.println("aggregationExpressions:"+aggregationExpressions);
		
		
		
		
		
		this.aggregationColumns = new ArrayList<String>();
		this.aggregationFunctions = new ArrayList<String>();
		
		
		
		

		

	}
	
//	public String getViewRecordKey(BaseTableUpdate btu) {
//		
//		String key = "";
//
//		
//		if(btu.getOperationType().equals("Put"))
//			key = btu.getBaseRecord().getColumns().get(getAggregationKey());
//		if(btu.getOperationType().equals("Delete") || btu.getOperationType().equals("DeleteColumn") || btu.getOperationType().equals("DeleteFamily"))
//			key = btu.getOldBaseRecord().getColumns().get(getAggregationKey());
//			
//		
//		return key;
//		
//		
//	}	
	
	public List<byte[]> getViewRecordColumns(ViewOperation viewOperation) {
		
		List<byte[]> getList = new ArrayList<byte[]>();
		
		if(viewOperation instanceof Aggregation){
			
			Aggregation aggregation = (Aggregation)viewOperation;
			List<String> aggValCols = aggregation.getAggregationValues();
			List<String> aggFuncs = aggregation.getAggregationFunctions();
			
			for (int i = 0; i < aggValCols.size(); i++) {
				
				String aggValCol = aggValCols.get(i);
				String aggFunc = aggFuncs.get(i);
				
				if(!aggFunc.equals("index"))getList.add(Bytes.toBytes(aggValCol+"_"+aggFunc));
			}
			
			
			

			
		}
//		if(viewOperation instanceof Delta){
//			
//			CreateDeltaView cDV = CreateDeltaView.parse(btu.getViewDefinition());
//			
//			for(String key : cDV.getColumns()){
//				
//				getList.add(Bytes.toBytes(key+"_new"));
//			}
//			
//		}
//		if(viewOperation.equals(ViewOperation.REVERSE_JOIN)){
//			getList=null;
//		}
		
		return getList;
	}
	
	
//
//	public Aggregation(String aggregationKey, String[] aggregationValues, String[] aggregationFunctions) {
//		super("\u03B3");
//		this.aggregationKey = aggregationKey;
//		this.aggregationValues = aggregationValues;
//		this.aggregationFunctions = aggregationFunctions;
//	}
	
	
	
	

	public String getAggregationKey() {
		return aggregationKeys.get(0).split("\\.")[1];
	}


	
	
	public List<String> getAggregationKeys() {
		return aggregationKeys;
	}

	public void setAggregationKeys(List<String> aggregationKeys) {
		this.aggregationKeys = aggregationKeys;
	}




	@Override
	public int computeFlow(int inputFlow) {

		
		return inputFlow;
	}
	
	@Override
	public String toString() {
		
		return this.symbol+"["+aggregationKeys+";"+aggregationColumns+";"+aggregationFunctions+"]";
	}









	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((aggregationColumns == null) ? 0 : aggregationColumns
						.hashCode());
		result = prime
				* result
				+ ((aggregationExpressions == null) ? 0
						: aggregationExpressions.hashCode());
		result = prime
				* result
				+ ((aggregationFunctions == null) ? 0 : aggregationFunctions
						.hashCode());
		result = prime * result
				+ ((aggregationKeys == null) ? 0 : aggregationKeys.hashCode());
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
		Aggregation other = (Aggregation) obj;
		if (aggregationColumns == null) {
			if (other.aggregationColumns != null)
				return false;
		} else if (!aggregationColumns.equals(other.aggregationColumns))
			return false;
		if (aggregationExpressions == null) {
			if (other.aggregationExpressions != null)
				return false;
		} else if (!aggregationExpressions.equals(other.aggregationExpressions))
			return false;
		if (aggregationFunctions == null) {
			if (other.aggregationFunctions != null)
				return false;
		} else if (!aggregationFunctions.equals(other.aggregationFunctions))
			return false;
		if (aggregationKeys == null) {
			if (other.aggregationKeys != null)
				return false;
		} else if (!aggregationKeys.equals(other.aggregationKeys))
			return false;
		return true;
	}
	
	

	public List<String> getAggregationValues() {
		List<String> result = new ArrayList<String>();
		for (String string : aggregationColumns) {
			result.add(string.split("\\.")[1]);
		}
		return result;
	}

	public void setAggregationValues(List<String> aggregationValues) {
		this.aggregationColumns = aggregationValues;
	}

	public List<String> getAggregationFunctions() {
		return aggregationFunctions;
	}

	public void setAggregationFunctions(List<String> aggregationFunctions) {
		this.aggregationFunctions = aggregationFunctions;
	}
	



	@Override
	public int computeCost(int outputFlow) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getViewRecordKey(BaseTableUpdate btu) {
		// TODO Auto-generated method stub
		return null;
	}

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

	@Override
	public String getViewRecordKey(TableRecord baseRecord) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Override
//	public boolean equivalent(Operation operation) {
//		
//		if(!operation.getSymbol().equals(symbol))return false;
//		else{
//			
//			Aggregation aggregation = (Aggregation)operation;
//			for (int i = 0; i < aggregationKeys.length; i++) {
//				for (int j = 0; j < aggregation.getAggregationKeys().length; j++) {
//					if(aggregationKeys[i] != aggregation.getAggregationKeys()[j])return false;
//				}
//			}
//			for (int i = 0; i < aggregationValues.length; i++) {
//				for (int j = 0; j < aggregation.getAggregationValues().length; j++) {
//					if(aggregationValues[i] != aggregation.getAggregationValues()[j])return false;
//				}
//			}
//		}
//		
//		
//		return true;
//	}
	


	
	

}
