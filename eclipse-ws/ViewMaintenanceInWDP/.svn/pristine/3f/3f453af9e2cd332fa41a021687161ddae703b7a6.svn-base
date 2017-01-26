package de.webdataplatform.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.webdataplatform.graph.CostModel;
import de.webdataplatform.graph.Graph;
import de.webdataplatform.graph.MaintenancePlanUtil;
import de.webdataplatform.graph.Node;
import de.webdataplatform.graph.OperationNode;
import de.webdataplatform.log.Log;
import de.webdataplatform.query.Cluster;
import de.webdataplatform.query.Query;
import de.webdataplatform.query.ViewExpression;
import de.webdataplatform.settings.NetworkConfig;
import de.webdataplatform.settings.SystemConfig;
import de.webdataplatform.settings.TableConfig;

public class Main {


	
	
	
	public static void main(String[] args) {
		
		
		Log log = new Log("TestParser");

		SystemConfig.load(log);
		
		NetworkConfig.load(log);
		
		TableConfig.load(log);
		
//		Parser parser = new Parser(log);
//		
//		Planner planner = new Planner(log);
//		select
//		l_returnflag,
//		l_linestatus,
//		sum(l_quantity) as sum_qty,
//		sum(l_extendedprice) as sum_base_price,
//		sum(l_extendedprice * (1 - l_discount)) as sum_disc_price,
//		sum(l_extendedprice * (1 - l_discount) * (1 + l_tax)) as sum_charge,
//		avg(l_quantity) as avg_qty,
//		avg(l_extendedprice) as avg_price,
//		avg(l_discount) as avg_disc,
//		count(*) as count_order
//	from
//		lineitem
//	where
//		l_shipdate <= date '1998-12-01' - interval ':1' day (3)
//	group by
//		l_returnflag,
//		l_linestatus
//	order by
//		l_returnflag,
//		l_linestatus;
//	:n -1
		
		String query0 = "select bt1.c1 from bt1";
		
		String query1 = "select bt1.c1 from bt1 where c2 < 20";

		String query2 = "select bt1.c1, sum(bt1.c3) as sum1, count(bt1.c3) as count1 from bt1 pre group by bt1.c1, bt1.c2";
		
		String query3 = "select bt1.c1, bt2.d2 from A bt1, bt2 where bt1.c1 = bt2.d1";
		
		
		String query4 = "select orders.o_custkey from orders where orders.o_totalprice < 100000.0";
		
		String query5 = "select count(orders.o_totalprice) as count1, sum(orders.o_totalprice) as sum1 from orders pre group by orders.o_custkey";
		
		String query6 = "select customer.c_custkey, customer.c_address, orders.o_totalprice from orders, customer where customer.c_custkey = orders.o_custkey";

		String query7 = "select lineitem.l_extendedprice * lineitem.l_discount as revenue from lineitem where lineitem.l_shipdate <= 1996-03-13 and lineitem.l_discount between 0.01 and 0.5 and lineitem.l_quantity < 40";
		
		String query8 = "select lineitem.l_returnflag, lineitem.l_linestatus, sum(lineitem.l_quantity) as sum_qty, sum(lineitem.l_extendedprice) as sum_base_price, sum(lineitem.l_extendedprice * (1 - lineitem.l_discount)) as sum_disc_price, sum(lineitem.l_extendedprice * (1 - lineitem.l_discount) * (1 + lineitem.l_tax)) as sum_charge, avg(lineitem.l_quantity) as avg_qty, avg(lineitem.l_extendedprice) as avg_price, avg(lineitem.l_discount) as avg_disc, count(lineitem.l_discount) as count_order from lineitem where lineitem.l_shipdate <= 1998-12-01 pre group by lineitem.l_returnflag, lineitem.l_linestatus";
		
		
		//		String query3 = "select A.* from A where A.c1 = 10";
		
//		String query4 = "select C.* from C where C.c1 = 10";
//		String query2 = "select D.c1, sum(D.c2) as sum1, count(D.c2) as count1, index(D.c2) as index1 from D pre group by D.c1 where D.c1 < 10 and D.c2 > 20 having sum1 > 20";

//		String query3 = "select bt1.c1, sum(bt1.c2) as sum1 from bt1 pre group by bt1.c1 where bt1.c2 < 20";
//		String query4 = "select sum(A.c2) as sum1 from A pre group by A.c1 where A.c2 < 20";
//		String query5 = "select A.c1 from A where A.c2 < 20";
		
//		String query6 = "select bt1.c1, sum(bt1.c2) as sum1, count(bt1.c2) as count1 from bt1 pre group by bt1.c1";
//		String query5 = "select bt1.c1, sum(bt1.c2) from bt1 group by bt1.c1 where bt1.c2 < 50 having sum(bt1.c2) < 10";
		
		
//		String query5 = "select "+
//		"lineitem.l_extendedprice * lineitem.l_discount as revenue "+
//		"from "+
//		"lineitem "+
//		"where "+
//		"lineitem.l_shipdate <= 1996-03-13 "+
////		"and l_shipdate < date ':1' + interval '1' year "+
//		"and lineitem.l_discount between 0.01 and 0.5 "+
//		"and lineitem.l_quantity < 40";
		
//		String query6 = "select lineitem.l_returnflag, lineitem.l_linestatus, "
//		+"sum(lineitem.l_quantity) as sum_qty, "
//		+"sum(lineitem.l_extendedprice) as sum_base_price, "
//		+"sum(lineitem.l_extendedprice * (1 - lineitem.l_discount)) as sum_disc_price, "
//		+"sum(lineitem.l_extendedprice * (1 - lineitem.l_discount) * (1 + lineitem.l_tax)) as sum_charge, "
//		+"avg(lineitem.l_quantity) as avg_qty, "
//		+"avg(lineitem.l_extendedprice) as avg_price, "
//		+"avg(lineitem.l_discount) as avg_disc, "
//		+"count(lineitem.l_discount) as count_order "
//		+"from lineitem "
////		+"where lineitem.shipdate <= date '1998-12-01' - interval '90' day (3) "
//		+"where lineitem.l_shipdate <= 1998-12-01 "
//		+"group by lineitem.l_returnflag, lineitem.l_linestatus "
//	    +"order by lineitem.l_returnflag, lineitem.l_linestatus ";

		
//		String query7 = "select lineitem.orderkey,"
//		+" sum(lineitem.extendedprice * (1 - lineitem.discount)) as revenue,"
//		+" orders.orderdate,"
//		+" orders.shippriority"
//		+" from customer, orders, lineitem"
//		+" where "
//		+" customer.custkey = orders.custkey"
//		+" and lineitem.orderkey = orders.orderkey"
//		+" and customer.mktsegment = ':1'"
//		+" and orders.orderdate < 1995-03-13"
//		+" and lineitem.shipdate > 1995-03-13"
//		+" group by lineitem.orderkey, orders.orderdate, orders.shippriority"
//		+" order by xy.revenue desc, orders.orderdate";
		
//		String query8 ="select all_nations.year, "
////		+"sum(case when nation = ':1' then volume else 0 end) / " +
//		+"sum(all_nations.volume) as mkt_share "
//		+"from "
//		+"( "
//		+"	select "
//		+"		orders.orderdate as o_year, "
//		+"		lineitem.extendedprice * (1 - lineitem.discount) as volume, "
//		+"		n2.name as nation "
//		+"	from "
//		+"		part, supplier, lineitem, orders, customer, nation as n1, nation as n2, region "
//		+"	where "
//		+"		part.partkey = lineitem.partkey "
//		+"		and supplier.suppkey = lineitem.suppkey "
//		+"		and lineitem.orderkey = orders.orderkey "
//		+"		and orders.custkey = customer.custkey "
//		+"		and customer.nationkey = n1.nationkey "
//		+"		and n1.regionkey = region.regionkey "
//		+"		and region.name = ':2' "
//		+"		and supplier.nationkey = n2.nationkey "
//		+"		and orders.orderdate between '1995-01-01' and '1996-12-31' "
//		+"		and part.type = ':3' "
//		+") as all_nations "
//		+"group by all_nations.year "
//		+"order by all_nations.year; ";
		
//		String query9="select "+
//		"orders.orderdate as year, lineitem.extendedprice * (1 - lineitem.discount) as volume, n2.name as nation "
//		+"from part, supplier, lineitem, orders, customer, nation as n1, nation as n2, region "
//		+"where part.partkey = lineitem.partkey "
//		+"and supplier.suppkey = lineitem.suppkey "
//		+"and lineitem.orderkey = orders.orderkey "
////		+"and orders.custkey = customer.custkey "
////		+"and customer.nationkey = n1.nationkey "
////		+"and n1.regionkey = region.regionkey "
//		+"and region.name = 'ITALY' "
//		+"and supplier.nationkey = n2.nationkey "
//		+"and orders.orderdate between '1995-01-01' and '1996-12-31' "
//		+"and part.type = 'MED' ";
		
//		String query10 ="select customer.custkey, customer.c_name, sum(lineitem.extendedprice * (1 - lineitem.discount)) as revenue, "
//		+"customer.acctbal, nation.name, customer.address, customer.phone, customer.comment "
//		+"from customer, orders, lineitem, nation "
//		+"where "
//		+"customer.custkey = orders.custkey "
//		+"and lineitem.orderkey = orders.orderkey "
//		+"and orders.orderdate >= ':1' "
//		+"and orders.orderdate < ':1' "
//		+"and lineitem.returnflag = 'R' "
//		+"and customer.nationkey = nation.nationkey "
//		+"group by customer.custkey, customer.name, customer.acctbal, customer.phone, nation.name, "
//		+"customer.address, customer.comment ";
//		+"order by revenue desc;";
		
//		String query35 ="select sum(s_acctbal), s_name, n_name, p_partkey, p_mfgr, s_address, s_phone, s_comment "
//		+"from part, supplier, partsupp, nation, region "
//		+"where p_partkey = ps_partkey "
//		+"and s_suppkey = ps_suppkey "
//		+"and p_size = 15 "
//		+"and p_type like '%BRASS' "
//		+"and s_nationkey = n_nationkey "
//		+"and n_regionkey = r_regionkey "
//		+"and r_name = 'EUROPE' "
//		+"and ps_supplycost = (select min(ps_supplycost) "
//		+"	from partsupp, supplier, nation, region "
//		+"	where p_partkey = ps_partkey "
//		+"		and s_suppkey = ps_suppkey "
//		+"		and s_nationkey = n_nationkey "
//		+"		and n_regionkey = r_regionkey "
//		+"		and r_name = 'EUROPE' "
//		+") "
//		+"order by s_acctbal desc, n_name, s_name, p_partkey; ";

		
		
//		String query17="select sum(lineitem.extendedprice * (1 - lineitem.discount)) as revenue "
//		+"from lineitem, part "
//		+"where "
//		+"( "
//		+"	part.partkey = lineitem.partkey "
//		+"	and part.brand = ':1' "
//		+"	and part.container in ('SM CASE', 'SM BOX', 'SM PACK', 'SM PKG') "
//		+"	and lineitem.quantity >= 4 and lineitem.quantity <= 14 "
//		+"	and part.size between 1 and 5 "
//		+"	and lineitem.shipmode in ('AIR', 'AIR REG') "
//		+"	and lineitem.shipinstruct = 'DELIVER IN PERSON' "
//		+") "
//		+"or "
//		+"( "
//		+"	part.partkey = lineitem.partkey "
//		+"	and part.brand = ':2' "
//		+"	and part.container in ('MED BAG', 'MED BOX', 'MED PKG', 'MED PACK') "
//		+"	and lineitem.quantity >= 5 and lineitem.quantity <= 15 "
//		+"	and part.size between 1 and 10 "
//		+"	and lineitem.shipmode in ('AIR', 'AIR REG') "
//		+"	and lineitem.shipinstruct = 'DELIVER IN PERSON' "
//		+") "
//		+"or "
//		+"( "
//		+"	part.partkey = lineitem.partkey "
//		+"	and part.brand = ':3' "
//		+"	and part.container in ('LG CASE', 'LG BOX', 'LG PACK', 'LG PKG') "
//		+"	and lineitem.quantity >= 6 and lineitem.quantity <= 16 "
//		+"	and part.size between 1 and 15 "
//		+"	and lineitem.shipmode in ('AIR', 'AIR REG') "
//		+"	and lineitem.shipinstruct = 'DELIVER IN PERSON' "
//		+"); ";
		
		
//		String query8 = "select * from A inner join B on A.c1 = B.c2";
//		String query9 = "select A.* from A inner join B on A.c1 = B.c1 inner join C on B.c2 = C.c2 inner join D on B.c1 = D.c2 where A.c2 = 20 and B.c1 < 10 and C.c3 > 20";
//		String query10 = "select * from A inner join B on A.c1 = B.c1 inner join C on B.c2 = C.c1 inner join D on C.c2 = D.c2 where A.c1 < 19";
//		String query11 = "select * from A inner join B on A.c1 = B.c2 inner join C on A.c1 = C.c2";
//		String query12 = "select * from A inner join B on A.c1 = B.c2 inner join C on A.c2 = C.c2 inner join D on A.c1 = D.c2";
//		String query13 = "select * from A inner join B on A.c1 = B.c2 inner join C on A.c2 = C.c2 inner join D on A.c3 = D.c2";
//		String query14 = "select * from A inner join B on A.c1 = B.c2 inner join C on B.c1 = C.c2";
//		String query15 = "select * from bt1, bt2 where bt1.c1 = bt2.d1";
//		String query16 = "select sum(bt1.c3) as sum1, max(bt1.c3) as max1 from bt1, bt2 where bt1.c1 = bt2.d1 pre group by bt2.d2";
//		String query50 = "select sum(bt1.c3) as sum1, max(bt1.c3) as max1 from bt1 where bt1.c3 > 9 pre group by bt1.c1 having bt1.sum1 > 20";
				
		List<String> queryStrings = new ArrayList<String>();
		
		
		
		
		
		queryStrings.add(query6);
//		queryStrings.add(query1);
//		queryStrings.add(query2);
		
		
//		queryStrings.add(query3);
//		queryStrings.add(query4);
//		queryStrings.add(query5);
		
		
		
//		queryStrings.add(query6);
//		queryStrings.add(query7);
//		queryStrings.add(query8);
//		queryStrings.add(query9);
//		queryStrings.add(query10);
//		queryStrings.add(query11);
//		queryStrings.add(query12);
//		queryStrings.add(query13);
//		queryStrings.add(query14);
//		queryStrings.add(query15);
//		queryStrings.add(query16);
//		queryStrings.add(query17);
		
		
		List<Query> queries = new ArrayList<Query>();
		List<Graph> queryDAGs = new ArrayList<Graph>();
		
		int i = 1;
		for (String string : queryStrings) {
			
			System.out.println("--------------------");
			System.out.println("Parse query: q"+i);
			System.out.println("--------------------");	

			Parser.log = log;
			Planner.log = log;
			
			
			SQL sql = Parser.parse("q"+i, string);
			
			Planner.initialize("q"+i);
			Planner.plan("q"+i, sql);
			Graph dag = Planner.getQueryDAG();
			
			System.out.println(dag);
			
			
			Query query = new Query("q"+i, dag);
			
		    System.out.println("--------------------");
			System.out.println("Query DAG: q"+i);
			System.out.println("--------------------");	
			System.out.println(query.getQueryDAG());
			//System.out.println("Topological order:"+ query.getQueryDAG().kahnsAlgorithm());
//			queries.add(query);
//			queryDAGs.add(query.getQueryDAG());
			
			System.out.println("tracked tables:"+query.getQueryDAG().getTrackedTables());
			
//			computeCost(query.getQueryDAG());*/
		
			i++;
			
			
//			verticalOptimization(query.getQueryDAG());
			
			
		}

		
/**

		Graph graph = GraphUtil.mergeGraphs(queryDAGs);
		System.out.println("num of view: "+graph.getViewTables().size());
		
//		computeCost(graph);

		horizontalOptimization(graph);

//		computeCost(graph);

//		System.out.println(graph);*/
		
		
		
		
		

	
	}
	
