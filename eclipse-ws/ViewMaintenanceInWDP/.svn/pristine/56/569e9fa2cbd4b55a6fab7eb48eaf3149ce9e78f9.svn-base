package de.webdataplatform.master;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.Server;
import de.webdataplatform.message.ServerMessageHandlerFactory;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.query.QueryManager;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.settings.TableConfig;
import de.webdataplatform.system.Component;
import de.webdataplatform.zookeeper.IZooKeeperService;
import de.webdataplatform.zookeeper.ZookeeperService;

public class Master{

	
	private IZooKeeperService zooKeeperService;
	

	private MasterController masterController;
	
	
	private EventProcessor eventProcessor;
//	
//	private LoadBalancer loadBalancer;
//	
//	private RecoveryManager recoveryManager;
	
	private MasterSender sender;
	
	private MasterDispatcher dispatcher;
	
//	private ComponentController componentController;
	
	
	private Server messageServer;
	
	private Queue<String> incomingMessages;
	
	private SystemID systemID;
	
	private MetaData metaData;
	
	private Log log;
	
//	private Parser queryParser;
//	
//	private QueryTable queryTable;

	private QueryManager queryManager;
	
	
	
	
	public Master(){
		
		
	}
	
	public Master(String name, String ip, int messagePort){
	
	
		this.systemID = new SystemID(Component.master, name, ip, messagePort);
		

//		if(!zooKeeperService.nodeExists("/hbase/vm")){
//			System.out.println("creating new view manager node");
//			zooKeeperService.createPersistentNode("/hbase/vm");
//		}
//		metaTable = new MetaTable();
		
	}
	
	public void initialize(){
		
		log = new Log("master.log");
		
		SystemConfig.load(log);
		NetworkConfig.load(log);
		TableConfig.load(log);
		
		log.info(this.getClass(),"name: "+systemID.getName());
		log.info(this.getClass(),"address: "+systemID.getIp());
		log.info(this.getClass(),"port: "+systemID.getMessagePort());
		log.info(this.getClass(),"message polling interval: "+SystemConfig.MESSAGES_POLLINGINTERVAL);
		
		metaData = new MetaData();
		
		incomingMessages = new ConcurrentLinkedQueue<String>();
		
		messageServer = new Server(new ServerMessageHandlerFactory(log, incomingMessages), systemID.getMessagePort());
		
		queryManager = new QueryManager(log);
		
		sender = new MasterSender(log, this);
		
//		componentController = new ComponentController(log, sender);

		dispatcher = new MasterDispatcher(log, this);

		masterController = new MasterController(log, this);
		

		
//		loadBalancer = new LoadBalancer(log, metaData, componentController);
//		
//		recoveryManager = new RecoveryManager(log, metaData, componentController, loadBalancer);

		eventProcessor = new EventProcessor(log, metaData, masterController);
		
		List<String> trigger = new ArrayList<String>();
		
		trigger.add(SystemConfig.REGIONSERVER_ZOOKEEPERPATH);
		
		trigger.add(SystemConfig.VIEWMANAGER_ZOOKEEPERPATH);
		
		zooKeeperService = new ZookeeperService(log, trigger, eventProcessor);
		
		Thread tcontroller = new Thread(masterController);
		
		tcontroller.start();
		
//		zooKeeperService = new ZookeeperService(zooKeeperAddress, this);
		
		
	}

	public IZooKeeperService getZooKeeperService() {
		return zooKeeperService;
	}

	public void setZooKeeperService(IZooKeeperService zooKeeperService) {
		this.zooKeeperService = zooKeeperService;
	}

	public MasterController getMasterController() {
		return masterController;
	}

	public void setMasterController(MasterController masterController) {
		this.masterController = masterController;
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

	public QueryManager getQueryManager() {
		return queryManager;
	}

	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	public SystemID getSystemID() {
		return systemID;
	}

	public void setSystemID(SystemID systemID) {
		this.systemID = systemID;
	}

	public EventProcessor getEventProcessor() {
		return eventProcessor;
	}

	public void setEventProcessor(EventProcessor eventProcessor) {
		this.eventProcessor = eventProcessor;
	}

//	public ComponentController getComponentController() {
//		return componentController;
//	}
//
//	public void setComponentController(ComponentController componentController) {
//		this.componentController = componentController;
//	}

	public MetaData getMetaData() {
		return metaData;
	}

	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}

	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
	}



	
	

	public MasterSender getSender() {
		return sender;
	}

	public void setSender(MasterSender sender) {
		this.sender = sender;
	}

	public MasterDispatcher getDispatcher() {
		return dispatcher;
	}

	public void setDispatcher(MasterDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}


	


	
}
