package de.webdataplatform.system;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Writable;

import de.webdataplatform.log.Log;
import de.webdataplatform.parser.ColumnName;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.util.StringUtil;


public class TableRecord implements Serializable, Writable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2573054878291189663L;

	/**
	 * 
	 */


	private Log log;
	
	private String tableName;

	private String key;
	
	private Map<String, String> columns=new HashMap<String, String>();
	
	private Map<String, String> colFamilies=new HashMap<String, String>();
	

	
	

	public TableRecord(){
		
		
	}
	



	public TableRecord(String key) {
		super();
		this.key = key;
	}








	public TableRecord(String tableName, String key) {
		super();
		this.tableName = tableName;
		this.key = key;
	}








	public TableRecord(String baseTable, String key, Map<String, String> columns, Map<String, String> colFamilies) {
		super();
		this.tableName = baseTable;
		this.key = key;
		this.columns = columns;
		this.colFamilies = colFamilies;


	}
	
	
	public TableRecord(String tableName, Result result) {
		super();
		
		
		this.key = Bytes.toString(result.getRow());
		
		columns = new HashMap<String, String>();
		colFamilies = new HashMap<String, String>();
		
		
		for (byte[] colFam : result.getNoVersionMap().keySet()) {
			
			
			for (byte[] col : result.getNoVersionMap().get(colFam).keySet()) {
				
				byte[] value = result.getNoVersionMap().get(colFam).get(col);
				
				columns.put(Bytes.toString(col), Bytes.toString(value));
				colFamilies.put(Bytes.toString(col), Bytes.toString(colFam));
				
			}
			
			
			
		}
		
		
		


	}
	
	
	public String resolveValue(String colName) throws Exception{
		
		String result = columns.get(colName);
		
		if(result == null)throw new Exception("Column "+colName+" couldn't be resolved");
		
		return result;
	}
	
	public String resolveFamily(String colName) throws Exception{
		
		String result = colFamilies.get(colName);
		
		if(result == null)throw new Exception("Column familiy of "+colName+" couldn't be resolved");
		
		return result;
	}
	
	public void putValue(String colFam, String colName, String value){
		
		columns.put(colName, value);
		colFamilies.put(colName, colFam);
		
	}
	
	public static TableRecord generateTableRecord(String table, String key, String col1, String val1){
		
		Map<String, String> cols = new HashMap<String, String>();
		Map<String, String> colFams = new HashMap<String, String>();
		
		cols.put(col1, val1);
		colFams.put(col1, "colfam1");
		
		TableRecord tableRecord = new TableRecord(table, key, cols, colFams);
		
		return tableRecord;
		
	}

	public static TableRecord generateTableRecord(String table, String key, String col1, String val1, String col2,  String val2){
		
		Map<String, String> cols = new HashMap<String, String>();
		Map<String, String> colFams = new HashMap<String, String>();
		
		cols.put(col1, val1);
		cols.put(col2, val2);
		colFams.put(col1, "colfam1");
		colFams.put(col2, "colfam1");
		
		TableRecord tableRecord = new TableRecord(table, key, cols, colFams);
		
		return tableRecord;
		
	}
	public static TableRecord generateTableRecord(String table, String key, String col1, String val1, String col2,  String val2, String col3, String val3){
		
		Map<String, String> cols = new HashMap<String, String>();
		Map<String, String> colFams = new HashMap<String, String>();
		
		cols.put(col1, val1);
		cols.put(col2, val2);
		cols.put(col3, val3);
		colFams.put(col1, "colfam1");
		colFams.put(col2, "colfam1");
		colFams.put(col3, "colfam1");
		
		TableRecord tableRecord = new TableRecord(table, key, cols, colFams);
		
		return tableRecord;
		
	}
	
	public static TableRecord generateTableRecord(String table, String key, String colfam1, String col1, String val1, String colfam2, String col2,  String val2, String colfam3, String col3,  String val3){
		
		Map<String, String> cols = new HashMap<String, String>();
		Map<String, String> colFams = new HashMap<String, String>();
		
		cols.put(col1, val1);
		cols.put(col2, val2);
		cols.put(col3, val3);
		colFams.put(col1, colfam1);
		colFams.put(col2, colfam2);
		colFams.put(col3, colfam3);
		
		TableRecord tableRecord = new TableRecord(table, key, cols, colFams);
		
		return tableRecord;
		
	}
	
	public void projectColumns(List<ColumnName> columnNames){
		
		
		Map<String, String> columns=new HashMap<String, String>();
		
		Map<String, String> colFamilies=new HashMap<String, String>();
		
		
		for (ColumnName colName : columnNames) {
			
			if(this.columns.containsKey(colName.getName())){
				
				columns.put(colName.getName(), this.columns.get(colName.getName()));
				colFamilies.put(colName.getName(), this.colFamilies.get(colName.getName()));
			}
			
			
		}
		this.columns = columns;
		this.colFamilies = colFamilies;
		
		
	}
	
	
	
	public String recordToString(List<ColumnName> columnNames, boolean includeColumnNames) throws Exception{
		
		StringBuilder result=new StringBuilder();
		
		result.append(getKey()+SystemConfig.RECORDS_SPLITROWKEYKVPAIRS);
		
		
		if(columnNames == null){
			
			int x = 1;
			for (String column : columns.keySet()) {
				
				if(includeColumnNames){
					result.append(column+SystemConfig.RECORDS_SPLITKV+columns.get(column));
				}else{
					result.append(columns.get(column));
				}
				
				if(x != columns.keySet().size())result.append(SystemConfig.RECORDS_SPLITKVPAIRS);
				x++;
			}
			
		}else{
		
			int x = 1;
			for (ColumnName columnName : columnNames) {
				

					String value =resolveValue(columnName.getName());
					if(includeColumnNames){
						result.append(columnName.getName()+SystemConfig.RECORDS_SPLITKV+value);
					}else{
						result.append(value);
					}

				
				if(x != columnNames.size())result.append(SystemConfig.RECORDS_SPLITKVPAIRS);
				x++;
			}
		}

		return result.toString();
	}
	
	
	public TableRecord recordFromString(String inputString,  List<ColumnName> columnNames, boolean columNamesIncluded) {
		
//		long start1 = System.nanoTime();
		TableRecord result = new TableRecord();
//		System.out.println("Set111: "+(System.nanoTime()-start1));
		
		// back transform row key
//		String recKey  = inputString.split(SystemConfig.RECORDS_SPLITROWKEYKVPAIRS)[0];
		List<String> split=StringUtil.splitBySingleChar(inputString.toCharArray(), SystemConfig.RECORDS_SPLITROWKEYKVPAIRS.charAt(0));
		String recKey  = split.get(0);
//		System.out.println("recKey: "+recKey);
		result.setKey(recKey);
//		preAggregation.getSelect().getDistinctBaseColumns()
		
		// back transform aggregation values
		
//		StringTokenizer st = new StringTokenizer("1/2/3",":");
//		List<String> tokens = new ArrayList<String>();
//		while (st.hasMoreTokens() ) {
//			tokens.add(st.nextToken());
//		}
		
//		String val = inputString.split(SystemConfig.RECORDS_SPLITROWKEYKVPAIRS)[1];
		String val  = split.get(1);
//		System.out.println("val: "+val);
		List<String> values = StringUtil.splitBySingleChar(val.toCharArray(), SystemConfig.RECORDS_SPLITKVPAIRS.charAt(0));

//		long start2 = System.nanoTime();
		if(columNamesIncluded){
			
			for (String value : values) {
				
				List<String> splitVal = StringUtil.splitString(value, SystemConfig.RECORDS_SPLITKV); 
				result.putValue("colfam1", splitVal.get(0), splitVal.get(1));
			}
			
		}else{
//			System.out.println("size: "+columnNames.size());
			for (int j = 0; j < columnNames.size(); j++) {
				
				result.putValue("colfam1", columnNames.get(j).getName(), values.get(j));
			}
		}
//		System.out.println("Set222: "+(System.nanoTime()-start2));
		return result;
	}

	public String getKey() {
		return key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}






	public TableRecord copy(){
		
		TableRecord btu = new TableRecord();
		
		btu.tableName = this.tableName;
		btu.key = this.key;
		btu.columns = this.columns;
		btu.colFamilies = this.colFamilies;
		
		return btu;
		
	}


	
	

	

	@Override
	public String toString() {
		return "BaseTableRecord [log=" + log + ", baseTable=" + tableName
				+ ", key=" + key + ", columns=" + columns + ", colFamilies="
				+ colFamilies + "]";
	}




	@Override
	public void readFields(DataInput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(DataOutput arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

	public Map<String, String> getColumns() {
		return columns;
	}

	public void setColumns(Map<String, String> columns) {
		this.columns = columns;
	}



	public Map<String, String> getColFamilies() {
		return colFamilies;
	}

	public void setColFamilies(Map<String, String> colFamilies) {
		this.colFamilies = colFamilies;
	}
	
	
	
	
}
