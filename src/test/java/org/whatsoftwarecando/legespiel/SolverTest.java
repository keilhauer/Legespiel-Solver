package org.whatsoftwarecando.legespiel;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.configs.AbsolutKniffligConfig;
import org.whatsoftwarecando.legespiel.configs.AsterixKnobeleiConfig;
import org.whatsoftwarecando.legespiel.configs.Crazy9KetnerOwls;
import org.whatsoftwarecando.legespiel.configs.DasVerrueckteLoriotLegespielConfig;
import org.whatsoftwarecando.legespiel.configs.FourPicturesConfig;
import org.whatsoftwarecando.legespiel.configs.KnifflidiffelsVersion4Config;
import org.whatsoftwarecando.legespiel.configs.KnifflidiffelsVersion5Config;
import org.whatsoftwarecando.legespiel.configs.UliSteinNochVerwzickterGehtNichtConfig;
import org.whatsoftwarecando.legespiel.configs.UliSteinNochVerzwickterConfig;
import org.whatsoftwarecando.legespiel.configs.WitchesPuzzleConfig;

public class SolverTest {

	@Test
	public void testNumberOfSolutions() {
		testNumberOfSolutions(148, 37, new AbsolutKniffligConfig());
		testNumberOfSolutions(48, 3, new AsterixKnobeleiConfig());
		testNumberOfSolutions(16, 2, new Crazy9KetnerOwls());
		testNumberOfSolutions(48, 3, new DasVerrueckteLoriotLegespielConfig());
		testNumberOfSolutions(37120, 145, new FourPicturesConfig());
		testNumberOfSolutions(9216, 24, new KnifflidiffelsVersion4Config());
		testNumberOfSolutions(7488, 26, new KnifflidiffelsVersion5Config());
		testNumberOfSolutions(4, 1, new UliSteinNochVerwzickterGehtNichtConfig());
		testNumberOfSolutions(4, 1, new UliSteinNochVerzwickterConfig());
		testNumberOfSolutions(8, 2, new WitchesPuzzleConfig());
	}

	protected void testNumberOfSolutions(int numberOfAllSolutions, int numberOfOriginalSolutions,
			GameConfig gameConfig) {
		Solver solver = new Solver(gameConfig);
		Collection<Field> solutions = solver.findAllSolutions();
		assertEquals(numberOfAllSolutions, solutions.size());
		List<Field> solutionsWithoutRotations = solver.removeRotationBasedDuplicates();
		assertEquals(numberOfOriginalSolutions, solutionsWithoutRotations.size());
	}

	@Test
	public void testAlwaysSameSolutions() {
		testAlwaysSameSolutions(new AbsolutKniffligConfig());
		testAlwaysSameSolutions(new AsterixKnobeleiConfig());
		testAlwaysSameSolutions(new Crazy9KetnerOwls());
		testAlwaysSameSolutions(new DasVerrueckteLoriotLegespielConfig());
		testAlwaysSameSolutions(new FourPicturesConfig());
		testAlwaysSameSolutions(new KnifflidiffelsVersion4Config());
		testAlwaysSameSolutions(new KnifflidiffelsVersion5Config());
		testAlwaysSameSolutions(new UliSteinNochVerwzickterGehtNichtConfig());
		testAlwaysSameSolutions(new UliSteinNochVerzwickterConfig());
		testAlwaysSameSolutions(new WitchesPuzzleConfig());
	}

	private void testAlwaysSameSolutions(GameConfig gameConfig) {
		System.out.println("Solutions for " + gameConfig.getClass().getSimpleName());
		Solver solver = new Solver(gameConfig);
		Collection<Field> allCorrect = solver.findAllSolutions();
		System.out.println("Found " + allCorrect.size() + " solutions");
		System.out.println("Number of tries " + solver.getNumberOfTries());

		Set<Field> allCorrectSet = new HashSet<Field>(allCorrect);
		long numberOfTriesExpected = solver.getNumberOfTries();
		for (int i = 0; i < 10; i++) {
			solver = new Solver(gameConfig);
			long startTime = System.nanoTime();
			Collection<Field> allCorrectControl = solver.findAllSolutions();
			if (!allCorrectSet.equals(new HashSet<Field>(allCorrectControl))) {
				throw new RuntimeException("Unexpected " + allCorrectControl.size() + ": " + allCorrectControl);
			}
			System.out.println("Number of tries " + solver.getNumberOfTries());
			if (numberOfTriesExpected != solver.getNumberOfTries()) {
				throw new RuntimeException("Unexpected " + solver.getNumberOfTries());
			}
			long endTime = System.nanoTime();
			System.out.println("Time needed: " + Util.nanosToMilliseconds(endTime - startTime) + " ms");
			Collections.shuffle(gameConfig.getAvailableCards());
		}
	}
}
