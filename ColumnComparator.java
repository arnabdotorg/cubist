package org.arnab.cubist;

import java.util.Comparator;

/**
 * Sorts tabular data according to configured column
 * 
 * @param <T> : Ignored, this is only for long[][] data
 */
public class ColumnComparator<T> implements Comparator<T> {
	
	final int column;
	
	public ColumnComparator(int column) {
		this.column = column;
	}

	@Override
	public int compare(T a1, T a2) {
		long[] A1 = (long[]) a1;
		long[] A2 = (long[]) a2;
		return A1[column] < A2[column] ? -1 : 1;
	}
	
}