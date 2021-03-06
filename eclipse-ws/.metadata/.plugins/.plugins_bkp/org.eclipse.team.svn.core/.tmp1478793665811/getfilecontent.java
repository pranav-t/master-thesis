package de.webdataplatform.viewmanager.processing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import de.webdataplatform.basetable.BaseTableOperation;
import de.webdataplatform.basetable.BaseTableUpdate;
import de.webdataplatform.graph.Node;
import de.webdataplatform.graph.OperationNode;
import de.webdataplatform.graph.TableNode;
import de.webdataplatform.log.Log;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.query.Query;
import de.webdataplatform.query.QueryManager;
import de.webdataplatform.regionserver.AssignedUpdate;
import de.webdataplatform.regionserver.NoQueueForViewManagerException;
import de.webdataplatform.regionserver.UpdateAssigner;
import de.webdataplatform.regionserver.UpdateDistributor;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.DeltaRecord;
import de.webdataplatform.system.PreAggRecord;
import de.webdataplatform.system.ReverseJoinRecord;
import de.webdataplatform.system.StatisticsElement;
import de.webdataplatform.system.Table;
import de.webdataplatform.system.TableRecord;
import de.webdataplatform.util.BytesUtil;
import de.webdataplatform.view.EditMode;
import de.webdataplatform.view.TableService;
import de.webdataplatform.view.ViewTable;
import de.webdataplatform.view.ViewUpdate;
import de.webdataplatform.view.operation.Aggregation;
import de.webdataplatform.view.operation.Delta;
import de.webdataplatform.view.operation.PreAggregation;
import de.webdataplatform.view.operation.Projection;
import de.webdataplatform.view.operation.ReverseJoin;
import de.webdataplatform.view.operation.Selection;
import de.webdataplatform.view.operation.ViewOperation;
import de.webdataplatform.viewmanager.CommitLog;
import de.webdataplatform.viewmanager.ICommitLog;
import de.webdataplatform.viewmanager.ViewManager;

public class Processing implements Runnable{

//	private SystemID selfID;
//	
//	private UpdateAssigner updateAssigner;
//	
	private UpdateDistributor updateDistributor;

	private Queue<String> incomingQueue;
	
	private QueryManager queryManager;
	
	private ConcurrentHashMap<String, SystemID> markers;
	
	private KVStoreUtil kvStoreUtil;


	
	
	private StatisticsElement statisticBU;
	
	private StatisticsElement statisticVR;
	
	private StatisticsElement statisticVU;
	
	private StatisticsElement statisticSent;
	
	private StatisticsElement statisticDetail;
	
	private Log log;
	

	
	public Processing(Log log, SystemID selfID, UpdateDistributor updateDistributor, Queue<String> incomingQueue, String vmName, QueryManager queryManager){
		this.incomingQueue = incomingQueue;
//		this.vmName = vmName;
//		this.commitLog = new CommitLog(log, vmName);
//		this.selfID = selfID; 
//		this.updateAssigner = updateAssigner;
		this.updateDistributor = updateDistributor;
		this.queryManager = queryManager;

		
		
		
		statisticBU = new StatisticsElement();
		statisticVR = new StatisticsElement();
		statisticVU = new StatisticsElement();
		statisticSent = new StatisticsElement();
		statisticDetail = new StatisticsElement();

		
		
		markers = new ConcurrentHashMap<String, SystemID>();
		this.log = log;
		
		kvStoreUtil = new KVStoreUtil(log, new TableService(log), statisticVR, statisticVU);

	}
	
	
	
	private long lastMeasure=new Date().getTime();
	
	
	
