package de.webdataplatform.parser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.TableRecord;

public class Condition implements IPredicate{

	
	private ColumnName columnName;
	
	private String operator;
	
	private ITerm value;
	
	
	

	public Condition() {
		super();
	}

	public Condition(ColumnName columnName, String operator, ITerm value) {
		super();
		this.columnName = columnName;
		this.operator = operator;
		this.value = value;
	}

	public ColumnName getColumnName() {
		return columnName;
	}

	public void setColumnName(ColumnName columnName) {
		this.columnName = columnName;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public boolean isJoinCondition(){
		
		if(value instanceof ColumnName)return true;
		
		return false;
		
	}
	
	
	

	public ITerm getValue() {
		return value;
	}

	public void setValue(ITerm value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "[" + columnName + ", " + operator	+ ", " + value + "]";
	}

	@Override
	public boolean eval(TableRecord tableRecord) throws Exception {
		
		String recordValue = tableRecord.resolveValue(columnName.getName());
		
		if(recordValue == null){
			
			throw new Exception("Value of column: "+columnName.getName()+" couldn't be resolved");
		}
		
		
		if(value instanceof Constant){
			
			Constant constant = (Constant)value;
			
			if(constant.isInteger()){
				
				int recValue = Integer.parseInt(recordValue);
				int compareValue = constant.getValInteger();
				
				switch(operator){
				
					case "=" : return (recValue == compareValue);
					case "<" : return (recValue < compareValue);
					case ">" : return (recValue > compareValue);
					case "<=" : return (recValue <= compareValue);
					case ">=" : return (recValue >= compareValue);				
				}
			}
			if(constant.isFloat()){
				
				float recValue = Float.parseFloat(recordValue);
				float compareValue = constant.getValFloat();
				
				switch(operator){
				
					case "=" : return (recValue == compareValue);
					case "<" : return (recValue < compareValue);
					case ">" : return (recValue > compareValue);
					case "<=" : return (recValue <= compareValue);
					case ">=" : return (recValue >= compareValue);				
				}
			}
			if(constant.isDate()){
				
				SimpleDateFormat format = new SimpleDateFormat(SystemConfig.RECORDS_DATEFORMAT);
				
				Date recValue = format.parse(recordValue);
				Date compareValue = constant.getValDate();
				
				switch(operator){
				
				case "=" : return (recValue.equals(compareValue));
				case "<" : return (recValue.before(compareValue));
				case ">" : return (recValue.after(compareValue));
				case "<=" : return (recValue.before(compareValue)|| recValue.equals(compareValue));
				case ">=" : return (recValue.after(compareValue)|| recValue.equals(compareValue));		
			}
				
				return recordValue.equals(compareValue);
				
			}
			if(constant.isString()){
				
				String compareValue = constant.getValString();
				return recordValue.equals(compareValue);
				
			}
			
			
		}
		if(value instanceof Range){
			
			Range range = (Range)value;
			
			if(range.getStart().isInteger()&& range.getEnd().isInteger()){
				
				int recValue = Integer.parseInt(recordValue);
				Integer start = range.getStart().getValInteger();
				Integer end = range.getEnd().getValInteger();
				
				return recValue >= start && recValue <= end;

			}
			if(range.getStart().isFloat()&& range.getEnd().isFloat()){
				
				float recValue = Float.parseFloat(recordValue);
				Float start = range.getStart().getValFloat();
				Float end = range.getEnd().getValFloat();
				
				return recValue >= start && recValue <= end;

			}
			
			if(range.getStart().isDate()&& range.getEnd().isDate()){
				
				SimpleDateFormat format = new SimpleDateFormat(SystemConfig.RECORDS_DATEFORMAT);
				
				Date recValue = format.parse(recordValue);
				Date start = range.getStart().getValDate();
				Date end = range.getEnd().getValDate();
				
				return recValue.equals(start)||recValue.equals(end) || (recValue.after(start)&&recValue.before(end));

			}
			
			
		}
		
		return false;
	}

	@Override
	public List<Condition> getColumnConditions() {
		
		List<Condition> result = new ArrayList<Condition>();
	
		if(!(value instanceof ColumnName))
		result.add(this);
		
		return result;
	}

	@Override
	public List<Condition> getJoinConditions() {
		
		List<Condition> result = new ArrayList<Condition>();
		
		if(value instanceof ColumnName)
		result.add(this);
		
		return result;
	}

	@Override
	public Map<IPredicate, Set<String>> getEvaluationMap() {
		
		Map<IPredicate, Set<String>> result = new HashMap<IPredicate, Set<String>>();
		
		if(!(value instanceof ColumnName)){
			
			Set<String> tables = new HashSet<String>();
			tables.add(columnName.getTable());
			
			result.put(this, tables);
		}
		
		return result;
	}
	
	

	
	
	
	

}
