package de.webdataplatform.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

import de.webdataplatform.basetable.BaseTableOperation;
import de.webdataplatform.basetable.BaseTableUpdate;
import de.webdataplatform.graph.GraphUtil;
import de.webdataplatform.graph.Node;
import de.webdataplatform.graph.OperationNode;
import de.webdataplatform.graph.TableNode;
import de.webdataplatform.log.Log;
import de.webdataplatform.message.SystemID;
import de.webdataplatform.parser.Parser;
import de.webdataplatform.query.Query;
import de.webdataplatform.query.QueryManager;
import de.webdataplatform.query.QueryTable;
import de.webdataplatform.regionserver.UpdateAssigner;
import de.webdataplatform.regionserver.UpdateDistributor;
import de.webdataplatform.regionserver.WALReader;
import de.webdataplatform.settings.JoinTablePair;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.system.Component;
import de.webdataplatform.system.StatisticsElement;
import de.webdataplatform.system.Table;
import de.webdataplatform.view.ViewTable;
import de.webdataplatform.view.ViewUpdate;
import de.webdataplatform.view.operation.ViewOperation;
import de.webdataplatform.viewmanager.ViewManager;
import de.webdataplatform.viewmanager.processing.Processing;

public class TestQueryProcessing {

	/**
	 * @param args
	 */
	
	private static Log log; 
	
	
	
	public static void printJoinRelations(List<JoinTablePair> jtps){
		
		for (JoinTablePair joinTablePair : jtps) {
			System.out.println(joinTablePair);
		}
		
	}
	
	private static Processing processing;
	
	private static QueryManager queryManager;
	
	private static Parser queryParser;
	
	private static QueryTable queryTable;
	
	
	public static void main(String[] args) {

		log = new Log("TestClient");

		SystemConfig.load(log);
		NetworkConfig.load(log);
//		DatabaseConfig.load(log);
//		EvaluationConfig.load(log);
		
//		
//		BaseTableService bts = new BaseTableService(log);
//		
//		List<Result> results = bts.scanValue("bt1", "colAggKey", "x060");
//		
//		System.out.println(results);
//		
//		Result result;
//		try {
//			result = bts.get("bt2", "x60");
//			System.out.println(result);	
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}


		

		
		
		/** CREATE AND FILL VIEW DEFINITIONS*/
		

		
		queryTable = new QueryTable(log);
		
		queryTable.getRawQueries().put("q0", "select sum(bt1.c2) as sum1, sum(bt1.c3) as sum2, count(bt1.c3) as count1 from bt1 where bt1.c3 <= 15 pre group by bt1.c1");
		
//		queryTable.getQueries().put("q0", "select bt1.c1 from bt1 where bt1.c3 <= 15");
//		
//		queryTable.getQueries().put("q1", "select bt1.c1, sum(bt1.c2) as sum1, count(bt1.c2), index(bt1.c2) from bt1 group by bt1.c1 where bt1.c2 < 50 having bt1.sum1 < 10");
//		
//		queryTable.getQueries().put("q0", "select bt1.c1 from bt1, bt2 where bt1.c1 = bt2.d1 and bt1.c3 < 10 and bt2.d3 > 15");
		
		String query6 = "select lineitem.returnflag, lineitem.linestatus, "
		+"sum(lineitem.quantity) as sum_qty, "
		+"sum(lineitem.extendedprice) as sum_base_price, "
		+"sum(lineitem.extendedprice * (1 - lineitem.discount)) as sum_disc_price, "
		+"sum(lineitem.extendedprice * (1 - lineitem.discount) * (1 + lineitem.tax)) as sum_charge, "
		+"avg(lineitem.quantity) as avg_qty, "
		+"avg(lineitem.extendedprice) as avg_price, "
		+"avg(lineitem.discount) as avg_disc, "
		+"count(lineitem.*) as count_order "
		+"from lineitem "
//		+"where lineitem.shipdate <= date '1998-12-01' - interval '90' day (3) "
		+"where lineitem.shipdate <= '1998-12-01' "
		+"group by lineitem.returnflag, lineitem.linestatus "
	    +"order by lineitem.returnflag, lineitem.linestatus ";
		