	@Override
	public void run() {
		

		
		while(true){
			
			try{
			
//			long currentTime = new Date().getTime();
//			if((currentTime - lastMeasure) > 1000){
//				lastMeasure = currentTime;
//
//				
//				statisticBU.measureTroughput();
//				statisticVR.measureTroughput();
//				statisticVU.measureTroughput();
//				statisticSent.measureTroughput();
//				
//				statisticBU.measureLatency();
//				statisticVR.measureLatency();
//				statisticVU.measureLatency();
//				statisticSent.measureLatency();
//				statisticDetail.measureLatency();
//			}
//			
			
			String update = incomingQueue.poll();
	
			
			if(update != null){
				
		    	BaseTableOperation bto = new BaseTableOperation(log, update);
//				BaseTableOperation bto = new BaseTableOperation(log, update);
				String baseTableName = bto.getBaseTable();
//				String regionServer = btu.getBaseOperation().getRegionServer();
				
//				if(SystemConfig.LOGGING_LOGUPDATES)log.info(this.getClass(),"processing update: "+btu);
				log.performance(this.getClass(), "----------------------------");
				try{
						
					if(baseTableName.startsWith(SystemConfig.MESSAGES_MARKERPREFIX)){
//						log.info(this.getClass(),"marker received, putting to map: "+baseTableName+","+bto.getRegionServer());
//						markers.put(baseTableName, new SystemID(bto.getRegionServer()));
						continue;
					}	

					
					processBaseTableOperation(bto);


					
					
				}catch(Exception e){
					
					log.info(this.getClass(), "errornous update:"+bto.convertToString());
					log.error(this.getClass(), e);
		
				}
				
			}
			
		
			if(incomingQueue.size() == 0){
				try {
					Thread.sleep(SystemConfig.VIEWMANAGER_UPDATEPOLLINGINTERVAL);
				} catch (InterruptedException e) {
			
					e.printStackTrace();
				}
			}
			
			}catch(Exception e){
				log.error(this.getClass(), e);
			}
		}
		
	}
	
	
	public void processBaseTableOperation(BaseTableOperation bto){
		
		if(SystemConfig.LOGGING_LOGUPDATES)log.info(this.getClass(), "processing bto: "+bto);
		
		log.info(this.getClass(), "------------------------base operation------------------------");
		
		log.info(this.getClass(), "base table operation:["+bto+"]");
		
		
		
		
		List<Query> queries = queryManager.getAffectedQueries(bto.getBaseTable(), false);
		
		if(queries.size() > 0){
			
			statisticBU.recordUpdate();
			
		}else{
			log.info(this.getClass(), "No query affected");
		}

		
		log.info(this.getClass(), "Affected queries:"+queries);
		long start = System.nanoTime();
		
		for (Query query : queries) {
				
			List<Table> trackedTables = query.getQueryDAG().getTrackedTables();
			
			List<ViewUpdate> viewUpdates = query.getQueryDAG().getViewUpdates(bto.getBaseTable());
			
			for (ViewUpdate viewUpdate : viewUpdates) {
				
				if(trackedTables.contains(viewUpdate.getViewTable()))viewUpdate.setTrackedTable(true);
			}
					
			log.info(this.getClass(), "View updates:"+viewUpdates);
			
			BaseTableUpdate btu = new BaseTableUpdate(bto);
			
			List<BaseTableUpdate> btus = new ArrayList<BaseTableUpdate>();
			btus.add(btu);
			
			
			
			
			processUpdates(viewUpdates,  btus);
			
			
				
		
		}

		statisticBU.recordLatency(System.nanoTime()-start);
//		System.out.println("BTTime: "+(System.currentTimeMillis()-start));
	}
	

	
	/**
	 * Process a number of view updates for a number of base table updates
	 * @param viewUpdates
	 * @param btus
	 */
	public void processUpdates(List<ViewUpdate> viewUpdates, List<BaseTableUpdate> btus){
		
//		btus = prepareBaseTableUpdates(btus);
		
		for (BaseTableUpdate baseTableUpdate : btus) {
			
			
			log.updates(this.getClass(), "-------------btu------------");
			
			log.info(this.getClass(), "base table update:["+baseTableUpdate+"]");
			
			processBaseTableUpdate(new ArrayList<ViewUpdate>(viewUpdates), baseTableUpdate);
			
		}
		
		
	}


//	private List<BaseTableUpdate> prepareBaseTableUpdates(List<BaseTableUpdate> btus) {
//		
//		List<BaseTableUpdate> newList = new ArrayList<BaseTableUpdate>();
//		
//		for (BaseTableUpdate btu : btus) {
//			
//				if(btu.isUpdate()){
//
//					newList.addAll(btu.splitUpdate());
//		
//				}else newList.add(btu);
//
//			
//		}
//		return newList;
//	}
	
	
	
	
	/**
	 * Process all view updates for a single base table update
	 * @param viewUpdates
	 * @param btu
	 */
	public void processBaseTableUpdate(List<ViewUpdate> viewUpdates, BaseTableUpdate btu){
		
		List<ViewUpdate> notProcessed = new ArrayList<ViewUpdate>(viewUpdates);
		List<BaseTableUpdate> outputBtus = null;
		
		for (ViewUpdate viewUpdate : viewUpdates) {
			
			if(notProcessed==null || notProcessed.size()==0 || btu==null)return;
			
			long insertionTime = new Date().getTime();
			log.info(this.getClass(), "--------view update---------");
			
			log.info(this.getClass(), "view update:["+viewUpdate+"]");
			
//			long start = System.currentTimeMillis();
			
			outputBtus = processViewUpdate(viewUpdate, btu);
			
//			System.out.println("VUTime: "+(System.currentTimeMillis()-start));
			
			log.info(this.getClass(), "output btu:["+outputBtus+"]");
			log.performance(this.getClass(), "insertionTime time: "+(new Date().getTime() - insertionTime));
			notProcessed.remove(viewUpdate);
			
			if(outputBtus.size() == 0){
				return;
			}
			if(outputBtus.size() == 1){
				btu = outputBtus.get(0);
			}
			if(outputBtus.size() > 1){
				processUpdates(notProcessed, outputBtus);
				return;
			}
	

		}
		
		
			


		
	}


