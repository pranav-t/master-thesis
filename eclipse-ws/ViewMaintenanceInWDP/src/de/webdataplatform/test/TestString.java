package de.webdataplatform.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestString {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String sample="asdfasdfsadfsadff.asdfasdfsadfas,25234234.fsdfsd";
		
		long start = System.nanoTime();
		List<String> result = splitString(sample,".");
		System.out.println("split-time: "+(System.nanoTime()-start));
		System.out.println(result);
		
		long start0 = System.nanoTime();
		String[] result0 = sample.split("\\.");
		System.out.println("split-time: "+(System.nanoTime()-start0));
		System.out.println(Arrays.asList(result0));
	}

	
	public static List<String> splitString(String sample, String splitter){
		
		List<String> list = new ArrayList<String>();
        int pos = 0, end;
        while ((end = sample.indexOf(splitter, pos)) >= 0) {
        	list.add(sample.substring(pos, end));
            pos = end + 1;
        }
        if(pos != sample.length())list.add(sample.substring(pos, sample.length()));
		return list;
	}
	
	
}
