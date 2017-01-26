package de.webdataplatform.master;

import java.util.Date;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.Command;
import de.webdataplatform.system.Event;

public class ComponentController{


//	private Queue<String> componentMessages;
	
	private Queue<CommandCall> commandsToExecute = new ConcurrentLinkedQueue<CommandCall>();
	
	private Log log;
	
//	private Master master;
	
	private MasterSender sender;
	
	
	public ComponentController(Log log, MasterSender sender) {
		super();
		this.log = log;
		this.sender = sender;

	}

	


	
	

	
	/** COMPONENT CALLBACKS */
	
	
	
//	public synchronized void queueCommand(ICommandCaller caller, Command command){
//		
//		commandsToExecute.add(new CommandCall(caller, command));
//	}
//	
	

	private void sendCommand(ICommandCaller caller, Command command){
	
		
		        command.setExecutionTimestamp(new Date().getTime());
		
				switch(command.getType()){
	
						case Command.VIEWMANAGER_ASSIGN : 
							log.info(this.getClass(),"assign view manager:"+command.getViewManager()+" to:"+command.getRegionServer());
							sender.sendAssignViewManager(command.getViewManager(), command.getRegionServer());
						break;	
						case Command.VIEWMANAGER_WITHDRAW : 
							log.info(this.getClass(),"withdraw view manager "+command.getViewManager());
							sender.sendWithdrawViewManager(command.getViewManager(), command.getRegionServer());
						break;
						case Command.VIEWMANAGER_REASSIGN : 
							log.info(this.getClass(),"reassign view manager "+command.getViewManager()+", to:"+command.getRegionServer());
							sender.reassignViewManager(command.getViewManager(), command.getRegionServer());
						break;
						case Command.VIEWMANAGER_SHUTDOWN : 
							log.message(this.getClass(),"view manager shutdown "+command.getViewManager());
							sender.shutdownViewManager(command.getViewManager());
						break;	
						case Command.REGIONSERVER_WITHDRAW_CRASHED_VM : 
							log.info(this.getClass(),"withdraw crashed view manager "+command.getViewManager());
//							sender.withdrawCrashedViewManager(command.getViewManager(), command.getRegionServer());
						break;
						case Command.REGIONSERVER_REPLAY_WRITEAHEADLOG : 
							log.info(this.getClass(),"replay write ahead log "+command.getContent());
//							sender.replayWriteAheadLog(command.getRegionServer(), command.getContent());
						break;
						default:
							log.info(this.getClass(),"command:  "+command.getType()+" not supported by component controller!!!");
							commandInProgress = null;
						break;		
				}

			
	}
	
	
	public String receiveCommand(SystemID name, String eventType){
		

		if(commandInProgress != null){	
			
			Command command = commandInProgress.getCommand();
			
		
			if(name.equals(command.getViewManager())){
			
				if((command.getType().equals(Command.VIEWMANAGER_ASSIGN) && eventType.equals(Event.VIEWMANAGER_ASSIGNED)) ||
				(command.getType().equals(Command.VIEWMANAGER_REASSIGN) && eventType.equals(Event.VIEWMANAGER_REASSIGNED)) ||
				(command.getType().equals(Command.VIEWMANAGER_WITHDRAW) && eventType.equals(Event.VIEWMANAGER_WITHDRAWN)) ||
				(command.getType().equals(Command.VIEWMANAGER_SHUTDOWN) && eventType.equals(Event.VIEWMANAGER_SHUTDOWN))){
					
					log.info(this.getClass(),"command found: "+command+", calling back");
					commandInProgress.getCommandCaller().callbackExecuteCommand(command);
					commandInProgress=null;
					
				}
			}

			if(name.equals(command.getRegionServer())){

				if((command.getType().equals(Command.REGIONSERVER_WITHDRAW_CRASHED_VM) && eventType.equals(Event.REGIONSERVER_CRASHED_VM_WITHDRAWN))  ||
						(command.getType().equals(Command.REGIONSERVER_REPLAY_WRITEAHEADLOG) && eventType.equals(Event.REGIONSERVER_WRITEAHEADLOG_REPLAYED))){
					
					log.info(this.getClass(),"command found: "+command+", calling back");
					commandInProgress.getCommandCaller().callbackExecuteCommand(command);
					commandInProgress=null;
					
				}
			}
					
		}			
					
		return null;
	}

	private void checkCommands(){
		
				
			long currentTime = new Date().getTime();
			
			if(commandInProgress != null){
				if((currentTime - commandInProgress.getCommand().getExecutionTimestamp()) > SystemConfig.MESSAGES_RETRYINTERVAL){
					
					log.info(this.getClass(),"command retries: "+commandInProgress.getCommand().getRetries());
					if(commandInProgress.getCommand().getRetries() < SystemConfig.MESSAGES_NUMOFRETRIES){
						
						log.info(this.getClass(),"command:"+commandInProgress.getCommand()+" can not be executed, component not responding, sending again");
						commandInProgress.getCommand().setRetries(commandInProgress.getCommand().getRetries()+1);
						sendCommand(commandInProgress.getCommandCaller(), commandInProgress.getCommand());
						
					}else{
						log.info(this.getClass(),"command"+commandInProgress.getCommand()+" can not be executed, component cannot be reached, giving up");
						commandInProgress.getCommandCaller().callbackExecuteCommand(commandInProgress.getCommand());
						commandInProgress = null;
					}
				}
			}

		
	}

	
	CommandCall commandInProgress=null;



	
	
	
//	@Override
//	public void run() {
//		
//		while(true){
//			
//			try{
//			
//			String messageString = componentMessages.poll();
//			
//			if(messageString != null){
//				
//				dispatcher.receiveMessage(messageString);
//				
//			}
//
//			checkCommands();
//			
//			if(commandInProgress == null){
//				
//				commandInProgress = commandsToExecute.poll();
//				
//				if(commandInProgress != null){
//					
//					log.info(this.getClass(),"executing command call"+commandInProgress+" ");
//					sendCommand(commandInProgress.getCommandCaller(), commandInProgress.getCommand());	
//					
//				}
//
//			}
//			
//			
//			try {
//				Thread.sleep(SystemConfig.MESSAGES_POLLINGINTERVAL);
//			} catch (InterruptedException e) {
//	
//				e.printStackTrace();
//			}
//			
//			
//			
//		}catch(Exception e){
//			log.error(this.getClass(), e);
//		}
//	}
//
//	}
	


}
