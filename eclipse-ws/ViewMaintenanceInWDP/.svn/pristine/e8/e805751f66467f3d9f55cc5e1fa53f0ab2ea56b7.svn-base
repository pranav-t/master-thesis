package de.webdataplatform.parser;


public class Column {

	
	private ITerm term;
	
	private String alias;
	
	public Column(String termString) {
		super();
		
		TokenStream tokenStream = new TokenStream(Tokenizer.tokenize(termString));
		this.term = Parser.parseTerm(tokenStream);
		
	}
	
	public Column(String termString, String alias) {
		super();
		
		TokenStream tokenStream = new TokenStream(Tokenizer.tokenize(termString));
		this.term = Parser.parseTerm(tokenStream);
		this.alias = alias;
		
	}

	
	public Column(ITerm term) {
		super();
		this.term = term;
	}

	public Column(ITerm term, String alias) {
		super();
		this.term = term;
		this.alias = alias;
	}
	
//	public 
	

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
	

	public ITerm getTerm() {
		return term;
	}

	public void setTerm(ITerm term) {
		this.term = term;
	}
	
	public ColumnName getColumnName(){
		
		if(term instanceof ColumnName)
		return (ColumnName)term;
		else return new ColumnName(alias);
		
	}
	

	@Override
	public String toString() {
		return "Column [term=" + term + ", alias="+alias+"]";
	}

//	@Override
//	public boolean eval(Map<String, String> columns) throws Exception {
//		// TODO Auto-generated method stub
//		return false;
//	}


	
	
	
	
	
	
}
