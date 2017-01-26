package de.webdataplatform.viewmanager;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.Message;
import de.webdataplatform.message.MessageClient;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.Command;
import de.webdataplatform.system.Event;

public class VMSender {



	private Log log;
	
	private ViewManager viewManager;
	
	
	
	
	public VMSender(Log log, ViewManager viewManager) {
		super();
		this.log = log;
		this.viewManager = viewManager;
	}


	//	VIEW MANAGER MESSAGES
	
	
	public void sendAssignViewManager(SystemID regionServer){
		
		log.info(this.getClass(),"sending assign vm:"+viewManager+" to rs:"+regionServer);
		
		Message message = new Message(viewManager.getSystemID(), Command.VIEWMANAGER_ASSIGN, "");
		
		MessageClient.sendMessage(log, regionServer, message);
	}
	
	
	public void sendWithdrawViewManager(SystemID regionServer){
		
		log.info(this.getClass(),"sending withdraw vm:"+viewManager+" to rs:"+regionServer);
		
		Message message = new Message(viewManager.getSystemID(), Command.VIEWMANAGER_WITHDRAW, "");
		
		MessageClient.sendMessage(log, regionServer, message);
		
	}
	
	
	public void sendViewManagerAssigned(SystemID rsID){
		
		
//		this.viewManager.setRegionServer(rsID);
//		
//		log.info(this.getClass(),"sending vm assigned to master:"+viewManager.getMaster());
//
//		Message message = new Message(viewManager.getSystemID(), Event.VIEWMANAGER_ASSIGNED, rsID.toString());
//		
//		MessageClient.sendMessage(log, viewManager.getMaster(), message);
		
		
	}
	
	
	public void sendViewManagerWithdrawn(){
		
			
//		this.viewManager.setRegionServer(null);
		
//		log.info(this.getClass(),"sending vm withdrawn to master:"+viewManager.getMaster());
//
//		Message message = new Message(viewManager.getSystemID(), Event.VIEWMANAGER_WITHDRAWN, "");
//		
//		MessageClient.sendMessage(log, viewManager.getMaster(), message);
		
	}
	
	
	public void sendViewManagerReassigned(SystemID rsID){
		
		
//		log.info(this.getClass(),"sending vm reassigned to master:"+viewManager.getMaster());
//
//		Message message = new Message(viewManager.getSystemID(), Event.VIEWMANAGER_REASSIGNED, rsID.toString());
//		
//		MessageClient.sendMessage(log, viewManager.getMaster(), message);
	}
	

	
	public void sendViewManagerShutdown(){
		
//		log.info(this.getClass(),"sending vm shutdown to master:"+viewManager.getMaster());
//		
//		Message message = new Message(viewManager.getSystemID(), Event.VIEWMANAGER_SHUTDOWN, "");
//		
//		MessageClient.sendMessage(log, viewManager.getMaster(), message);
		
		
	}	

	public void sendMarkerConfirmation(SystemID rsID, String marker){
		
		log.info(this.getClass(),"marker: "+marker+" received sending answer to :"+viewManager.getProcessing().getMarkers().get(marker));
		
		Message message = new Message(viewManager.getSystemID(), Event.VIEWMANAGER_MARKER_RECEIVED, marker);
		
		MessageClient.sendMessage(log, rsID, message);
		
	}	
	
	public void sendStatusReport(){
		
		log.info(this.getClass(),"sending status report to master:"+viewManager.getMasterID());
		
		String statusReport = viewManager.getStatisticReceived().getTotalUpdates().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getStatisticReceived().getAvgThroughput().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
//		statusReport += viewManager.getStatisticReceived().getMaxThroughput().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		
		statusReport += viewManager.getProcessing().getStatisticBU().getTotalUpdates().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticBU().getThroughput().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticBU().getAvgThroughput().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
//		statusReport += viewManager.getProcessing().getStatisticBU().getMaxThroughput().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticBU().getAvgLatency().intValue()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		
		statusReport += viewManager.getProcessing().getStatisticVR().getTotalUpdates().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticVR().getAvgThroughput().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
//		statusReport += viewManager.getProcessing().getStatisticVR().getMaxThroughput().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticVR().getAvgLatency().intValue()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		
		statusReport += viewManager.getProcessing().getStatisticVU().getTotalUpdates().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticVU().getAvgThroughput().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
//		statusReport += viewManager.getProcessing().getStatisticVU().getMaxThroughput().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticVU().getAvgLatency()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;

		statusReport += viewManager.getProcessing().getStatisticSent().getTotalUpdates().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticSent().getAvgThroughput().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
//		statusReport += viewManager.getProcessing().getStatisticVU().getMaxThroughput().get()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticSent().getAvgLatency().intValue()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticDetail().getAvgLatency().intValue()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		
		statusReport += viewManager.getProcessing().getStatisticsProjection().getTotalUpdates()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticsProjection().getAvgLatency()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticsDelta().getTotalUpdates()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticsDelta().getAvgLatency()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticsPreAggregation().getTotalUpdates()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += viewManager.getProcessing().getStatisticsPreAggregation().getAvgLatency()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		
		Message message = new Message(viewManager.getSystemID(), Event.VIEWMANAGER_STATUS_REPORT, statusReport);
		
		MessageClient.sendMessage(log, viewManager.getMasterID(), message);
		
	}	
}
