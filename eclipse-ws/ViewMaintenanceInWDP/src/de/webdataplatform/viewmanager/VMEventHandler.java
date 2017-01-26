package de.webdataplatform.viewmanager;

import de.webdataplatform.log.Log;
import de.webdataplatform.master.MetaData;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.regionserver.UpdateAssigner;
import de.webdataplatform.regionserver.UpdateDistributor;
import de.webdataplatform.system.IEventHandler;

public class VMEventHandler implements IEventHandler{

	
	
	private Log log;
	
	private UpdateDistributor updateDistributor;
	
	private MetaData metaData;
	
	private SystemID selfID;
	
	
	
	
	
	public VMEventHandler(Log log,  UpdateDistributor updateDistributor, MetaData metaData, SystemID selfID) {
		super();
		this.log = log;
		this.updateDistributor = updateDistributor;
		this.metaData = metaData;
		this.selfID = selfID;
	}


	public void viewManagerAdded(SystemID vmID){
		
		log.info(this.getClass(),"view manager has been added: "+vmID);
		metaData.getViewManagers().add(vmID);


	}
	
	
	@Override
	public void viewManagerShutdown(SystemID vmID) {

		log.info(this.getClass(),"view manager has been shutdown: "+vmID);
		metaData.getViewManagers().remove(vmID);

		
	}

	
	public void viewManagerCrashed(SystemID vmID){
		

		log.info(this.getClass(),"view manager has been crashed: "+vmID);
		metaData.getViewManagers().remove(vmID);



	}



	@Override
	public void regionServerAdded(SystemID rsID) {

		log.info(this.getClass(),"region server has been added: "+rsID);
		metaData.getRegionServers().add(rsID);

		log.info(this.getClass(),"updateDistributor: "+updateDistributor);
		updateDistributor.addReceiver(rsID);
		
		
		
	}



	@Override
	public void regionServerShutdown(SystemID rsID) {
		
		log.info(this.getClass(),"region server has been shutdown: "+rsID);
		metaData.getRegionServers().remove(rsID);
		
//		log.info(this.getClass(),"updateDistributor: "+updateDistributor);
//		updateDistributor.removeReceiver(rsID);
		
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