		String query7 = "select lineitem.orderkey,"
		+" sum(lineitem.extendedprice * (1 - lineitem.discount)) as revenue,"
		+" orders.orderdate,"
		+" orders.shippriority"
		+" from customer, orders, lineitem"
		+" where "
		+" customer.c_custkey = orders.o_custkey"
		+" and lineitem.l_orderkey = orders.o_orderkey"
		+" and customer.mktsegment = 'FOOD"
		+" and orders.orderdate < '15-10-2015'"
		+" and lineitem.shipdate > '16-10-2016'"
		+" group by lineitem.l_orderkey, orders.orderdate, orders.shippriority"
		+" order by lineitem.revenue desc, orders.orderdate";
		
		String query8 ="select all_nations.year, "
//				+"sum(case when nation = ':1' then volume else 0 end) / " +
				+"sum(all_nations.volume) as mkt_share "
				+"from "
				+"( "
				+"	select "
				+"		orders.orderdate as o_year, "
				+"		lineitem.extendedprice * (1 - lineitem.discount) as volume, "
				+"		n2.name as nation "
				+"	from "
				+"		part, supplier, lineitem, orders, customer, nation as n1, nation as n2, region "
				+"	where "
				+"		part.p_partkey = lineitem.l_partkey "
				+"		and supplier.s_suppkey = lineitem.l_suppkey "
				+"		and lineitem.l_orderkey = orders.o_orderkey "
				+"		and orders.o_custkey = customer.c_custkey "
				+"		and customer.c_nationkey = n1.n_nationkey "
				+"		and n1.n_regionkey = region.r_regionkey "
				+"		and region.name = ':2' "
				+"		and supplier.s_nationkey = n2.n_nationkey "
				+"		and orders.orderdate between '1995-01-01' and '1996-12-31' "
				+"		and part.type = ':3' "
				+") as all_nations "
				+"group by all_nations.year "
				+"order by all_nations.year; ";
		
		String query10="select sum(lineitem.extendedprice * (1 - lineitem.discount)) as revenue "
		+"from lineitem, part "
		+"where "
		+"( "
		+"	part.partkey = lineitem.partkey "
		+"	and part.brand = ':1' "
		+"	and part.container in ('SM CASE', 'SM BOX', 'SM PACK', 'SM PKG') "
		+"	and lineitem.quantity >= 4 and lineitem.quantity <= 14 "
		+"	and part.size between 1 and 5 "
		+"	and lineitem.shipmode in ('AIR', 'AIR REG') "
		+"	and lineitem.shipinstruct = 'DELIVER IN PERSON' "
		+") "
		+"or "
		+"( "
//		+"	part.partkey = lineitem.partkey "
		+"	part.brand = ':2' "
		+"	and part.container in ('MED BAG', 'MED BOX', 'MED PKG', 'MED PACK') "
		+"	and lineitem.quantity >= 5 and lineitem.quantity <= 15 "
		+"	and part.size between 1 and 10 "
		+"	and lineitem.shipmode in ('AIR', 'AIR REG') "
		+"	and lineitem.shipinstruct = 'DELIVER IN PERSON' "
		+") "
		+"or "
		+"( "
//		+"	part.partkey = lineitem.partkey "
		+"	part.brand = ':3' "
		+"	and part.container in ('LG CASE', 'LG BOX', 'LG PACK', 'LG PKG') "
		+"	and lineitem.quantity >= 6 and lineitem.quantity <= 16 "
		+"	and part.size between 1 and 15 "
		+"	and lineitem.shipmode in ('AIR', 'AIR REG') "
		+"	and lineitem.shipinstruct = 'DELIVER IN PERSON' "
		+"); ";
		
		
//		queryTable.getQueries().put("q2", query10);
		
//		
//		queryTable.getQueries().put("q3", "select * from A inner join B on A.c1 = B.c1 inner join C on B.c1 = C.c1 where A.c1 < 19");
//		
//		queryTable.getQueries().put("q4", "select D.c1, sum(D.c2), count(D.c2), index(D.c2) from D pre group by D.c1");
//		
//		queryTable.getQueries().put("q5", "select D.c1, sum(D.c2), count(D.c2), index(D.c2) from D group by D.c1");
		
		
		
//		try {
////			queryTable.generateQueryTable();
//			queryTable.addQuery("q1", "select sum(c2) from bt1 where c2 < 60 group by c1 having sum(c2) < 50");
////			queryTable.addQuery("q2", "select c1 from bt1 where c2 < 60");
//			queryTable.loadQueries();
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		
//		
////		queryTable.loadQueries();
		
//		queryParser = new Parser(log);
		
