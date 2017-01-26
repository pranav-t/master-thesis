package de.webdataplatform.regionserver;

import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.message.UpdateClient;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.StatisticsElement;

public class SendingThread extends Thread{


	private Log log;   
	
	private StatisticsElement statisticsSent;
	
	public SendingThread(Log log, StatisticsElement statisticsSent) {
		super();
		this.log = log;
		this.statisticsSent = statisticsSent;
	}

	
	
	
	private Queue<AssignedUpdate> sendingQueue=new ConcurrentLinkedQueue<AssignedUpdate>(); 
	
	private Map<SystemID, UpdateClient> receiverMap = new ConcurrentHashMap<SystemID, UpdateClient>();
	  
	  
	
	
	public synchronized void queueUpdate(AssignedUpdate bto) { 
		  
		  sendingQueue.add(bto);
		  
	}
	
	public int getQueueSize(){
		
		return sendingQueue.size();
	}
	
	
	
	@Override
	public void run() {
		
		
		
	    while(true){
	    	
	    	
	    	try{

	    		AssignedUpdate assignedUpdate = sendingQueue.poll();
	    		
	    		if (assignedUpdate != null){
	    			
	    			
	    			long start = System.nanoTime();
	    			receiverMap.get(assignedUpdate.getViewManager()).sendUpdate(assignedUpdate.getBaseTableOperation());

	    			
	    			statisticsSent.recordLatency(System.nanoTime()-start);
	    			statisticsSent.recordUpdate();
	    			
	    		}else{
	    		
		    		try {
		    			Thread.sleep(SystemConfig.VIEWMANAGER_UPDATEPOLLINGINTERVAL);
		    		} catch (InterruptedException e) {
		    			
		    			e.printStackTrace();
		    		}
	    		}
				
	    	}catch(Exception e){
				log.error(this.getClass(), e);
			}
	    	
	    	
	    }



	}

	public Queue<AssignedUpdate> getSendingQueue() {
		return sendingQueue;
	}

	public void setSendingQueue(Queue<AssignedUpdate> sendingQueue) {
		this.sendingQueue = sendingQueue;
	}

	public Map<SystemID, UpdateClient> getReceiverMap() {
		return receiverMap;
	}

	public void setReceiverMap(Map<SystemID, UpdateClient> receiverMap) {
		this.receiverMap = receiverMap;
	}


	
	

}
