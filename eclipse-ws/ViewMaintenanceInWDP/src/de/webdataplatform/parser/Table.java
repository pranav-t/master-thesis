package de.webdataplatform.parser;


public class Table {

	
	private ISource source;
	
	private String alias;
	


	
	

	public Table(ISource table) {
		super();
		this.source = table;
	}

	public Table(ISource table, String alias) {
		super();
		this.source = table;
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public ISource getSource() {
		return source;
	}

	public void setSource(ISource table) {
		this.source = table;
	}

	@Override
	public String toString() {
		return "Table [table=" + source + ", alias=" + alias + "]";
	}



	
	
//	@Override
//	public boolean eval(Map<String, String> columns) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}


	
	
	
	
	
	
}
