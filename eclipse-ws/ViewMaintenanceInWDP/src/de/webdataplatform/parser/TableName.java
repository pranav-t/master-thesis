package de.webdataplatform.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableName implements ISource{

	
	private String name;

	
	
	
	public TableName(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String tableName) {
		this.name = tableName;
	}



	public static boolean isTableName(String token) {
		
		if(SQL.isSQLKeyword(token))return false;
		
		Pattern logEntry = Pattern.compile("[a-zA-Z]+");
		
		Matcher matchPattern = logEntry.matcher(token);
		
		if(matchPattern.find()){
			return true;
		}
		
		return false;
		
	}

	@Override
	public String toString() {
		return "TableName [tableName=" + name +"]";
	}


	
	
	
	
	
	
}
