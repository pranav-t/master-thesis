package de.webdataplatform.master;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.Message;
import de.webdataplatform.message.MessageClient;
import de.webdataplatform.message.MessageUtil;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.Command;
import de.webdataplatform.system.Event;

public class MasterSender {

		private Log log;
	
		private Master master;
	
		
		
		
		
	public MasterSender(Log log, Master master) {
			super();
			this.log = log;
			this.master = master;
		}

	/**	VIEW MANAGER MESSAGES */
	
		
	
//	public void assignViewManager(SystemID viewManager, SystemID regionServer){
//		
//		log.message(this.getClass(),"command assign vm:"+viewManager+" to rs:"+regionServer);
//		
//		Message message = new Message(master.getSystemID(), Command.VIEWMANAGER_ASSIGN, regionServer.toString());
//		
//		MessageClient.sendMessage(log, viewManager,  message);
//	}
//	
//	
//	public void withdrawViewManager(SystemID viewManager){
//		
//		log.message(this.getClass(),"command withdraw vm:"+viewManager);
//		
//		Message message = new Message(master.getSystemID(), Command.VIEWMANAGER_WITHDRAW, "");
//		
//		MessageClient.sendMessage(log, viewManager,  message);
//	}
	
	
	public void reassignViewManager(SystemID viewManager, SystemID newRegionServer){
		
		log.message(this.getClass(),"command reassign vm:"+viewManager+" to rs:"+newRegionServer);
		
		Message message = new Message(master.getSystemID(), Command.VIEWMANAGER_REASSIGN, newRegionServer.toString());
		
		MessageClient.sendMessage(log, viewManager,  message);
		
	}
	
	public void shutdownViewManager(SystemID viewManager){
		
		log.message(this.getClass(),"command shutdown vm:"+viewManager);
		
		Message message = new Message(master.getSystemID(), Command.VIEWMANAGER_SHUTDOWN, "");
		
		MessageClient.sendMessage(log, viewManager,  message);	
	}
	
	
	public void sendRefreshQueryTable(SystemID viewManager){
		
//		for(SystemID vm : master.getMetaData().getViewManagers()){
			
		log.message(this.getClass(),"command refresh query vm:"+viewManager);
	
		Message message = new Message(master.getSystemID(), Command.VIEWMANAGER_REFRESH_QUERYTABLE, "");
		
		MessageClient.sendMessage(log, viewManager ,  message);
			
//		}
		
		
	}

	
	/**	REGION SERVER MESSAGES */	
	
	
	public void sendAssignViewManager(SystemID receiver, SystemID viewManager){
		
		log.message(this.getClass(),"command assign vm:"+viewManager+" to:"+receiver);
		
		Message message = new Message(master.getSystemID(), Command.VIEWMANAGER_ASSIGN, viewManager.toString());
		
		MessageClient.sendMessage(log, receiver,  message);
	}
	
	public void sendWithdrawViewManager(SystemID regionServer, SystemID viewManager){
		
		log.message(this.getClass(),"command withdraw vm:"+viewManager);
		
		Message message = new Message(master.getSystemID(), Command.VIEWMANAGER_WITHDRAW, viewManager.toString());
		
		MessageClient.sendMessage(log, regionServer,  message);
	}
	
	
//	public void withdrawCrashedViewManager(SystemID viewManager, SystemID regionServer){
//		
//		log.message(this.getClass(),"command withdraw crashed vm:"+viewManager);
//		
//		Message message = new Message(master.getSystemID(), Command.REGIONSERVER_WITHDRAW_CRASHED_VM, viewManager.toString());
//		
//		MessageClient.sendMessage(log, regionServer,  message);
//	}
//	
//	public void replayWriteAheadLog(SystemID regionServer, String seqNo){
//		
//		log.message(this.getClass(),"command replay wal:"+seqNo);
//		
//		Message message = new Message(master.getSystemID(), Command.REGIONSERVER_REPLAY_WRITEAHEADLOG, seqNo+"");
//		
//		MessageClient.sendMessage(log, regionServer,  message);
//	}		
	
	
	
