package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.webdataplatform.system.TableRecord;

public abstract class BooleanOperator implements IPredicate{

	
	protected IPredicate leftPredicate;
	
	protected IPredicate rightPredicate;

	public BooleanOperator(IPredicate leftPredicate) {
		super();
		this.leftPredicate = leftPredicate;

	}
	
	public BooleanOperator(IPredicate leftPredicate, IPredicate rightPredicate) {
		super();
		this.leftPredicate = leftPredicate;
		this.rightPredicate = rightPredicate;
	}
	
	
	
	
	
	
	
	
	

	public IPredicate getLeftPredicate() {
		return leftPredicate;
	}

	public void setLeftPredicate(IPredicate leftPredicate) {
		this.leftPredicate = leftPredicate;
	}

	public IPredicate getRightPredicate() {
		return rightPredicate;
	}

	public void setRightPredicate(IPredicate rightPredicate) {
		this.rightPredicate = rightPredicate;
	}
	
	
	

	@Override
	public boolean eval(TableRecord tableRecord)  throws Exception{

		return false;
	}

	@Override
	public List<Condition> getColumnConditions() {
		
		List<Condition> result = new ArrayList<Condition>();
		result.addAll(leftPredicate.getColumnConditions());
		result.addAll(rightPredicate.getColumnConditions());
		
		return result;
	}


	@Override
	public List<Condition> getJoinConditions() {
//		System.out.println("leftPredicate:"+leftPredicate);
		List<Condition> result = new ArrayList<Condition>();
		result.addAll(leftPredicate.getJoinConditions());
		result.addAll(rightPredicate.getJoinConditions());
		
		return result;
	}



	

}