	public void sendUpdateBack(ViewUpdate viewUpdate, BaseTableUpdate btu){
		

		log.info(this.getClass(),"sending update back to rs: "+btu.getSender());
		
		BaseTableOperation bto;
		
		
		try {
			
			if(btu.isUpdate()){

					BaseTableUpdate btu1 = btu.splitUpdate().get(0);
					BaseTableUpdate btu2 = btu.splitUpdate().get(1);
					
					bto = new BaseTableOperation(btu1.getRecord(), btu1.getOperationType(), btu1.getSender());
					log.info(this.getClass(),"bto: "+bto);
					updateDistributor.queueUpdate(new AssignedUpdate(bto.getSender(), bto));
					
					bto = new BaseTableOperation(btu2.getRecord(), btu2.getOperationType(), btu2.getSender());
					log.info(this.getClass(),"bto: "+bto);
					updateDistributor.queueUpdate(new AssignedUpdate(bto.getSender(), bto));
					
				}else{
					
					bto = new BaseTableOperation(btu.getRecord(), btu.getOperationType(), btu.getSender());
					log.info(this.getClass(),"bto: "+bto);
					updateDistributor.queueUpdate(new AssignedUpdate(bto.getSender(), bto));
				}
			
			
			
		} catch (NoQueueForViewManagerException e) {
			log.error(this.getClass(),e);
		}

		
		
	}
	/**
	public void sendUpdateToNextVM(ViewUpdate viewUpdate, BaseTableUpdate btu){
		
		
//		BaseTableOperation
		
		log.info(this.getClass(),"sending view update to next vm: "+btu);
		
		ViewOperation viewOperation = viewUpdate.getViewOperation();
		
//		log.info(this.getClass(),"key: "+key+" has been assigned to:"+viewManager.getVMName());

		
		if(btu.isUpdate()){

			String newKey = viewOperation.getViewRecordKey(btu.getBaseRecord());
			String oldKey = viewOperation.getViewRecordKey(btu.getOldBaseRecord());
			
			if(!newKey.equals(oldKey)){
				
				log.info(this.getClass(),"reference-key updated, splitting updates: newKey:"+newKey+", oldKey:"+oldKey);
				
				BaseTableUpdate btu1 = btu.splitUpdate().get(0);
				BaseTableUpdate btu2 = btu.splitUpdate().get(1);
				
				BaseTableOperation bto = new BaseTableOperation(btu1.getRecord(), btu1.getOperationType());
				sendUpdateToVM(oldKey, bto);
				
				bto = new BaseTableOperation(btu2.getRecord(), btu2.getOperationType());
				sendUpdateToVM(newKey, bto);
				
			}else{
				
				BaseTableOperation bto = new BaseTableOperation(btu.getRecord(), btu.getOperationType());
				sendUpdateToVM(newKey, bto);
			}

		}else{
			
			String key = viewOperation.getViewRecordKey(btu.getRecord());
			BaseTableOperation bto = new BaseTableOperation(btu.getRecord(), btu.getOperationType());
			sendUpdateToVM(key, bto);
		}
		

		
		
	}
	
	
	public void sendUpdateToVM(String key, BaseTableOperation bto){
		
		ViewManager viewManager = updateAssigner.assignUpdate(key);
		
		
		if(viewManager.getVMName().equals(selfID.getName())){
			
			log.info(this.getClass(),"self reference, self-queuing update");
			processBaseTableOperation(bto);

		}else{
			
			log.info(this.getClass(),"sending updates to VM");
			try {
				updateDistributor.queueUpdate(new AssignedUpdate(viewManager, bto));
			} catch (NoQueueForViewManagerException e) {
				log.error(this.getClass(),e);
			}
				
			
		}
		
	}
	*/
	
	
	
