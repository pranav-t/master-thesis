package de.webdataplatform.graph;

import de.webdataplatform.view.operation.ViewOperation;


public class OperationNode extends Node{


	private ViewOperation operation;
	

	public OperationNode(ViewOperation operation) {
		super();
		this.operation = operation;
	}	



	public ViewOperation getOperation() {
		return operation;
	}

	public void setOperation(ViewOperation operation) {
		this.operation = operation;
	}

	@Override
	public String toString() {
		
		return operation.toString();
	}

	@Override
	public String toExtendedString() {
		// TODO Auto-generated method stub
		return operation.toString()+":"+cost;
	}

//	private int flow;
	

	
	
	
	
	
	


//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((table == null) ? 0 : table.hashCode());
//		return result;
//	}
//
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Node other = (Node) obj;
//		if (table == null) {
//			if (other.table != null)
//				return false;
//		} else if (!table.equals(other.table))
//			return false;
//		return true;
//	}	
	
	
	
	
	

}
