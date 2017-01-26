package de.webdataplatform.system;

import de.webdataplatform.message.SystemID;

public interface IEventHandler {

	
	
	
	public abstract void regionServerAdded(SystemID rsID);
	public abstract void regionServerShutdown(SystemID rsID);
	public abstract void regionServerCrashed(SystemID rsID);	
	public abstract void viewManagerAdded(SystemID vmID);
	public abstract void viewManagerShutdown(SystemID vmID);
	public abstract void viewManagerCrashed(SystemID vmID);
	

	
}
