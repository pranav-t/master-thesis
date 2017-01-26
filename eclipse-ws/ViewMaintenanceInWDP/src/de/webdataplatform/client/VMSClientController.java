package de.webdataplatform.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.Message;
import de.webdataplatform.message.MessageClient;
import de.webdataplatform.message.MessageUtil;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.regionserver.RegionServer;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.Command;
import de.webdataplatform.system.Component;
import de.webdataplatform.system.Event;

public class VMSClientController{

	
	

	
	private VMSClient vmsClient;
	
	private Log log; 
	
	private String[] args;

	public VMSClientController(Log log, String[] args, VMSClient viewManager) {
		
		
		super();

		this.vmsClient = viewManager;
		this.log = log;
		this.args= args;
	}


	
	
	
	public void initialize() throws Exception{
	


//		}while(!created);
		
//		if(!created)throw new Exception("Zookeeper Session node could not be created");
		log.info(RegionServer.class,"connecting to zookeeper on address: "+NetworkConfig.ZOOKEEPER_QUORUM+":"+NetworkConfig.ZOOKEEPER_CLIENTPORT);
		vmsClient.getZooKeeperService().startup();
		
		List<String> result = vmsClient.getZooKeeperService().getChildren(SystemConfig.MASTER_ZOOKEEPERPATH);
		if(result.size() == 1){
			vmsClient.setMaster(new SystemID(result.get(0)));
			log.info(this.getClass(),"master found at: "+vmsClient.getMaster());
			
		}
		
		log.info(this.getClass(),"starting message server on port:"+vmsClient.getSystemID().getMessagePort());
		vmsClient.getMessageServer().start();
		

		
		
	}



	
	

	

//	VIEW MANAGER CALLS
	
	

	
	public void sendStatusRequest(){
		
		log.info(this.getClass(),"sending status request to master:"+vmsClient.getMaster());
		
		Message message = new Message(vmsClient.getSystemID(), Command.VMS_STATUS_REPORT, vmsClient.getSystemID().toString());
		
		MessageClient.sendMessage(log, vmsClient.getMaster(), message);
		
	}
	
	public void sendThroughputReport(){
		
		log.info(this.getClass(),"sending throughput report request to master:"+vmsClient.getMaster());
		
		Message message = new Message(vmsClient.getSystemID(), Command.VMS_THROUGHPUT_REPORT, vmsClient.getSystemID().toString());
		
		MessageClient.sendMessage(log, vmsClient.getMaster(), message);
		
	}
	
	
	public void sendThroughputRSReport(){
		
		log.info(this.getClass(),"sending throughput report request to master:"+vmsClient.getMaster());
		
		Message message = new Message(vmsClient.getSystemID(), Command.VMS_RS_THROUGHPUT_REPORT, vmsClient.getSystemID().toString());
		
		MessageClient.sendMessage(log, vmsClient.getMaster(), message);
		
	}
	
	public void sendThroughputSummary(){
		
		log.info(this.getClass(),"sending throughput summary request to master:"+vmsClient.getMaster());
		
		Message message = new Message(vmsClient.getSystemID(), Command.VMS_THROUGHPUT_SUMMARY, vmsClient.getSystemID().toString());
		
		MessageClient.sendMessage(log, vmsClient.getMaster(), message);
		
	}
	
	public void sendThroughputRSSummary(){
		
		log.info(this.getClass(),"sending throughput summary request to master:"+vmsClient.getMaster());
		
		Message message = new Message(vmsClient.getSystemID(), Command.VMS_RS_THROUGHPUT_SUMMARY, vmsClient.getSystemID().toString());
		
		MessageClient.sendMessage(log, vmsClient.getMaster(), message);
		
	}
	public void sendAddQuery(String queryName, String query){
		
		log.info(this.getClass(),"sending add query master:"+vmsClient.getMaster());
		
		Message message = new Message(vmsClient.getSystemID(), Command.VMS_ADD_QUERY, queryName+SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE+query);
		
		MessageClient.sendMessage(log, vmsClient.getMaster(), message);
		
	}	
	

	public void sendRemoveQuery(String queryName){
		
		log.info(this.getClass(),"sending remove query to master:"+vmsClient.getMaster());
		
		Message message = new Message(vmsClient.getSystemID(), Command.VMS_REMOVE_QUERY, queryName);
		
		MessageClient.sendMessage(log, vmsClient.getMaster(), message);
		
	}

	public void sendReloadQuery(String queryName){
		
		log.info(this.getClass(),"sending reload query to master:"+vmsClient.getMaster());
		
		Message message = new Message(vmsClient.getSystemID(), Command.VMS_RELOAD_QUERY, queryName);
		
		MessageClient.sendMessage(log, vmsClient.getMaster(), message);
		
	}	

