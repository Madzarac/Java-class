package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Tester;

/**
 * implementation of tester interface
 * @author TeaMadzarac
 * @version 22/10/2022
 */
public class EvenIntegerTester implements Tester {
	
	/**
	 * Tests if given object is even integer
	 */
	public boolean test(Object obj) {
		 if(!(obj instanceof Integer)) return false;
		 Integer i = (Integer)obj;
		 return i % 2 == 0;
		 }

}
