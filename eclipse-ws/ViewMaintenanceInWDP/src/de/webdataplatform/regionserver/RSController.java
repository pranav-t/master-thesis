package de.webdataplatform.regionserver;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.webdataplatform.basetable.BaseTableOperation;
import de.webdataplatform.log.Log;
import de.webdataplatform.message.Message;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.IEventHandler;
import de.webdataplatform.system.SimpleEventHandler;
import de.webdataplatform.viewmanager.ViewManager;

public class RSController implements Runnable, IEventHandler{

	private Log log;
	
	private RegionServer regionServer;
	
	private RSSender sender;
	
	private RSDispatcher dispatcher;
	
	private IEventHandler eventHandler;
	
	
	
	public RSController(Log log, RegionServer regionServer) {
		super();
		this.regionServer = regionServer;
		this.log = log;
		this.sender = new RSSender(log, regionServer);
		this.dispatcher = new RSDispatcher(log, sender, this, regionServer);
		this.eventHandler = new SimpleEventHandler(log, regionServer.getUpdateAssigner(), regionServer.getUpdateDistributor(), regionServer.getMetaData(), regionServer.getSystemID());
	}


	public void initialize() throws Exception{
		
		
		log.info(RegionServer.class,"Starting region server: name:"+regionServer.getSystemID().getName()+", ip:"+regionServer.getSystemID().getIp()+", message port:"+regionServer.getSystemID().getMessagePort());
		
		log.info(this.getClass(),"starting message server on port:"+regionServer.getSystemID().getMessagePort());
		regionServer.getMessageServer().start();
		
		log.info(this.getClass(),"starting update server on port:"+regionServer.getSystemID().getUpdatePort());
		regionServer.getUpdateServer().start();
		
		
		log.info(RegionServer.class,"connecting to zookeeper on address: "+NetworkConfig.ZOOKEEPER_QUORUM+":"+NetworkConfig.ZOOKEEPER_CLIENTPORT);
		regionServer.getZooKeeperService().startup();
		
		List<String> result = regionServer.getZooKeeperService().getChildren(SystemConfig.MASTER_ZOOKEEPERPATH);
		if(result.size() == 1){
			System.out.println(result.get(0));
			regionServer.setMasterID(new SystemID(result.get(0)));
			log.info(RegionServer.class,"master found at: "+regionServer.getMasterID());
			
		}
		
		log.info(RegionServer.class,"creating session node");
		boolean created = regionServer.getZooKeeperService().createSessionNode(SystemConfig.REGIONSERVER_ZOOKEEPERPATH+"/"+regionServer.getSystemID().toString());
		
		if(!created)throw new Exception("Zookeeper Session node could not be created");
		
		
		
		log.info(this.getClass(),"loading queries:");
		regionServer.getQueryManager().loadQueries();
		
		log.info(this.getClass(),"starting wal reader ");
		Thread t = new Thread(regionServer.getWalReader());
		
		t.start();
		
		log.info(this.getClass(),"starting update processing ");
		Thread trs = new Thread(regionServer);
		trs.start();
		
		log.info(this.getClass(),"starting update distribution: "+SystemConfig.REGIONSERVER_SENDINGTHREADS+" threads");
		regionServer.getUpdateDistributor().start();

		
		
	}
	
	// SYSTEM EVENTS
	

	@Override
	public void regionServerAdded(SystemID rsID) {
		
		eventHandler.regionServerAdded(rsID);
		
	}


	@Override
	public void regionServerShutdown(SystemID rsID) {

		eventHandler.regionServerShutdown(rsID);
		
	}


	@Override
	public void regionServerCrashed(SystemID rsID) {

		eventHandler.regionServerCrashed(rsID);
		
	}


	@Override
	public void viewManagerAdded(SystemID vmID) {
		
		eventHandler.viewManagerAdded(vmID);
		
	}


	@Override
	public void viewManagerShutdown(SystemID vmID) {

		eventHandler.viewManagerShutdown(vmID);
		
	}


	@Override
	public void viewManagerCrashed(SystemID vmID) {

		eventHandler.viewManagerCrashed(vmID);
		
	}



	
	
	
	

	
	
	public void replayWriteAheadLog(Map<String, Long> seqNo) throws InterruptedException{
		
		log.info(this.getClass(),"replay wal");

//		regionServer.getWalReader().replayWAL(seqNo);
		
	}	
	
