package de.webdataplatform.parser;

import java.util.List;

public class TokenStream {

	
	private List<String> tokens;
	
	
	
	
	
	public TokenStream(List<String> tokens) {
		super();
		this.tokens = tokens;
	}

	public String nextToken(){
		
		String result;
		if(tokens.size()> 0){
			result = tokens.get(0);
			return result;
		}else return null;
		
	}		
	
	public String consumeToken(){
		
		String result  = tokens.get(0);
		tokens.remove(0);
		return result;
		
	}
	public void pushToken(String token){
		
		tokens.add(0, token);
		
	}
	
	public boolean hasNext(){
		
		if(tokens.size()> 0){
			return true;
		}else return false;
		
	}

	public List<String> getTokens() {
		return tokens;
	}

	public void setTokens(List<String> tokens) {
		this.tokens = tokens;
	}
	
	
	
	
	
	

}
