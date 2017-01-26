package de.webdataplatform.system;

import de.webdataplatform.message.SystemID;

public class Event {

	
	/** Region server events */
	public final static String REGIONSERVER_ADDED="rsAdded";
	public final static String REGIONSERVER_CRASHED="rsCrashed";
	public final static String REGIONSERVER_SHUTDOWN="rsShutdown";
	public final static String REGIONSERVER_WRITEAHEADLOG_REPLAYED="walReplayed";
	public final static String REGIONSERVER_STATUS_REPORT="statusReportRs";
	public final static String REGIONSERVER_CRASHED_VM_WITHDRAWN="crashedVmWithdrawn";
	
	/** View manager events */
	public final static String VIEWMANAGER_ADDED="vmAdded";
	public final static String VIEWMANAGER_ASSIGNED="vmAssigned";
	public final static String VIEWMANAGER_WITHDRAWN="vmWithdrawn";
	public final static String VIEWMANAGER_REASSIGNED="vmReassigned";
	public final static String VIEWMANAGER_SHUTDOWN="vmShutdown";
	public final static String VIEWMANAGER_RS_CRASHED="vmCrashed";	
	public final static String VIEWMANAGER_STATUS_REPORT="statusReportVm";
	public final static String VIEWMANAGER_MARKER_RECEIVED="mRec";
	
	
	/** Client events*/
	public final static String VMS_STATUS_REPORT="statusReportVMS";
	public final static String VMS_THROUGHPUT_REPORT="throughputReportVMS";
	public final static String VMS_RS_THROUGHPUT_REPORT="throughputReportVMSRS";
	public final static String VMS_THROUGHPUT_SUMMARY="throughputSummaryVMS";
	public final static String VMS_RS_THROUGHPUT_SUMMARY="throughputSummaryVMSRS";
	public final static String VMS_QUERY_ADDED="queryAdded";
	public final static String VMS_QUERY_REMOVED="queryRemoved";
	public final static String VMS_QUERY_LIST="queryList";
	
	
	
	public final static String BALANCE_LOAD="balanceLoad";
	public final static String PROCESS_FINISHED="pFinished";
	
	private String type;
	
	private SystemID viewManager;
	
	private SystemID regionServer;


	public Event(String type, SystemID viewManager) {
		super();
		this.viewManager = viewManager;
		this.type = type;
	}

	public Event(String type, SystemID viewManager, SystemID regionServer) {
		super();
		this.type = type;
		this.viewManager = viewManager;
		this.regionServer = regionServer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	@Override
	public String toString() {
		return "Event [type=" + type + ", viewManager=" + viewManager
				+ ", regionServer=" + regionServer + "]";
	}
	
	
	
	
	
}
