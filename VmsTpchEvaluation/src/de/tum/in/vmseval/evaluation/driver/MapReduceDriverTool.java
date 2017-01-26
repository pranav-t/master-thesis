package de.tum.in.vmseval.evaluation.driver;

import de.tum.in.vmseval.evaluation.util.SplitPointsGenerator;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.hadoop.conf.Configuration;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.mapred.lib.NLineInputFormat;
import org.apache.hadoop.util.*;

public class MapReduceDriverTool extends Configured implements Tool {

    public static final String TBL_CUSTOMER = "customer";
    public static final String TBL_LINEITEM = "lineitem";
    public static final String TBL_NATION = "nation";
    public static final String TBL_ORDERS = "orders";
    public static final String TBL_PART = "part";
    public static final String TBL_REGION = "region";
    public static final String TBL_SUPPLIER = "supplier";
    public static final String TBL_PARTSUPP = "partsupp";
    
//    public static final String[] TABLES = {TBL_CUSTOMER, TBL_LINEITEM, TBL_NATION, TBL_ORDERS, TBL_PART, TBL_REGION, TBL_SUPPLIER, TBL_PARTSUPP};
//    public static final String[] TABLES = {TBL_LINEITEM};
//    public static final String[] TABLES = {TBL_ORDERS};
    public static final String[] TABLES = {TBL_PART};
    
    public static Map<String, Long> NUMBER_OF_PARTS = new HashMap<>();
    
    static AtomicInteger LOADED_CTR = new AtomicInteger(0);
    
    @Override
    public int run(final String[] args) throws Exception {
        
        // generate and add mapreduce input files to the mapreduce input directory in hdfs
        // the input files define how many parts a given table is to be broken into for loading
        generateMRInputFiles(args);
        
        // load tables in parallel with one MR job per table
        for(final String table : TABLES) {
            new Thread() {
                public void run() {
                    try {        
                        JobClient.runJob(createNewConfForLoadJob(args, table)).waitForCompletion();
                        LOADED_CTR.incrementAndGet();
                    } catch (IOException ex) { ex.printStackTrace(); }
                }
            }.start();        
        }
        
        while(LOADED_CTR.get() < TABLES.length) {
            // wait for all tables to get loaded
        }
        return 0;
    }
    
    public JobConf createNewConfForLoadJob(final String[] args, final String tableName) {
        JobConf conf = new JobConf(getConf(), MapReduceDriverTool.class);
        conf.setJobName("vms-load-" + tableName);

        conf.setMapperClass(MapTask.class);
        conf.setNumMapTasks(12);  // the number of mappers defines how many parts are to be loaded in parallel for each table
        conf.setNumReduceTasks(0);

//        conf.setInputFormat(KeyValueTextInputFormat.class);
        conf.setInputFormat(NLineInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        for (int i=0; i < args.length; ++i) {
            if(null != args[i]) switch (args[i]) {
                case "-inputlocation":
                    FileInputFormat.setInputPaths(conf, new Path(args[++i] + "/" + tableName));
                    break;
                case "-outputlocation":
                    FileOutputFormat.setOutputPath(conf, new Path(args[++i]+ "/" + tableName));
                    break;
                case "-dbgenscriptlocation":
                    DistributedCache.addCacheFile(new Path(args[++i]).toUri(), conf);
                    break;
                case "-tpchscale":
                    conf.setInt("tpchscale", Integer.parseInt(args[++i]));
                    break;
                case "-testflag":
                    conf.setBoolean("testflag", args[++i].equals("1"));
                    break;
                case "-mapcount":
                    conf.setNumMapTasks(Integer.parseInt(args[++i]));
            }
        }
        
        conf.setLong("mapred.line.input.format.linespermap", NUMBER_OF_PARTS.get(tableName)/conf.getNumMapTasks());  
        conf.set("tpchtablename", tableName);    
        
        return conf;
    }

    private void generateMRInputFiles(String[] args) throws IOException {

        int tpchScaleFactor = 1;
        boolean testFlag = true;
        String loadType = "uniform";
        int mapCount = 1;
        double partSize = 0.1;
        for (int i=0; i < args.length; ++i) {
            if(null != args[i]) switch (args[i]) {
                case "-tpchscale":
                    tpchScaleFactor = Integer.parseInt(args[++i]);
                    break;
                case "-testflag":
                    testFlag = args[++i].equals("1");
                    break;
                case "-loadtype": 
                    loadType = args[++i];
                    break;
                case "-mapcount": 
                    mapCount = Integer.parseInt(args[++i]);
                    break;
                case "-partsize": 
                    partSize = Double.parseDouble(args[++i]);
                    break;
            }
        }
        
        FileSystem fs = FileSystem.get(new Configuration());
        fs.delete(new Path("hdfs://evaluation-driver-1:54310/user/ubuntu/vmsevaluation/input"), true);
        fs.delete(new Path("hdfs://evaluation-driver-1:54310/user/ubuntu/vmsevaluation/output"), true);
        for(String tableName : TABLES) {
            double tableSizeAt1X = SplitPointsGenerator.getTableSizeAt1X(tableName);
            double totalTableSize = tableSizeAt1X * tpchScaleFactor;
            long numberOfParts = (long)(totalTableSize / (testFlag? 0.1 : partSize)); // each part is 10 MB. 1 MB in test mode
            numberOfParts = numberOfParts <= 1 ? 2 : numberOfParts;             // break table into at least 2 parts
            
            numberOfParts = roundUp(numberOfParts, mapCount); // number of input splits = number of mappers(by design)
            
            if(tableName.equals(TBL_REGION) || tableName.equals(TBL_NATION)) {
                numberOfParts = 1; // these two tables are too small and can be loaded in a single part
            }
            
            OutputStreamWriter out = new OutputStreamWriter(fs.create(new Path("hdfs://evaluation-driver-1:54310/user/ubuntu/vmsevaluation/input/"+ tableName +"/input.txt")));
            BufferedWriter bw = new BufferedWriter(out);
            if(loadType.equals("uniform")) {
                for(long i = 1; i <= numberOfParts; i++) {
                    bw.write(i + "\t" + numberOfParts+"\n");
                }
            } else { // "hotspotted"
                for(long inputSplit = 1; inputSplit <= mapCount; inputSplit++) {
                    for(long partNumber = inputSplit; partNumber <= numberOfParts; partNumber += mapCount) {
                        bw.write(partNumber + "\t" + numberOfParts+"\n");
                    }
                }
            }
            bw.flush();
            bw.close();
            
            NUMBER_OF_PARTS.put(tableName, numberOfParts);
        }
        fs.close();
    }
    
    /* round n up to nearest multiple of m */
    private static long roundUp(long n, long m) {
        return n >= 0 ? ((n + m - 1) / m) * m : (n / m) * m;
    }    
    
}





