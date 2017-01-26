package de.tum.in.vmseval.evaluation.driver;


import static de.tum.in.vmseval.evaluation.driver.MapReduceDriverTool.TBL_CUSTOMER;
import static de.tum.in.vmseval.evaluation.driver.MapReduceDriverTool.TBL_LINEITEM;
import static de.tum.in.vmseval.evaluation.driver.MapReduceDriverTool.TBL_NATION;
import static de.tum.in.vmseval.evaluation.driver.MapReduceDriverTool.TBL_ORDERS;
import static de.tum.in.vmseval.evaluation.driver.MapReduceDriverTool.TBL_PART;
import static de.tum.in.vmseval.evaluation.driver.MapReduceDriverTool.TBL_PARTSUPP;
import static de.tum.in.vmseval.evaluation.driver.MapReduceDriverTool.TBL_REGION;
import static de.tum.in.vmseval.evaluation.driver.MapReduceDriverTool.TBL_SUPPLIER;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.HashMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.util.Shell;
import org.apache.hadoop.util.StringUtils;

public class MapTask extends MapReduceBase implements Mapper<LongWritable, Text, LongWritable, Text> {
        static enum Counters { INPUT_PARTS }
                                
        private int tpchScale;
        private String tpchTableName;
        private Path[] dbgenScriptLocn;
        private boolean testFlag;

        private long numRecords = 0;

        @Override
        public void configure(JobConf jobConf) {
            tpchScale = jobConf.getInt("tpchscale", 1);
            tpchTableName = jobConf.get("tpchtablename");
            testFlag = jobConf.getBoolean("testflag", true);
          
            dbgenScriptLocn = new Path[0];
            try {
                dbgenScriptLocn = DistributedCache.getLocalCacheFiles(jobConf);
            } catch (IOException ioe) {
                System.err.println("Exception in getting cached dbgen script files: " + StringUtils.stringifyException(ioe));
            }
        }

//        public void map(Text key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
        public void map(LongWritable key, Text value, OutputCollector<LongWritable, Text> output, Reporter reporter) throws IOException {
            
            // [key] = byte offset of the line in the input file
            // [value] = [partNumber, totalNumberOfParts]
            
            long partNumber = Long.parseLong(value.toString().split("\\s")[0]);
            long totalParts = Long.parseLong(value.toString().split("\\s")[1]);
            
//            if(testFlag && partNumber > 1) {
//                output.collect(key, new Text("SKIPPED (in test mode)"));
//                return;
//            }
            
            if(tpchTableName.equals(TBL_NATION) || tpchTableName.equals(TBL_REGION)) {
                if(partNumber > 1) {
                    output.collect(key, new Text("SKIPPED (small tables are fully loaded in the first part)"));
                    return;
                }            
            }
            
            String dbgenTableNameArgument = "";
            switch(tpchTableName) {
                case  TBL_CUSTOMER: dbgenTableNameArgument = "c"; break;
                case  TBL_LINEITEM:  dbgenTableNameArgument = "L"; break;
                case  TBL_NATION:    dbgenTableNameArgument = "n"; break;
                case  TBL_ORDERS:    dbgenTableNameArgument = "O"; break;
                case  TBL_PART:      dbgenTableNameArgument = "P"; break;
                case  TBL_REGION:    dbgenTableNameArgument = "r"; break;
                case  TBL_SUPPLIER:  dbgenTableNameArgument = "s"; break;
                case  TBL_PARTSUPP:  dbgenTableNameArgument = "S"; break;
                default: System.err.println("table name not specified!: ");
            }
            
            long startTime = System.currentTimeMillis();
            // create file part corresponding to partNumber using dbgen utility which is available as distributed cache file
            // ./dgben -s tpchScale -S partNumber -C totalParts -T dbgenTableNameArgument
            try {
                new Shell.ShellCommandExecutor(new String[] {"mkdir","/app/hadoop/tmp/mapred/local/taskTracker/tpchTableParts"}, new File(dbgenScriptLocn[0].toUri().toString())).execute();
            } catch(Exception e) { 
                // tpchTableParts/ directory created by the first mapper so already exists error for all other mappers 
            }
            
            if(tpchTableName.equals(TBL_NATION) || tpchTableName.equals(TBL_REGION)) {
                
                new Shell.ShellCommandExecutor(new String[] {"./dbgen", "-s", tpchScale+"", "-T", dbgenTableNameArgument}, new File(dbgenScriptLocn[0].toUri().toString())).execute();
                new Shell.ShellCommandExecutor(new String[] {"mv", tpchTableName + ".tbl", "/app/hadoop/tmp/mapred/local/taskTracker/tpchTableParts"}, new File(dbgenScriptLocn[0].toUri().toString())).execute();
                long endTime = System.currentTimeMillis();            
                try {
                    this.writeTablePartToHbase(tpchTableName, "/app/hadoop/tmp/mapred/local/taskTracker/tpchTableParts/" + tpchTableName + ".tbl", partNumber, totalParts, reporter);
                    output.collect(key, new Text("SUCCESS in writing table '"+ tpchTableName + "@" + tpchScale + "x' part " + partNumber + "/"+ totalParts + " to HBase. (" + (endTime - startTime)/1000 +" seconds)"));
                    new Shell.ShellCommandExecutor(new String[] {"rm", "/app/hadoop/tmp/mapred/local/taskTracker/tpchTableParts/" + tpchTableName + ".tbl"}, new File(dbgenScriptLocn[0].toUri().toString())).execute();
                } catch(Exception e) {
                    StringBuilder st = new StringBuilder("");
                    for(StackTraceElement ste : e.getStackTrace()) {
                        st.append(ste.toString()).append(" --> ");
                    }
                    output.collect(key, new Text("ERROR in writing table '"+ tpchTableName + "@" + tpchScale + "x' part " + partNumber + "/"+ totalParts + " to HBase. Exception Message: " + st));
                }
            } else {
                new Shell.ShellCommandExecutor(new String[] {"./dbgen", "-s", tpchScale+"", "-S", partNumber+"", "-C", totalParts+"", "-T", dbgenTableNameArgument}, new File(dbgenScriptLocn[0].toUri().toString())).execute();

                new Shell.ShellCommandExecutor(new String[] {"mv", tpchTableName + ".tbl." + partNumber , "/app/hadoop/tmp/mapred/local/taskTracker/tpchTableParts"}, new File(dbgenScriptLocn[0].toUri().toString())).execute();
                long endTime = System.currentTimeMillis();            
                try {
                    this.writeTablePartToHbase(tpchTableName, "/app/hadoop/tmp/mapred/local/taskTracker/tpchTableParts/" + tpchTableName + ".tbl." + partNumber, partNumber, totalParts, reporter);
                    output.collect(key, new Text("SUCCESS in writing table '"+ tpchTableName + "@" + tpchScale + "x' part " + partNumber + "/"+ totalParts + " to HBase. (" + (endTime - startTime)/1000 +" seconds)"));
                    new Shell.ShellCommandExecutor(new String[] {"rm", "/app/hadoop/tmp/mapred/local/taskTracker/tpchTableParts/" + tpchTableName + ".tbl." + partNumber}, new File(dbgenScriptLocn[0].toUri().toString())).execute();
                } catch(Exception e) {
                    StringBuilder st = new StringBuilder("");
                    for(StackTraceElement ste : e.getStackTrace()) {
                        st.append(ste.toString()).append(" --> ");
                    }
                    output.collect(key, new Text("ERROR in writing table '"+ tpchTableName + "@" + tpchScale + "x' part " + partNumber + "/"+ totalParts + " to HBase. Exception Message: " + st));
                }
            }
            reporter.incrCounter(Counters.INPUT_PARTS, 1);

            if ((++numRecords % 100) == 0) {
                reporter.setStatus("Finished processing " + numRecords + " parts");
            }
        }
        
