package de.webdataplatform.client;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.Server;
import de.webdataplatform.message.ServerMessageHandlerFactory;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.Component;
import de.webdataplatform.zookeeper.IZooKeeperService;
import de.webdataplatform.zookeeper.ZookeeperService;

public class VMSClient implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5256629957274602452L;

	/**
	 * 
	 */

	



	private SystemID systemID;
	
	private IZooKeeperService zooKeeperService;
	
	private Server messageServer ;
	
	
	
	private Queue<String> incomingMessages;
	
	private VMSClientController clientController;
	

	
	
	private SystemID master;

	private Log log;
	
	
	
	public VMSClient(String name, String ip, int messagePort){

		this.systemID = new SystemID(Component.client, name, ip, messagePort);
		

	}
	
	public VMSClient(Log log, String name, String ip, int messagePort){

		this.log = log;
		this.systemID = new SystemID(Component.client, name, ip, messagePort);
		

	}


	/*
	 * (non-Javadoc)
	 * @see de.webdataplatform.viewmanager.IViewManager#getVMName()
	 */
	public String getVMName() {
		return systemID.getName();
	}

	
	
	public static void main(String[] args) {
		

		Log log = new Log("vmsclient.log");

		SystemConfig.load(log);
		NetworkConfig.load(log);
		
		
		VMSClient client=null;
		
		client = new VMSClient(log, "client1", NetworkConfig.VMS_CLIENT, 5555);
		
		client.initialize(args);
		
	}
	
	public void initialize(String[] args){
		
	
		log.info(this.getClass(),"initializing new VMS client:");
		log.info(this.getClass(),"name: "+systemID.getName());
		log.info(this.getClass(),"address: "+systemID.getIp());
		log.info(this.getClass(),"port: "+systemID.getMessagePort());

		
		/** components for inter-component communication */
		
		incomingMessages = new ConcurrentLinkedQueue<String>();
		
		messageServer = new Server(new ServerMessageHandlerFactory(log, incomingMessages), systemID.getMessagePort());
		
		zooKeeperService = new ZookeeperService(log);
		
		clientController = new VMSClientController(log, args, this);
		
//		Thread controllerThread = new Thread(clientController);
		
		clientController.run();
		

	}



	public IZooKeeperService getZooKeeperService() {
		return zooKeeperService;
	}


	public void setZooKeeperService(IZooKeeperService zooKeeperService) {
		this.zooKeeperService = zooKeeperService;
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


	public VMSClientController getVmController() {
		return clientController;
	}


	public void setVmController(VMSClientController vmController) {
		this.clientController = vmController;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	

	public SystemID getMaster() {
		return master;
	}


	public void setMaster(SystemID master) {
		this.master = master;
	}


	public SystemID getSystemID() {
		return systemID;
	}


	public void setSystemID(SystemID systemID) {
		this.systemID = systemID;
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
		VMSClient other = (VMSClient) obj;
		if (systemID == null) {
			if (other.systemID != null)
				return false;
		} else if (!systemID.equals(other.systemID))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "ViewManager [systemID=" + systemID + "]";
	}









	
	
}