	/**
	 * Process a single view updates for a base table update
	 * @param viewUpdate
	 * @param btu
	 * @return
	 */
	public List<BaseTableUpdate> processViewUpdate(ViewUpdate viewUpdate, BaseTableUpdate btu) {

		
//		throughputVU.incrementAndGet();
		
		ViewOperation viewOperation = viewUpdate.getViewOperation();
		ViewTable viewTable = viewUpdate.getViewTable();
		
		List<BaseTableUpdate> outputBtus= new ArrayList<BaseTableUpdate>();
		log.info(this.getClass(), "processing update:[viewtable="+viewTable+"][viewOperation="+viewOperation);

		
		if(viewTable == null){
			
			
			long start = System.nanoTime();
			
			sendUpdateBack(viewUpdate, btu);
			
			statisticSent.recordLatency(System.nanoTime() - start);
			statisticSent.recordUpdate();

			return outputBtus;
			
		}
		
		
		
		TableRecord  viewRecord = kvStoreUtil.retrieveOldViewRecord(viewTable, viewOperation, btu);
		if(viewRecord != null){
			
			viewUpdate.setViewRecord(viewRecord);
			log.info(this.getClass(), "view record:["+viewRecord+"]");
		}else{
			viewUpdate.setViewRecord(null);
			log.info(this.getClass(), "no view record found");
		}
		

		long start = System.nanoTime();
		
		
		if(viewOperation instanceof Delta){
			
			Delta delta = (Delta)viewOperation;
			BaseTableUpdate outputBtu = updateDeltaView(btu, viewUpdate, delta);
			if(outputBtu!=null)outputBtus.add(outputBtu);
		}
		
		if(viewOperation instanceof Projection){
			
			Projection projection = (Projection)viewOperation;
			BaseTableUpdate outputBtu = updateProjectionView(btu, viewUpdate, projection);
			if(outputBtu!=null)outputBtus.add(outputBtu);

		}
		
		if(viewOperation instanceof Selection){
			
			Selection selection = (Selection)viewOperation;
			BaseTableUpdate outputBtu = updateSelectionView(btu, viewUpdate, selection);
			if(outputBtu!=null)outputBtus.add(outputBtu);

		}

//		if(viewOperation instanceof Aggregation){
//			
//			Aggregation aggregation = (Aggregation)viewOperation;
//			BaseTableUpdate outputBtu = updateAggregationView(btu, viewUpdate, aggregation);
//			if(outputBtu!=null)outputBtus.add(outputBtu);
//		}

		if(viewOperation instanceof PreAggregation){
			
			PreAggregation preAggregation = (PreAggregation)viewOperation;
			BaseTableUpdate outputBtu = updatePreAggregationView(btu, viewUpdate, preAggregation);
			if(outputBtu!=null)outputBtus.add(outputBtu);
		}		

		if(viewOperation instanceof ReverseJoin){
			
			ReverseJoin reverseJoin = (ReverseJoin)viewOperation;
			outputBtus = updateReverseJoinView(btu, viewUpdate, reverseJoin);
		}
		
		statisticDetail.recordLatency(System.nanoTime()-start);
		
		return outputBtus;

		
	}
	

	
	
	
	
