package de.webdataplatform.parser;

import java.util.List;
import java.util.Map;
import java.util.Set;

import de.webdataplatform.system.TableRecord;

public interface IPredicate {

	
	public String toString();
	
	public boolean eval(TableRecord tableRecord) throws Exception;
	
	public List<Condition> getColumnConditions();
	
	public List<Condition> getJoinConditions();
	
	public Map<IPredicate, Set<String>> getEvaluationMap();
}
