package de.webdataplatform.basetable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.webdataplatform.log.Log;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.TableRecord;


public class BaseTableOperation implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9013050318641185187L;

	private Log log;
	

	public BaseTableOperation(){
		
		
	}
	
	public BaseTableOperation(TableRecord tableRecord, String type, SystemID sender){
		
		this.setBaseTable(tableRecord.getTableName());
		this.setKey(tableRecord.getKey());
		this.setColumns(tableRecord.getColumns());
		this.setColFamilies(tableRecord.getColFamilies());
		this.setType(type);
		this.setSender(sender);	
		
	}
	
	
	public Map<String, String> readMapFromString(String inputString){

		
		Map<String, String> result = new HashMap<String, String>();
		
		
//		Log.info(this.getClass(), "columnsString: "+columnsString);

		if(!inputString.equals(" ") && !inputString.equals("") && !inputString.equals("=")){
		
			String[] columnsKVs = inputString.split(SystemConfig.MESSAGES_SPLITIDSEQUENCE);
			
			
			for (int i = 0; i < columnsKVs.length; i++) {
				
				String columnKV = columnsKVs[i];
				String[] kv = columnKV.split("=");
				
				if(kv.length > 1)
					result.put(kv[0], kv[1]);
				if(kv.length == 1)
					result.put(kv[0], "");
//				else columns.put(kv[0], "");
				
			}
		}
		return result;
		
	}
	
	
	
	public BaseTableOperation(Log log, String updateString){
		
		this.log = log;
		
		try{
			
		
		String[] updateParts = updateString.split(SystemConfig.MESSAGES_SPLITSEQUENCE);
		
		baseTable = updateParts[0].trim();

		key = updateParts[1].trim();

		type = updateParts[2].trim();
		
		columns = readMapFromString(updateParts[3].trim());
		
//		oldColumns = readMapFromString(updateParts[8].trim());
		
		colFamilies = readMapFromString(updateParts[4].trim());
		
		if(updateParts.length >= 6 && !updateParts[5].trim().equals("null"))this.sender = new SystemID(updateParts[5].trim());
		
		
		List<String> keysToRemove = new ArrayList<String>();
		Map<String, String> keysToAdd = new HashMap<String, String>();
		
//		for (String key : columns.keySet()) {
//			
//			if(key.contains("_new")){
//				keysToAdd.put(key.replace("_new", ""),columns.get(key));
//				keysToRemove.add(key);
//			}
//			if(key.contains("_old")){
//				oldColumns.put(key.replace("_old", ""),columns.get(key));
//				keysToRemove.add(key);
//			}
//			
//		}
		
		columns.putAll(keysToAdd);
		
		for (String string : keysToRemove) {
			columns.remove(string);
		}
		

		
		}catch(Exception e){
			
			log.info(this.getClass(), "update failed: "+updateString);
			log.error(this.getClass(), e);
			
		}

//		this.sender = sender;
		
//		aggKey = updateParts[4].trim();
		
//		value = updateParts[5].trim();
		

		
	}

//	public Map<String, String> removeTags(Map<String, String> map){
//		
//		
//		Map<String, String> result  = new HashMap<String, String>();
//		
//		
//		for (String key : map.keySet()) {
//			result.put(key.replace("_new", "").replace("_old", ""), map.get(key));
//		}
//		
//		return result;
//		
//	}


	public BaseTableOperation(String baseTable, String regionServer, String region, String seqNo, String timestamp, String key, String type, Map<String, String> columns, Map<String, String> colFamilies) {
		super();
		this.baseTable = baseTable;
		this.key = key;
//		this.aggKey = aggKey;
		this.columns = columns;
//		this.oldColumns = oldColumns;
		this.colFamilies = colFamilies;
//		this.value = value;
		this.type = type;

	}

	private String baseTable;
	
//	private String regionServer;
	
//	private String region;
	
	private String viewManager;
	
//	private String seqNo;

//	private String timestamp;

	private String key;
	
	private String type;
	
	private Map<String, String> columns;
	
//	private Map<String, String> oldColumns;
	
	private Map<String, String> colFamilies;
	
	
	private SystemID sender;
	
	
//	private String aggKey;
	
