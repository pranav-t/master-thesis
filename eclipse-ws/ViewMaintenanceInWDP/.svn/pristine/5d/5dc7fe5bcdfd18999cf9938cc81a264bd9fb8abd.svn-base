package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

	
	
	/**
	 * Splits a string into multiple tokens
	 * @param string
	 * @return
	 */
	
	public static List<String> tokenize(String string){
		
		String resultString = findStrings(string);
//		System.out.println("xx:"+resultString);
		
		List<String> result = new ArrayList<String>();
		String[] tokens = resultString.split(" ");
		
		for (int i = 0; i < tokens.length; i++) {
			
			result.addAll(splitString(tokens[i].replace("Œ", " ").trim()));

			
		}
		return result;
		
		
	}
	
	public static String findStrings(String string) {
		
		System.out.println("Searching constants");
		Pattern logEntry = Pattern.compile("\\'[\\w_\\s]*\\'|\\'[\\d_\\-]*\\'");
		
		Matcher matchPattern = logEntry.matcher(string);
		
	    while (matchPattern.find()) {	     

	    		string = string.replace(matchPattern.group(), matchPattern.group().replace(" ", "Œ"));
	    		System.out.println(matchPattern.group());
		    }

		return string;
		
	}
	

	public static List<String> splitString(String string){
		
		List<String> result = new ArrayList<String>();
		
		List<Integer> charIndices=new ArrayList<Integer>();
		char c=' ';
		
		for (int i = 0, n = string.length(); i < n; i++) {
		    
			c = string.charAt(i);
//			System.out.println("D:"+c);
		    if(isSpecialCharacter(c)){
		    	
		    	charIndices.add(i);
		    }
		    
		}
		int start = 0;
		for (Integer index : charIndices) {
		
			if(!string.substring(start, index).equals(""))result.add(string.substring(start, index));
			if(!(string.charAt(index)+"").equals(""))result.add(string.charAt(index)+"");
			start = index + 1;
			
		}
		if(!string.substring(start, string.length()).equals(""))result.add(string.substring(start, string.length()));
		return result;

	}
	
	
	
	public static char[] getSpecialCharacters(){
		
		return new char[]{',','(',')','/','+',';'};
		
		
	}
	
	public static boolean isSpecialCharacter(char c){
		
		
		for (char temp : getSpecialCharacters()) {
			if(temp == c)return true;
		}
		return false;
		
	}



	





	

	
}
