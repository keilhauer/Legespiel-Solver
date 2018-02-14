package org.whatsoftwarecando.legespiel.experiments;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Combinations implements Iterable<List<Integer>>{

	private List<Integer> activeIndexes;

	private int n;

	private int k;

	private boolean finished;

	public Combinations(int k, int n) {
		if (k > n) {
			throw new RuntimeException("k cannot be greater than n");
		}
		this.n = n;
		this.k = k;
		this.finished = false;
	}

	List<Integer> nextActiveIndexes() {
		if(finished) {
			return null;
		}else if (activeIndexes == null) {
			activeIndexes = new ArrayList<Integer>(k);
			for (int i = 0; i < k; i++) {
				activeIndexes.add(i, i);
			}
			return activeIndexes;
		}
		int currentArrayIndex = k - 1;
		int candidateValue = activeIndexes.get(currentArrayIndex);
		while (candidateValue >= n - k + currentArrayIndex && currentArrayIndex > 0) {
			currentArrayIndex--;
			candidateValue = activeIndexes.get(currentArrayIndex);
		}
		if (currentArrayIndex == 0 && candidateValue == n - k) {
			this.finished = true;
			return null;
		}
		activeIndexes.set(currentArrayIndex, activeIndexes.get(currentArrayIndex) + 1);
		while (currentArrayIndex < k - 1) {
			activeIndexes.set(++currentArrayIndex, activeIndexes.get(currentArrayIndex - 1) + 1);
		}
		return activeIndexes;
	}

	public BigInteger getTotalNumberOfCombinations() {
		return factorial(k + 1, n).divide(factorial(2, n - k));
	}

	BigInteger factorial(int numberFrom, int numberTo) {
		BigInteger result = BigInteger.ONE;
		for (int i = numberFrom; i <= numberTo; i++) {
			result = result.multiply(BigInteger.valueOf(i));
		}
		return result;
	}

	@Override
	public Iterator<List<Integer>> iterator() {
		return new Iterator<List<Integer>>() {

			@Override
			public boolean hasNext() {
				return !finished;
			}

			@Override
			public List<Integer> next() {
				return nextActiveIndexes();
			}
			
		};
	}

}
