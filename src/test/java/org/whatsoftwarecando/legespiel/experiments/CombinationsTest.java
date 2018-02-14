package org.whatsoftwarecando.legespiel.experiments;

import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;
import org.whatsoftwarecando.legespiel.experiments.Combinations;

public class CombinationsTest {

	@Test
	public void test() {
		int n = 7;
		int k = 3;
		Combinations c = new Combinations(k, n);
		long numberOfCombinations = c.getTotalNumberOfCombinations().longValue();
		for (int i = 1; i <= numberOfCombinations; i++) {
			System.out.println(c.nextActiveIndexes());
		}
	}
	
	@Test
	public void testGetTotalNumberOfCombinations() {
		Combinations c = new Combinations(3, 7);
		Assert.assertEquals(35, c.getTotalNumberOfCombinations());
	}
	
	@Test
	public void testGetTotalNumberOfCombinations2() {
		Combinations c = new Combinations(9, 24);
		Assert.assertEquals(1307504, c.getTotalNumberOfCombinations());
	}
	
	@Test
	public void testFactorial() {
		Combinations c = new Combinations(-1, -1);
		Assert.assertEquals(BigInteger.valueOf(24), c.factorial(2, 4));
		Assert.assertEquals(new BigInteger("1307674368000"), c.factorial(2, 15));
		Assert.assertEquals(new BigInteger("1709789466857472000"), c.factorial(10, 24));
	}
}