        private void writeTablePartToHbase(String tableName, String partFileLocn, long partNumber, long totalParts, Reporter reporter) throws IOException {
            
            BufferedReader reader = new BufferedReader(new FileReader(partFileLocn));
            long totalLinesToWrite = 0;
            while (reader.readLine() != null) totalLinesToWrite++;
            reader.close();            
            
            java.nio.file.Path path = FileSystems.getDefault().getPath(partFileLocn);
            
            Configuration conf = HBaseConfiguration.create();
            HTable hTable = new HTable(conf, tableName);

            reader =  Files.newBufferedReader(path, Charset.defaultCharset() );
            String line;
            long lineCtr = 0;
            while ((line = reader.readLine()) != null ) {
                java.util.Map<String, String> rowData = this.parseDataLine(line, tableName);
                String rowKey = rowData.get("rowkey");
                Put p = new Put(Bytes.toBytes(rowKey));
                for(String columnQualifier :rowData.keySet()) {
                    if("rowkey".equals(columnQualifier)) {
                        continue;
                    }
                    p.add(Bytes.toBytes("colfam1"), Bytes.toBytes(columnQualifier), Bytes.toBytes(rowData.get(columnQualifier)));
                    hTable.put(p);
                }
                
                if((++lineCtr % 100) == 0) {
                    reporter.setStatus("current part ("+ partNumber+") progress: " + String.format("%.2f", (float)(lineCtr)/(float)totalLinesToWrite * 100.0) + " %");
                }
            }
            
            hTable.close();
        }
        
