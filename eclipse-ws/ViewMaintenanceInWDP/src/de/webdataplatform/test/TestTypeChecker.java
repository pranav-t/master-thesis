package de.webdataplatform.test;

import java.util.ArrayList;
import java.util.List;

import de.webdataplatform.system.TypeChecker;

public class TestTypeChecker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		
		List<String> vals = new ArrayList<String>();
		vals.add("12390.0");
		vals.add("12390");
		vals.add("sonstwas");
		vals.add("1954-20-12");
		
		
		for (String val : vals) {
			System.out.println("isInteger: "+TypeChecker.isInteger(val));
			System.out.println("isFloat: "+TypeChecker.isFloat(val));
			System.out.println("isDate: "+TypeChecker.isDate(val));	
			System.out.println("-------");
			
		}
		
		
		

	}

}
