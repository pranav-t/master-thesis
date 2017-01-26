package de.webdataplatform.message;

import java.net.Socket;
import java.util.Queue;

import de.webdataplatform.basetable.BaseTableOperation;

public abstract class ServerHandler implements Runnable { //oder 'extends Thread'
	
	  protected Socket client;

//	  protected Queue<BaseTableOperation> updateQueue;
	  
	  
	  public ServerHandler(Socket client) { //Server/Client-Socket
	

	    this.client = client;

	  }
	  

}
