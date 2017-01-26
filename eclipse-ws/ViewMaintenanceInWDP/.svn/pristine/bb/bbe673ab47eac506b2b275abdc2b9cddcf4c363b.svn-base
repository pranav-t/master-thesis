package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.webdataplatform.system.TableRecord;

public class Range implements ITerm{


	private Constant start;
	private Constant end;
	


	public Range(Constant start, Constant end) {
		super();
		this.start = start;
		this.end = end;
	}




	@Override
	public String toString() {
		return "Range [start=" + start + ", end=" + end + "]";
	}


	@Override
	public List<ColumnName> getBaseColumns() {

		return new ArrayList<ColumnName>();
	}




	@Override
	public Object eval(TableRecord tableRecord) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Object evalGrouping(List<TableRecord> tableRecord) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}




	public Constant getStart() {
		return start;
	}




	public void setStart(Constant start) {
		this.start = start;
	}




	public Constant getEnd() {
		return end;
	}




	public void setEnd(Constant end) {
		this.end = end;
	}
	


	
	
	

}
