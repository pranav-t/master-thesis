package de.tum.in.vmseval.evaluation.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class LocalShellService {
    
    public static void runCommand(String command) {
        try {
            System.out.print("\tlocal command: " + command);
            long start = System.currentTimeMillis();
            Process proc = Runtime.getRuntime().exec(new String[] {"bash", "-c", command});
            
            BufferedReader reader =  new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while((line = reader.readLine()) != null) {
                System.out.println(line+"\n");
            }
            proc.waitFor();
            
            reader =  new BufferedReader(new InputStreamReader(proc.getInputStream()));
            while((line = reader.readLine()) != null) {
                System.out.println(line+"\n");
            }
            System.out.println(" --> time: "+(new Date().getTime() - start+" ms"));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }    
    
    public static void transferDirectory(SSHConnection node, String source, String target) {
        String command = "yes | scp -i ~/.ssh/pranav-vmsevaluation.pem -r " + source + " ubuntu@" + node.getHostname()+ ":" + target;
        runCommand(command);
    }
	
    public static void transferFile(SSHConnection node, String source, String target) {
        String command = "yes | scp  -i ~/.ssh/pranav-vmsevaluation.pem " + source + " ubuntu@" + node.getHostname()+ ":" + target;
        runCommand(command);
    }
    
}
