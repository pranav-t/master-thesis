package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.webdataplatform.system.MathUtil;
import de.webdataplatform.system.TableRecord;
import de.webdataplatform.system.TypeChecker;

public class Function implements ITerm{


	
	private String type;
	
	private ITerm term;

	
	public static List<String> getFunctions(){
		
		List<String> keywords = new ArrayList<String>();
		keywords.add("count");
		keywords.add("sum");
		keywords.add("min");
		keywords.add("max");
		keywords.add("avg");

		return keywords;
	}
	
	public static boolean isFunction(String string){
		
		return getFunctions().contains(string);
		
	}
	
	
	
	public Function(String type, ITerm term) {
		super();
		this.type = type;
		this.term = term;
	}

	
	public Integer buildCount(List<TableRecord> tableRecords){

		
		return tableRecords.size();
		
	}
	
	
	public Object buildSum(List<TableRecord> tableRecords)throws Exception{
		
		
		if(tableRecords.size() > 0){
	
			Object val = eval(tableRecords.get(0));
			
			if(TypeChecker.isInteger((String)val)){

				Integer sum = 0;
				for (TableRecord tableRecord : tableRecords) {
					sum += Integer.parseInt((String)eval(tableRecord));
				}
				return sum;
			}
			if(TypeChecker.isFloat((String)val)){
				Float sum = 0f;
				
//				long start = System.nanoTime();
				
				for (TableRecord tableRecord : tableRecords) {
					
//					System.out.println("test: ");
					
//					long startx = System.nanoTime();
					Object num = eval(tableRecord);
//					System.out.println("Startxxx: "+(System.nanoTime()-startx));
					
//					System.out.println("num: "+num);
					sum += Float.parseFloat((String)num);
				}
//				System.out.println("Sum: "+sum);
//				System.out.println("Sum float: "+(System.nanoTime()-start));
				return sum;
			}
			
			if(TypeChecker.isDate((String)val)){
						
			}
		}else return new Integer(0);
		
		return null;
		
	}
	
	
	public Object buildMin(List<TableRecord> tableRecords)throws Exception{
		
		
		
		
		if(tableRecords.size() > 0){
	
			Object val = eval(tableRecords.get(0));
				
			if(val instanceof Integer){
				Integer min = Integer.MAX_VALUE;
				for (TableRecord tableRecord : tableRecords) {
					
					
					Integer delta=null;

					delta = (Integer)eval(tableRecord);

					if(delta < min)
						min = delta;
					
				}
				if(min == Integer.MAX_VALUE)return 0;
				return min;
			}
			if(val instanceof Float){
				Float min = Float.MAX_VALUE;
				for (TableRecord tableRecord : tableRecords) {
					
					Float delta=null;
					delta = (Float)eval(tableRecord);

					if(delta < min)
						min = delta;
					
				}
				if(min == Float.MAX_VALUE)return 0;
				return min;
			}			
			
		}else return new Integer(0);
		
		return null;
	}
	
	public Object buildMax(List<TableRecord> tableRecords)throws Exception{
	
		
		if(tableRecords.size() > 0){
			
			Object val = eval(tableRecords.get(0));
				
			if(val instanceof Integer){		
				
				Integer max = Integer.MIN_VALUE;
				for (TableRecord tableRecord : tableRecords) {
					
					Integer delta=null;
					delta = (Integer)eval(tableRecord);

					if(delta > max)
						max = delta;
					
				}
				if(max == Integer.MIN_VALUE)return 0;
				return max;
			}
			if(val instanceof Float){		
				
				Float max = Float.MIN_VALUE;
				for (TableRecord tableRecord : tableRecords) {
					
					Float delta=null;

					delta = (Float)eval(tableRecord);

					if(delta > max)
						max = delta;
					
				}
				if(max == Float.MIN_VALUE)return 0;
				return max;
			}			
			
		}else return new Integer(0);
		
		return null;
	}
	
	public Object buildAvg(List<TableRecord> tableRecords) throws Exception {
		
		if(buildCount(tableRecords)== 0)return 0;
		
		if(tableRecords.size() > 0){
			
			Object val = eval(tableRecords.get(0));
				
			if(val instanceof Integer){		
		
				return (Integer)buildSum(tableRecords)/(Integer)buildCount(tableRecords);
			}
			if(val instanceof Float){		
				
				return (Float)buildSum(tableRecords)/(Integer)buildCount(tableRecords);
			}			
		}else return new Integer(0);
		
		return null;
	}
	
	

	@Override
	public Object eval(TableRecord tableRecord) throws Exception {

		return term.eval(tableRecord);
	}

	@Override
	public List<ColumnName> getBaseColumns() {
		List<ColumnName> result = new ArrayList<ColumnName>();
		result.addAll(term.getBaseColumns());
		return result;
	}


	@Override
	public String toString() {
		return "Function [type=" + type + ", term=" + term + "]";
	}

	@Override
	public Object evalGrouping(List<TableRecord> tableRecords) throws Exception {

		Object result=null;
		
		switch(type){
			case "count" : result = buildCount(tableRecords);
			break;
			case "sum" : result = buildSum(tableRecords);
			break;		
			case "min" : result = buildMin(tableRecords);
			break;				
			case "max" : result = buildMax(tableRecords);
			break;			
			case "avg" : result = buildAvg(tableRecords);
			break;			
		}
		
		return result;
	}
	
	
	
	

}
