package de.webdataplatform.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.webdataplatform.parser.ColumnName;
import de.webdataplatform.parser.ColumnSequence;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.view.operation.ReverseJoin;

public class ReverseJoinRecord {


	
	
	private ReverseJoin reverseJoin;
	
	private Map<String, List<TableRecord>> tableRecords;

	
	
	
	
	public ReverseJoinRecord(ReverseJoin reverseJoinOperation) {
		super();
		this.tableRecords = new HashMap<String, List<TableRecord>>();
		this.reverseJoin = reverseJoinOperation;

	}
	
	public ReverseJoinRecord(TableRecord tableRecord, ReverseJoin reverseJoinOperation) {
		super();
		this.tableRecords = new HashMap<String, List<TableRecord>>();
		this.reverseJoin = reverseJoinOperation;
		
		backTransform(tableRecord);
		

	}
	
	
	
	
	public void addRecord(TableRecord tableRecord){
		
		if(tableRecords.get(tableRecord.getTableName())==null){
			tableRecords.put(tableRecord.getTableName(), new ArrayList<TableRecord>());
		}
		
		tableRecords.get(tableRecord.getTableName()).add(tableRecord);
		
	}
	
	public void removeRecord(TableRecord tableRecord){

		TableRecord found=null;
		
		for(TableRecord tabRec : tableRecords.get(tableRecord.getTableName())){
			
			if(tabRec.getKey().equals(tableRecord.getKey())){
				found = tabRec;
			}
			
		}
		if(found != null)tableRecords.get(tableRecord.getTableName()).remove(found);
	}

	public void removeTable(String tableName){

		
		tableRecords.put(tableName, new ArrayList<TableRecord>());
	}

	public TableRecord transform() throws Exception{
		
		
		TableRecord result = new TableRecord();
		
//		result.setTableName(inputRecord.getTableName());
//		result.setKey(inputRecord.getColumns().get(aggCol));
		
		String joinVal="";
		for(ColumnName columnName : reverseJoin.getJoinColumns()){
//			String joinCol = reverseJoin.getJoinColumn(tableName);
			
			
			if(tableRecords.get(columnName.getTable()) != null && tableRecords.get(columnName.getTable()).size() > 0){
				
				joinVal+=columnName.getTable()+"{";
				for (TableRecord tableRecord : tableRecords.get(columnName.getTable())) {
				
					result.setTableName(tableRecord.getTableName());
//					
//					System.out.println("tr: "+tableRecord);
//					System.out.println("test: "+reverseJoin.getViewRecordKey(tableRecord));
					result.setKey(reverseJoin.getViewRecordKey(tableRecord));
					
					joinVal += tableRecord.recordToString(null, true);
					joinVal += SystemConfig.RECORDS_SPLITPREAGGREGATION;

				
				}
				joinVal+="}|";
			}
			
		}
		result.putValue("colfam1", "c1", joinVal);
		
		return result;
	}
	

//	private TableRecord transformRecord(TableRecord inputRecord, TableRecord result, String aggCol) {
//		
////		System.out.println("inputRecord: "+inputRecord);
////		System.out.println("aggCol: "+aggCol);
////		
//		result.setTableName(inputRecord.getTableName());
//		result.setKey(inputRecord.getColumns().get(aggCol));
//		
//		
//		
////		String joinValues="";
////		int x = 1;
//		for (String joinValCol : inputRecord.getColumns().keySet()) {
//			
////			joinValues+=inputRecord.getColumns().get(joinValCol);
//			
//			result.getColumns().put(inputRecord.getKey()+"."+joinValCol, inputRecord.getColumns().get(joinValCol));
//			result.getColFamilies().put(inputRecord.getKey()+"."+joinValCol, "colfam_"+inputRecord.getTableName());
////			if(x != inputRecord.getColumns().keySet().size())joinValues+=SystemConfig.RECORDS_SPLITREVERSEJOIN;
////			x++;
//		}
//		return result;
//	}
	
//	public TableRecord transform() {
//		
//		TableRecord result = new TableRecord();
//		
//		String aggVal ="";
//		for (TableRecord tableRecord : records) {
//			
//			result.setTableName(tableRecord.getTableName());
//			result.setKey(preAggregation.getViewRecordKey(tableRecord));
//			
//			aggVal += tableRecord.recordToString(preAggregation.getSelect().getDistinctBaseColumns(), false);
//			aggVal += SystemConfig.RECORDS_SPLITPREAGGREGATION;
//
//			
//		}
//		if(!aggVal.equals("")){
//			
//			result.putValue("colfam1", "c1", aggVal);
//		}
//
//
//		
//		return result;
//	}
	
	
	