//	private String value;
	
	

	public String convertMapToString(Map<String, String> map){

		String result = "";
		
		if(map != null && !map.isEmpty() && !map.keySet().isEmpty()){
			for (String key : map.keySet()) {
				
				result += key+"="+map.get(key)+SystemConfig.MESSAGES_SPLITIDSEQUENCE;
				
			}
			result = result.substring(0, result.length()-1);
		}else{
			result = " ";
		}
		
		return result;
		
	}

	public String convertToString(){
		
		
		return baseTable+SystemConfig.MESSAGES_SPLITSEQUENCE+key+SystemConfig.MESSAGES_SPLITSEQUENCE+type+SystemConfig.MESSAGES_SPLITSEQUENCE+convertMapToString(columns)+SystemConfig.MESSAGES_SPLITSEQUENCE+convertMapToString(colFamilies)+((sender != null)?(SystemConfig.MESSAGES_SPLITSEQUENCE+sender):"");

	}
	public String convertToMessageString(){
		
		
		return SystemConfig.MESSAGES_STARTSEQUENCE+baseTable+SystemConfig.MESSAGES_SPLITSEQUENCE+key+SystemConfig.MESSAGES_SPLITSEQUENCE+type+SystemConfig.MESSAGES_SPLITSEQUENCE+convertMapToString(columns)+SystemConfig.MESSAGES_SPLITSEQUENCE+convertMapToString(colFamilies)+SystemConfig.MESSAGES_ENDSEQUENCE;

	}

	
	public Map<String, String> getColumnsWithoutSignatures(){
		
		Map<String, String> result = new HashMap<String, String>();
		
		for (String columnKey : columns.keySet()) {
			
			if(colFamilies.get(columnKey) != null && colFamilies.get(columnKey).equals("sigfam1")){
				continue;
			}
			result.put(columnKey, columns.get(columnKey));
			
		}
		return result;
	}
	

	public static BaseTableOperation generateBtuDelete(String table, String key){
		
		Map<String, String> cols = new HashMap<String, String>();
		Map<String, String> colFams = new HashMap<String, String>();
		
		
		BaseTableOperation btu = new BaseTableOperation(table, "rs1", "regionxy", 100+"",new Date().getTime()+"", key, "Delete", cols, colFams);
		
		return btu;
		
	}
	
	public static BaseTableOperation generateBtuPut(String table, String key, String col1, String val1, String col2,  String val2, String col3, String val3){
		
		Map<String, String> cols = new HashMap<String, String>();
		Map<String, String> colFams = new HashMap<String, String>();
		
		cols.put(col1, val1);
		cols.put(col2, val2);
		cols.put(col3, val3);
		colFams.put(col1, "colfam1");
		colFams.put(col2, "colfam1");
		colFams.put(col3, "colfam1");
		
		
		BaseTableOperation btu = new BaseTableOperation(table, "rs1", "regionxy", 100+"",new Date().getTime()+"", key, "Put", cols, colFams);
		
		return btu;
		
	}
//	public Map<String, String> getOldColumnsWithoutSignatures(){
//		
//		Map<String, String> result = new HashMap<String, String>();
//		
//		for (String columnKey : oldColumns.keySet()) {
//			
//			if(colFamilies.get(columnKey) != null && colFamilies.get(columnKey).equals("sigfam1")){
//				continue;
//			}
//			result.put(columnKey, oldColumns.get(columnKey));
//			
//		}
//		return result;
//	}
	
	
//	public String getColumnFamily(){
//		
//		
//		for (String columnKey : columns.keySet()) {
//			
//			if(colFamilies.get(columnKey) != null && !colFamilies.get(columnKey).equals("sigfam1")){
//				return colFamilies.get(columnKey);
//			}
//
//			
//		}
//		return null;
//	}
	
	
//	public PropagationMode getPropagationMode(){
//		
//
//		return propagationMode;
//	}
//	
//	
	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}





	public String getBaseTable() {
		return baseTable;
	}




	public void setBaseTable(String baseTable) {
		this.baseTable = baseTable;
	}




	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	



	public SystemID getSender() {
		return sender;
	}

	public void setSender(SystemID sender) {
		this.sender = sender;
	}

	
	
	
	public BaseTableOperation copy(){
		
		BaseTableOperation bto = new BaseTableOperation();
		
		bto.baseTable = this.baseTable;
		bto.key = this.key;
		bto.columns = this.columns;
		bto.colFamilies = this.colFamilies;
		bto.type = this.type;
		bto.sender = sender;
		
		return bto;
		
	}


	
	




	@Override
	public String toString() {
		return "BaseTableOperation [log=" + log + ", baseTable=" + baseTable
				+ ", viewManager=" + viewManager + ", key=" + key + ", type="
				+ type + ", columns=" + columns + ", colFamilies="
				+ colFamilies +", sender="+sender+ "]";
	}

	public Map<String, String> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, String> columns) {
		this.columns = columns;
	}


//	public String getRegion() {
//		return region;
//	}
//
//	public void setRegion(String region) {
//		this.region = region;
//	}
//
//	public String getTimestamp() {
//		return timestamp;
//	}
//
//	public void setTimestamp(String timestamp) {
//		this.timestamp = timestamp;
//	}

	public Map<String, String> getColFamilies() {
		return colFamilies;
	}

	public void setColFamilies(Map<String, String> colFamilies) {
		this.colFamilies = colFamilies;
	}
	
	
	
	
}
