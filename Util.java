package org.arnab.cubist;

import gnu.trove.set.hash.TIntHashSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Miscellaneous Utility functions and classes.
 */
public class Util {

	// Clone of python's range() function, returns [0..n-1]
	public static int[] range(int n) {
		int[] result = new int[n];
		for (int i = 0; i < n; i++) {
			result[i] = i;
		}
		return result;
	}

	/**
	 * @param data
	 * @param column
	 * @return data GROUP BY column (NOTE: No sorting happens here, so if you
	 *         want SQL semantics, please SORT before!)
	 */
	public static ArrayList<long[][]> partitionByColumn(long[][] data,
			int column) {

		ArrayList<long[][]> parts = new ArrayList<long[][]>();

		// edge case -- if only one tuple, send back as group
		if (data.length == 1) {
			parts.add(data);
			return parts;
		}

		int marker = 0;
		long value = data[0][column];

		for (int i = 1; i < data.length; i++) {
			if (data[i][column] != value) { // new group! let's copy everything
				// we've seen so far and reset
				parts.add(Arrays.copyOfRange(data, marker, i));
				marker = i;
				value = data[i][column];
			}
		}

		// copy the last partition
		parts.add(Arrays.copyOfRange(data, marker, data.length));

		return parts;
	}

	/**
	 * @param array
	 * @param val
	 * @return array - val
	 */
	public static int[] subtract(int[] array, int val) {
		TIntHashSet res = new TIntHashSet();
		res.addAll(array);
		res.remove(val);
		return res.toArray();
	}

	public static String toString(int[] arr) {
		StringBuilder sb = new StringBuilder("[");
		for (int i : arr) {
			sb.append(i + " ");
		}
		sb.append("]");
		return sb.toString();
	}

	public static String toString(long[] arr) {
		StringBuilder sb = new StringBuilder("[");
		for (long i : arr) {
			sb.append(i + " ");
		}
		sb.append("]");
		return sb.toString();
	}

	public static String toString(HashMap<Integer, Long> map) {
		StringBuilder sb = new StringBuilder("[");
		for (Entry<Integer, Long> entry : map.entrySet()) {
			sb.append(entry.getKey() + ":" + entry.getValue() + " ");
		}
		sb.append("]");
		return sb.toString();
	}
}
