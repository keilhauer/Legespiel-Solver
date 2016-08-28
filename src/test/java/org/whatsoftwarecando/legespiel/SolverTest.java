package org.whatsoftwarecando.legespiel;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.configs.AbsolutKniffligConfig;
import org.whatsoftwarecando.legespiel.configs.AsterixKartenlegespielConfig;
import org.whatsoftwarecando.legespiel.configs.DasVerrueckteLoriotLegespielConfig;
import org.whatsoftwarecando.legespiel.configs.KnifflidiffelsVersion4Config;
import org.whatsoftwarecando.legespiel.configs.KnifflidiffelsVersion5Config;
import org.whatsoftwarecando.legespiel.configs.UliSteinNochVerwzickterGehtNichtConfig;
import org.whatsoftwarecando.legespiel.configs.UliSteinNochVerzwickterConfig;

public class SolverTest {

	@Test
	public void testNumberOfSolutions() {
		testNumberOfSolution(148, 37, new AbsolutKniffligConfig());
		testNumberOfSolution(48, 3, new AsterixKartenlegespielConfig());
		testNumberOfSolution(48, 3, new DasVerrueckteLoriotLegespielConfig());
		testNumberOfSolution(9216, 24, new KnifflidiffelsVersion4Config());
		testNumberOfSolution(7488, 26, new KnifflidiffelsVersion5Config());
		testNumberOfSolution(4, 1, new UliSteinNochVerwzickterGehtNichtConfig());
		testNumberOfSolution(4, 1, new UliSteinNochVerzwickterConfig());
	}

	protected void testNumberOfSolution(int numberOfAllSolutions, int numberOfOriginalSolutions, GameConfig gameConfig) {
		Solver solver = new Solver();
		List<Field> solutions = solver
				.findAllSolutions(gameConfig);
		assertEquals(numberOfAllSolutions, solutions.size());
		List<Field> solutionsWithoutRotations = solver
				.removeRotationBasedDuplicates(solutions);
		assertEquals(numberOfOriginalSolutions, solutionsWithoutRotations.size());
	}

	@Test
	public void testAlwaysSameSolutions() {
		testAlwaysSameSolutions(new AbsolutKniffligConfig());
		testAlwaysSameSolutions(new AsterixKartenlegespielConfig());
		testAlwaysSameSolutions(new DasVerrueckteLoriotLegespielConfig());
		testAlwaysSameSolutions(new KnifflidiffelsVersion4Config());
		testAlwaysSameSolutions(new KnifflidiffelsVersion5Config());
		testAlwaysSameSolutions(new UliSteinNochVerwzickterGehtNichtConfig());
		testAlwaysSameSolutions(new UliSteinNochVerzwickterConfig());
	}
	
	private void testAlwaysSameSolutions(GameConfig gameConfig) {
		Solver solver = new Solver();
		List<Field> allCorrect = solver.findAllSolutions(gameConfig);
		System.out.println("Found " + allCorrect.size() + " solutions");
		System.out.println("Number of tries " + solver.numberOfTries());

		Set<Field> allCorrectSet = new HashSet<Field>(allCorrect);
		long numberOfTriesExpected = solver.numberOfTries();
		for (int i = 0; i < 10; i++) {
			long startTime = System.nanoTime();
			List<Field> allCorrectControl = solver.findAllSolutions(gameConfig);
			if (!allCorrectSet.equals(new HashSet<Field>(allCorrectControl))) {
				throw new RuntimeException("Unexpected " + allCorrectControl.size() + ": " + allCorrectControl);
			}
			System.out.println("Number of tries " + solver.numberOfTries());
			if (numberOfTriesExpected != solver.numberOfTries()) {
				throw new RuntimeException("Unexpected " + solver.numberOfTries());
			}
			long endTime = System.nanoTime();
			System.out.println("Time needed: " + Util.nanosToMilliseconds(endTime - startTime) + " ms");
			Collections.shuffle(gameConfig.getAvailableCards());
		}
	}
}