	public void sendListQueries(){
		
		log.info(this.getClass(),"sending status request to master:"+vmsClient.getMaster());
		
		Message message = new Message(vmsClient.getSystemID(), Command.VMS_LIST_QUERIES, vmsClient.getSystemID().toString());
		
		MessageClient.sendMessage(log, vmsClient.getMaster(), message);
		
	}
	
	
	


	public void run() {

		try {
			initialize();
		} catch (Exception e) {
			
			log.error(this.getClass(), e);

		}
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input;

		if(args == null || args.length == 0){
			boolean esc = false;
			while (!esc) {
				try {
					System.out.print("Enter input: ");
					input = br.readLine();
					String[] argIn = input.split(" ");
	
					if (argIn[0].equals("quit")) {
						esc = true;
					}else{
						readCommand(argIn);
					}
					
					
				}catch (IOException e) {
					e.printStackTrace();
				}catch (Exception generalEx) {
					generalEx.printStackTrace();
					System.out.println("Error in command");
				}
				
			}
		}
		if(args != null || args.length > 0){
			
			readCommand(args);
			log.info(this.getClass(),"command finished ");
			
		}
		System.exit(0);
		
	}
	
	public String arrayToString(String[] array){
		
		String result="";
		
		for (int i = 0; i < array.length; i++) {
			result += array[i]+" ";
		}
		return result.trim();
		
		
	}
	
	public void readCommand(String[] argIn){
		
		if (argIn[0].equals("status")) {
			sendStatusRequest();
			waitForResponse();
		}else if (argIn[0].equals("throughput")) {
			
			if(argIn[1].equals("rs")){
				if(argIn[2].equals("detailed")){
					
					sendThroughputRSReport();
				}
				if(argIn[2].equals("summary")){
					sendThroughputRSSummary();
				}
			}
			if(argIn[1].equals("detailed")){
				
				sendThroughputReport();
			}
			if(argIn[1].equals("summary")){
				sendThroughputSummary();
			}
			waitForResponse();
		}else if (argIn[0].equals("add")) {
			String queryName = argIn[1];
			String query = arrayToString(argIn).replace("add ", "").replace(queryName+" ", "");
			sendAddQuery(queryName, query);
			waitForResponse();
		}else if (argIn[0].equals("remove")) {
			String queryName = argIn[1];
			sendRemoveQuery(queryName);
			waitForResponse();
		}else if (argIn[0].equals("reload")) {
			String queryName = argIn[1];
			sendReloadQuery(queryName);
			waitForResponse();
		}else if (argIn[0].equals("list")) {
			sendListQueries();
			waitForResponse();
		}else{
			System.out.println("command not found ");
			System.out.println("--status");
			System.out.println("--throughput [detailed, summary]");
			System.out.println("--add [queryName] [query]");
			System.out.println("--remove [queryName]");
			System.out.println("--reload [queryName]");
			System.out.println("--list");
			System.out.println("--quit");
		}
		
		
		
		
	}
	
	
	public void waitForResponse(){
		
		boolean response = false;
		int i = 0;
		
		while(!response && i < 50000){
		
				/**receive incoming messages*/
				String messageString = vmsClient.getIncomingMessages().poll();
				i++;
				
				if(messageString != null){
					
						response = true;
					
						Message message = new Message(messageString);
						log.message(this.getClass(),"received message: "+message);
						
						String component = message.getSystemID().getComponent();
						String operation = message.getOperation();
						String content = message.getContent();
			
						
						switch(component){
						
							case Component.master : 
									switch(operation){
										case Event.VMS_STATUS_REPORT : 
											String[] result = content.split(SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE);
											for (String string : result) {
												log.info(this.getClass(),string);
											}
								
										break;	
										case Event.VMS_THROUGHPUT_REPORT : 
											log.info(this.getClass(),"received message: "+content);
											Map<SystemID, List<Integer>> map = MessageUtil.readMap(content);
											for (SystemID vmID : map.keySet()) {
												log.info(this.getClass(),vmID+": "+map.get(vmID));
											}
								
										break;	
										case Event.VMS_THROUGHPUT_SUMMARY : 
											log.info(this.getClass(),"received message: "+content);
											map = MessageUtil.readMap(content);
											for (SystemID vmID : map.keySet()) {
												log.info(this.getClass(),vmID+": "+map.get(vmID));
											}
								
										break;	
										case Event.VMS_QUERY_ADDED : 
											log.info(this.getClass(),"received message: "+content);
								
										break;	
										case Event.VMS_QUERY_REMOVED : 
											log.info(this.getClass(),"received message: "+content);
								
										break;	
										case Event.VMS_QUERY_LIST: 
								
											log.info(this.getClass(),"query list vms:");
											result = content.split(SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE);
											for (String string : result) {
												log.info(this.getClass(),string);
											}
											
										break;	
									}
							break;
									
						}				
				}
				
				try {
					Thread.sleep(SystemConfig.MESSAGES_POLLINGINTERVAL);
				} catch (InterruptedException e) {
					log.error(this.getClass(), e);
				}
				
				
		}
	}
	
	

}
