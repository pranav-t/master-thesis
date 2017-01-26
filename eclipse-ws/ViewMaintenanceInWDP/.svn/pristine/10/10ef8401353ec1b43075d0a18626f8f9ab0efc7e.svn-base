package de.webdataplatform.view.operation;

import de.webdataplatform.basetable.BaseTableUpdate;
import de.webdataplatform.graph.CostModel;
import de.webdataplatform.graph.Node;
import de.webdataplatform.parser.IPredicate;
import de.webdataplatform.system.TableRecord;



public class Selection extends ViewOperation{


	private IPredicate predicate;
	private float selectivity;
//	private boolean delta;

	public Selection(IPredicate predicate, float selectivity, boolean rowKeyChanged) {
		super("\u03C3", rowKeyChanged);
		this.predicate = predicate;
		this.selectivity = selectivity;
	}

	public Selection(IPredicate predicate, boolean rowKeyChanged) {
		super("\u03C3", rowKeyChanged);
		this.predicate = predicate;
		
	}


	
	public IPredicate getPredicate() {
		return predicate;
	}




	public float getSelectivity() {
		return selectivity;
	}




	public void setSelectivity(float selectivity) {
		this.selectivity = selectivity;
	}


	@Override
	public int computeFlow(int inputFlow) {
				
		float result = inputFlow*selectivity;
		
		return (int)result;
	}

	@Override
	public int computeCost(int inputFlow) {
		
		float cost = inputFlow*CostModel.GET_WEIGHT+(inputFlow*selectivity)*CostModel.PUT_WEIGHT;
		
		return (int)cost;
	}

	
	

	@Override
	public String toString() {

		return this.symbol+predicate;
	}


	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((predicate == null) ? 0 : predicate.hashCode());
		result = prime * result + Float.floatToIntBits(selectivity);
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
		Selection other = (Selection) obj;
		if(!predicate.equals(other.getPredicate()))
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
		Selection other = (Selection) obj;
		

		return true;
	}	
	
	
	@Override
	public String getViewRecordKey(BaseTableUpdate btu) {

		return btu.getRecord().getKey();
	}

	@Override
	public Node getSplitOperation() {
		
//		Selection selection = new Selection(new ArrayList<String[]>(predicate),selectivity, super.isRowKeyChanged());
//		
//		OperationNode operationNode = new OperationNode(selection);
//		
//		return operationNode;
		return null;
	}

	@Override
	public String getViewRecordKey(TableRecord baseRecord) {

		return baseRecord.getKey();
	}









	


	


	
	

}
