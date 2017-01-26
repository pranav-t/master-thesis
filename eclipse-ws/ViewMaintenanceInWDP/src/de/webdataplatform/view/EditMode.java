package de.webdataplatform.view;

public enum EditMode {

	INSERT("INSERT"),
	DELETE("DELETE");
	
	private String name;
	
	private EditMode(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
}
