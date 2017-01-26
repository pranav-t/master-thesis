package de.webdataplatform.parser;

import java.util.List;

import de.webdataplatform.system.TableRecord;

public interface ITerm{

	
	public String toString();
	
	public Object eval(TableRecord tableRecord) throws Exception;
	
	public Object evalGrouping(List<TableRecord> tableRecord) throws Exception;
	
	public List<ColumnName> getBaseColumns();
	
}
