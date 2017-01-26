package de.webdataplatform.regionserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.Message;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.Command;
import de.webdataplatform.system.Component;
import de.webdataplatform.system.Event;

public class RSDispatcher {

	
	
	private Log log;
	
	private RSSender sender;
	
	private RSController controller;
	
	private RegionServer regionServer;
	
	
	
	
	public RSDispatcher(Log log, RSSender sender, RSController controller,
			RegionServer regionServer) {
		super();
		this.log = log;
		this.sender = sender;
		this.controller = controller;
		this.regionServer = regionServer;
	}






	public void receiveMessage(Message message){
		
		String component = message.getSystemID().getComponent();
		String operation = message.getOperation();
		
		switch(component){
		
			case Component.master : 
				switch(operation){
//				case Command.REGIONSERVER_WITHDRAW_CRASHED_VM: 
//					log.info(this.getClass(),"withdraw view manager: "+message.getContent());
//					try {
//						controller.withdrawViewManager(message.getSystemID());
//						sender.sendCrashedViewManagerWithdrawn(message.getContent());
//					} catch (InterruptedException e) {
//	
//						log.error(this.getClass(), e);
//					}
//				break;
				case Command.REGIONSERVER_REPLAY_WRITEAHEADLOG: 
					log.info(this.getClass(),"replaying write ahead log: "+message.getContent());
					try {
						
						
						String msg = message.getContent().replace("{", "").replace("}", "").replace(" ", "");
						
						log.info(this.getClass(),"replay msg: "+msg);
						Map<String, Long> seqNos = new HashMap<String, Long>();
						
						for (String keyValue : msg.split(",")) {
							
							seqNos.put(keyValue.split("=")[0], Long.parseLong(keyValue.split("=")[1]));
						}
						log.info(this.getClass(),"seqNos: "+seqNos);
						
						controller.replayWriteAheadLog(seqNos);
						
						sender.sendWALReplayed();
						
					} catch (InterruptedException e) {
	
						log.error(this.getClass(), e);
					}
				break;
				}
			break;
			case Component.regionServer : 
			break;				
			case Component.viewManager : 
				switch(operation){
				case Event.VIEWMANAGER_STATUS_REPORT : 
					log.info(this.getClass(),"status report from "+message.getSystemID().getName()+":"+message.getContent());
					
					
					List<Integer> vals = new ArrayList<Integer>();
					
					for(String val : message.getContent().split(SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE)){
						try{
							vals.add(Integer.parseInt(val));
						}catch(Exception e){
							
						}
					}
//					regionServer.getStatusReports().put(message.getSystemID(), vals);
					
				break;	
				case Event.VIEWMANAGER_MARKER_RECEIVED : 

					log.info(this.getClass(),"marker received from "+message.getSystemID().getName()+", "+message.getContent());
					controller.markerReceived(message.getSystemID(), message.getContent());
					
				break;
				case Command.VIEWMANAGER_ASSIGN : 
					log.info(this.getClass(),"assign view manager: "+message.getSystemID());
					try {
//						controller.assignViewManager(message.getSystemID());
						sender.sendViewManagerAssigned(message.getSystemID());
					} catch (InterruptedException e) {

						log.error(this.getClass(), e);
					}
				break;	
				case Command.VIEWMANAGER_WITHDRAW : 
					log.info(this.getClass(),"withdraw view manager: "+message.getContent());
					try {
//						controller.withdrawViewManager(message.getSystemID());
						sender.sendViewManagerWithdrawn(message.getSystemID());
					} catch (InterruptedException e) {
	
						log.error(this.getClass(), e);
					}
				break;
				}	
			
		}
		
	}

}
