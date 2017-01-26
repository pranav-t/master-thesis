package de.webdataplatform.regionserver;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.Message;
import de.webdataplatform.message.MessageClient;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.Event;

public class RSSender {


	private Log log;
	
	private RegionServer regionServer;



	
	public RSSender(Log log, RegionServer regionServer) {
		super();
		this.log = log;
		this.regionServer = regionServer;
	}


	/** MESSAGES*/
	
	public void sendViewManagerAssigned(SystemID vmID) throws InterruptedException{
		
		log.info(this.getClass(),"sending view manager assigned to: "+vmID);
		
		Message message = new Message(regionServer.getSystemID(), Event.VIEWMANAGER_ASSIGNED, "");
		
		MessageClient.sendMessage(log, vmID, message);

	}
	

	public void sendViewManagerWithdrawn(SystemID vmID) throws InterruptedException{
		
		log.info(this.getClass(),"sending view manager withdrawn to: "+vmID);
		
		Message message = new Message(regionServer.getSystemID(), Event.VIEWMANAGER_WITHDRAWN, "");
		
		MessageClient.sendMessage(log, vmID, message);
		

	}
	
	
	public void sendWALReplayed() throws InterruptedException{
		
		log.info(this.getClass(),"sending WAL replayed to: "+regionServer.getMasterID());
		
		Message message = new Message(regionServer.getSystemID(), Event.REGIONSERVER_WRITEAHEADLOG_REPLAYED, regionServer.getSystemID().toString());
		
		MessageClient.sendMessage(log, regionServer.getMasterID(), message);
		

	}
	

	
	public void sendCrashedViewManagerWithdrawn(String viewManager) throws InterruptedException{
		
		log.info(this.getClass(),"sending crashed view manager withdrawn to: "+regionServer.getMasterID());		
		
		Message message = new Message(regionServer.getSystemID(), Event.REGIONSERVER_CRASHED_VM_WITHDRAWN, viewManager);
		
		MessageClient.sendMessage(log, regionServer.getMasterID(), message);
		

	}
	

	
	public void sendStatusReport(){
		
		log.info(this.getClass(),"sending status report to master:"+regionServer.getMasterID());
		
		String statusReport = regionServer.getWalReader().getStatisticsRetrieved().getTotalUpdates()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += regionServer.getWalReader().getStatisticsRetrieved().getAvgThroughput()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += regionServer.getWalReader().getStatisticsRetrieved().getMaxThroughput()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += regionServer.getStatisticsAssigned().getTotalUpdates()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += regionServer.getStatisticsAssigned().getAvgThroughput()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += regionServer.getStatisticsAssigned().getMaxThroughput()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += regionServer.getStatisticsSent().getTotalUpdates()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += regionServer.getStatisticsSent().getAvgLatency()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += regionServer.getStatisticsSent().getAvgThroughput()+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		statusReport += regionServer.getStatisticsSent().getMaxThroughput();
		
		Message message = new Message(regionServer.getSystemID(), Event.REGIONSERVER_STATUS_REPORT, statusReport);
		
		MessageClient.sendMessage(log, regionServer.getMasterID(), message);
		
	}	
	


}
