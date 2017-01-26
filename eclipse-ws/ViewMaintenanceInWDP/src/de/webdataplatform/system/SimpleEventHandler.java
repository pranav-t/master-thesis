package de.webdataplatform.system;

import de.webdataplatform.log.Log;
import de.webdataplatform.master.MetaData;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.regionserver.UpdateAssigner;
import de.webdataplatform.regionserver.UpdateDistributor;
import de.webdataplatform.viewmanager.ViewManager;

public class SimpleEventHandler implements IEventHandler{

	
	
	private Log log;

	private UpdateAssigner updateAssigner;
	
	private UpdateDistributor updateDistributor;
	
	private MetaData metaData;
	
	private SystemID selfID;
	
	
	
	
	
	public SimpleEventHandler(Log log, UpdateAssigner updateAssigner, UpdateDistributor updateDistributor, MetaData metaData, SystemID selfID) {
		super();
		this.log = log;
		this.updateAssigner = updateAssigner;
		this.updateDistributor = updateDistributor;
		this.metaData = metaData;
		this.selfID = selfID;
	}


	public void viewManagerAdded(SystemID vmID){
		
		log.info(this.getClass(),"view manager has been added: "+vmID);
		metaData.getViewManagers().add(vmID);

		if(!vmID.equals(selfID)){
				
			ViewManager viewManager = new ViewManager(vmID);
			
			log.info(this.getClass(),"updateDistributor: "+updateDistributor);
			updateDistributor.addReceiver(viewManager.getSystemID());
		
			log.info(this.getClass(),"assigning view manager to hash ring");
			updateAssigner.addViewManager(viewManager);
			
//			log.info(this.getClass(),"starting sending thread of view manager:"+viewManager);
//			try {
//				updateDistributor.startSendingThread(viewManager);
//			} catch (InterruptedException e) {
//	
//				e.printStackTrace();
//			}
		}else{
			log.info(this.getClass(),"self added: "+vmID);
			
			ViewManager viewManager = new ViewManager(vmID);
			
			log.info(this.getClass(),"assigning self to hash ring");
			updateAssigner.addViewManager(viewManager);
		}

	}
	
	
	@Override
	public void viewManagerShutdown(SystemID vmID) {

		log.info(this.getClass(),"view manager has been shutdown: "+vmID);
		metaData.getViewManagers().remove(vmID);
		
		ViewManager viewManager = new ViewManager(vmID.getName(), vmID.getIp(),vmID.getUpdatePort(), vmID.getMessagePort());
		
//		removeMarker(viewManager);
		
		updateAssigner.removeViewManager(viewManager);
		
//		try {
//			updateDistributor.stopSendingThread(viewManager);
//		} catch (InterruptedException e) {
//
//			e.printStackTrace();
//		}
		
//		log.info(this.getClass(),"sending thread stopped");
		
//		updateDistributor.emptyQueue(viewManager);	
			
		updateDistributor.removeReceiver(viewManager.getSystemID());
		
		log.info(this.getClass(),"queue removed");
		
	}

	
	public void viewManagerCrashed(SystemID vmID){
		

		log.info(this.getClass(),"view manager has been crashed: "+vmID);
		metaData.getViewManagers().remove(vmID);

		ViewManager viewManager = new ViewManager(vmID.getName(), vmID.getIp(),vmID.getUpdatePort(), vmID.getMessagePort());
		
//		removeMarker(viewManager);
		
		updateAssigner.removeViewManager(viewManager);
		
//		try {
//			updateDistributor.stopSendingThread(viewManager);
//		} catch (InterruptedException e) {
//
//			e.printStackTrace();
//		}
		
		log.info(this.getClass(),"sending thread stopped");
		
//		updateDistributor.emptyQueue(viewManager);	
			
		updateDistributor.removeReceiver(viewManager.getSystemID());
		
		log.info(this.getClass(),"queue removed");


	}



	@Override
	public void regionServerAdded(SystemID rsID) {

		log.info(this.getClass(),"region server has been added: "+rsID);
		metaData.getRegionServers().add(rsID);

		if(!rsID.equals(selfID)){
			
		}else{
			log.info(this.getClass(),"self added");
		}
		
	}



	@Override
	public void regionServerShutdown(SystemID rsID) {
		
		log.info(this.getClass(),"region server has been shutdown: "+rsID);
		metaData.getRegionServers().remove(rsID);
		
	}



	@Override
	public void regionServerCrashed(SystemID rsID) {
		
		log.info(this.getClass(),"region server has crashed: "+rsID);
		metaData.getRegionServers().remove(rsID);
		
	}






	

//	if(regionServer.getUpdateAssigner().numOfVms() <= 1){
//		
//		log.info(this.getClass(),"num of view manager <= 1, starting sending thread instantly");
//		regionServer.getUpdateDistributor().startSendingThread(viewManager);
//		
//	}else{
//		
//		log.info(this.getClass(),"num of view manager > 1, queuing markers");
//		queueMarkers(SystemConfig.MESSAGES_MARKERPREFIX+"assign"+vmID.toString());
//	}
	

}
