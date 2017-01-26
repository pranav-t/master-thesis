package de.webdataplatform.system;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.webdataplatform.parser.SQL;

public class TypeChecker {



	

	private static final Pattern INT_PATTERN  = Pattern.compile(
			"^-?\\d+$");
	private static final Pattern FLOAT_PATTERN = Pattern.compile(
		    "^[0-9]+(\\.)[0-9]*$");
	private static final Pattern DATE_PATTERN = Pattern.compile(
		    "^[0-9]{4}-[0-9]{2}-[0-9]{2}$");
	
	
	
	public static boolean isFloat(String s)
	{
	    return FLOAT_PATTERN.matcher(s).matches();
	}
	
	public static boolean isInteger(String s)
	{
	    return INT_PATTERN.matcher(s).matches();
	}	

	public static boolean isDate(String s)
	{
	    return DATE_PATTERN.matcher(s).matches();
	}	
	
	
	
//	
//	public static boolean isDate(String string) {
//
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		
//		try {
//			format.e
//		} catch (ParseException e) {
//
//			return false;
//		}
//
//		return true;
//
//	}	
	
	
	public static boolean isString(String token) {
		
		if(SQL.isSQLKeyword(token))return false;
		
		Pattern logEntry = Pattern.compile("\\'[\\w_\\s]*\\'");
		
		Matcher matchPattern = logEntry.matcher(token);
		
		if(matchPattern.find()){
			return true;
		}
		
		return false;
		
	}

	
	
	
	
	
	
	
	

}
