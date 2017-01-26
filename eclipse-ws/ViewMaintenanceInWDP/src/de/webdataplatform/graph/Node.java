package de.webdataplatform.graph;

public abstract class Node {


	protected int cost;
	
	public static int ALL=0;
	public static int TABLE_NODE=1;
	public static int OPERATION_NODE=2;
	
	
	public abstract String toString();
	
	public abstract String toExtendedString();

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}


}
