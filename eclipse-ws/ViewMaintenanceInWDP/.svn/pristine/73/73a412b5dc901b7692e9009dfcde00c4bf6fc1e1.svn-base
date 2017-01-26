package de.webdataplatform.viewmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

import de.webdataplatform.basetable.BaseTableOperation;
import de.webdataplatform.log.Log;
import de.webdataplatform.log.StatisticLog;
import de.webdataplatform.master.EventProcessor;
import de.webdataplatform.master.MetaData;
import de.webdataplatform.message.Server;
import de.webdataplatform.message.ServerMessageHandlerFactory;
import de.webdataplatform.message.ServerUpdateHandlerFactory;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.query.QueryManager;
import de.webdataplatform.regionserver.UpdateDistributor;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.Component;
import de.webdataplatform.system.StatisticsElement;
import de.webdataplatform.viewmanager.processing.Processing;
import de.webdataplatform.zookeeper.IZooKeeperService;
import de.webdataplatform.zookeeper.ZookeeperService;

public class ViewManager  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 201204933834777882L;
	
	// Identification 
	private SystemID systemID;

	private SystemID masterID;

	
	
	// Data structures
	private MetaData metaData;
	
	private String condition;
	
//	private AtomicLong updatesReceived;
	
//	private AtomicLong updatesAssigned;
//	
	private StatisticsElement statisticsSent;
	
	private StatisticsElement statisticReceived;
	

	private Queue<String> incomingMessages;
	
	private Queue<String> incomingUpdates;
	


	
	
	// Message components	
	private Server updateServer ;
	
	private Server messageServer ;
	
	
	
	// Subcomponents
	private IZooKeeperService zooKeeperService;

	private VMController vmController;
	
	private EventProcessor eventProcessor;
	
//	private PreProcessing preProcessing ;
	
	private Processing processing;
	
//	private UpdateAssigner updateAssigner;
	
	private UpdateDistributor updateDistributor;
	
	private QueryManager queryManager;



	
	
//	private SystemID regionServer;
//	
//	private SystemID reassignRS;
//	
	

	
	

	

	private Log log;
	
	private Log statisticsLog;
	
	public ViewManager(SystemID systemID){

		this.systemID = systemID;
		

	}
	
	
	public ViewManager(String name, String ip, int updatePort, int messagePort){

		this.systemID = new SystemID(Component.viewManager, name, ip, messagePort, updatePort);
		

	}
	
	public ViewManager(Log log, String name, String ip, int updatePort, int messagePort){

		this.log = log;
		statisticsLog = new Log(name+"-statistics.log");
		this.systemID = new SystemID(Component.viewManager, name, ip, messagePort, updatePort);
		

	}


	/*
	 * (non-Javadoc)
	 * @see de.webdataplatform.viewmanager.IViewManager#getVMName()
	 */
	public String getVMName() {
		return systemID.getName();
	}

	
	
	public void initialize() {
		
//		log = new Log(systemID.getName()+".log");
		StatisticLog.name = systemID.getName();
		StatisticLog.targetDirectory = "logs/";
		
		log.info(this.getClass(),"initializing new View Manager with config:");
		log.info(this.getClass(),"name: "+systemID.getName());
		log.info(this.getClass(),"address: "+systemID.getIp());
		log.info(this.getClass(),"port: "+systemID.getMessagePort());
		log.info(this.getClass(),"-------------");
        
		
		log.info(this.getClass(),"creating data structures");
		
        metaData = new MetaData();
        
        incomingMessages = new LinkedBlockingQueue<String>();
		
        incomingUpdates = new LinkedBlockingQueue<String>();
        
        
        
        statisticReceived = new StatisticsElement();

        statisticsSent = new StatisticsElement();


        log.info(this.getClass(),"creating message components ");
        
        messageServer = new Server(new ServerMessageHandlerFactory(log, incomingMessages), systemID.getMessagePort());
		
        updateServer = new Server(new ServerUpdateHandlerFactory(log, incomingUpdates, statisticReceived), systemID.getUpdatePort());


        
        
        log.info(this.getClass(),"creating Subcomponents ");

//		updateAssigner = new UpdateAssigner(log, new ArrayList<ViewManager>());
        
        updateDistributor = new UpdateDistributor(log, systemID, statisticsSent, 1);
		
		queryManager = new QueryManager(log);
		
//		preProcessing = new PreProcessing(log, incomingUpdates, preprocessedUpdates);
		
		processing = new Processing(log, systemID, updateDistributor, incomingUpdates, getVMName(), queryManager);
		
		vmController = new VMController(log, this);
		
		eventProcessor = new EventProcessor(log, metaData, vmController);
		
		
		
		log.info(this.getClass(),"connecting to zookeeper");
		
		List<String> trigger = new ArrayList<String>();
		
		trigger.add(SystemConfig.REGIONSERVER_ZOOKEEPERPATH);
		
		trigger.add(SystemConfig.VIEWMANAGER_ZOOKEEPERPATH);
		
		zooKeeperService = new ZookeeperService(log, trigger, eventProcessor);

		
		
		log.info(this.getClass(),"initializing controller");

		Thread controllerThread = new Thread(vmController);
		
		controllerThread.start();
		


	}



	public IZooKeeperService getZooKeeperService() {
		return zooKeeperService;
	}


	public void setZooKeeperService(IZooKeeperService zooKeeperService) {
		this.zooKeeperService = zooKeeperService;
	}


	public Server getUpdateServer() {
		return updateServer;
	}


	public void setUpdateServer(Server updateServer) {
		this.updateServer = updateServer;
	}


	public Server getMessageServer() {
		return messageServer;
	}


	public void setMessageServer(Server messageServer) {
		this.messageServer = messageServer;
	}


	public Queue<String> getIncomingMessages() {
		return incomingMessages;
	}


	public void setIncomingMessages(Queue<String> incomingMessages) {
		this.incomingMessages = incomingMessages;
	}


	public Queue<String> getIncomingUpdates() {
		return incomingUpdates;
	}


	public void setIncomingUpdates(Queue<String> incomingUpdates) {
		this.incomingUpdates = incomingUpdates;
	}



	public VMController getVmController() {
		return vmController;
	}


	public void setVmController(VMController vmController) {
		this.vmController = vmController;
	}

