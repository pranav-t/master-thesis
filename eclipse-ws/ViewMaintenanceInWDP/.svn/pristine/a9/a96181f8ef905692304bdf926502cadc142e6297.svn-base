package de.webdataplatform.regionserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.message.UpdateClient;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.StatisticsElement;

public class UpdateDistributor{

	private int maxThreads;
	
	
	private List<SendingThread> runningThreads=new ArrayList<SendingThread>();

	private Map<SystemID, SendingThread> assignedThreads = new ConcurrentHashMap<SystemID, SendingThread>();
	

	private SystemID selfID;
	
	private Log log;
	
	private StatisticsElement statisticsSent;
	
	public UpdateDistributor(Log log, SystemID selfID, StatisticsElement statisticsSent, int maxThreads){

		this.maxThreads = maxThreads;
		this.selfID = selfID;
		this.log = log;
		this.statisticsSent = statisticsSent;
	}

	
	public void start(){
		
		log.info(this.getClass(),"starting update distribution: "+maxThreads+" threads");
		for (int i = 0; i < maxThreads; i++) {
			
			
			SendingThread sendingThread = new SendingThread(log, statisticsSent);
			
			Thread thread = new Thread(sendingThread);
			thread.start();
			
			runningThreads.add(sendingThread);
			
		}
		
		
		
	}

	private int count=0;
	
	public synchronized void addReceiver(SystemID receiver){
		
		
		UpdateClient updateClient = new UpdateClient(log, selfID, receiver);
		
		updateClient.initialize();
		
		int numThread = count%maxThreads;
		
		log.info(this.getClass(), "add receiver: "+receiver);
		
		log.info(this.getClass(), "thread number: "+numThread+" assigned");
		
		SendingThread sendingThread = runningThreads.get(numThread);
		
		log.info(this.getClass(), "sending thread: "+sendingThread);
		
		sendingThread.getReceiverMap().put(receiver, updateClient);
		
		assignedThreads.put(receiver, sendingThread);
		
		count++;
		
		
		

	}
	
	public void removeReceiver(SystemID receiver){
		
		
//		receiverMap.remove(receiver);
		

	}
	
	public int getQueueSize(){
		
		int result=0;
		for (SendingThread sendingThread : runningThreads) {
			
			result+=sendingThread.getQueueSize();
		}
		return result;
	}

	
	public void queueUpdate(AssignedUpdate assignedUpdate) throws NoQueueForViewManagerException{
		
		
		assignedThreads.get(assignedUpdate.getViewManager()).queueUpdate(assignedUpdate);
			

		
	}

	
	
	
	



	
	
	

}
