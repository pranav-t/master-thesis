package de.webdataplatform.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {

	public static List<String> splitString(String string, String splitter){
		
		List<String> list = new ArrayList<String>();
        int pos = 0, end;
        while ((end = string.indexOf(splitter, pos)) >= 0) {
        	list.add(string.substring(pos, end));
            pos = end + 1;
        }
        if(pos != string.length())list.add(string.substring(pos, string.length()));
		return list;
	}

	public static ArrayList<String> splitBySingleChar(final char[] s,
	        final char splitChar) {
	    final ArrayList<String> result = new ArrayList<String>();
	    final int length = s.length;
	    int offset = 0;
	    int count = 0;
	    for (int i = 0; i < length; i++) {
	        if (s[i] == splitChar) {
	            if (count > 0) {
	                result.add(new String(s, offset, count));
	            }
	            offset = i + 1;
	            count = 0;
	        } else {
	            count++;
	        }
	    }
	    if (count > 0) {
	        result.add(new String(s, offset, count));
	    }
	    return result;
	}

}
