package de.webdataplatform.viewmanager;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.Message;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.system.Command;
import de.webdataplatform.system.Component;
import de.webdataplatform.system.Event;

public class VMDispatcher {


	private Log log;
	
	private ViewManager viewManager;
	
	private VMSender sender;
	
	
	
	
	
	
	public VMDispatcher(Log log, ViewManager viewManager, VMSender sender) {
		super();
		this.log = log;
		this.viewManager = viewManager;
		this.sender = sender;
	}






	public void receiveMessage(Message message){
		
		String component = message.getSystemID().getComponent();
		String operation = message.getOperation();
		String content = message.getContent();

		
		switch(component){
		
			case Component.master : 
				if(this.viewManager.getCondition().equals(Condition.RUNNING)){
					switch(operation){
//						case Command.VIEWMANAGER_ASSIGN : 
//							viewManager.setCondition(Condition.ASSIGNING);
//							sender.sendAssignViewManager(new SystemID(content));
//						break;	
//						case Command.VIEWMANAGER_WITHDRAW : 
//							viewManager.setCondition(Condition.WITHDRAWING);
//							if(viewManager.getRegionServer() != null)
//								sender.sendWithdrawViewManager(viewManager.getRegionServer());
//							else log.info(this.getClass(),"cannot withdraw, region server not set");
//						break; 
//						case Command.VIEWMANAGER_REASSIGN :
//							viewManager.setReassignRS(new SystemID(content));
//							if(this.viewManager.getRegionServer() != null){
//								this.viewManager.setCondition(Condition.REASSIGNING);
//								sender.sendWithdrawViewManager(this.viewManager.getRegionServer());
//							}else{
//								log.info(this.getClass(),"cannot withdraw, region server not set, assigning instead");
//								viewManager.setCondition(Condition.ASSIGNING);
//								sender.sendAssignViewManager(new SystemID(content));
//							}
//						break;
						case Command.VIEWMANAGER_SHUTDOWN : 
							log.info(this.getClass(),"shutting down view manager");
							this.viewManager.setCondition(Condition.SHUTTING_DOWN);
//							if(this.viewManager.getRegionServer() != null)
//							sender.sendWithdrawViewManager(this.viewManager.getRegionServer());
//							else{
								sender.sendViewManagerShutdown();
								log.info(this.getClass(),"system going down");
								System.exit(0);
//							}
						break;
						case Command.VIEWMANAGER_REFRESH_QUERYTABLE : 
							log.info(this.getClass(),"refreshing query table");
							viewManager.getQueryManager().loadQueries();

							
						break;
						case Event.VIEWMANAGER_RS_CRASHED : 
							log.info(this.getClass(),"region server crashed :"+message.getContent());

						break;	
					}
				}else{
					log.info(this.getClass(),"cannot execute command from master because condition is:"+viewManager.getCondition());
				}
			break;
//			case Component.regionServer : log.info(this.getClass(),"receive message from region server");
//				switch(operation){
//					case Event.VIEWMANAGER_ASSIGNED : 
//						if(viewManager.getCondition().equals(Condition.ASSIGNING)){
//							sender.sendViewManagerAssigned(message.getSystemID());
//							viewManager.setCondition(Condition.RUNNING);
//						}
//						if(viewManager.getCondition().equals(Condition.REASSIGNING)){
//							
//							sender.sendViewManagerReassigned(message.getSystemID());
//							viewManager.setCondition(Condition.RUNNING);
//							
//						}					
//					break;	
//					case Event.VIEWMANAGER_WITHDRAWN : 
//						if(viewManager.getCondition().equals(Condition.WITHDRAWING)){
//							sender.sendViewManagerWithdrawn();
//							viewManager.setCondition(Condition.RUNNING);
//						}
//						if(viewManager.getCondition().equals(Condition.REASSIGNING)){
//							sender.sendAssignViewManager(viewManager.getReassignRS());
//						}
//						if(viewManager.getCondition().equals(Condition.SHUTTING_DOWN)){
//							sender.sendViewManagerShutdown();
//				
//						}
//					break;
//
//				}
//			break;				

		}
		

		
		
	}

}
