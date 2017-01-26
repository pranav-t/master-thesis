package de.webdataplatform.view.operation;

import de.webdataplatform.basetable.BaseTableUpdate;
import de.webdataplatform.graph.CostModel;
import de.webdataplatform.graph.Node;
import de.webdataplatform.system.TableRecord;



public class Source extends ViewOperation{



	private String baseTable;

	public Source(String baseTable, boolean rowKeyChanged) {
		super("\u25cf", rowKeyChanged);

		this.baseTable = baseTable;
	}
	





	@Override
	public String getViewRecordKey(BaseTableUpdate btu) {

		return btu.getRecord().getKey();
	}




	@Override
	public String toString() {
		
		return this.symbol+"["+baseTable+"]";
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
		result = prime * result
				+ ((baseTable == null) ? 0 : baseTable.hashCode());
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
		Source other = (Source) obj;
		if (baseTable == null) {
			if (other.baseTable != null)
				return false;
		} else if (!baseTable.equals(other.baseTable))
			return false;
		return true;
	}






	@Override
	public boolean similar(Object obj) {

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
	
	
	


	
	

}
