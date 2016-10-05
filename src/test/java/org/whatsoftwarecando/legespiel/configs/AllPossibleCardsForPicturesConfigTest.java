package org.whatsoftwarecando.legespiel.configs;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Condition;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.Solver;
import org.whatsoftwarecando.legespiel.configs.AllPossibleCardsForPicturesConfig.FourPictures;

public class AllPossibleCardsForPicturesConfigTest {

	@Test
	public void testCalcBorderline1() {
		Field field = new Field(2, 2);
		field = field.addedIfFits(new Card(FourPictures.GREEN,
				FourPictures.GREEN, FourPictures.BLUE, FourPictures.GREEN));
		List<Condition> borderline = new AllPossibleCardsForPicturesConfig()
				.calcBorderline(field);
		assertEquals(2, borderline.size());
		assertEquals(new Condition(1, 1, FourPictures.BLUE, null), borderline.get(0));
		assertEquals(new Condition(1, 1, null, FourPictures.GREEN), borderline.get(1));
	}

	@Test
	public void testCalcBorderline2() {
		Field field = new Field(2, 2);
		field = field.addedIfFits(new Card(FourPictures.GREEN,
				FourPictures.GREEN, FourPictures.BLUE, FourPictures.GREEN));
		field = field.addedIfFits(new Card(FourPictures.GREEN,
				FourPictures.BLUE, FourPictures.GREEN, FourPictures.RED));
		List<Condition> borderline = new AllPossibleCardsForPicturesConfig()
				.calcBorderline(field);
		assertEquals(2, borderline.size());
		assertEquals(new Condition(1, 1, null, FourPictures.GREEN), borderline.get(0));
		assertEquals(new Condition(1, 2, null, FourPictures.RED), borderline.get(1));
	}

	@Test
	public void testCalcBorderline3() {
		Field field = new Field(2, 2);
		field = field.addedIfFits(new Card(FourPictures.GREEN,
				FourPictures.GREEN, FourPictures.BLUE, FourPictures.GREEN));
		field = field.addedIfFits(new Card(FourPictures.GREEN,
				FourPictures.BLUE, FourPictures.GREEN, FourPictures.RED));
		field = field.addedIfFits(new Card(FourPictures.GREEN,
				FourPictures.RED, FourPictures.RED, FourPictures.BLUE));
		List<Condition> borderline = new AllPossibleCardsForPicturesConfig()
				.calcBorderline(field);
		assertEquals(2, borderline.size());
		assertEquals(new Condition(2, 1, FourPictures.RED, null), borderline.get(0));
		assertEquals(new Condition(1, 2, null, FourPictures.RED), borderline.get(1));
	}

	@Test
	public void reallyOnlyOneSolution() {
		AllPossibleCardsForPicturesConfig testConfig = new AllPossibleCardsForPicturesConfig();
		Solver solver = new Solver();
		List<Field> solutions = solver.findAllSolutions(testConfig);
		Map<Field, List<Field>> failures = new HashMap<Field, List<Field>>();
		Set<Field> correctSolutions = new HashSet<Field>();
		Set<Field> noSolution = new HashSet<Field>();
		for (Field solution : solutions) {
			TestGameConfig currentSolutionConfig = new TestGameConfig(
					solution.getAllCards(), testConfig.createEmptyField());
			solver = new Solver();
			List<Field> solutionsForCurrent = solver
					.findAllSolutions(currentSolutionConfig);
			List<Field> solutionsWithoutRotations = solver
					.removeRotationBasedDuplicates(solutionsForCurrent);
			if (solutionsWithoutRotations.size() > 1) {
				failures.put(solution, solutionsWithoutRotations);
			} else if (solutionsWithoutRotations.size() == 1) {
				correctSolutions.add(solution);
			} else {
				noSolution.add(solution);
			}
		}
		assertEquals("No solution for " + noSolution, 0, noSolution.size());
		System.out.println(solutions.size() + " solutions " + solutions);
		System.out.println(correctSolutions.size() + " correct solutions "
				+ correctSolutions);
		List<Field> correctSolutionsWithoutRotations = solver
				.removeRotationBasedDuplicates(correctSolutions);
		System.out.println(correctSolutionsWithoutRotations.size()
				+ " correct solutions without rotations "
				+ correctSolutionsWithoutRotations);
		assertEquals(failures.size() + " failures out of " + solutions.size()
				+ " solutions: " + failures, 0, failures.size());
	}

}