	public BaseTableUpdate updateDeltaView(BaseTableUpdate btu, ViewUpdate viewUpdate, Delta delta){

		String viewTableName = viewUpdate.getViewTable().getTableName();
		String type = btu.getOperationType();
		
		TableRecord newViewRecord = btu.getRecord();
		TableRecord oldViewRecord = viewUpdate.getViewRecord();
		
		if(oldViewRecord != null){
			
//			DeltaRecord deltaRecord = new DeltaRecord(oldViewRecord);
//			oldViewRecord = deltaRecord.getNewRecord();
			oldViewRecord = oldViewRecord.recordFromString(oldViewRecord.getColumns().get("c1"), null, true);
		}
		
		if(type.equals("Put")){
				

			TableRecord tableRecord = new TableRecord();
			tableRecord.setKey(btu.getRecord().getKey());
			try {
				tableRecord.putValue("colfam1", "c1", btu.getRecord().recordToString(delta.getColumns(), true));
			} catch (Exception e) {
				log.error(this.getClass(), e);
			}
			newViewRecord.projectColumns(delta.getColumns());
			newViewRecord = new DeltaRecord(newViewRecord, oldViewRecord).transform();
			newViewRecord.setTableName(viewTableName);
			
			kvStoreUtil.insertToViewTable(viewTableName, tableRecord, viewUpdate.isTrackedTable());
		}	
		
		if(type.equals("Delete") || type.equals("DeleteColumn") || type.equals("DeleteFamily")){
				
			newViewRecord = new DeltaRecord(null, oldViewRecord).transform();
			newViewRecord.setTableName(viewTableName);
			
			kvStoreUtil.deleteFromViewTable(viewTableName, btu.getRecord(), viewUpdate.isTrackedTable());
		}
		log.info(this.getClass(), "viewTableName: "+viewTableName);
		log.info(this.getClass(), "newViewRecord: "+newViewRecord);
		
		
		btu.setRecord(type, newViewRecord);
		
		return btu;
	}
	
	
	
	
	public BaseTableUpdate updateProjectionView(BaseTableUpdate btu, ViewUpdate viewUpdate, Projection projection){
		
		String type = btu.getOperationType();
		String viewTableName = viewUpdate.getViewTable().getTableName();
		
		if(type.equals("Put")){
			
			String viewRecordKey = projection.getViewRecordKey(btu);		
	
			TableRecord viewRecord = new TableRecord(viewTableName, viewRecordKey);

			try {
				viewRecord =  projection.projectColumns(btu.getRecord());
			} catch (Exception e) {
	
				log.error(this.getClass(), e);
			}
			log.info(this.getClass(), "Projected view record: "+viewRecord);
			
			if(viewRecord.getColumns().isEmpty()){
				log.info(this.getClass(), "Projection columns not found, skipping update");
				return null;
			}
			
			
			kvStoreUtil.insertToViewTable(viewTableName, viewRecord, viewUpdate.isTrackedTable());
			btu.getRecord().setTableName(viewTableName);

		}	
		if(type.equals("Delete") || type.equals("DeleteColumn") || type.equals("DeleteFamily")){
			if(viewUpdate.getViewRecord()==null){
				log.info(this.getClass(), "Delete column not found, skipping update");
				return null;
			}
			kvStoreUtil.deleteFromViewTable(viewTableName, viewUpdate.getViewRecord(), viewUpdate.isTrackedTable());
			btu.setRecord(type, viewUpdate.getViewRecord());
		}
		
//		if(viewUpdate.getViewRecord()!=null){
//			btu.setOldBaseRecord(viewUpdate.getViewRecord());
//		}
		
		return btu;
	}
	
	
	
	
	
	public BaseTableUpdate updateSelectionView(BaseTableUpdate btu, ViewUpdate viewUpdate, Selection selection){
		
		String type = btu.getOperationType();
		TableRecord deltaRecord = btu.getRecord();
		TableRecord viewRecord = viewUpdate.getViewRecord();
		
		String viewTableName = viewUpdate.getViewTable().getTableName();
		
			
		if(type.equals("Put")){
			
			boolean isMatching=false;
			try {
				isMatching = selection.getPredicate().eval(deltaRecord);
				log.info(this.getClass(), "isMatching: "+isMatching);
			} catch (Exception e) {

				e.printStackTrace();
				log.error(this.getClass(), e);
			}
			
			
			if(isMatching){
				
				kvStoreUtil.insertToViewTable(viewTableName, deltaRecord, viewUpdate.isTrackedTable());
				
			}
			
			if(!isMatching){
				
				log.info(this.getClass(), "Selection condition not met");
				if(viewRecord != null){
					kvStoreUtil.deleteFromViewTable(viewTableName, deltaRecord, viewUpdate.isTrackedTable());

					btu.setOperationType("Delete");
					
					
				}else{
					
					return null;
				}
			}
			btu.getRecord().setTableName(viewTableName);
			
		}

		if(type.equals("Delete") || type.equals("DeleteColumn") || type.equals("DeleteFamily")){
			
			
			if(viewRecord != null){
//				boolean isMatching=false;
//				try {
//					isMatching = selection.getPredicate().eval(viewRecord);
//					log.info(this.getClass(), "isMatching: "+isMatching);
//				} catch (Exception e) {
//	
//					e.printStackTrace();
//					log.error(this.getClass(), e);
//				}
//				
//				if(isMatching){
//					
					kvStoreUtil.deleteFromViewTable(viewTableName, deltaRecord, viewUpdate.isTrackedTable());
					btu.setRecord(type, viewUpdate.getViewRecord());
//					
//				}else{
//					return null;
//				}
			}else{
				return null;
			}

		}
		return btu;
		
	}
	


	
	
