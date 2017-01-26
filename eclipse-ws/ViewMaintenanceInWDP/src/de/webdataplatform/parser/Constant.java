package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.webdataplatform.system.TableRecord;

public class Constant implements ITerm{


	private Integer valInteger;
	private String valString;
	private Float valFloat;
	private Date valDate;
	

	public Constant(Integer value) {
		super();
		this.valInteger = value;
	}

	public Constant(String value) {
		super();
		this.valString = value;
	}

	public Constant(Float value) {
		super();
		this.valFloat = value;
	}
	
	public Constant(Date value) {
		super();
		this.valDate = value;
	}
	
	
	
	
	
	public boolean isInteger() {

		
		return (valInteger != null);

	}
	
	public boolean isString() {

		
		return (valString != null);

	}	
	
	public boolean isFloat() {
		
		return (valFloat != null);

	}	
	
	public boolean isDate() {
		
		return (valDate != null);

	}		
	

	
//	public static boolean isDate(String string) {
//
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		
//		try {
//			format.parse(string);
//		} catch (ParseException e) {
//
//			return false;
//		}
//
//		return true;
//
//	}	
	
	
//	public static boolean isString(String token) {
//		
//		if(SQL.isSQLKeyword(token))return false;
//		
//		Pattern logEntry = Pattern.compile("\\'[\\w_\\s]*\\'");
//		
////		System.out.println("isstring: "+token);
//		
//		Matcher matchPattern = logEntry.matcher(token);
//		
//		if(matchPattern.find()){
////			System.out.println("isstring: "+matchPattern.group());
//			return true;
//		}
//		
//		return false;
//		
//	}

	
	

	@Override
	public Object eval(TableRecord tableRecord) throws Exception {

		return valInteger;
	}





	public Integer getValInteger() {
		return valInteger;
	}

	public void setValInteger(Integer valInteger) {
		this.valInteger = valInteger;
	}

	public String getValString() {
		return valString;
	}

	public void setValString(String valString) {
		this.valString = valString;
	}
	
	

	public Float getValFloat() {
		return valFloat;
	}

	public void setValFloat(Float valFloat) {
		this.valFloat = valFloat;
	}

	public Date getValDate() {
		return valDate;
	}

	public void setValDate(Date valDate) {
		this.valDate = valDate;
	}

	@Override
	public String toString() {
		
		if(valString != null)return valString;
		if(valInteger != null)return valInteger+"";
		if(valFloat != null)return valFloat+"";
		if(valDate != null)return valDate+"";
		
		return "";

	}

	@Override
	public List<ColumnName> getBaseColumns() {

		return new ArrayList<ColumnName>();
	}

	@Override
	public Object evalGrouping(List<TableRecord> tableRecord) throws Exception {

		return valInteger;
	}
	
	
	
	

}
