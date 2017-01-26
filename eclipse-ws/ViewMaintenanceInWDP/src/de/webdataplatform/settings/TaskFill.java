package de.webdataplatform.settings;

public class TaskFill extends Task{


	
	private int numOperations;
	
	private String distribution;

	

	public TaskFill(String name, String table, String type, int numOperations, String distribution) {
		super(name, table, type);
		this.numOperations = numOperations;
		this.distribution = distribution;
	}
	

	public TaskFill copy(){
		
		TaskFill taskFill = new TaskFill(this.name, this.table, this.type, this.numOperations, this.distribution);
		
		return taskFill;
	}
	

	public int getNumOperations() {
		return numOperations;
	}

	public void setNumOperations(int numOperations) {
		this.numOperations = numOperations;
	}

	public String getDistribution() {
		return distribution;
	}

	public void setDistribution(String distribution) {
		this.distribution = distribution;
	}


	
	
	
	


}
