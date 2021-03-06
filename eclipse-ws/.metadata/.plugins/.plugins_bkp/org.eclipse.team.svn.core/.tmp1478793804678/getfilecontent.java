package de.webdataplatform.master;

import java.util.ArrayList;
import java.util.List;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.Message;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.Command;
import de.webdataplatform.system.Component;
import de.webdataplatform.system.Event;

public class MasterDispatcher {

	
	private Log log;
	
	private Master master;
	
	private MasterSender sender;
	
//	private ComponentController componentController;
	
	

	
	
	public MasterDispatcher(Log log, Master master) {
		super();
		this.log = log;
		this.sender = master.getSender();
//		this.componentController = master.getComponentController();
		this.master = master;
	}


	
	/** CLIENT ACTIONS */
	
	public boolean addQuery(String queryName, String queryString){
		
		boolean isAdded=false;
		try {

			isAdded = master.getQueryManager().addQuery(queryName, queryString);
			if(isAdded)refreshViewManager();
			
			
		} catch (Exception e) {
			
			log.error(this.getClass(), e);
		}
		return isAdded;
		
	}
	

	
	public boolean removeQuery(String queryName){
		
		boolean isDeleted=false;
		try {

			isDeleted = master.getQueryManager().deleteQuery(queryName);
			if(isDeleted)refreshViewManager();
			
			
		} catch (Exception e) {
			
			log.error(this.getClass(), e);
		}
		return isDeleted;
		
	}
	
	
	
	public boolean reloadQuery(String queryName){
		

		try {
			
			master.getQueryManager().reloadQuery(queryName);
			
			return true;
			
		} catch (Exception e) {
			
			log.error(this.getClass(), e);
		}
		return false;
		
	}

	
	
	public void refreshViewManager(){
		
//		boolean successful = controller.addQuery(splitContent[0], splitContent[1]);

			for(SystemID vm : master.getMetaData().getViewManagers()){
				
				sender.sendRefreshQueryTable(vm);
			}
			
//		}
		
	}
	




