package de.webdataplatform.parser;

import java.util.List;

import de.webdataplatform.system.TableRecord;

public class Multiplication extends MathOperator{

	
	
	
	
	public Multiplication(ITerm leftTerm) {
		super(leftTerm);
	}
	
	
	public Multiplication(ITerm leftTerm, ITerm rightTerm) {
		super(leftTerm, rightTerm);
	}
	
	@Override
	public String toString() {
		return "[" + leftTerm+ " * " + rightTerm + "]";
	}
	


	@Override
	public Object eval(TableRecord tableRecord)  throws Exception{
		
		Object leftVal = leftTerm.eval(tableRecord);
		Object rightVal = rightTerm.eval(tableRecord);
		
		
		if(leftVal instanceof Integer && rightVal instanceof Integer){
			
			return (Integer)leftTerm.eval(tableRecord) * (Integer)rightTerm.eval(tableRecord);
		}
		
		if(leftVal instanceof Float && rightVal instanceof Float){
			
			return (Float)leftTerm.eval(tableRecord) * (Float)rightTerm.eval(tableRecord);
		}
		
		return null;
	}

	@Override
	public Object evalGrouping(List<TableRecord> tableRecords) throws Exception {

		if(tableRecords.size() > 0){
			
			Object leftVal = leftTerm.eval(tableRecords.get(0));
			Object rightVal = leftTerm.eval(tableRecords.get(0));
		
			if(leftVal instanceof Integer && rightVal instanceof Integer){
				return (Integer)leftTerm.evalGrouping(tableRecords)*(Integer)rightTerm.evalGrouping(tableRecords);
			}
			if(leftVal instanceof Float && rightVal instanceof Float){
				return (Float)leftTerm.evalGrouping(tableRecords)*(Float)rightTerm.evalGrouping(tableRecords);
			}
			
		}else return new Integer(0);
		return null;
		
		
		
	}

	
}
