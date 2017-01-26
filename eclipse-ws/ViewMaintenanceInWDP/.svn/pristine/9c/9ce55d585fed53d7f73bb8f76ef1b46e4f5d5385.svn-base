package de.webdataplatform.message;

import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicLong;

import de.webdataplatform.basetable.BaseTableOperation;
import de.webdataplatform.log.Log;
import de.webdataplatform.system.StatisticsElement;

public class ServerUpdateHandlerFactory implements ServerHandlerFactory {


	private Log log;
	
	private Queue<String> incomingUpdates;
	
	private StatisticsElement statisticReceived;
	
	

	public ServerUpdateHandlerFactory(Log log, Queue<String> incomingUpdates, StatisticsElement statisticReceived) {
		super();
		this.log = log;
		this.incomingUpdates = incomingUpdates;
		this.statisticReceived = statisticReceived;
	}





	@Override
	public ServerHandler getServerHandler(Socket cs) {
		
		return new ServerUpdateHandler(log, incomingUpdates, statisticReceived, cs);
	}

}