		queryManager = new QueryManager(log);
		
		queryManager.setQueryTable(queryTable);
		
//		queryManager.parseQueries();
		
		
		
//		System.out.println("Queries: "+queryManager.getQueries());
//		
//		System.out.println("-------------------------------------------------");
//		System.out.println("Basetables:"+queryManager.getQuery("q0").getQueryDAG().getBaseTables());
//		System.out.println("Viewtables:"+queryManager.getQuery("q0").getQueryDAG().getViewTables());
//		System.out.println("Tracked tables:"+queryManager.getQuery("q0").getQueryDAG().getTrackedTables());
//		System.out.println("Zone1:"+queryManager.getQuery("q0").getQueryDAG().getZone("bt1"));
//		System.out.println("Zone2:"+queryManager.getQuery("q0").getQueryDAG().getZone("q0_0"));
		
		

		System.out.println("-------------------------------------------------");		
		System.out.println("Basetables:"+queryManager.getQuery("q0").getQueryDAG().getBaseTables());
		System.out.println("Viewtables:"+queryManager.getQuery("q0").getQueryDAG().getViewTables());
		System.out.println("Tracked tables:"+queryManager.getQuery("q0").getQueryDAG().getTrackedTables());
//		System.out.println("Zone:"+queryManager.getQuery("q2").getQueryDAG().getZone("bt1"));
/**		

		System.out.println("-------------------------------------------------");
		System.out.println("Basetables:"+queryPlanner.getQuery("q2").getQueryDAG().getBaseTables());
		System.out.println("Viewtables:"+queryPlanner.getQuery("q2").getQueryDAG().getViewTables());
		System.out.println("Tracked tables:"+queryPlanner.getQuery("q2").getQueryDAG().getTrackedTables());
		System.out.println("Zone:"+queryPlanner.getQuery("q2").getQueryDAG().getZone("A"));
		System.out.println("Zone:"+queryPlanner.getQuery("q2").getQueryDAG().getZone("B"));
		
		System.out.println("-------------------------------------------------");
		System.out.println("Basetables:"+queryPlanner.getQuery("q3").getQueryDAG().getBaseTables());
		System.out.println("Viewtables:"+queryPlanner.getQuery("q3").getQueryDAG().getViewTables());
		System.out.println("Tracked tables:"+queryPlanner.getQuery("q3").getQueryDAG().getTrackedTables());
		System.out.println("Zone:"+queryPlanner.getQuery("q3").getQueryDAG().getZone("A"));
		System.out.println("Zone:"+queryPlanner.getQuery("q3").getQueryDAG().getZone("B"));
		System.out.println("Zone:"+queryPlanner.getQuery("q3").getQueryDAG().getZone("C"));
		
		System.out.println("------------------------------------------------");

		System.out.println("Basetables:"+queryPlanner.getQuery("q4").getQueryDAG().getBaseTables());
		System.out.println("Viewtables:"+queryPlanner.getQuery("q4").getQueryDAG().getViewTables());
		System.out.println("Tracked tables:"+queryPlanner.getQuery("q4").getQueryDAG().getTrackedTables());
		System.out.println("Zone:"+queryPlanner.getQuery("q4").getQueryDAG().getZone("D"));
		System.out.println("Zone:"+queryPlanner.getQuery("q4").getQueryDAG().getZone("q1_V0"));
		
		System.out.println("-------------------------------------------------");

		System.out.println("Basetables:"+queryPlanner.getQuery("q5").getQueryDAG().getBaseTables());
		System.out.println("Viewtables:"+queryPlanner.getQuery("q5").getQueryDAG().getViewTables());
		System.out.println("Tracked tables:"+queryPlanner.getQuery("q5").getQueryDAG().getTrackedTables());
		System.out.println("Zone:"+queryPlanner.getQuery("q5").getQueryDAG().getZone("D"));
		System.out.println("Zone:"+queryPlanner.getQuery("q5").getQueryDAG().getZone("q1_V0"));*/
		
