package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class Predicate {


	
	
	private List<Condition> conditions;

	public Predicate() {
		super();
		conditions = new ArrayList<Condition>();
		
	}

	public void addCondition(Condition condition){
		
		this.conditions.add(condition);
		
	}

	
	public static List<String> getConjunctions(){
		 List<String> result = new ArrayList<String>();
		 result.add("or");
		 result.add("and");
		 return result;
	}
	
	public boolean isConjunction(String token){
		
		if(token.equals("and") || token.equals("or")){
			
			return true;
		}
		return false;
	}
	
	
	public List<String> getTerms(List<String> tokens){
		
		List<String> result = new ArrayList<String>();

		
		for (String token : tokens) {
			
			switch(token){
			
				case "(" :
					System.out.println("bracket");
				break;
				
			
			
			
			}
			
		}
		return result;
		
		
	}
		
		
	
	public static List<String[]> getPredicateStrings(List<Condition> joinConditions){
		
		List<String[]> result = new ArrayList<String[]>();
		
		
		for (Condition condition : joinConditions) {
			
			
			result.add(new String[]{condition.getColumnName().getFullName(), ((ColumnName)condition.getValue()).getFullName()});
			
		}
		
		return result;
		
		
	}
	
	public static List<String[]> mergeAll(List<String[]> predicates){
		

		String matching=null;
		
		int startElement=0;
		int matchingPos=0;


		for (int i = 0; i < predicates.size(); i++) {
			for (int j = i+1; j < predicates.size(); j++) {
	


					matching = searchColumnMatching(predicates.get(i), predicates.get(j));

					if(matching != null){
						startElement = i;
						matchingPos = j;
						break;
					}

			}
			if(matching != null){
				break;
			}
		}
		
		
		if(matching != null){
			return mergeAll(mergePredicates(predicates, startElement, matchingPos));
		}else{
			return predicates;
		}
		


	}	

		
	
	public static String searchColumnMatching(String[] predicate1, String[] predicate2){
		
		
		String matchingColumn=null;
		for (int i = 0; i < predicate1.length; i++) {
			
			for (int j = 0; j < predicate2.length; j++) {
				
				if(predicate1[i].equals(predicate2[j])){
					matchingColumn= predicate1[i];
				}
				
			}
			
		}
		return matchingColumn;
	}
	
	
	public static List<String[]> mergePredicates(List<String[]> predicates, int i, int j){
		
		List<String[]> result = new ArrayList<String[]>();
		
		System.out.println("i:"+i+", j"+j+", predicates:"+predicates);
		
		for (int k = 0; k < predicates.size(); k++) {
			
			
			if(k == i){
				String matching = searchColumnMatching(predicates.get(i), predicates.get(j));
				String[] mergedPred = mergePredicates(predicates.get(i), predicates.get(j), matching);
				result.add(mergedPred);
			}
			if(k != i && k != j){
				
				result.add(predicates.get(k));
			}
			
		}
		return result;

		
	}	
	
	
	public static String[] mergePredicates(String[] predicate1, String[] predicate2, String matching){
		
		
		String[] result = new String[predicate1.length+predicate2.length-1];
		
		for (int i = 0; i < predicate1.length; i++) {
			
			result[i]=predicate1[i];
		}
		
		String[] pred2 = removeElement(predicate2, matching);
		
		for (int i = 0; i < pred2.length; i++) {
			result[predicate1.length+i]=pred2[i];
		}
		
		return result;
	}	

	
	
	public static String[] removeElement(String[] array, String element){
		
		String[] result=array;
		int pos = Arrays.binarySearch(array, element);
		
		if(pos > -1){
			result = new String[array.length-1];
			
			int k=0;
			for (int j = 0; j < array.length; j++) {
				if(j != pos){
					result[k]=array[j];
					k++;
				}
			}
			
			
		}
		return result;
		
		
	}
	
	
	
	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	@Override
	public String toString() {
		return "Predicate [conditions=" + conditions + "]";
	}
	

	

	
	

}
