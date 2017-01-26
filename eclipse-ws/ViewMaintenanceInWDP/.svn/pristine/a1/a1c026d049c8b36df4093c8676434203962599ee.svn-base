package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.List;

public class TokenUtil {

	
	
	
	public static String nextToken(List<String> tokens){
		
		String result;
		if(tokens.size()> 0){
			result = tokens.get(0);
			return result;
		}else return null;
		
	}		
	
	public static String consumeToken(List<String> tokens){
		
		String result;
		if(tokens.size()> 0){
			result = tokens.get(0);
			tokens.remove(0);
			return result;
		}else return null;
		
	}
	
	
	
	
	/**
	 * 
	 * @param tokens - tokens to search
	 * @param start - token to start the parsing
	 * @param keywords - list of keyword that stop the parsing
	 * 
	 * @return
	 */
	public static List<String> consumeToNextKeyword(List<String> tokens, String start, List<String> keywords){
		
		List<String> result=new ArrayList<String>();
		boolean parse=false;
		int i = 0;
		for (String string : tokens) {
			
			
			if(string.contains(start)){
				parse=true;
			}else{
				if(keywords.contains(string)){
					parse=false;
				}
				
				if(parse){
					
					result.add(string);
				}
			}
			i++;	
		}
		return result;
		
	}	
		
		

		
		
		
//		System.out.println("uu: "+result);


	
	
	/**
	 * Splits the tokens of an array by a given separator. Tokens that
	 * are not separated by the separator will be merged together.
	 * @param array
	 * @param separator
	 * @return
	 */
	public static List<String> splitTokens(List<String> array, String separator){

		List<String> separators = new ArrayList<String>();
		separators.add(separator);
		return splitTokens(array, separators);
	}
	
	
	
	
	public static List<String> splitTokens(List<String> array, List<String> separators){
		
		List<String> result = new ArrayList<String>();
		
		List<String> term = new ArrayList<String>();
		for (String string : array) {
			
			
			boolean match=false;
			for (String separator : separators) {
				if(string.equals(separator)){
					match=true;
				}
			}
			
			if(match){
				result.add(mergeTokens(term));
				term = new ArrayList<String>();
			}else{
				term.add(string.trim());
			}
	
		}
		result.add(mergeTokens(term));
		return result;
	}
	
	/**
	 * Merges a number of tokens to a string, separated by line-space
	 * @param tokens
	 * @return
	 */
	public static String mergeTokens(List<String> tokens){
	
		String result = "";
		
		int i = 0;
		for (String token : tokens) {
			
			result += token;
			if(i != tokens.size()-1)result += " ";
			i++;
		}
		return result;
		
	}
	
	
	public static List<String> tokenList;
	
	
	public static List<String> parseHierarchical(List<String> tokens, String opener, String closer, String keyWord){
		
		 tokenList = new ArrayList<String>(tokens);
		
		 List<String> result = new ArrayList<String>();
		
		Integer numOfOpeners=0;
		Integer startIndex=null;
		Integer endIndex=null;
		
		int i = 0;
		int offset=0;
		for (String token : tokens) {
			
			if(token.contains(opener)){
				
				if(startIndex == null){
					startIndex = i;
				}
				numOfOpeners++;

			}
			if(token.contains(closer)){

				if(numOfOpeners==1){
					
					endIndex = i;
					
				}else{
					numOfOpeners--;
				}
				
			}
			if(startIndex != null && endIndex != null){

				System.out.println("sublist: "+tokens.subList(startIndex+1, endIndex));
				if(keyWord == null || tokens.subList(startIndex+1, endIndex).get(0).equals(keyWord)){
//					System.out.println("sublist: "+tokens.subList(startIndex+1, endIndex));
					
					result.add(mergeTokens(tokens.subList(startIndex+1, endIndex)));
					
					tokenList.subList(startIndex-offset, endIndex+1-offset).clear();
					offset += endIndex - startIndex+1;
				}
				
				startIndex=null;
				endIndex=null;
				numOfOpeners=0;
			}
			i++;
		}

		return result;
		
	}
	
	
	
	
	

}
