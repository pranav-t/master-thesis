package de.webdataplatform.regionserver;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.hadoop.hbase.util.Bytes;

public class SHA implements HashFunction{

	@Override
	public Integer hash(String value) {
		
		

		return Bytes.toInt(DigestUtils.sha256(Bytes.toBytes(value)));
	}


	
	

}