	public BaseTableUpdate updatePreAggregationView(BaseTableUpdate btu, ViewUpdate viewUpdate, PreAggregation preAggregation){
		
		
		String type = btu.getOperationType();
		ViewTable viewTable = viewUpdate.getViewTable();
		String viewTableName = viewTable.getTableName();
	
//		long start = System.currentTimeMillis();
		
//		TableRecord viewDelta=null;
		PreAggRecord viewRecord=null;
		
		if(viewUpdate.getViewRecord() != null){
			
			viewRecord = new PreAggRecord(viewUpdate.getViewRecord(), preAggregation);
			log.info(this.getClass(), "PreAggRecord:"+viewRecord);
		}else{
			viewRecord = new PreAggRecord(preAggregation);
			log.info(this.getClass(), "no view record found");
		}
		
//		System.out.println("INITTime: "+(System.currentTimeMillis()-start));

		
		// Inserting delta into view
//		PreAggRecord preAggRecord = new PreAggRecord(preAggregation);
//		preAggRecord.addRecord(btu.getRecord());
//		viewDelta = preAggRecord.transform();
		
		if(type.equals("Put")){

			viewRecord.addRecord(btu.getRecord());		

//			log.info(this.getClass(), "insert viewDelta:"+viewDelta);
//			kvStoreUtil.insertToViewTable(viewTableName, viewDelta);
			
		}
		
		
		/** DELETE **/
		if(type.equals("Delete") || type.equals("DeleteColumn") || type.equals("DeleteFamily")){
			
			viewRecord.removeRecord(btu.getRecord());	
			
//			log.info(this.getClass(), "delete viewDelta:"+viewDelta);
//			kvStoreUtil.deleteFromViewTable(viewTableName, viewDelta);
		}
		
//		long start1 = System.currentTimeMillis();
		
		if(viewRecord.getRecords().size() > 0){
			// Transforming complete updated view record
			try {
				kvStoreUtil.insertToViewTable(viewTableName, viewRecord.transform(), viewUpdate.isTrackedTable());
			} catch (Exception e) {
				log.error(this.getClass(), e);
			}

			
		}else{
			PreAggRecord preAggRecord = new PreAggRecord(preAggregation);
			preAggRecord.addRecord(btu.getRecord());
			try {
				kvStoreUtil.deleteFromViewTable(viewTableName, preAggRecord.transform(), viewUpdate.isTrackedTable());
			} catch (Exception e) {
				log.error(this.getClass(), e);
			}
		}
		
//		System.out.println("WRITETime: "+(System.currentTimeMillis()-start1));
		
//		long start2 = System.currentTimeMillis();
		
		
		TableRecord aggRecord = null;
		
		try{
			aggRecord = viewRecord.buildAggRecord(viewTableName, preAggregation.getViewRecordKey(btu));
		
		}catch(Exception e){
			
			log.error(this.getClass(), e);
		}
		
//		log.info(this.getClass(), "aggRecord:"+aggRecord);
//		log.info(this.getClass(), "operation type:"+viewRecord.getOperationType());
		
		btu.setRecord(viewRecord.getOperationType(), aggRecord);
		
//		System.out.println("AGGTime: "+(System.currentTimeMillis()-start2));

		
		return btu;			
	}		
		

		
	
