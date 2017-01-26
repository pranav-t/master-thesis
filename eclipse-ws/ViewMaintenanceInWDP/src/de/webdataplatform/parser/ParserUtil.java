package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserUtil {

	
	
	/**
	
	
	public static List<String> tokenize(String string){
		
		List<String> result = new ArrayList<String>();
		String[] tokens = string.split(" ");
//		System.out.println(tokens.length);
		
		for (int i = 0; i < tokens.length; i++) {
			
			result.addAll(splitString(tokens[i].trim()));

			
		}
		return result;
		
		
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
		
		return new char[]{',','(',')','*','/','+','-'};
		
		
	}
	
	public static boolean isSpecialCharacter(char c){
		
		
		for (char temp : getSpecialCharacters()) {
			if(temp == c)return true;
		}
		return false;
		
	}

**/

	

	public static boolean containsArrayKey(String[] array, String key){
		
		for (String string : array) {
			if(string.contains(key))return true;
		}
		return false;
	}

	
	public static boolean isTokenKeyword(List<String> keywords, String token){
			
		for (String keyword : keywords) {
			if(token.equals(keyword)){
				return true;
			}
		}
		return false;
		
	}
	
	




	
	
	public static String searchTableMatching(String[] predicate1, String[] predicate2){
		
		
		String matchingTable=null;
		for (int i = 0; i < predicate1.length; i++) {
			
			for (int j = 0; j < predicate2.length; j++) {
				
				if(predicate1[i].split("\\.")[0].equals(predicate2[j].split("\\.")[0])){
					matchingTable= predicate1[i].split("\\.")[0];
				}
				
			}
			
		}
		return matchingTable;
	}
	
	
	

	
	

	
	

	

	public static String[] mergeArrays(String[] array1, String[] array2){
		
		String[] result = new String[array1.length+array2.length];
		
		for (int i = 0; i < array1.length; i++) {
			result[i] = array1[i];
		}
		for (int j = 0; j < array2.length; j++) {
			result[array1.length+j] = array2[j];
		}
		
		return result;
	}
	
	public static String[] removeFunctions(String[] tokens){
		
		String[] result = tokens;
		
		for (int i = 0; i < result.length; i++) {
		
			if(result[i].contains("(")){
				
//				System.out.println("matching pattern:");
				
				Pattern logEntry = Pattern.compile("\\((.*?)\\)");
				Matcher matchPattern = logEntry.matcher(result[i]);
				boolean find = matchPattern.find();
				
				if(find){
					result[i] = matchPattern.group(1);
//					System.out.println("xx:"+matchPattern.group(1));
				}
			}
			
		}
		return result;
		
		
	}
	
	public static int search(String[] array, String key){
		int i = 0;
		for (String string : array) {
			if(string.equals(key)){
				return i;
			}else{
				i++;
			}
		}
		return -1;
		
	}
	





	

	
}
