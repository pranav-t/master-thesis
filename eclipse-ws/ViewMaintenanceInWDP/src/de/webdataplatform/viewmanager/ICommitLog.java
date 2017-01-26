package de.webdataplatform.viewmanager;

import java.util.Map;

import de.webdataplatform.basetable.BaseTableOperation;

public interface ICommitLog {
	
	public void createLogDirectory();
	
	public void createLogFile();
	
	public boolean openLogFile();
	
	public void closeLogFile();
	
	public void append(String vmName, BaseTableOperation baseTableUpdate);
	
	public Map<String, Long> readHighestSeqNos();
	
	
	

}
