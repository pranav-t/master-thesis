package de.webdataplatform.view.operation;

import de.webdataplatform.basetable.BaseTableUpdate;
import de.webdataplatform.graph.CostModel;
import de.webdataplatform.graph.Node;
import de.webdataplatform.system.TableRecord;



public class Materialize extends ViewOperation{




	public Materialize(String tableName, boolean rowKeyChanged) {
		super("Materialize", rowKeyChanged);
		this.tableName = tableName;

	}

	private String tableName;

	@Override
	public int computeCost(int outputFlow) {
		
		float cost = outputFlow*CostModel.PUT_WEIGHT;
		
		return (int)cost;
	}



	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public int computeFlow(int inputFlow) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((tableName == null) ? 0 : tableName.hashCode());
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
		Materialize other = (Materialize) obj;
		if (tableName == null) {
			if (other.tableName != null)
				return false;
		} else if (!tableName.equals(other.tableName))
			return false;
		return true;
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
//			Selection selection = (Selection)operation;
//			for (int i = 0; i < predicate.length; i++) {
//				for (int j = 0; j < selection.getPredicate().length; j++) {
//					if(predicate[i] != selection.getPredicate()[j])return false;
//				}
//			}
//			
//		}
//		
//		
//		return true;
//	}





	


	


	
	

}
