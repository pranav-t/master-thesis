package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.webdataplatform.system.TableRecord;

public class Conjunction extends BooleanOperator{

	
	
	
	
	public Conjunction(IPredicate leftPredicate) {
		super(leftPredicate);
	}
	
	
	public Conjunction(IPredicate leftPredicate, IPredicate rightPredicate) {
		super(leftPredicate, rightPredicate);
	}
	
	@Override
	public String toString() {
		return "[" + leftPredicate
				+ " and " + rightPredicate + "]";
	}
	

	@Override
	public boolean eval(TableRecord tableRecord)  throws Exception{
		
		
		return leftPredicate.eval(tableRecord) && rightPredicate.eval(tableRecord);
	}


	@Override
	public Map<IPredicate, Set<String>> getEvaluationMap() {
		
		Map<IPredicate, Set<String>> result = new HashMap<IPredicate, Set<String>>();
		
		result.putAll(leftPredicate.getEvaluationMap());
		result.putAll(rightPredicate.getEvaluationMap());
		
		return result;
	}



	
}
