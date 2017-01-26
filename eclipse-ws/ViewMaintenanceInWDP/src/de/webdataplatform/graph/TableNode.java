package de.webdataplatform.graph;

import de.webdataplatform.system.Table;


public class TableNode extends Node{


	private Table table;

	
	
	
	
	public TableNode(Table table) {
		super();
		this.table = table;
	}
	
	
	public String toString() {
		return table.toString();
	}

	public String toExtendedString() {
		return table.toExtendedString()+":"+cost;
	}


	public Table getTable() {
		return table;
	}


	public void setTable(Table table) {
		this.table = table;
	}



	
	


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
