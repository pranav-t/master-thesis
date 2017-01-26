package de.webdataplatform.test;

import java.io.IOException;

import org.apache.hadoop.hbase.coprocessor.CoprocessorService;
import org.apache.hadoop.hbase.filter.Filter;

public interface RowCountProtocol extends CoprocessorService {
	
	long getRowCount() throws IOException;
	
	long getRowCount(Filter filter) throws IOException;
	
	long getKeyValueCount() throws IOException;
	
}