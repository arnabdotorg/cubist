package org.arnab.cubist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Implementation of 
 * Bottom-Up Cubing Algorithm for Iceberg Cubes, Beyer et al, SIGMOD 99.
 * 
 * Assumes Measure is monotonically increasing i.e. M(A union B) >= M(A)
 * 
 * Successively sort and partition data by columns, aborting sort if partition's
 * aggregate doesn't meet threshold, since it's sub-partitions will apriori not
 * meet threshold either.
 */
 
public class BottomUpCubingAlgorithm extends CubingAlgorithm {

	public BottomUpCubingAlgorithm(Dataset data, Measure measure, int threshold) {
		super(data, measure, threshold);
	}

	/**
	 * Recursively partition dataset, sort, partition and aggregate
	 * 
	 * @param result
	 * @param data
	 * @param columnsSoFar
	 * @param sortByColumns
	 */
	public void buc(ResultCollector result, long[][] data,
			HashMap<Integer, Long> columnsSoFar, int[] sortByColumns) {

		long aggregate = this.measure.aggregate(data);
		if (aggregate < this.threshold) {
			return; // abort further computation
		}

		result.collect(columnsSoFar, aggregate);

		// sort and partition
		for (int column : sortByColumns) {

			Arrays.sort(data, new ColumnComparator<long[]>(column));

			ArrayList<long[][]> partitions = Util.partitionByColumn(data,
					column);

			for (long[][] p : partitions) {

				HashMap<Integer, Long> newColumnsSoFar = new HashMap<Integer, Long>(
						columnsSoFar);
				newColumnsSoFar.put(column, p[0][column]);

				int[] newSortByColumns = Util.subtract(sortByColumns, column);

				buc(result, p, newColumnsSoFar, newSortByColumns);
			}

		}

	}

	@Override
	public void materializeCube(ResultCollector result) {
		HashMap<Integer, Long> sofar = new HashMap<Integer, Long>();
		this.buc(result, this.data.values, sofar,
				Util.range(this.data.values[0].length));
	}

}
