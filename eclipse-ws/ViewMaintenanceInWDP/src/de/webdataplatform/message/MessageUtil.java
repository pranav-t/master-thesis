package de.webdataplatform.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.webdataplatform.settings.SystemConfig;

public class MessageUtil {

	/**
	 * @param args
	 */
	public static String translateMap(Map<SystemID, List<Integer>> map) {
		String result ="";
		
		for (SystemID vm : map.keySet()) {
			
			String vmStatus = vm.toString();
			for(Integer val : map.get(vm)){
			
				vmStatus += SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE2+val;
				
			}
			result+= SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE+vmStatus;
			
		}
		return result;
	}
	
	
	
	public static Map<SystemID, List<Integer>> readMap(String map) {
		
		
		Map<SystemID, List<Integer>> result = new HashMap<SystemID, List<Integer>>();
		
		for (String kvPair : map.split(SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE)) {
			System.out.println("kvPair: "+kvPair);
			if(kvPair != null && !kvPair.equals("")){
				
				String[] vmInformation = kvPair.split(SystemConfig.MESSAGES_SPLITCONTENTSEQUENCE2);
			
				SystemID vmID=null;
				List<Integer> vals = new ArrayList<Integer>();
				
				int y = 0;
				for (String vmInf : vmInformation) {
					
					if(y == 0){
						vmID = new SystemID(vmInf);
					}else{
						vals.add(Integer.parseInt(vmInf));
						
					}
					y++;
				}
				result.put(vmID, vals);
			}
		}
		
		
		return result;
		
		
	}
	

}
