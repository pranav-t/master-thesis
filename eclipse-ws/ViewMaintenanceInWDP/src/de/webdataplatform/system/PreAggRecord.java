package de.webdataplatform.system;

import java.util.ArrayList;
import java.util.List;

import de.webdataplatform.parser.Column;
import de.webdataplatform.parser.ColumnName;
import de.webdataplatform.parser.ColumnSequence;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.util.StringUtil;
import de.webdataplatform.view.operation.PreAggregation;

public class PreAggRecord {


	
	private PreAggregation preAggregation;

	private List<TableRecord> records;
	
	private String operationType;

	public PreAggRecord(PreAggregation preAggOperation) {
		super();
		this.records = new ArrayList<TableRecord>();
		this.preAggregation = preAggOperation;

	}
	
	public PreAggRecord(TableRecord storedRecord, PreAggregation preAggOperation) {
		super();
		this.records = new ArrayList<TableRecord>();
		this.preAggregation = preAggOperation;
		
		
		
		backTransform(storedRecord);

	}
	
	
	
	
	public void addRecord(TableRecord tableRecord){
		
		
		TableRecord matchRecord = null;
		
		for (TableRecord rec : records) {
		
			if(tableRecord.getKey().equals(rec.getKey())){
				matchRecord = rec;
			}
			
		}
		if(matchRecord != null){
			records.remove(matchRecord);
		}
		records.add(tableRecord);
		
		
	}
	
	public void removeRecord(TableRecord tableRecord){
		
		TableRecord found=null;
		for (TableRecord tabRec : records) {
			
			if(tableRecord.getKey().equals(tabRec.getKey()))found=tabRec;
			
		}
		if(found != null)records.remove(found);
		
	}


	
	public TableRecord transform() throws Exception{
		
//		long start = System.currentTimeMillis();
		String splitter = SystemConfig.RECORDS_SPLITPREAGGREGATION;
		
		TableRecord result = new TableRecord();
		
		List<ColumnName> columns = preAggregation.getSelect().getDistinctBaseColumns();
		
//		String aggVal ="";
		boolean first=true;
//		System.out.println("Size: "+records.size());
		
		StringBuilder aggVal = new StringBuilder();

		for (TableRecord tableRecord : records) {
			
			if(first){
//				long start1 = System.nanoTime();
				result.setTableName(tableRecord.getTableName());
//				System.out.println("Set1: "+(System.nanoTime()-start1));
				
//				long start2 = System.nanoTime();
				result.setKey(preAggregation.getViewRecordKey(tableRecord));
//				System.out.println("Set2: "+(System.nanoTime()-start2));
				first=false;
			}
			
//			long start3 = System.nanoTime();
			aggVal.append(tableRecord.recordToString(columns, false));
//			System.out.println("Set3: "+(System.nanoTime()-start3));
			
//			long start4 = System.nanoTime();
			aggVal.append(splitter);
//			System.out.println("Set4: "+(System.nanoTime()-start4));
			
		}
		if(!aggVal.equals("")){
			
			result.putValue("colfam1", "c1", aggVal.toString());
		}

//		System.out.println("TRANSTime: "+(System.currentTimeMillis()-start));
		
		return result;
	}

