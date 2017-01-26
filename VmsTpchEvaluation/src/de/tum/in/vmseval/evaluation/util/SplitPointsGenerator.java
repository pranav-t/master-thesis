package de.tum.in.vmseval.evaluation.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class SplitPointsGenerator {

    public static Table[] TABLES = {
        //       "tablename"              size(MB)  maxKeyAt1X  doesScale?
        new Table("part",                    25,      200000,    true),
        new Table("supplier", (float)       1.5,       10000,    true),
        new Table("customer",                25,      150000,    true),
        new Table("orders",                 175,     1500000 * 4,    true),  // !!75% sparse
        
        new Table("lineitem",               760,     1500000 * 4,    true),  // <<-- |orders|   !!75% sparse
        new Table("partsupp",               120,      10000,     true),      // <<-- |supplier|
        
        new Table("nation",   (float)     0.002,          25,    false),
        new Table("region",   (float)   0.00039,           5,    false)
    };
    
    public static long MIN_REGION_SIZE = 1;                 // (MB)   (http://archive.cloudera.com/cdh5/cdh/5/hbase-0.98.1-cdh5.1.5/book/ops.capacity.html)
    public static long MEMSTORE_SIZE = 128;                 // (MB)
    public static float MEMSTORE_FACTOR = (float) 0.4;      // max 40% of JVM heap goes to memstore per region server
    public static float BLOCK_CACHE_FACTOR = (float) 0.4;   // max 40% of JVM heap goes to blockcache per region server
    
    public static double getTableSizeAt1X(String tableName) {
        for(Table table : TABLES) {
            if(tableName.equals(table.name)) {
                return table.size1X;
            }
        }
        return -1;
    }
    
    public static Map<String, List<String>> generate(int tpchScaleFactor, int nodeCount, int nodeMemory, int nodeDisk, int nodeReplication) throws IOException {
        
        Map<String, List<String>> splitPointsMap = new HashMap<>();
        
        // we don't want to use > 50% disk or memory for base table storage:
        nodeMemory = nodeMemory / 2;    
        nodeDisk = nodeDisk / 2;
        
        long totalData = 1024 * tpchScaleFactor;
        double effectiveNodeMemory = Math.min(1024 * nodeMemory * 0.9, 16.0 * 1024.0); // (JVM_HEAP <= 16 GB && <= 0.9 * nodeMemory)
        long blockCachePerNode = (long)(BLOCK_CACHE_FACTOR * effectiveNodeMemory);
        long logicalStoragePerNode = (1024 * nodeDisk) / nodeReplication;
        float dataToBeStoredPerNode = totalData / nodeCount;
        
        long regionsPerNode = (long) (0.4 * effectiveNodeMemory / MEMSTORE_SIZE);
        float regionSize = dataToBeStoredPerNode / regionsPerNode; // NOTE: HBASE DEFAULT IS 256 MB
        
        if(regionSize < MIN_REGION_SIZE) {
            // if the region size becomes too small then adjust it by decreasing the number of regions so that it doesn't go below MIN_REGION_SIZE
            regionSize = MIN_REGION_SIZE;
            regionsPerNode = (long)(dataToBeStoredPerNode / regionSize);
        }
        
        if(regionsPerNode == 0) { System.out.println(">> MIN_REGION_SIZE is too big. It is preventing all data from getting allocated."); System.exit(-1);} 
        
        float readCacheFactor = (float)Math.min((float)blockCachePerNode/(float)dataToBeStoredPerNode, 1);
        
        if(dataToBeStoredPerNode > logicalStoragePerNode) {
            System.out.println(">> Not enough Physical storage! You need to add more nodes, or decrease the replication factor, or decrease the data scale factor.");
        } else {
            System.out.println(">> regionsPerNode: " + regionsPerNode);
            System.out.println(">> regionSize: " + regionSize + " MB");
            System.out.print(">> readCache: "); System.out.printf("%.2f", readCacheFactor * 100); System.out.print(" %\n");
        }

        for(Table table : TABLES) {
            if(table.name.equals("nation") || table.name.equals("region")) { 
                // nation and region tables are too small to split
                splitPointsMap.put(table.name, new ArrayList<String>());                
                continue;
            }
            
            List<String> splitPoints = new ArrayList<>();
            long tableSize = (long) (table.size1X * (table.doesScale? tpchScaleFactor : 1));
            long numberOfRegionsForThisTable = (long)Math.ceil((tableSize/regionSize));
            numberOfRegionsForThisTable = roundUp(numberOfRegionsForThisTable, nodeCount);
            System.out.println(table.name + " :--> " + numberOfRegionsForThisTable);

            long totalKeys = table.maxKeyAt1X * (table.doesScale? tpchScaleFactor : 1);
            long keysPerRegion = (long)(totalKeys / numberOfRegionsForThisTable);
            for(long splitPoint = keysPerRegion; splitPoint < totalKeys; splitPoint += keysPerRegion) {
                if(table.name.equals("lineitem") || table.name.equals("partsupp")) {
                    splitPoints.add(String.format("%012d", splitPoint) + "-" + String.format("%012d", 1)) ;
                } else {
                    splitPoints.add(String.format("%012d", splitPoint));
                }
            }
            
            System.out.println(">> " + table.name + ": " + splitPoints);
            splitPointsMap.put(table.name, splitPoints);
        }
        
        return splitPointsMap;
    }
    
    static class Table {
        public String name;
        public float size1X;
        public long maxKeyAt1X;
        public boolean doesScale;

        public Table(String name, float size1X, long maxKeyAt1X, boolean doesScale) {
            this.name = name;
            this.size1X = size1X;
            this.maxKeyAt1X = maxKeyAt1X;
            this.doesScale = doesScale;
        }
    }

    /* round n up to nearest multiple of m */
    private static long roundUp(long n, long m) {
        return n >= 0 ? ((n + m - 1) / m) * m : (n / m) * m;
    }    
    
    public static void main(String... args) throws IOException {
        Map<String, List<String>> m = generate(1, 4, 4, 10, 1);
        System.out.println("----------");
        for(String k : m.keySet()) {
            System.out.println(k + ": " + m.get(k));//(m.get(k).size() > 0? (m.get(k).size() + 1) : 0));
        }
    }
    
}

