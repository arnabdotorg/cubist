package org.arnab.cubist;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.arnab.cubist.ResultCollector.HashMapResultCollector;
import org.arnab.cubist.ResultCollector.NullResultCollector;
import org.junit.Test;

public class CubeTests {

	@Test
	public void sortTest() {
		long[][] values = { { 0, 1 }, { 3, 0 }, { 2, 2 } };
		long[][] sortedValues = { { 0, 1 }, { 2, 2 }, { 3, 0 } };
		long[][] sortedValues2 = { { 3, 0 }, { 0, 1 }, { 2, 2 } };
		Arrays.sort(values, new ColumnComparator<long[]>(0));
		assertTrue(Arrays.deepEquals(values, sortedValues));
		Arrays.sort(values, new ColumnComparator<long[]>(1));
		assertTrue(Arrays.deepEquals(values, sortedValues2));

	}

	@Test
	public void partitionTest() {
		long[][] values = { { 0, 1 }, { 0, 2 }, { 3, 0 }, { 3, 4 }, { 2, 2 } };
		long[][][] partitionedValues = { { { 0, 1 }, { 0, 2 } },
				{ { 3, 0 }, { 3, 4 } }, { { 2, 2 } } };
		long[][][] x = { { { 0 } } };
		ArrayList<long[][]> partitions = Util.partitionByColumn(values, 0);
		long[][][] partitionArray = partitions.toArray(x);
		assertTrue(Arrays.deepEquals(partitionArray, partitionedValues));
	}

	@Test
	public void materializeTest() {
		Dataset dataset = new Dataset();

		int threshold = 0;

		long[][] values = { { 1, 2 }, { 1, 3 }, { 14, 2 }, { 3, 4 } };
		
		dataset.values = values;
		
		CubingAlgorithm algo = new BottomUpCubingAlgorithm(dataset,
				new Measure.SumMeasure(0), threshold);
		
		HashMapResultCollector hmCollector = new HashMapResultCollector();
		
		algo.materializeCube(hmCollector);
		
		assertTrue(hmCollector.results.get("{}") == 19);
		assertTrue(hmCollector.results.get("{1=2}") == 15);
		assertTrue(hmCollector.results.get("{0=14, 1=2}") == 14);

	}

	@Test
	public void largeMaterializeTest() {
		Dataset dataset = new Dataset();

		int threshold = 10;

		int tuples = 100000;
		int columns = 100;
		dataset.values = new long[tuples][columns];
		for (int i = 0; i < tuples; i++) {
			for (int j = 0; j < columns; j++) {
				dataset.values[i][j] = (long) (Math.random() * 100);
			}
		}
		System.err.println("Cubing....");
		CubingAlgorithm algo = new BottomUpCubingAlgorithm(dataset,
				new Measure.CountMeasure(), threshold);
		algo.materializeCube(new NullResultCollector());
		System.err.println("Done Cubing....");
	}

}
