package de.webdataplatform.settings;

public class TaskCreate extends Task{

	private int numRegions;

	public TaskCreate(String name, String table, String type, int numRegions) {
		super(name, table, type);
		this.numRegions = numRegions;
	}

	public int getNumRegions() {
		return numRegions;
	}

	public void setNumRegions(int numRegions) {
		this.numRegions = numRegions;
	}


	

}