	public List<BaseTableUpdate> updateReverseJoinView(BaseTableUpdate btu, ViewUpdate viewUpdate, ReverseJoin reverseJoin){
		
		String type = btu.getOperationType();
		ViewTable viewTable = viewUpdate.getViewTable();
		String viewTableName = viewTable.getTableName();
	
		log.info(this.getClass(), "viewTableName:"+viewTableName);
		
//		TableRecord oldViewRecord = retrieveOldViewRecord(viewTable, viewUpdate.getViewOperation(), btu.getBaseRecord());
		
		
		ReverseJoinRecord viewRecord=null;
		
		if(viewUpdate.getViewRecord() != null){
			
			log.info(this.getClass(), "viewUpdate.getViewRecord():"+viewUpdate.getViewRecord());
			viewRecord = new ReverseJoinRecord(viewUpdate.getViewRecord(), reverseJoin);
			log.info(this.getClass(), "view record:"+viewRecord);
			
		}else{
			viewRecord = new ReverseJoinRecord(reverseJoin);
			log.info(this.getClass(), "no view record found");
		}
		

		
		log.info(this.getClass(), "btu.getBaseRecord():"+btu.getRecord());

		
//		Update view table
		if(type.equals("Put")){

			log.info(this.getClass(), "add record:"+btu.getRecord());
			viewRecord.addRecord(btu.getRecord());		
			
		}
		
		
		/** DELETE **/
		if(type.equals("Delete") || type.equals("DeleteColumn") || type.equals("DeleteFamily")){
			
			log.info(this.getClass(), "remove record:"+btu.getRecord());
			viewRecord.removeRecord(btu.getRecord());
			
			
			
		}
		
		if(viewRecord.isEmpty()){
			kvStoreUtil.deleteFromViewTable(viewTableName, new TableRecord(reverseJoin.getViewRecordKey(btu)), viewUpdate.isTrackedTable());
		}else{
			try {
				kvStoreUtil.insertToViewTable(viewTableName, viewRecord.transform(), viewUpdate.isTrackedTable());
			} catch (Exception e) {
				log.error(this.getClass(), e);
			}
		}
		
//		prepare incremental update operations
		viewRecord.removeTable(btu.getRecord().getTableName());
		viewRecord.addRecord(btu.getRecord());	
		
		
		
		
		
		
		// Transforming complete updated view record
		List<TableRecord> joinRecords = viewRecord.buildJoinRecords();
		log.info(this.getClass(), "joinRecords:"+joinRecords);
		
		List<BaseTableUpdate> outputBtus=new ArrayList<BaseTableUpdate>();
		
		if(joinRecords != null){
			for (TableRecord joinRecord : joinRecords) {
				
				
				BaseTableUpdate btuCopy = btu.copy();
				joinRecord.setTableName(viewTableName);
				btuCopy.setRecord(type, joinRecord);

				outputBtus.add(btuCopy);
			}
		}
		
		return outputBtus;		
							
	}		
	
	
	
	
	

	
	
	

	public boolean containsNullValues(Map<String, String> map){
		
		for (String key : map.keySet()) {
			
			if(map.get(key) == null || map.get(key).equals("") || map.get(key).equals(" ") || map.get(key).equals("null"))return true;
		}
		
		return false;
		
	}
	
	public Map<String, String> removeNullValues(Map<String, String> map){
		
		Map<String, String> result = new HashMap<String, String>();
		
		for (String key : map.keySet()) {
			
			if(map.get(key) != null  && !map.get(key).equals("") && !map.get(key).equals(" "))result.put(key, map.get(key));
		}
		
		return result;
		
	}





	public StatisticsElement getStatisticBU() {
		return statisticBU;
	}


	public void setStatisticBU(StatisticsElement statisticBU) {
		this.statisticBU = statisticBU;
	}


	public StatisticsElement getStatisticVR() {
		return statisticVR;
	}


	public void setStatisticVR(StatisticsElement statisticVR) {
		this.statisticVR = statisticVR;
	}


	public StatisticsElement getStatisticVU() {
		return statisticVU;
	}


	public void setStatisticVU(StatisticsElement statisticVU) {
		this.statisticVU = statisticVU;
	}


	public ConcurrentHashMap<String, SystemID> getMarkers() {
		return markers;
	}

	public void setMarkers(ConcurrentHashMap<String, SystemID> markers) {
		this.markers = markers;
	}


	public StatisticsElement getStatisticSent() {
		return statisticSent;
	}


	public void setStatisticSent(StatisticsElement statisticSent) {
		this.statisticSent = statisticSent;
	}


	public StatisticsElement getStatisticDetail() {
		return statisticDetail;
	}


	public void setStatisticDetail(StatisticsElement statisticDetail) {
		this.statisticDetail = statisticDetail;
	}



	
	
	

}
