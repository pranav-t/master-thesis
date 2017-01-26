package de.webdataplatform.master;

import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.AsyncCallback.ChildrenCallback;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.Command;
import de.webdataplatform.system.Event;
import de.webdataplatform.system.IEventHandler;

public class EventProcessor implements Runnable, ChildrenCallback{

	

	private MetaData metaData;
	
//	private ILoadBalancer loadBalancer;
//	
//	private RecoveryManager recoveryManager;
	
	private IEventHandler controller;
	
	private Log log;
	
	
	public EventProcessor(Log log, MetaData metaData, IEventHandler controller) {
		super();

		this.metaData = metaData;
		this.log = log;
		this.controller = controller;
		
	}


//	EVENTS
	
	
//	public SystemID lookupRSOfViewManager(SystemID viewManager){
//		
//		for(SystemID regionServer : metaData.getAssignedViewManagers().keySet()){
//			
//			List<SystemID> viewManagers = metaData.getAssignedViewManagers().get(regionServer);
//			if(viewManagers.contains(viewManager)){
//				return regionServer;
//				
//			}
//		}
//		return null;
//	}
//	
	

	


	
//	ZOOKEEPER EVENT DISPATCHER
	
	@Override
	public void processResult(int arg0, String arg1, Object arg2, List<String> arg3) {
		

		String znode = arg1;
		List<String> children = arg3;
		
		log.info(this.getClass(), "znode: "+znode);
		log.info(this.getClass(), "children: "+children);
		
		List<SystemID> regionServerAdded = new ArrayList<SystemID>();
		List<SystemID> regionServerRemoved = new ArrayList<SystemID>();
		List<SystemID> regionServerCrashed = new ArrayList<SystemID>();
		List<SystemID> viewManagerAdded = new ArrayList<SystemID>();
		List<SystemID> viewManagerRemoved = new ArrayList<SystemID>();
		List<SystemID> viewManagerCrashed = new ArrayList<SystemID>();

		
		
			if(znode.equals(SystemConfig.REGIONSERVER_ZOOKEEPERPATH)&& children != null){
				
					for(String regionServer : children){
						SystemID rsID = new SystemID(regionServer);
						
						if(!metaData.getRegionServers().contains(rsID)){
							
							regionServerAdded.add(rsID);

						}
					}
					
					for(SystemID regionServer : metaData.getRegionServers()){
						
						if(!children.contains(regionServer.toString())){
							
							if(metaData.getRsRemoved().contains(regionServer)){
								
								regionServerRemoved.add(regionServer);
							}else{
								
								regionServerCrashed.add(regionServer);
							}


							
						}
						
					}
					
				
			}
			for (SystemID rsAdded : regionServerAdded) controller.regionServerAdded(rsAdded);
			for (SystemID rsShutdown : regionServerRemoved) controller.regionServerShutdown(rsShutdown);
			for (SystemID rsCrashed : regionServerCrashed) controller.regionServerCrashed(rsCrashed);
			
			if(znode.equals(SystemConfig.VIEWMANAGER_ZOOKEEPERPATH) && children != null){
				
					for(String viewManager : children){
					
						SystemID vmID = new SystemID(viewManager);
						
						if(!metaData.getViewManagers().contains(vmID)){
							
							viewManagerAdded.add(vmID);
						}
					}
					
					
					
					for(SystemID viewManager : metaData.getViewManagers()){
						
						if(!children.contains(viewManager.toString())){

							if(metaData.getVmRemoved().containsKey(viewManager)){
								viewManagerRemoved.add(viewManager);
								
							}else{
								
								viewManagerCrashed.add(viewManager);
							}
						}
					}
					
				
			}
			
			for (SystemID vmAdded : viewManagerAdded) controller.viewManagerAdded(vmAdded);
			for (SystemID vmShutdown : viewManagerRemoved) controller.viewManagerShutdown(vmShutdown);
			for (SystemID vmCrashed : viewManagerCrashed) controller.viewManagerCrashed(vmCrashed);

		
	}

	@Override
	public void run() {

		
	}
	
	
}