	/**	CLIENT MESSAGES */	

	
	public void sendStatusReport(SystemID clientID){
		
		String statusReport = " system status: "
		+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE+master.getMetaData().getViewManagers().size()+" view manager online; "
	    +SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE+master.getMetaData().getRegionServers().size()+" region server online "
	    +SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE+" assignments:";
		
		
		Message message = new Message(master.getSystemID(), Event.VMS_STATUS_REPORT, statusReport );
		MessageClient.sendMessage(log, clientID,  message);

		
	}
	

	public List<Integer> computeSum(Map<SystemID, List<Integer>> map){
		
		List<Integer> result = new ArrayList<Integer>();
		
		for (SystemID systemID : map.keySet()) {
			
				List<Integer> temp = map.get(systemID);
				
				for (int i = 0; i < temp.size(); i++) {
					if(result.size() < temp.size()){
						
						result.add(temp.get(i));
						
					}else{
						
						result.set(i, result.get(i) + temp.get(i));
						
					}
				}
			
		}
		return result;
				
	}
	
	public void sendThroughputReport(SystemID clientID, boolean regionServers){
		
		
		String throughputReport = "";
				
		if(!regionServers){		
			throughputReport = MessageUtil.translateMap(master.getMetaData().getVMReports());
		}else{
			throughputReport = MessageUtil.translateMap(master.getMetaData().getRSReports());
		}
		
		Message message = new Message(master.getSystemID(), Event.VMS_THROUGHPUT_REPORT, throughputReport);
		MessageClient.sendMessage(log, clientID,  message);

		
	}
	

	
	public void sendThroughputSummary(SystemID clientID, boolean regionServers){
		
		Map<SystemID, List<Integer>> result = new HashMap<SystemID, List<Integer>>();
		
		if(!regionServers){	
			result.put(new SystemID("Sum", "Sum", "localhost", 2344), computeSum(master.getMetaData().getVMReports()));
		}else{
			result.put(new SystemID("Sum", "Sum", "localhost", 2344), computeSum(master.getMetaData().getRSReports()));
		}
			
		String throughputReport = MessageUtil.translateMap(result);
		
		
		Message message = new Message(master.getSystemID(), Event.VMS_THROUGHPUT_SUMMARY, throughputReport);
		MessageClient.sendMessage(log, clientID,  message);

		
	}
	

	
	
	public void sendQueryAdded(SystemID clientID, String queryName, boolean successful){
		
		
		Message message = null;
		
		if(successful)message = new Message(master.getSystemID(), Event.VMS_QUERY_ADDED, queryName+" added to table");
		else message = new Message(master.getSystemID(), Event.VMS_QUERY_ADDED, queryName+" failed");
		MessageClient.sendMessage(log, clientID,  message);
		
		
	}
	
	
	public void sendQueryRemoved(SystemID clientID, String queryName, boolean successful){

		
		Message message = null;
		
		if(successful)message = new Message(master.getSystemID(), Event.VMS_QUERY_REMOVED, queryName+" removed from table");
		else message = new Message(master.getSystemID(), Event.VMS_QUERY_REMOVED, "removing "+queryName+" failed");
		MessageClient.sendMessage(log, clientID,  message);
		
		
	}
	
	
	public void sendQueryReloaded(SystemID clientID, String queryName, boolean successful){

		
		Message message = null;
		
		if(successful)message = new Message(master.getSystemID(), Event.VMS_QUERY_REMOVED, queryName+" reloaded");
		else message = new Message(master.getSystemID(), Event.VMS_QUERY_REMOVED, "reloading "+queryName+" failed");
		MessageClient.sendMessage(log, clientID,  message);
		
		
	}	
	
	public void sendQueryList(SystemID clientID){
		

		master.getQueryManager().loadQueries();
		
		String queryList="";

		for (String queryName : master.getQueryManager().getQueryTable().getRawQueries().keySet()) {
			
			queryList += queryName+": "+master.getQueryManager().getQueryTable().getRawQueries().get(queryName)+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE;
		}
		
		
		Message message = new Message(master.getSystemID(), Event.VMS_QUERY_LIST, queryList);
		MessageClient.sendMessage(log, clientID ,  message);
		
		
	}


}