	public void receiveMessage(String messageString) {
		
		log.info(this.getClass(),"received message: "+messageString);
		Message message = new Message(messageString);
		
		
		String component = message.getSystemID().getComponent();
		String operation = message.getOperation();
		SystemID systemID = message.getSystemID();

		
		switch(component){
		

			case Component.regionServer : 
					switch(operation){
						case Event.REGIONSERVER_STATUS_REPORT : 
							log.info(this.getClass(),"status report from "+message.getSystemID().getName()+":"+message.getContent());
							
							
							List<Integer> vals = new ArrayList<Integer>();
							
							for(String val : message.getContent().split(SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE)){
								try{
									vals.add(Integer.parseInt(val));
								}catch(Exception e){
									
								}
							}
							master.getMetaData().getRSReports().put(message.getSystemID(), vals);
							
						break;	
						case Event.REGIONSERVER_CRASHED_VM_WITHDRAWN: 
							log.message(this.getClass(),"crashed view manager:"+message.getContent()+" withdrawn from:"+message.getSystemID().getName());
//							controller.crashedViewManagerWithdrawn(new SystemID(message.getContent()));
//							componentController.receiveCommand(message.getSystemID(), Event.REGIONSERVER_CRASHED_VM_WITHDRAWN);
						break;
						case Event.REGIONSERVER_WRITEAHEADLOG_REPLAYED: 
							log.message(this.getClass(),"write ahead log replayed:"+message.getContent()+" from:"+message.getSystemID().getName());
//							componentController.receiveCommand(message.getSystemID(), Event.REGIONSERVER_WRITEAHEADLOG_REPLAYED);
						break;
					}
				break;				
			case Component.viewManager : 
					switch(operation){
					case Event.VIEWMANAGER_ASSIGNED : 
						log.info(this.getClass(),"view manager assigned "+message.getSystemID().getName()+", to:"+message.getContent());
//						controller.viewManagerAssigned(message.getSystemID(), new SystemID(message.getContent()));
//						componentController.receiveCommand(message.getSystemID(), Event.VIEWMANAGER_ASSIGNED);
					break;	
					case Event.VIEWMANAGER_REASSIGNED : 
						log.info(this.getClass(),"view manager reassigned "+message.getSystemID().getName()+", to:"+message.getContent());
//						controller.viewManagerReassigned(message.getSystemID(), new SystemID(message.getContent()));
//						componentController.receiveCommand(message.getSystemID(), Event.VIEWMANAGER_REASSIGNED);
					break;
					case Event.VIEWMANAGER_WITHDRAWN : 
						log.info(this.getClass(),"view manager withdrawn "+message.getSystemID().getName()+", from:"+message.getContent());
//						controller.viewManagerWithdrawn(message.getSystemID());
//						componentController.receiveCommand(message.getSystemID(), Event.VIEWMANAGER_WITHDRAWN);
					break;
					case Event.VIEWMANAGER_SHUTDOWN : 
						log.info(this.getClass(),"view manager shutdown "+message.getSystemID().getName());
//						viewManagerShutdown(message.getSystemID());
//						componentController.receiveCommand(message.getSystemID(), Event.VIEWMANAGER_SHUTDOWN);
					break;	
					case Event.VIEWMANAGER_STATUS_REPORT : 
						log.info(this.getClass(),"status report from "+message.getSystemID().getName()+":"+message.getContent());
						
						
						List<Integer> vals = new ArrayList<Integer>();
						
						for(String val : message.getContent().split(SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE)){
							try{
								vals.add(Integer.parseInt(val));
							}catch(Exception e){
								
							}
						}
						master.getMetaData().getVMReports().put(message.getSystemID(), vals);
						
					break;	
					
					}
				break;
			case Component.client : 
				switch(operation){
				case Command.VMS_STATUS_REPORT : 
					log.info(this.getClass(),"status requested "+message.getSystemID().getName()+", to:"+message.getContent());
					sender.sendStatusReport(message.getSystemID());
					
				break;	
				case Command.VMS_THROUGHPUT_REPORT : 
					log.info(this.getClass(),"throughput requested "+message.getSystemID().getName()+", to:"+message.getContent());
					sender.sendThroughputReport(message.getSystemID(), false);
					
				break;
				case Command.VMS_RS_THROUGHPUT_REPORT : 
					log.info(this.getClass(),"throughput RS requested "+message.getSystemID().getName()+", to:"+message.getContent());
					sender.sendThroughputReport(message.getSystemID(), true);
					
				break;
				case Command.VMS_THROUGHPUT_SUMMARY : 
					log.info(this.getClass(),"throughput summary requested "+message.getSystemID().getName()+", to:"+message.getContent());
					sender.sendThroughputSummary(message.getSystemID(), false);
					
				break;
				case Command.VMS_RS_THROUGHPUT_SUMMARY : 
					log.info(this.getClass(),"throughput summary RS requested "+message.getSystemID().getName()+", to:"+message.getContent());
					sender.sendThroughputSummary(message.getSystemID(), true);
					
				break;
				case Command.VMS_ADD_QUERY : 
					log.info(this.getClass(),"add query "+message.getSystemID().getName()+", to:"+message.getContent());
					String[] splitContent = message.getContent().split(SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE);
					boolean successful = addQuery(splitContent[0], splitContent[1]);
					sender.sendQueryAdded(systemID, splitContent[0], successful);
					
				break;
				case Command.VMS_REMOVE_QUERY : 
					log.info(this.getClass(),"remove query "+message.getSystemID().getName()+" :"+message.getContent());
					successful = removeQuery(message.getContent());
					sender.sendQueryRemoved(systemID, message.getContent(), successful);
					
				break;
				case Command.VMS_RELOAD_QUERY : 
					log.info(this.getClass(),"reload query "+message.getSystemID().getName()+" :"+message.getContent());
					successful = reloadQuery(message.getContent());
					sender.sendQueryReloaded(systemID, message.getContent(), successful);
					
				break;
				case Command.VMS_LIST_QUERIES : 
					log.info(this.getClass(),"query list requested "+message.getSystemID().getName());
					sender.sendQueryList(systemID);
					
				break;
				}
			break;				
		}
	}
	


}
