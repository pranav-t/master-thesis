package de.webdataplatform.message;

import java.io.Serializable;
import java.util.Arrays;

import de.webdataplatform.settings.SystemConfig;


public class Message implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9013050318641185187L;


	public Message(){
		
		
	}
	
	public Message(String updateString){
		
		String[] messageParts = updateString.replace(SystemConfig.MESSAGES_STARTSEQUENCE, "").replace(SystemConfig.MESSAGES_ENDSEQUENCE, "").split(SystemConfig.MESSAGES_SPLITSEQUENCE);
		
		systemID = new SystemID(messageParts[0].trim());
		
		operation = messageParts[1].trim();
		
		if(messageParts.length == 3)content = messageParts[2].trim();
		else content = "";
		
		

		
	}



	
	public Message(SystemID systemID, String operation, String content) {
		super();
		this.systemID = systemID;
		this.operation = operation;
		this.content = content;
	}
	
	
	

//	private String component;
	
	private SystemID systemID;
	
	private String operation;

	private String content;
	
	

	


	public String convertToString(){
		return SystemConfig.MESSAGES_STARTSEQUENCE+systemID+SystemConfig.MESSAGES_SPLITSEQUENCE+operation+SystemConfig.MESSAGES_SPLITSEQUENCE+content+SystemConfig.MESSAGES_ENDSEQUENCE;
	}




	public SystemID getSystemID() {
		return systemID;
	}

	public void setSystemID(SystemID systemID) {
		this.systemID = systemID;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	

	@Override
	public String toString() {
		return "Message [systemID=" + systemID + ", operation=" + operation + ", content=" + content + "]";
	}
	
	

	
	

	public Message copy(){
		
		Message message = new Message();
		
		message.systemID = this.systemID;
		message.operation = this.operation;
		message.content = this.content;

		
		return message;
		
	}
	
	
	
	
}
