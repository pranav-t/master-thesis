package de.webdataplatform.view.operation;

import java.util.List;

import de.webdataplatform.basetable.BaseTableUpdate;
import de.webdataplatform.graph.CostModel;
import de.webdataplatform.graph.Node;
import de.webdataplatform.parser.ColumnName;
import de.webdataplatform.parser.ColumnSequence;
import de.webdataplatform.system.TableRecord;



public class Delta extends ViewOperation{


	private List<ColumnName>  columns;
	

	public Delta(boolean rowKeyChanged, List<ColumnName> columns) {
		super("\u03B4", rowKeyChanged);
		this.columns = columns;
	}
	





	@Override
	public String getViewRecordKey(BaseTableUpdate btu) {

		return btu.getRecord().getKey();
	}


	@Override
	public String getViewRecordKey(TableRecord baseRecord) {

		return baseRecord.getKey();
	}
	
	


	@Override
	public String toString() {
		
		return this.symbol+"[]";
	}

	
	@Override
	public int computeFlow(int inputFlow) {

		return inputFlow;
	}
	
	@Override
	public int computeCost(int outputFlow) {
		
		float cost = outputFlow*CostModel.PUT_WEIGHT+outputFlow*CostModel.GET_WEIGHT;
		
		return (int)cost;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
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
		return true;
	}


	@Override
	public boolean similar(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		return true;
	}









	public List<ColumnName> getColumns() {
		return columns;
	}






	public void setColumns(List<ColumnName> columns) {
		this.columns = columns;
	}






	@Override
	public Node getSplitOperation() {
		// TODO Auto-generated method stub
		return null;
	}





	
	

}
