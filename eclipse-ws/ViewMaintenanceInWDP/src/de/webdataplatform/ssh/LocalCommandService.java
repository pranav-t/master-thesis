package de.webdataplatform.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import de.webdataplatform.log.Log;
import de.webdataplatform.scripts.InstallNodes;

public class LocalCommandService {

	
	
	public static void runCommand(Log log, String command) {
		

        Process proc;
		try {
			
			proc = Runtime.getRuntime().exec(command);
			// Read the output
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			
			String line = "";
			while((line = reader.readLine()) != null) {
				log.info(line + "\n");
			}
			
			proc.waitFor();  
			
		} catch (IOException e) {

			log.error(LocalCommandService.class, e);
		} catch (InterruptedException e) {

			log.error(LocalCommandService.class, e);
		}
		
		
	}
	
	/**
	 * @param args
	 */
	public static void transferDirectory(Log log, SSHConnection node, String source, String target) {
		
	     String command = "scp -r "+source+" ubuntu@"+node.getAddress()+":"+target;

        Process proc;
		try {
			
			proc = Runtime.getRuntime().exec(command);
			// Read the output
			
			BufferedReader reader =  
					new BufferedReader(new InputStreamReader(proc.getInputStream()));
			
			String line = "";
			while((line = reader.readLine()) != null) {
				log.info(InstallNodes.class, line + "\n");
			}
			
			proc.waitFor();  
			
		} catch (IOException e) {
			
			log.error(LocalCommandService.class, e);
		} catch (InterruptedException e) {
			
			log.error(LocalCommandService.class, e);
		}
	}
	
	public static void transferFile(Log log, SSHConnection node, String source, String target) {
		
	     String command = "scp "+source+" ubuntu@"+node.getAddress()+":"+target;

       Process proc;
		try {
			
			proc = Runtime.getRuntime().exec(command);
			// Read the output
			
			BufferedReader reader =  
					new BufferedReader(new InputStreamReader(proc.getInputStream()));
			
			String line = "";
			while((line = reader.readLine()) != null) {
				log.info(InstallNodes.class, line + "\n");
			}
			
			proc.waitFor();  
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
