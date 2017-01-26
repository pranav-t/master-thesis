package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.webdataplatform.system.TableRecord;

public class Disjunction extends BooleanOperator{



	public Disjunction(IPredicate leftPredicate, IPredicate rightPredicate) {
		super(leftPredicate, rightPredicate);
	}

	
	
	@Override
	public String toString() {
		return "[" + leftPredicate
				+ " or " + rightPredicate + "]";
	}
	
	@Override
	public boolean eval(TableRecord tableRecord)  throws Exception{
		
		
		return leftPredicate.eval(tableRecord) || rightPredicate.eval(tableRecord);
	}
	
	@Override
	public Map<IPredicate, Set<String>> getEvaluationMap() {
		
		Map<IPredicate, Set<String>> result = new HashMap<IPredicate, Set<String>>();
		
		Set<String> tables = new HashSet<String>();
		
		for (IPredicate predicate : leftPredicate.getEvaluationMap().keySet()) {
			
			for (String table : leftPredicate.getEvaluationMap().get(predicate)) {
				
				if(!tables.contains(table))tables.add(table);
			}
			
		}	
		for (IPredicate predicate  : rightPredicate.getEvaluationMap().keySet()) {
			
			for (String table : rightPredicate.getEvaluationMap().get(predicate)) {
				
				if(!tables.contains(table))tables.add(table);
			}
			
		}	
		
		result.put(this, tables);
		
		return result;
	}
	
}
