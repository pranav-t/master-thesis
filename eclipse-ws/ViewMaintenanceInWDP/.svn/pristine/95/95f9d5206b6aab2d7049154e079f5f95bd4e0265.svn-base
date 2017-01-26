package de.webdataplatform.system;

import de.webdataplatform.message.SystemID;


public class Command {


	/** View manager commands*/
	public final static String VIEWMANAGER_ASSIGN="assignVm";
	public final static String VIEWMANAGER_WITHDRAW="withdrawVm";
	public final static String VIEWMANAGER_REASSIGN="reassignVm";
	public final static String VIEWMANAGER_SHUTDOWN="shutdownVm";
	public final static String VIEWMANAGER_REFRESH_QUERYTABLE="refreshQueryTableVm";
	

	/** Region server commands*/
	public final static String REGIONSERVER_WITHDRAW_CRASHED_VM="withdrawCrashedVm";
	public final static String REGIONSERVER_REPLAY_WRITEAHEADLOG="replayWAL";
	public final static String REGIONSERVER_START_UPDATE_PROCESSING="startUP";
	
	/** Client commands*/
	public final static String VMS_STATUS_REPORT="statusReportVMS";
	public final static String VMS_THROUGHPUT_REPORT="throughputReportVMS";
	public final static String VMS_RS_THROUGHPUT_REPORT="throughputReportVMSRS";
	public final static String VMS_THROUGHPUT_SUMMARY="throughputSummaryVMS";
	public final static String VMS_RS_THROUGHPUT_SUMMARY="throughputSummaryVMSRS";
	public final static String VMS_ADD_QUERY="addQuery";
	public final static String VMS_REMOVE_QUERY="removeQuery";
	public final static String VMS_RELOAD_QUERY="reloadQuery";
	public final static String VMS_LIST_QUERIES="listQueries";
	
	
	

	
	private String type;

	private SystemID viewManager;
	
	private SystemID regionServer;

	private String content;
	
	private long executionTimestamp;
	
	private int retries=0;
	
	
	
	public Command(String type, SystemID viewManager) {
		super();
		this.viewManager = viewManager;
		this.type = type;
	}

	public Command(String type, SystemID viewManager, SystemID regionServer) {
		super();
		this.viewManager = viewManager;
		this.type = type;
		this.regionServer = regionServer;
	}

	public Command(String type, SystemID viewManager, SystemID regionServer, String content) {
		super();
		this.viewManager = viewManager;
		this.type = type;
		this.regionServer = regionServer;
		this.content = content;
	}

	public SystemID getViewManager() {
		return viewManager;
	}

	public void setViewManager(SystemID viewManager) {
		this.viewManager = viewManager;
	}

	public SystemID getRegionServer() {
		return regionServer;
	}

	public void setRegionServer(SystemID regionServer) {
		this.regionServer = regionServer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	

	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getExecutionTimestamp() {
		return executionTimestamp;
	}

	public void setExecutionTimestamp(long executionTimestamp) {
		this.executionTimestamp = executionTimestamp;
	}

	public int getRetries() {
		return retries;
	}

	public void setRetries(int retries) {
		this.retries = retries;
	}

	@Override
	public String toString() {
		return "Command [type=" + type + ", viewManager=" + viewManager
				+ ", regionServer=" + regionServer + "]";
	}


	
	

}