        private java.util.Map<String, String> parseDataLine(String line, String tableName) {
            
            java.util.Map<String, String> rowData = new HashMap<>();
            String[] values = line.split("\\|");
            
            switch(tpchTableName) {
                case  TBL_CUSTOMER: 
                    // PRIMARY KEY = ("custkey")
                    rowData.put("rowkey", String.format("%012d", Integer.parseInt(values[0]))); 
                    
                    // ROW DATA
                    rowData.put("c_custkey", values[0]);
                    rowData.put("c_name", values[1]);
                    rowData.put("c_address", values[2]);
                    rowData.put("c_nationkey", values[3]);
                    rowData.put("c_phone", values[4]);
                    rowData.put("c_acctbal", values[5]);
                    rowData.put("c_mktsegment", values[6]);
                    rowData.put("c_comment", values[7]);
                    break;
                case  TBL_LINEITEM:
                    //PRIMARY KEY = ("orderkey"-"linenumber")
                    rowData.put("rowkey", String.format("%012d", Integer.parseInt(values[0])) + "-" + String.format("%012d", Integer.parseInt(values[3])));
                    
                    // ROW DATA
                    rowData.put("l_orderkey", values[0]);
                    rowData.put("l_partkey", values[1]);
                    rowData.put("l_suppkey", values[2]);
                    rowData.put("l_linenumber", values[3]);
                    rowData.put("l_quantity", values[4]);
                    rowData.put("l_extendedprice", values[5]);
                    rowData.put("l_discount", values[6]);
                    rowData.put("l_tax", values[7]);
                    rowData.put("l_returnflag", values[8]);
                    rowData.put("l_linestatus", values[9]);
                    rowData.put("l_shipdate", values[10]);
                    rowData.put("l_commitdate", values[11]);
                    rowData.put("l_receiptdate", values[12]);
                    rowData.put("l_shipinstruct", values[13]);
                    rowData.put("l_shipmode", values[14]);
                    rowData.put("l_comment", values[15]);
                    break;
                case  TBL_NATION:    
                    // PRIMARY KEY = ("nationkey")
                    rowData.put("rowkey", String.format("%012d", Integer.parseInt(values[0])));
                    
                    // ROW DATA
                    rowData.put("n_nationkey", values[0]);
                    rowData.put("n_name", values[1]);
                    rowData.put("n_regionkey", values[2]);
                    rowData.put("n_comment", values[3]);
                    
                    break;
                case  TBL_ORDERS:
                    // PRIMARY KEY = ("orderkey")
                    rowData.put("rowkey", String.format("%012d", Integer.parseInt(values[0])));
                    
                    // ROW DATA
                    rowData.put("o_orderkey", values[0]);
                    rowData.put("o_custkey", values[1]);
                    rowData.put("o_orderstatus", values[2]);
                    rowData.put("o_totalprice", values[3]);
                    rowData.put("o_orderdate", values[4]);
                    rowData.put("o_orderpriority", values[5]);
                    rowData.put("o_clerk", values[6]);
                    rowData.put("o_shippriority", values[7]);
                    rowData.put("o_comment", values[8]);
                    break;
                case  TBL_PART:
                    // PRIMARY KEY = ("partkey")
                    rowData.put("rowkey", String.format("%012d", Integer.parseInt(values[0])));
                    
                    // ROW DATA
                    rowData.put("p_partkey", values[0]);
                    rowData.put("p_name", values[1]);
                    rowData.put("p_mfgr", values[2]);
                    rowData.put("p_brand", values[3]);
                    rowData.put("p_type", values[4]);
                    rowData.put("p_size", values[5]);
                    rowData.put("p_container", values[6]);
                    rowData.put("p_retailprice", values[7]);
                    rowData.put("p_comment", values[8]);
                    break;
                case  TBL_REGION:
                    // PRIMARY KEY = ("regionkey")
                    rowData.put("rowkey", String.format("%012d", Integer.parseInt(values[0])));
                    
                    // ROW DATA
                    rowData.put("r_regionkey", values[0]);
                    rowData.put("r_name", values[1]);
                    rowData.put("r_comment", values[2]);
                    break;
                case  TBL_SUPPLIER:
                    // PRIMARY KEY = ("suppkey")
                    rowData.put("rowkey", String.format("%012d", Integer.parseInt(values[0])));
                    
                    // ROW DATA
                    rowData.put("s_suppkey", values[0]);
                    rowData.put("s_name", values[1]);
                    rowData.put("s_address", values[2]);
                    rowData.put("s_nationkey", values[3]);
                    rowData.put("s_phone", values[4]);
                    rowData.put("s_acctbal", values[5]);
                    rowData.put("s_comment", values[6]);
                    break;
                case  TBL_PARTSUPP:
                    // PRIMARY KEY = ("partkey", "suppkey")
                    rowData.put("rowkey", String.format("%012d", Integer.parseInt(values[0])) + "-" + String.format("%012d", Integer.parseInt(values[1])));
                    
                    // ROW DATA
                    rowData.put("ps_partkey", values[0]);
                    rowData.put("ps_suppkey", values[1]);
                    rowData.put("ps_availqty", values[2]);
                    rowData.put("ps_supplycost", values[3]);
                    rowData.put("ps_comment", values[4]);
                    break;
            }
            
            return rowData;
        }
        
    }