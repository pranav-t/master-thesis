package de.tum.in.vmseval.main;

import java.util.Map;
import java.util.List;

import de.tum.in.vmseval.evaluation.util.HbaseClient;
import de.tum.in.vmseval.evaluation.util.SplitPointsGenerator;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.ToolRunner;

public class Main {

    static int tpchScaleFactor = 1;
    static int nodeCount = 3;
    static int nodeMemory = 2;
    static int nodeDisk = 10;
    static int nodeReplication = 3;
    static String loadType = "uniform";
    
    /*
        Note: hdfs, mr, hbase, vmsmaster, and vms must have been started beforehand AND in this order
    */
    public static void main(String[] args) throws Exception {

//        long start = System.currentTimeMillis();
//        System.out.print(">> 0. Start VMS-Master");
//        LocalShellService.runCommand("java -cp vmsystem.jar de.webdataplatform.test.VMS startmaster");
//        System.out.println("\t\t\t[" + ((float)(System.currentTimeMillis() - start))/ 1000.0 + "s]\t[OK]");
        
        // process command line arguments
        processCLAs(args);
        
        
        // generate base table split points based on the cluster info provided via args
        long start = System.currentTimeMillis();
        Map<String, List<String>> splitsMap = SplitPointsGenerator.generate(tpchScaleFactor, nodeCount, nodeMemory, nodeDisk, nodeReplication);
        
        System.out.println(">> 1. Generate split points\t\t[" + ((float)(System.currentTimeMillis() - start))/ 1000.0 + "s]\t[OK]");
        
        //create base tables using this split points info
        start = System.currentTimeMillis();
        System.out.print(">> 2. Create base tables");
        HbaseClient.createBaseTables(splitsMap, de.tum.in.vmseval.evaluation.driver.MapReduceDriverTool.TABLES);
        System.out.println("\t\t[" + ((float)(System.currentTimeMillis() - start))/ 1000.0 + "s]\t\t[OK]");
        
        // add all queries to vms
//        start = System.currentTimeMillis();
//        List<Boolean> addQueryStatuses = new ArrayList<>();
////        addQueryStatuses.add(VMSClient.addQuery("Q1Ã¾select orders.o_custkey from orders where orders.o_totalprice < 100000.0").equals("queryAdded"));
////        addQueryStatuses.add(VMSClient.addQuery("Q2Ã¾select count(orders.o_totalprice) as count1, sum(orders.o_totalprice) as sum1 from orders pre group by orders.o_custkey").equals("queryAdded"));
////        addQueryStatuses.add(VMSClient.addQuery("Q3Ã¾select customer.c_custkey, customer.c_address, orders.o_totalprice from orders, customer where customer.c_custkey = orders.o_custkey").equals("queryAdded"));
////        addQueryStatuses.add(VMSClient.addQuery("Q4Ã¾select lineitem.l_extendedprice * lineitem.l_discount as revenue from lineitem where lineitem.l_shipdate <= 1996-03-13 and lineitem.l_discount between 0.01 and 0.5 and lineitem.l_quantity < 40").equals("queryAdded"));
//        addQueryStatuses.add(VMSClient.addQuery("Q5Ã¾select lineitem.l_returnflag, lineitem.l_linestatus, sum(lineitem.l_quantity) as sum_qty, sum(lineitem.l_extendedprice) as sum_base_price, sum(lineitem.l_extendedprice * (1 - lineitem.l_discount)) as sum_disc_price, sum(lineitem.l_extendedprice * (1 - lineitem.l_discount) * (1 + lineitem.l_tax)) as sum_charge, avg(lineitem.l_quantity) as avg_qty, avg(lineitem.l_extendedprice) as avg_price, avg(lineitem.l_discount) as avg_disc, count(lineitem.l_discount) as count_order from lineitem where lineitem.l_shipdate <= 1998-12-01 pre group by lineitem.l_returnflag, lineitem.l_linestatus").equals("queryAdded"));
//        System.out.println(">> 3. Add queries\t\t\t[" + ((float)(System.currentTimeMillis() - start))/ 1000.0 + "s]\t\t"+addQueryStatuses);

//        System.out.print(">> 4. Start VMS");
//        LocalShellService.runCommand("java -cp vmsystem.jar de.webdataplatform.test.VMS start");
//        System.out.println("\t\t\t\t[" + ((float)(System.currentTimeMillis() - start))/ 1000.0 + "s]\t[OK]");
        
        // load all base tables in parallel for the input tpchScaleFactor
        start = System.currentTimeMillis();
        System.out.print(">> 5. Load base tables");
        ToolRunner.run(new Configuration(), new de.tum.in.vmseval.evaluation.driver.MapReduceDriverTool(), args);
        System.out.println("\t\t\t[" + ((float)(System.currentTimeMillis() - start))/ 1000.0 + "s]\t[OK]");
        
//        // make call to vms master for status
//        start = System.currentTimeMillis();
//        System.out.print(">> 6. Wait for VMs to finish");
//        String throughputSummaryLast = "";
//        int ctr = 0;
//        while(true) {
//            String throughputSummary = VMSClient.getThroughputSummary();
//            if(throughputSummary.equals(throughputSummaryLast) && ctr > 3) {
//                // if master reports the same status summary 4 times it means VMs have finished processing
//                break;
//            } else {
//                throughputSummaryLast = throughputSummary;
//                Thread.sleep(2000);
//                ctr++;
//            }
//        }
//        System.out.println("\t\t[" + ((float)(System.currentTimeMillis() - start))/ 1000.0 + "s]\t[OK]");
//        
//        // plot status on graph
//        start = System.currentTimeMillis();
//        String[] summaryParts = BarChart.plotThroughputSummary(tpchScaleFactor, throughputSummaryLast);
//        System.out.println(">> 7. Plotted status summary\t\t[" + ((float)(System.currentTimeMillis() - start))/ 1000.0 + "s]\t[OK]");
//        System.out.println("----------**------------");
//        System.out.println("Status Summary" + Arrays.toString(summaryParts));
//        System.out.println("----------**------------");
    }
    
    private static void processCLAs(String[] args) {
        for (int i=0; i < args.length; ++i) {
            if(null != args[i]) switch (args[i]) {
                case "-tpchscalefactor":
                    tpchScaleFactor = Integer.parseInt(args[++i]);
                    break;
                case "-nodecount":
                    nodeCount = Integer.parseInt(args[++i]);
                    break;
                case "-nodememory":
                    nodeMemory = Integer.parseInt(args[++i]);
                    break;
                case "-nodedisk":
                    nodeDisk = Integer.parseInt(args[++i]);
                    break;
                case "-nodereplication":
                    nodeReplication = Integer.parseInt(args[++i]);
                    break;
                case "-loadtype":
                    loadType = args[++i];
                    break;
            }
        }
    }
}
