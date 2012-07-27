package org.arnab.cubist;

import java.util.HashMap;


/**
 * Ways to collect the materialized cube.
 * 
 */
public interface ResultCollector {

	public void collect(HashMap<Integer, Long> key, long value);
	

	/**
	 * Write to a HashMap. NOTE: May get large!
	 */
	public class HashMapResultCollector implements ResultCollector {
		
		public HashMap<String, Long> results = new HashMap<String, Long>();
		
		public void collect(HashMap<Integer, Long> key, long value) {
			results.put(key.toString(), value);
		}
	}

	/**
	 * Write to Standard Out
	 */
	public class StdOutResultCollector implements ResultCollector {
		
		public void collect(HashMap<Integer, Long> key, long value) {
			System.out.println(key + " => " + value);
		}
	}
	
	/**
	 * Drop results, but keep count
	 */
	public class NullResultCollector implements ResultCollector {
		int count = 0;

		public void collect(HashMap<Integer, Long> key, long value) {
			if (++count % 100 == 0) {
				System.err.println(count + " records produced.");
			}
			// do nothing!
		}
	}
}
