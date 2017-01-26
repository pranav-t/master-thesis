package de.webdataplatform.view.operation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.webdataplatform.basetable.BaseTableUpdate;
import de.webdataplatform.graph.CostModel;
import de.webdataplatform.graph.Node;
import de.webdataplatform.parser.ColumnName;
import de.webdataplatform.parser.ColumnSequence;
import de.webdataplatform.system.TableRecord;



public class Sort extends ViewOperation{


	private ColumnSequence orderBy;
	
//	private String rowKey;

	public Sort(ColumnSequence orderBy, boolean rowKeyChanged) {
		super("\u03c4", rowKeyChanged);
		this.orderBy = orderBy;
		
		
	}
	

	
	


	public List<ColumnName> getColumns() {

		return orderBy.getOutputColumns();
		
	}


	@Override
	public String getViewRecordKey(BaseTableUpdate btu) {

//		if(btu.getBaseRecord()==null && (btu.getOperationType().equals("Delete") || btu.getOperationType().equals("DeleteColumn") || btu.getOperationType().equals("DeleteFamily"))){
//			return btu.getOldBaseRecord().getKey();
//		}
		
		return btu.getRecord().getKey();
	}


	
	@Override
	public int computeFlow(int inputFlow) {

		
		return inputFlow;
	}
	
	@Override
	public int computeCost(int outputFlow) {
		
		float cost = outputFlow*CostModel.PUT_WEIGHT;
		
		return (int)cost;
	}
	

	@Override
	public String toString() {
		
		return this.symbol+"["+orderBy.getColumns()+"]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + orderBy.hashCode();
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
		Sort other = (Sort) obj;
		if (!orderBy.equals(other.orderBy))
			return false;
		return true;
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

//
//	@Override
//	public boolean equivalent(Operation operation) {
//		
//		if(!operation.getSymbol().equals(symbol))return false;
//		else{
//			
//			Projection projection = (Projection)operation;
//			for (int i = 0; i < columns.length; i++) {
//				for (int j = 0; j < projection.getColumns().length; j++) {
//					if(columns[i] != projection.getColumns()[j])return false;
//				}
//			}
//			
//		}
//		
//		
//		return true;
//	}

	
	


	


	
	

}