//
//	public PreProcessing getPreProcessing() {
//		return preProcessing;
//	}
//
//
//	public void setPreProcessing(PreProcessing preProcessing) {
//		this.preProcessing = preProcessing;
//	}


	public Processing getProcessing() {
		return processing;
	}


	public void setProcessing(Processing processing) {
		this.processing = processing;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	

	public QueryManager getQueryManager() {
		return queryManager;
	}

	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	public SystemID getMasterID() {
		return masterID;
	}


	public void setMasterID(SystemID master) {
		this.masterID = master;
	}


	public SystemID getSystemID() {
		return systemID;
	}


	public void setSystemID(SystemID systemID) {
		this.systemID = systemID;
	}



	


	public Log getStatisticsLog() {
		return statisticsLog;
	}


	public void setStatisticsLog(Log statisticsLog) {
		this.statisticsLog = statisticsLog;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((systemID == null) ? 0 : systemID.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ViewManager other = (ViewManager) obj;
		if (systemID == null) {
			if (other.systemID != null)
				return false;
		} else if (!systemID.equals(other.systemID))
			return false;
		return true;
	}


	public String getCondition() {
		return condition;
	}


	public void setCondition(String condition) {
		this.condition = condition;
	}

//
//	public AtomicLong getUpdatesReceived() {
//		return updatesReceived;
//	}
//
//
//	public void setUpdatesReceived(AtomicLong updatesReceived) {
//		this.updatesReceived = updatesReceived;
//	}


	public QueryManager getQueryPlanner() {
		return queryManager;
	}

	public void setQueryPlanner(QueryManager queryPlanner) {
		this.queryManager = queryPlanner;
	}

	
	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

//	public UpdateAssigner getUpdateAssigner() {
//		return updateAssigner;
//	}
//
//	public void setUpdateAssigner(UpdateAssigner updateAssigner) {
//		this.updateAssigner = updateAssigner;
//	}

	public UpdateDistributor getUpdateDistributor() {
		return updateDistributor;
	}

	public void setUpdateDistributor(UpdateDistributor updateDistributor) {
		this.updateDistributor = updateDistributor;
	}

	@Override
	public String toString() {
		return systemID.getName();
	}


	public StatisticsElement getStatisticReceived() {
		return statisticReceived;
	}


	public void setStatisticReceived(StatisticsElement statisticReceived) {
		this.statisticReceived = statisticReceived;
	}









	
	
}
