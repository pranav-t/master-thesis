package de.webdataplatform.parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.webdataplatform.system.TableRecord;
import de.webdataplatform.system.TypeChecker;

public class ColumnName implements ITerm{

	public static String TABLE_COLUMN_SEPARATOR=".";
	
	private String table;
	
	private String name;
	
	
	
	public ColumnName() {
		super();

	}
	
	public ColumnName(String fullName) {
		super();
		
		String[] splitName = fullName.split("\\"+TABLE_COLUMN_SEPARATOR);
		
		if(splitName.length == 1){
			
			name = fullName;
		}
		if(splitName.length == 2){
			
			table = splitName[0];
			name = splitName[1];
		}
	}
	
	

	public ColumnName(String table, String name) {
		super();
		this.table = table;
		this.name = name;
	}

	public String getTable(){
		
		return table;
		

		
	}
	
	public String getName(){
		
		return name;
		
	}
	
	
	public static boolean isColumnName(String token) {
		
		if(token.contains("'"))return false;
		
		if(SQL.isSQLKeyword(token))return false;
		
		if(Function.isFunction(token))return false;
		
		Pattern logEntry = Pattern.compile("\\w+\\"+TABLE_COLUMN_SEPARATOR+"[\\w_\\*]+|[a-zA-Z]+");
		Pattern logEntry2 = Pattern.compile("^[\\d]+\\.[\\d]+$");
		

		
		
		Matcher matchPattern = logEntry.matcher(token);
		Matcher matchPattern2 = logEntry2.matcher(token);
		
		return matchPattern.find() && !matchPattern2.find();
		
		
	}
	
	
	public String getFullName() {
		return table+TABLE_COLUMN_SEPARATOR+name;
	}


	
	
	@Override
	public String toString() {
		return getFullName();
	}
	
	
	
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColumnName other = (ColumnName) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		return true;
	}

	@Override
	public Object eval(TableRecord tableRecord) throws Exception {
		
		
		String value = tableRecord.resolveValue(name);
		
//		if(TypeChecker.isInteger(value)){
//			return Integer.parseInt(value);
//		}
//		if(TypeChecker.isFloat(value)){
//			return Float.parseFloat(value);
//		}
//		if(TypeChecker.isDate(value)){
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			return format.parse(value);
//			
//		}
		
		
		return value;
	}

	
	
	
	@Override
	public List<ColumnName> getBaseColumns() {
		List<ColumnName> result = new ArrayList<ColumnName>();
		result.add(this);
		return result;
	}

	@Override
	public Object evalGrouping(List<TableRecord> tableRecord) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void setName(String name) {
		this.name = name;
	}


	

}
