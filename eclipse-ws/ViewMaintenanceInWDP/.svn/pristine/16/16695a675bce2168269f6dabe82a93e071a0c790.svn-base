package de.webdataplatform.system;

import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.util.StringUtil;

public class DeltaRecord {


	private String operationType;
	
	private TableRecord newRecord;
	
	private TableRecord oldRecord;
	
	
	public DeltaRecord() {
		
		this.newRecord = new TableRecord();
		this.oldRecord = new TableRecord();

	}
	
	
	
	public DeltaRecord(TableRecord newRecord, TableRecord oldRecord) {
		super();
		this.oldRecord = oldRecord;
		this.newRecord = newRecord;
	}

	
	public DeltaRecord(TableRecord storedRecord) {
		super();
		
		this.newRecord = new TableRecord();
		this.oldRecord = new TableRecord();
		if(isDeltaRecord(storedRecord)){
			
			backTransform(storedRecord);
		}
	}

	

	public static boolean isDeltaRecord(TableRecord tableRecord){
		
		if(tableRecord == null)return false;
		
		for (String colKey : tableRecord.getColumns().keySet()) {
			
			if(tableRecord.getColumns().get(colKey).contains(SystemConfig.RECORDS_SPLITDELTA))return true;
			
		}
		
		return false;
		
		
		
	}

	public TableRecord transform(){
		
		
		TableRecord result = new TableRecord();
		
		TableRecord newRecord = this.getNewRecord();
		TableRecord oldRecord = this.getOldRecord();
		
		if(newRecord != null  && oldRecord == null){
			
			result.setTableName(newRecord.getTableName());
			result.setKey(newRecord.getKey());
			for (String key : newRecord.getColumns().keySet()) {
				
				result.getColumns().put(key, "null"+SystemConfig.RECORDS_SPLITDELTA+newRecord.getColumns().get(key));
				result.getColFamilies().put(key, newRecord.getColFamilies().get(key));
			}
			
		}
		if(newRecord != null && oldRecord != null){
			
			result.setTableName(newRecord.getTableName());
			result.setKey(newRecord.getKey());
			for (String key : newRecord.getColumns().keySet()) {
				
				result.getColumns().put(key, oldRecord.getColumns().get(key)+SystemConfig.RECORDS_SPLITDELTA+newRecord.getColumns().get(key));
				result.getColFamilies().put(key, newRecord.getColFamilies().get(key));
			}			
			
			
		}
		
		if(oldRecord!=null&& newRecord == null){
			
			result.setTableName(oldRecord.getTableName());
			result.setKey(oldRecord.getKey());
			for (String key : oldRecord.getColumns().keySet()) {
				
				result.getColumns().put(key, oldRecord.getColumns().get(key)+SystemConfig.RECORDS_SPLITDELTA+"null");
				result.getColFamilies().put(key, oldRecord.getColFamilies().get(key));
			}	
			
		}
		
		
		return result;
	}
	
	public void backTransform(TableRecord tableRecord){
		
		

		
		newRecord.setKey(tableRecord.getKey());
		newRecord.setTableName(tableRecord.getTableName());
		
		oldRecord.setKey(tableRecord.getKey());
		oldRecord.setTableName(tableRecord.getTableName());

		
		for (String colKey : tableRecord.getColumns().keySet()) {
			
			String colVal = tableRecord.getColumns().get(colKey);
			String colFam = tableRecord.getColFamilies().get(colKey);

				
			String newColVal = StringUtil.splitString(colVal, SystemConfig.RECORDS_SPLITDELTA).get(1);
			String oldColVal = StringUtil.splitString(colVal, SystemConfig.RECORDS_SPLITDELTA).get(0);
			

			
			/** reading columns and values */
			if(!newColVal.equals("null")){
				newRecord.getColumns().put(colKey, newColVal);
				newRecord.getColFamilies().put(colKey, colFam);
				operationType = "Put";
			}else{
				newRecord=null;
				operationType = "Delete";
			}
			
			if(!oldColVal.equals("null")){
		
				oldRecord.getColumns().put(colKey, oldColVal);
				oldRecord.getColFamilies().put(colKey, colFam);
			}else{
				oldRecord=null;
			}
				

		}
		

	}
	


	public TableRecord getOldRecord() {
		return oldRecord;
	}


	public void setOldRecord(TableRecord oldRecord) {
		this.oldRecord = oldRecord;
	}


	public TableRecord getNewRecord() {
		return newRecord;
	}


	public void setNewRecord(TableRecord newRecord) {
		this.newRecord = newRecord;
	}



	public String getOperationType() {
		return operationType;
	}



	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}



	@Override
	public String toString() {
		return "DeltaRecord [operationType=" + operationType + ", newRecord="
				+ newRecord + ", oldRecord=" + oldRecord + "]";
	}
	
	
	
	

}
