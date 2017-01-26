package de.tum.in.vmseval.evaluation.util;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.util.List;
import java.util.Map;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseClient {
    
    public static Configuration conf;
    public static HBaseAdmin admin;
    static {
        try {
            conf = HBaseConfiguration.create();
            admin = new HBaseAdmin(conf);
        } catch (ZooKeeperConnectionException ex) { 
        } catch (IOException ex) {
        }
    }
    
    public static void createBaseTables(Map<String, List<String>> splitsMap, String[] tables) throws IOException {
        int ctr = 0;
        for(String tableName : tables) {
            
            long start = System.currentTimeMillis();
            
            // define table name
            HTableDescriptor table = new HTableDescriptor(TableName.valueOf(tableName));
            
            // define default column family
            HColumnDescriptor family = new HColumnDescriptor(Bytes.toBytes("colfam1"));
            table.addFamily(family);
            
            // define split points
            byte[][] splitKeys = getSplitPoints(splitsMap, tableName);
            
            // creat table
            if(splitKeys.length > 0) {
                System.out.println(">> " + table + ": " + splitKeys);
                admin.createTable(table, splitKeys);    
            } else {
                admin.createTable(table);    
            }
        }
    }
    
    private static byte[][] getSplitPoints(Map<String, List<String>> splitsMap, String tableName) {
        List<String> splitPoints = splitsMap.get(tableName);
        byte[][] splitPointsBytes = new byte[splitPoints.size()][];
        int i = 0;
        for(String splitPoint : splitPoints) {
            splitPointsBytes[i++] = Bytes.toBytes(splitPoint);
        }
        
        return splitPointsBytes;
    }
    
}