		System.out.println("-------------------------------------------------");
		/** READ OUT WAL RS1 */	

		
		Queue<BaseTableOperation> btuQueue = new ConcurrentLinkedQueue<>();

		readWAL(btuQueue);

		processUpdates(btuQueue, queryManager);
	

		
		
	}

	
	public static void readWAL(Queue<BaseTableOperation> btuQueue){
		
//		btuQueue.add(BaseTableOperation.generateBtuDelete("bt1", "k1"));
		btuQueue.add(BaseTableOperation.generateBtuPut("bt1", "k1", "c1", "x2", "c2", "5", "c3", "10"));
//		btuQueue.add(BaseTableOperation.generateBtuPut("bt1", "k2", "c1", "x2", "c2", "y1", "c3", "16"));
		btuQueue.add(BaseTableOperation.generateBtuPut("q0_1", "k1", "c1", "null;x1", "c2", "null;20", "c3", "null;20"));

//		btuQueue.add(generateBtu("q2_4", "k1", "c1", "x1", "c2", "y1", "c3", "10"));
//		btuQueue.add(generateBtu("q2_5", "k2", "c1", "x2", "c2", "y1", "c3", "15"));
//		btuQueue.add(generateBtu("q2_6", "k3", "c1", "x1", "c2", "y1", "c3", "20"));
//		
		
//		btuQueue.add(generateBtu("q2_8", "k3", "c1", "x1", "c2", "y1", "c3", "20"));
//		btuQueue.add(generateBtu("q2_9", "k3", "c1", "x1", "c2", "y1", "c3", "20"));
		
		
		
	}
	



	
	public static void processUpdates(Queue<BaseTableOperation> btuQueue, QueryManager queryManager){
		
		System.out.println("---------------------");
		
		SystemID systemID1 = new SystemID(Component.viewManager, "vm1", "127.0.0.1", 4561, 4611);
		
		SystemID systemID2 = new SystemID(Component.viewManager, "vm2", "127.0.0.2", 4562, 4612);

		UpdateAssigner updateAssigner = new UpdateAssigner(log, new ArrayList<ViewManager>());
		
		updateAssigner.addViewManager(new ViewManager(systemID1));
		
		updateAssigner.addViewManager(new ViewManager(systemID2));
        
		StatisticsElement updatesSent = new StatisticsElement();
		
		
		UpdateDistributor updateDistributor = new UpdateDistributor(log, systemID1, updatesSent, SystemConfig.REGIONSERVER_SENDINGTHREADS);
		
		processing = new Processing(log, systemID1, updateDistributor, null, null, queryManager);

		int i = 0;
		while(!btuQueue.isEmpty()){
			
			BaseTableOperation bto = btuQueue.poll();

			processing.processBaseTableOperation(bto);
			
			
//			System.out.println("------------------------------------------------");

			
			
		}

		
		System.out.println("Updates processed: "+i);
	}
	
	
	
	public static void processBaseTableUpdate(BaseTableUpdate btu){
		
		System.out.println("BTU:"+btu);
		
		Query query = queryManager.getQuery("q1");

		System.out.println(query.getQueryDAG().getBaseTables());
		
		if(queryManager.isTableTracked(btu.getBaseRecord().getTableName(), query)){
		
			List<Node> linearNodeList = GraphUtil.kahnsAlgorithm(query.getQueryDAG());
			
//			List<ViewUpdate> viewUpdates = getViewUpdates(btu.getBaseOperation().getBaseTable(), linearNodeList);
			
//			System.out.println("Affected views:"+viewUpdates);
			
//			updateViewTables(viewUpdates, btu);
				
			
		
		}

	}
	

	
//	public static void updateViewTables(List<ViewUpdate> viewUpdates, BaseTableUpdate btu){
//		
//		
////		System.out.println("nodes:"+nodes);
//		
//		for (ViewUpdate viewUpdate : viewUpdates) {
//					
//				ViewTable viewTable =viewUpdate.getViewTable();
////				
//				System.out.println("Process: "+viewUpdate.getViewOperation()+" on "+viewTable.getTableName());
//				
//				processing.process(viewTable, viewUpdate.getViewOperation(), btu);
//				
//				if(processing.isUpdateProcessFinished() || viewTable.isRowKeyChanged())break;
//					
//				
//			
//		}
//	}
	
	
}
