package de.webdataplatform.message;

import java.net.Socket;
import java.util.Queue;

import de.webdataplatform.log.Log;

public class ServerMessageHandlerFactory implements ServerHandlerFactory {


	private Log log;
	
	private Queue<String> incomingMessages;
	
	
	
	

	public ServerMessageHandlerFactory(Log log, Queue<String> incomingMessages) {
		super();
		this.log = log;
		this.incomingMessages = incomingMessages;
	}





	@Override
	public ServerHandler getServerHandler(Socket cs) {
		
		return new ServerMessageHandler(log, incomingMessages, cs);
	}

}
