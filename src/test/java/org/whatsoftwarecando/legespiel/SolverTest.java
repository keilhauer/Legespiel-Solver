package org.whatsoftwarecando.legespiel;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.configs.AbsolutKniffligConfig;

public class SolverTest {

	@Test
	public void testNumberOfSolutions() {
		Solver solver = new Solver();
		List<Field> solutions = solver
				.findAllSolutions(new AbsolutKniffligConfig());
		assertEquals(148, solutions.size());
		List<Field> solutionsWithoutRotations = solver
				.removeRotationBasedDuplicates(solutions);
		assertEquals(37, solutionsWithoutRotations.size());
	}

	@Test
	public void testAlwaysSameSolutions() {
		Field board = new Field(3, 3);
		List<Card> cards = new AbsolutKniffligConfig().getAvailableCards();

		Solver solver = new Solver();
		List<Field> allCorrect = solver.findAllSolutionsStart(board, cards);
		for (Field correct : allCorrect) {
			System.out.println(correct);
		}
		System.out.println("Found " + allCorrect.size() + " solutions");
		System.out.println("Number of tries " + solver.numberOfTries());

		Set<Field> allCorrectSet = new HashSet<Field>(allCorrect);
		long numberOfTriesExpected = solver.numberOfTries();
		for (int i = 0; i < 1000; i++) {
			long startTime = System.nanoTime();
			List<Field> allCorrectControl = solver.findAllSolutionsStart(board,
					cards);
			if (!allCorrectSet.equals(new HashSet<Field>(allCorrectControl))) {
				throw new RuntimeException("Unexpected "
						+ allCorrectControl.size() + ": " + allCorrectControl);
			}
			System.out.println("Number of tries " + solver.numberOfTries());
			if (numberOfTriesExpected != solver.numberOfTries()) {
				throw new RuntimeException("Unexpected "
						+ solver.numberOfTries());
			}
			long endTime = System.nanoTime();
			System.out.println("Time needed: "
					+ Util.nanosToMilliseconds(endTime - startTime) + " ms");
			Collections.shuffle(cards);

		}
	}
}