	public Map<String, String> getColumnsOfTable(TableRecord tableRecord, String tableName){
		
		Map<String, String> result = new HashMap<String, String>();
		
		for (String colKey : tableRecord.getColumns().keySet()) {
			
			if(tableRecord.getColFamilies().get(colKey).equals("colfam_"+tableName)){
				result.put(colKey, tableRecord.getColumns().get(colKey));
			}
			
		}
		
		return result;
		
	}
	
	
//	public void backTransform(TableRecord tableRecord){
//		
//		tableRecords = new HashMap<String, List<TableRecord>>();
//
//
//		for(String tableName : reverseJoin.getJoinTables()){
//		
//			String joinCol = reverseJoin.getJoinColumn(tableName);
//			
//	
//				backTransformRecords(tableName, tableRecord, joinCol, getColumnsOfTable(tableRecord, tableName).keySet());
//	
//	
//		}
//		
//
//	}
	public void backTransform(TableRecord inputRecord) {
		

		tableRecords = new HashMap<String, List<TableRecord>>();


//		for(ColumnName columnName : reverseJoin.getJoinColumns()){
		
//			String joinCol = reverseJoin.getJoinColumn(tableName);
			
	
//			String compositeKey = inputRecord.getKey();
			
			String compressedRecord =  inputRecord.getColumns().get("c1");
			String[] tableParts = compressedRecord.split("\\|");
			
			System.out.println("tableParts:"+Arrays.toString(tableParts));
			for (int i = 0; i < tableParts.length; i++) {
				
				System.out.println("tableParts[i]:"+tableParts[i]);
				
				String tableName = tableParts[i].split("\\{")[0];
				String records = tableParts[i].split("\\{")[1].replace("}", "");
				
//				String compressedVal = inputRecord.getColumns().get(columnName.getTable());
	
				if(records != null){
				
					for (String aggVal : records.split(SystemConfig.RECORDS_SPLITPREAGGREGATION)) {
						
						System.out.println("aggVal: "+aggVal);
						
						TableRecord tabRec = inputRecord.recordFromString(aggVal, null, true);
						tabRec.setTableName(tableName);
						
						// back transform grouping keys
//						tabRec.putValue("colfam1", columnName.getName(), inputRecord.getKey());
						
						if(tableRecords.get(tableName)==null)tableRecords.put(tableName, new ArrayList<TableRecord>());
						tableRecords.get(tableName).add(tabRec);
					}
				}
//			}
	
		}


		
	}	
	
	
	
//	private void backTransformRecords(String tableName, TableRecord tableRecord, String joinCol, Set<String> colKeys) {
//		
//		
//		Set<String> rowKeys = new HashSet<String>();
//		
//		for (String compositeKey : colKeys) {
//			
//			
//			String recKey = compositeKey.split("\\.")[0];
//			
//			
//			rowKeys.add(recKey);
//			
//		}
//		for (String rowKey : rowKeys) {
//			
//			System.out.println("rowkey: "+rowKey);
//			
//			TableRecord tabRec = new TableRecord();
//			tabRec.setTableName(tableName);
//			tabRec.setKey(rowKey);
//			
//			for (String compositeKey : colKeys) {
//				
//				String recKey = compositeKey.split("\\.")[0];
//				String colKey = compositeKey.split("\\.")[1];
//
//				if(rowKey.equals(recKey)){
//					
//					tabRec.getColumns().put(colKey, tableRecord.getColumns().get(compositeKey));
//					tabRec.getColFamilies().put(colKey, "colfam1");
//					
//					
//				}
//				
//			}
//			if(tableRecords.get(tableName) == null)tableRecords.put(tableName, new ArrayList<TableRecord>());
//			tableRecords.get(tableName).add(tabRec);
//			
//			
//		}
//		
//		
//	}
	


	
	public List<TableRecord> buildCrossProduct(List<TableRecord> tableA, List<TableRecord> tableB){
		
		List<TableRecord> result = new ArrayList<TableRecord>();
		
		for (TableRecord tableRecordA : tableA) {
			for (TableRecord tableRecordB : tableB) {
				
				String compositeKey = tableRecordA.getKey()+"_"+tableRecordB.getKey();
				TableRecord tableRecord = new TableRecord("",compositeKey);
				
				for (String key : tableRecordA.getColumns().keySet()) {

					tableRecord.putValue(tableRecordA.getColFamilies().get(key), key, tableRecordA.getColumns().get(key));
				}
				for (String key : tableRecordB.getColumns().keySet()) {

					tableRecord.putValue(tableRecordB.getColFamilies().get(key), key, tableRecordB.getColumns().get(key));
				}

				
				
				result.add(tableRecord);
				
			}
		}
		return result;
	}	
	
	
	
	public List<TableRecord> buildJoinRecords(){
		

		List<TableRecord> joinedRecords = null;
		
		for(String tableName : reverseJoin.getJoinTables()){
			
			if(tableRecords.get(tableName) == null)return null;
			
			if(joinedRecords == null){
				joinedRecords = tableRecords.get(tableName);
			}else{
				joinedRecords = buildCrossProduct(joinedRecords, tableRecords.get(tableName));
				
			}
			
		}
		
		
		return joinedRecords;
	}
	
	
	public boolean isEmpty(){
		
		boolean result=true;
		
		for(String tableName : reverseJoin.getJoinTables()){
			
			if(tableRecords.get(tableName) != null && tableRecords.get(tableName).size() > 0){
				result=false;
			}
			
		}
		return result;
	}
	
	

	public Map<String, List<TableRecord>> getTableRecords() {
		return tableRecords;
	}

	public void setTableRecords(Map<String, List<TableRecord>> tableRecords) {
		this.tableRecords = tableRecords;
	}

	@Override
	public String toString() {
		return "ReverseJoinRecord [reverseJoinOperation="
				+ reverseJoin + ", tableRecords=" + tableRecords + "]";
	}

	
	

	


	
	
	
	

}