	public static void verticalOptimization(Graph graph){
		
		System.out.println("--------------------");
		System.out.println("Get zones");
		System.out.println("--------------------");		
		
//		queryDAGs.add(query.getQueryDAG());
		
		Map<String, List<Node>> zones = graph.getZones();
		
//		System.out.println(zones);
		
		printZones(zones);

		
		
		
		System.out.println("--------------------");
		System.out.println("Optimize zones: ");
		System.out.println("--------------------");		
		
		
		for (String zoneName : zones.keySet()) {
			
			if(zones.get(zoneName).get(0) instanceof OperationNode)
				graph.optimizeZone(zones.get(zoneName));
			
		}
		System.out.println(graph);

		
	}
	
	public static void horizontalOptimization(Graph graph){


		
		System.out.println("--------------------");
		System.out.println("Equal operations");
		System.out.println("--------------------");
		
		List<Cluster>  viewClusters =  MaintenancePlanUtil.findViewClusters(graph, Node.OPERATION_NODE, ViewExpression.EQUAL);
		
		for (Cluster viewCluster : MaintenancePlanUtil.findMatchings(viewClusters)) {
			
			System.out.println(viewCluster);
			
			
			MaintenancePlanUtil.mergeViewCluster(graph, viewCluster, ViewExpression.EQUAL);
			 System.out.println("----");
		}
		
		System.out.println("--------------------");
		System.out.println("Equal tables");
		System.out.println("--------------------");

		viewClusters =  MaintenancePlanUtil.findViewClusters(graph, Node.TABLE_NODE, ViewExpression.EQUAL);
		
		for (Cluster viewCluster : MaintenancePlanUtil.findMatchings(viewClusters)) {
			
			System.out.println(viewCluster);
			System.out.println();
			
			
			MaintenancePlanUtil.mergeViewCluster(graph, viewCluster, ViewExpression.EQUAL);
			 System.out.println("----");
			 
		}
		
		 
		
		System.out.println("--------------------");
		System.out.println("Similar operations");
		System.out.println("--------------------");
		
		viewClusters =  MaintenancePlanUtil.findViewClusters(graph, Node.OPERATION_NODE, ViewExpression.SIMILAR);
		
		for (Cluster viewCluster : MaintenancePlanUtil.findMatchings(viewClusters)) {
			
			System.out.println(viewCluster);
			System.out.println();
			
			
			MaintenancePlanUtil.mergeViewCluster(graph, viewCluster, ViewExpression.SIMILAR);
			 
		}


		
		System.out.println("--------------------");
		System.out.println("Modified graph");
		System.out.println("--------------------");			 
		System.out.println(graph);
		System.out.println("");
		System.out.println("num of view: "+graph.getViewTables().size());
		

		
	}
	
