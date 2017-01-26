package de.webdataplatform.settings;

public class KeyDefinition {

	
	private String name;

	private String prefix;
	
	private long startRange;
	
	private long endRange;

	
	public long getNumOfPrimaryKeys(){
		
		return endRange - startRange;
	}
	

	public KeyDefinition(String name, String prefix, long startRange, long endRange) {
		super();
		this.name = name;
		this.prefix = prefix;
		this.startRange = startRange;
		this.endRange = endRange;
	}

	
	
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public long getStartRange() {
		return startRange;
	}

	public void setStartRange(long startRange) {
		this.startRange = startRange;
	}

	public long getEndRange() {
		return endRange;
	}

	public void setEndRange(long endRange) {
		this.endRange = endRange;
	}

	
	@Override
	public String toString() {
		return "KeyDefinition [name=" + name + ", prefix=" + prefix
				+ ", startRange=" + startRange + ", endRange=" + endRange + "]";
	}


	public KeyDefinition copy(){
		
		
		return new KeyDefinition(this.name, this.prefix, this.startRange, this.endRange);
		
		
	}
	
	
	
}