	public String generateMarker(){
		
		Set<String> markerKeys = regionServer.getMarkers().keySet();
	
		Integer highestMarker=Integer.MIN_VALUE;
		for(String marker : markerKeys){
			
			Integer markerCount = Integer.parseInt(marker.replace(SystemConfig.MESSAGES_MARKERPREFIX, ""));
			if(markerCount > highestMarker){
				highestMarker = markerCount;
			}
			
		}
		if(highestMarker == Integer.MIN_VALUE)highestMarker = 0;
		
		String newMarker = SystemConfig.MESSAGES_MARKERPREFIX+(highestMarker+1);
		
		return newMarker;

	}
	
	public void queueMarkers(String marker){
		

		
		if(marker == null)marker = generateMarker();
		
		Set<ViewManager> viewManagers = regionServer.getUpdateAssigner().getViewManager();
		
		if(marker.contains("assign")){
			
			String viewManagerId = marker.split("assign")[1];
			SystemID systemID = new SystemID(viewManagerId);
			ViewManager viewManager = new ViewManager(systemID.getName(), systemID.getIp(),systemID.getUpdatePort(), systemID.getMessagePort());
			viewManagers.remove(viewManager);
		}	
		
		regionServer.getMarkers().put(marker, viewManagers);
		
		log.info(this.getClass(),"queuing markers:"+marker+"to vms:"+viewManagers);
		
		for (ViewManager viewManager : viewManagers) {
		
			
				BaseTableOperation btu = new BaseTableOperation(marker, regionServer.getSystemID().toString(),"region", "m1", "m1","m2","m3", new HashMap<String, String>(), new HashMap<String, String>());
	
//				
//				try {
////					regionServer.getUpdateDistributor().queueUpdate(viewManager, btu.convertToString());
//				} catch (NoQueueForViewManagerException e) {
//				   log.error(this.getClass(), e);
//				} 
			
		}
		
	}
	
	
	public void removeMarker(ViewManager viewManager){
		
		for(String marker : regionServer.getMarkers().keySet()){
			
			if(regionServer.getMarkers().get(marker).contains(viewManager)){
				
				regionServer.getMarkers().get(marker).remove(viewManager);
				
			}
			
		}
	}
	
	public void markerReceived(SystemID vmID, String marker){
		
		
		Set<ViewManager> viewManagers = regionServer.getMarkers().get(marker);
		
		ViewManager viewManager = new ViewManager(vmID.getName(), vmID.getIp(),vmID.getUpdatePort(), vmID.getMessagePort());
		
		viewManagers.remove(viewManager);
		
		if(viewManagers.isEmpty()){
			log.info(this.getClass(),"all answers received for marker: "+marker);
			
			if(marker.contains("assign")){
				
				
				String viewManagerId = marker.split("assign")[1];
				
				log.info(this.getClass(),"markers received for assigning view manager:"+viewManagerId);
				
				vmID = new SystemID(viewManagerId);

				viewManager = new ViewManager(vmID.getName(), vmID.getIp(),vmID.getUpdatePort(), vmID.getMessagePort());
				
				
			}
	
			
		}
		
		
	}
	

	
	private long lastMeasure = new Date().getTime();
	

	
	@Override
	public void run() {
	
		try {
			initialize();
		} catch (Exception e) {

			log.error(this.getClass(), e);

		}
		
		while(true){
			
			try{
			
			long currentTime = new Date().getTime();
//			
//			/**send status report to master*/
			if((currentTime - lastMeasure) > SystemConfig.REGIONSERVER_STATUSINTERVAL){
				
				lastMeasure = currentTime;
				
				sender.sendStatusReport();
				
			}

			
			
			String messageString = regionServer.getIncomingMessages().poll();
			
			
			/**process incoming messages*/
			if(messageString != null){
				
				Message message = new Message(messageString);
				
				log.message(this.getClass(),"receiving message: "+message);

				dispatcher.receiveMessage(message);

				
				
			}
			try {
				Thread.sleep(SystemConfig.MESSAGES_POLLINGINTERVAL);
			} catch (InterruptedException e) {
				log.error(this.getClass(), e);
//				e.printStackTrace();
			}
			
			
			}catch(Exception e){
				log.error(this.getClass(), e);
			}
			
		}

		
	}



	

}