	public static void computeCost(Graph graph){
		
		
		System.out.println("--------------------------");
		System.out.println("Compute cost");
		System.out.println("--------------------------");		

		//Define topological order
//		List<Node> topologicalOrder = graph.kahnsAlgorithm();
//		System.out.println("Topological order:"+ graph.getTableNodes(topologicalOrder));
		
		//Define input rates
		Map<String, Integer> inputRates = new HashMap<String, Integer>();
		inputRates.put("A", 100);
		inputRates.put("B", 300);
		inputRates.put("C", 100);
		inputRates.put("D", 100);
		
		
//		Map<String, Integer> range = new HashMap<String, Integer>();
//		inputRates.put("A", 100);
//		inputRates.put("B", 300);
//		inputRates.put("C", 100);
//		inputRates.put("D", 100);
//		
//		
//		Map<String, Integer> fillingDegree = new HashMap<String, Integer>();
//		inputRates.put("A", 100);
//		inputRates.put("B", 300);
//		inputRates.put("C", 100);
//		inputRates.put("D", 100);		
		
		//Compute flow
		
		CostModel.computeFlow(graph, inputRates);
		
		
		System.out.println("Total cost:"+graph.getCost());
		
		System.out.println("---------------");
		System.out.println("Number of views:"+graph.getViewTables().size());
		System.out.println();
		
		
	}
	
	
	
	public static void printZones(Map<String, List<Node>> zones){
		int x=0;
		List<Node> zone = null;
		
		while(true){
			
			zone = zones.get("zone"+x);
			if(zone != null){
				System.out.println("zone"+x+": "+zone);
				x++;
			}else break;
			
		}
		
	}

	
	
	
	

}
