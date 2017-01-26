package de.webdataplatform.settings;

public class TaskFile extends Task{




	
	private String fileName;
	
	private int count;
	
	private int start;
	
	private int end;
	

	
	public TaskFile(String name, String table, String type, String fileName, int count, int start, int end) {
		super(name, table, type);
		this.fileName = fileName;
		this.start = start;
		this.end = end;
		this.count=count;
	}	
	

	
	public TaskFile copy(){
		
		TaskFile taskFill = new TaskFile(this.name, this.table, this.type, this.fileName, this.count, this.start, this.end);
		
		return taskFill;
	}



	public String getFileName() {
		return fileName;
	}



	public void setFileName(String fileName) {
		this.fileName = fileName;
	}



	public int getStart() {
		return start;
	}



	public void setStart(int start) {
		this.start = start;
	}



	public int getEnd() {
		return end;
	}



	public void setEnd(int end) {
		this.end = end;
	}



	public int getCount() {
		return count;
	}



	public void setCount(int count) {
		this.count = count;
	}
	

	
	
	


}
