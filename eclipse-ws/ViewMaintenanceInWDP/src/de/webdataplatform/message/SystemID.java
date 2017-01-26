package de.webdataplatform.message;

import de.webdataplatform.settings.SystemConfig;

public class SystemID {


	private String component;
	
	private String name;
	
	private String ip;
	
	private int messagePort;
	
	private int updatePort;

	public SystemID() {

	}
	
	public SystemID(String component, String name, String ip, int port) {
		super();
		this.component = component;
		this.name = name;
		this.ip = ip;
		this.messagePort = port;
	}
	
	

	public SystemID(String component, String name, String ip, int messagePort, int updatePort) {
		super();
		this.component = component;
		this.name = name;
		this.ip = ip;
		this.messagePort = messagePort;
		this.updatePort = updatePort;
	}



	public SystemID(String address) {
		super();
		
		
		String[] parts = address.split(SystemConfig.MESSAGES_SPLITIDSEQUENCE);
		
//		System.out.println(SystemConfig.MESSAGES_SPLITIDSEQUENCE);
		this.component = parts[0];
		this.name = parts[1];
		this.ip = parts[2];
		this.messagePort = Integer.parseInt(parts[3]);

		if(parts.length == 5){
			this.updatePort = Integer.parseInt(parts[4].replace(">", ""));
		}
	}
	
	
	public String toString(){
		
		if(updatePort != 0)return getComponent()+SystemConfig.MESSAGES_SPLITIDSEQUENCE+getName()+SystemConfig.MESSAGES_SPLITIDSEQUENCE+getIp()+SystemConfig.MESSAGES_SPLITIDSEQUENCE+getMessagePort()+SystemConfig.MESSAGES_SPLITIDSEQUENCE+getUpdatePort();
		return getComponent()+SystemConfig.MESSAGES_SPLITIDSEQUENCE+getName()+SystemConfig.MESSAGES_SPLITIDSEQUENCE+getIp()+SystemConfig.MESSAGES_SPLITIDSEQUENCE+getMessagePort();
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getMessagePort() {
		return messagePort;
	}

	public void setMessagePort(int port) {
		this.messagePort = port;
	}

	public int getUpdatePort() {
		return updatePort;
	}

	public void setUpdatePort(int updatePort) {
		this.updatePort = updatePort;
	}

	
	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + messagePort;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + updatePort;
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
		SystemID other = (SystemID) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (messagePort != other.messagePort)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (updatePort != other.updatePort)
			return false;
		return true;
	}
	
	
	
	

}