	@Deprecated
	private TableRecord transformRecord(TableRecord inputRecord, TableRecord result) {
		

		result.setTableName(inputRecord.getTableName());
		result.setKey(preAggregation.getViewRecordKey(inputRecord));
		
		
		List<ColumnName> columnNames = preAggregation.getSelect().getDistinctBaseColumns();
		
		String aggValues="";
		int x = 1;
		for (ColumnName columnName : columnNames) {
			
			try {
				aggValues+=inputRecord.resolveValue(columnName.getName());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(x != columnNames.size())aggValues+=SystemConfig.RECORDS_SPLITPREAGGREGATION;
			x++;
		}
		result.putValue("colfam1", inputRecord.getKey(), aggValues);

		
		return result;
	}
	
	
	
	public void backTransform(TableRecord inputRecord) {
		
		
//		long start = System.currentTimeMillis();
//		long startx = System.currentTimeMillis();
		
		records = new ArrayList<TableRecord>();

		String compositeKey = inputRecord.getKey();
		String compressedVal = inputRecord.getColumns().get("c1");

//		System.out.println("compressedVal: "+compressedVal);
		
		List<ColumnName> columns = preAggregation.getSelect().getDistinctBaseColumns();
		List<ColumnName> groupingColumns = preAggregation.getGroupingKeys().getOutputColumns();
//		System.out.println("Setx: "+(System.currentTimeMillis()-startx));
		
//		long start0 = System.currentTimeMillis();
		List<String> recordArr = StringUtil.splitString(compressedVal, SystemConfig.RECORDS_SPLITPREAGGREGATION);
//		System.out.println("Set0: "+(System.currentTimeMillis()-start0));
		
//		long start1 = System.currentTimeMillis();
		for (String aggVal : recordArr) {
			
//			long start11 = System.nanoTime();
//			System.out.println("aggVal: "+aggVal);
			TableRecord tabRec = inputRecord.recordFromString(aggVal, columns, false);
//			System.out.println("Set11: "+(System.nanoTime()-start11));
			
//			long start22 = System.nanoTime();
			tabRec.setTableName(inputRecord.getTableName());
//			System.out.println("Set12: "+(System.nanoTime()-start22));
			
			// back transform grouping keys
//			long start33 = System.nanoTime();
			List<String> keys = StringUtil.splitString(compositeKey, "_");
			int i = 0;
			for (ColumnName columnName : groupingColumns) {
				
				tabRec.putValue("colfam1", columnName.getName(), keys.get(i));
				i++;
			}

			
			records.add(tabRec);
//			System.out.println("Set33: "+(System.nanoTime()-start11));
		}
//		System.out.println("Set1: "+(System.currentTimeMillis()-start1));
		
//		System.out.println("BTRANSTime: "+(System.currentTimeMillis()-start));
	}	
	
	

	
	


	@Deprecated
	private void backTransformRecord(TableRecord inputRecord, String colKey, String compositeKey) {
		
		
		TableRecord tabRec = new TableRecord();
		
		tabRec.setTableName(inputRecord.getTableName());
		tabRec.setKey(colKey);
		
		// back transform grouping keys
		ColumnSequence groupingKeys = preAggregation.getGroupingKeys();
		String[] keys = compositeKey.split("_");
		int j = 0;
		for (ColumnName columnName : groupingKeys.getOutputColumns()) {
			
			tabRec.putValue("colfam1", columnName.getName(), keys[j]);
			j++;
		}
		
		// back transform aggregation values
		String compressedVal = inputRecord.getColumns().get(colKey);

		List<ColumnName> columnNames = preAggregation.getSelect().getInputColumns();
		
		int i = 0;
		for (String aggVal : compressedVal.split(SystemConfig.RECORDS_SPLITPREAGGREGATION)) {
			
			tabRec.putValue(inputRecord.getColFamilies().get(colKey), columnNames.get(i).getName(), aggVal);
			
			i++;
		}
		
		
		records.add(tabRec);
	}

	
	
	
	
	public TableRecord buildAggRecord(String viewTableName, String viewTableKey) throws Exception{
		

		long start0 = System.currentTimeMillis();
		
		
		TableRecord aggRecord= new TableRecord(viewTableName, viewTableKey);
		Object result = null;
		
		List<Column> columns = preAggregation.getSelect().getColumns();

		
		for (Column column : columns) {

				if(column.getTerm() instanceof ColumnName){
					
					String colName = column.getColumnName().getName();

					aggRecord.putValue(records.get(0).resolveFamily(colName), colName, records.get(0).resolveValue(colName));


				}else{
				

					result = column.getTerm().evalGrouping(records);

					aggRecord.putValue("colfam1", column.getColumnName().getName(), String.valueOf(result));
				}
		
		}
		
		
		if(result != null){
		
			if((result instanceof Integer && (Integer)result != 0)
					||(result instanceof Float && (Float)result != 0f)
					||(result instanceof String && !((String)result).equals("")))operationType="Put";
			else operationType="Delete";
			
			
		}else{
			
			operationType="Delete";
		}
		
		System.out.println("BUILDAGGTime: "+(System.currentTimeMillis()-start0));
		
		return aggRecord;
	}

	


	public List<TableRecord> getRecords() {
		return records;
	}



	public void setRecords(List<TableRecord> records) {
		this.records = records;
	}

	
	
	
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	@Override
	public String toString() {
		return "PreAggRecord [records=" + records + "]";
	}
	

	
	
	
	

}