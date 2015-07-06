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
		Field board = new Field(3, 3);
		Solver solver = new Solver();
		Field.resetTries();
		List<Field> solutions = solver.findAllSolutions(board,
				new AbsolutKniffligConfig().getAvailableCards());
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
		Field.resetTries();
		List<Field> allCorrect = solver.findAllSolutions(board, cards);
		for (Field correct : allCorrect) {
			System.out.println(correct);
		}
		System.out.println("Found " + allCorrect.size() + " solutions");
		System.out.println("Number of tries " + Field.numberOfTriesAndReset());

		Set<Field> allCorrectSet = new HashSet<Field>(allCorrect);
		long numberOfTriesExpected = Field.numberOfTriesAndReset();
		for (int i = 0; i < 1000; i++) {
			Field.resetTries();
			long startTime = System.nanoTime();
			List<Field> allCorrectControl = solver.findAllSolutions(board,
					cards);
			if (!allCorrectSet.equals(new HashSet<Field>(allCorrectControl))) {
				throw new RuntimeException("Unexpected "
						+ allCorrectControl.size() + ": " + allCorrectControl);
			}
			System.out.println("Number of tries "
					+ Field.numberOfTriesAndReset());
			if (numberOfTriesExpected != Field.numberOfTriesAndReset()) {
				throw new RuntimeException("Unexpected "
						+ Field.numberOfTriesAndReset());
			}
			long endTime = System.nanoTime();
			System.out.println("Time needed: "
					+ Util.nanosToMilliseconds(endTime - startTime) + " ms");
			Collections.shuffle(cards);

		}
	}
}
