package de.webdataplatform.viewmanager.processing;

import java.util.Date;
import java.util.List;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import de.webdataplatform.basetable.BaseTableUpdate;
import de.webdataplatform.log.Log;
import de.webdataplatform.system.StatisticsElement;
import de.webdataplatform.system.TableRecord;
import de.webdataplatform.view.TableService;
import de.webdataplatform.view.ViewTable;
import de.webdataplatform.view.operation.ViewOperation;

public class KVStoreUtil {
	
	private Log log;
	
	public TableService tableService;
	
	private StatisticsElement statisticVR;
	
	private StatisticsElement statisticVU;
	
	
	public KVStoreUtil(Log log, TableService tableService) {
		
		this.tableService = tableService;
		this.log = log;
		
	}
	
	public KVStoreUtil(Log log, TableService tableService, StatisticsElement statisticVR, StatisticsElement statisticVU) {
		
		this.tableService = tableService;
		this.statisticVR = statisticVR;
		this.statisticVU = statisticVU;
		this.log = log;
		
	}	
	
	
	
	public TableRecord retrieveOldViewRecord(ViewTable viewTable, ViewOperation viewType, BaseTableUpdate btu){
		

		statisticVR.recordUpdate();
		
		String viewRecordKey = viewType.getViewRecordKey(btu);
		List<byte[]> colfams=null;
		log.info(this.getClass(), "viewRecordKey: "+viewRecordKey);

		
		if((viewRecordKey == null || viewRecordKey.equals(""))){
			
			return null;
		}
		
//		if(viewType instanceof ReverseJoin){
//			colfams = new ArrayList<byte[]>();
//			ReverseJoin reverseJoin = (ReverseJoin)viewType;
//			for (String colFam : reverseJoin.getViewRecordColFams(btu)) {
//				colfams.add(Bytes.toBytes(colFam));
//			}
//			
//		}
		
		long start = System.nanoTime();
		
		Result result = tableService.get(Bytes.toBytes(viewTable.getTableName()), Bytes.toBytes(viewRecordKey), colfams, null);
		
		statisticVR.recordLatency(System.nanoTime()-start);
		
//		System.out.println("GETTime: "+(System.currentTimeMillis()-start));

		if(result==null)return null;
			
		TableRecord btr = new TableRecord(viewTable.getTableName(), result);
		
		
		return btr;
//		if(viewTable.getTableName().equals("q0_0"))return TableRecord.generateTableRecord("q0_0", "k1", "c1", "x1", "c2", "y1", "c3", "20");
//		if(viewTable.getTableName().equals("q0_2"))return TableRecord.generateTableRecord("q0_2", "x1", "k2", "4;5", "k3", "5;6", "k4", "6;7");
//		if(viewTable.getTableName().equals("q0_4"))return TableRecord.generateTableRecord("q0_4", "x1", "colfam_q0_3", "l1_d1", "x1", "colfam_q0_3", "l1_d2", "50", "colfam_q0_3", "l1_d3", "20");
//
//		return null;
	}
	
	
	public boolean insertToViewTable(String viewTableName, TableRecord tableRecord, boolean writeWAL) {
	
//		log.info(this.getClass(), "put tablerecord:[tableRecord="+tableRecord+"]");
//		log.info(this.getClass(), "write WAL: "+writeWAL);
		
		
		statisticVU.recordUpdate();
		
		Put put = new Put(Bytes.toBytes(tableRecord.getKey()));
		
//		if(!writeWAL)
			put.setWriteToWAL(false);
		
		for (String columnQualifier : tableRecord.getColumns().keySet()) {
			String value = tableRecord.getColumns().get(columnQualifier);
			String colFam = tableRecord.getColFamilies().get(columnQualifier);
			
//			log.info(this.getClass(), "put add:[colFam="+colFam+"][columnQualifier="+columnQualifier+"][value="+value+"]");
			put.add(Bytes.toBytes(colFam), Bytes.toBytes(columnQualifier), Bytes.toBytes(value));	
		}
		
		long start = System.nanoTime();
		
		tableService.put(Bytes.toBytes(viewTableName), put);
		
		statisticVU.recordLatency(System.nanoTime()-start);
		
//		System.out.println("PUTTime: "+(System.currentTimeMillis()-start));
	
		return true;
	}
	


	
	
	
	public boolean deleteFromViewTable(String viewTableName, TableRecord tableRecord,  boolean writeWAL) {
		
		log.info(this.getClass(), "delete tablerecord:[tableRecord="+tableRecord+"]");
		log.info(this.getClass(), "write WAL: "+writeWAL);
		
		statisticVU.recordUpdate();
		
		Delete delete = new Delete(Bytes.toBytes(tableRecord.getKey()));

//		if(!writeWAL)
			delete.setWriteToWAL(false);
		
		for(String col : tableRecord.getColumns().keySet()){
			
			String colFam = tableRecord.getColFamilies().get(col);

//			log.info(this.getClass(), "delete add:[colFam="+colFam+"][columnQualifier="+col+"]");
			
			delete.deleteColumns(Bytes.toBytes(colFam), Bytes.toBytes(col));
		}
		
		long start = System.nanoTime();
		
		tableService.delete(Bytes.toBytes(viewTableName), delete);
		
		statisticVU.recordLatency(System.nanoTime()-start);
		

		return true;
		
	}









	
	
	
	
	
}