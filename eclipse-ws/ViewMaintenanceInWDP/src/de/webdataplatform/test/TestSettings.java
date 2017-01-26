package de.webdataplatform.test;

import de.webdataplatform.log.Log;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.SystemConfig;

public class TestSettings {


	
	
	
	public static void main(String[] args) {

		
		Log log = new Log("newlog");
		
		SystemConfig.load(log);
		System.out.println();
		NetworkConfig.load(log);
		System.out.println();
//		DatabaseConfig.load(log);
		System.out.println();		
		
	}

}
