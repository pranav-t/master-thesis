package de.webdataplatform.message;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.webdataplatform.basetable.BaseTableOperation;
import de.webdataplatform.log.Log;
import de.webdataplatform.regionserver.AssignedUpdate;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.Command;
import de.webdataplatform.viewmanager.ViewManager;

public class UpdateClient{

	
	  private SystemID selfID;	
	  private SystemID receiverID;
	  
	
	  
	  private Socket socket;
	  private volatile boolean running =false;
	  private Log log; 
	  
	  private PrintWriter printWriter;
	 
	  public boolean isRunning() {
		return running;
	  }

	public void terminate() {
		this.running = false;
	}

	public UpdateClient(Log log, SystemID selfID, SystemID receiverID) {


		this.selfID = selfID;
		this.receiverID = receiverID;
	    this.log = log;

	  }
	
	
	  public void initialize(){
		 
		  
		  
		  try{
			  
			    socket = new Socket(receiverID.getIp(), receiverID.getUpdatePort());  //verbindet sich mit Server
	
			    log.info(this.getClass(),"starting handshake for "+receiverID.getName()+":"+receiverID.getUpdatePort());
			    
				Message message = new Message(selfID, Command.REGIONSERVER_START_UPDATE_PROCESSING, selfID.toString());		    
			    
				printWriter = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()), false);
				
				sendMessage(message.convertToString());
			  
			    String empfangeneNachricht = leseNachricht();
			    
			    log.info(this.getClass(), "receiving handshake from "+receiverID.getName()+":  "+empfangeneNachricht);
		  
			    
			    
		    
		  }catch(IOException e){
			  log.error(this.getClass(), e);
		  }
		
		  
	  }
	  

	  

	  public synchronized boolean sendUpdate(BaseTableOperation bto) { 

		  try{


			  	
		    	if(bto != null){
		    		
		    		
					sendMessage(bto.convertToMessageString());

					return true;
		
		    	}
			    	
			    	
			  }catch (Exception e){
				  log.error(this.getClass(),e);
				  running =false;
			  }
		  return false;
	  }
	 
	  


	void sendMessage(String nachricht) throws IOException {
//	    PrintWriter printWriter = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
	    printWriter.print(nachricht);
	    printWriter.flush();
	  }
	  String leseNachricht() throws IOException {
		    BufferedReader bufferedReader =
		      new BufferedReader(
		        new InputStreamReader(
		          socket.getInputStream()));
		    char[] buffer = new char[SystemConfig.MESSAGES_LENGTH];
		    //blockiert bis Nachricht empfangen
		    int anzahlZeichen = bufferedReader.read(buffer, 0, SystemConfig.MESSAGES_LENGTH);
		    String nachricht = new String(buffer, 0, anzahlZeichen);
		    return nachricht;
	 }

	}