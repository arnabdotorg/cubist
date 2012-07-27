package org.arnab.cubist;

/**
 * Measures to apply against the tabular dataset (represented as long[][])
 * TODO: Add attributes to denote monotonic, algebraic
 */
public interface Measure {

	long aggregate(long[][] values);

	/**
	 * Computes SUM() of the assigned column.
	 */
	public static class SumMeasure implements Measure {

		final int column;

		public SumMeasure(int column) {
			this.column = column;
		}

		@Override
		// FIXME: does not check for overflow
		public long aggregate(long[][] values) {
			long sum = 0;
			for (long[] tuple : values) {
				sum += tuple[this.column];
			}
			return sum;
		}

	}

	/**
	 * Computes COUNT() of the dataset
	 */
	public static class CountMeasure implements Measure {
		@Override
		public long aggregate(long[][] values) {
			return values.length;
		}

	}
}