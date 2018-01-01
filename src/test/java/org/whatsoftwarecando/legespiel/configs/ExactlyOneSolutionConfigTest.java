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
import org.whatsoftwarecando.legespiel.configs.ExactlyOneSolutionConfig.Pictures;

public class ExactlyOneSolutionConfigTest {

	@Test
	public void testCalcBorderline1() {
		Field field = new Field(2, 2);
		field = field.addedIfFits(new Card(Pictures.GREEN,
				Pictures.GREEN, Pictures.BLUE, Pictures.GREEN));
		List<Condition> borderline = new ExactlyOneSolutionConfig()
				.calcBorderline(field);
		assertEquals(2, borderline.size());
		assertEquals(new Condition(1, 1, Pictures.BLUE, null), borderline.get(0));
		assertEquals(new Condition(1, 1, null, Pictures.GREEN), borderline.get(1));
	}

	@Test
	public void testCalcBorderline2() {
		Field field = new Field(2, 2);
		field = field.addedIfFits(new Card(Pictures.GREEN,
				Pictures.GREEN, Pictures.BLUE, Pictures.GREEN));
		field = field.addedIfFits(new Card(Pictures.GREEN,
				Pictures.BLUE, Pictures.GREEN, Pictures.RED));
		List<Condition> borderline = new ExactlyOneSolutionConfig()
				.calcBorderline(field);
		assertEquals(2, borderline.size());
		assertEquals(new Condition(1, 1, null, Pictures.GREEN), borderline.get(0));
		assertEquals(new Condition(1, 2, null, Pictures.RED), borderline.get(1));
	}

	@Test
	public void testCalcBorderline3() {
		Field field = new Field(2, 2);
		field = field.addedIfFits(new Card(Pictures.GREEN,
				Pictures.GREEN, Pictures.BLUE, Pictures.GREEN));
		field = field.addedIfFits(new Card(Pictures.GREEN,
				Pictures.BLUE, Pictures.GREEN, Pictures.RED));
		field = field.addedIfFits(new Card(Pictures.GREEN,
				Pictures.RED, Pictures.RED, Pictures.BLUE));
		List<Condition> borderline = new ExactlyOneSolutionConfig()
				.calcBorderline(field);
		assertEquals(2, borderline.size());
		assertEquals(new Condition(2, 1, Pictures.RED, null), borderline.get(0));
		assertEquals(new Condition(1, 2, null, Pictures.RED), borderline.get(1));
	}

	@Test
	public void reallyOnlyOneSolution() {
		ExactlyOneSolutionConfig testConfig = new ExactlyOneSolutionConfig();
		Solver solver = new Solver();
		List<Field> solutions = solver.findAllSolutions(testConfig);
		Map<Field, List<Field>> moreSolutions = new HashMap<Field, List<Field>>();
		Set<Field> correctSolutions = new HashSet<Field>();
		Set<Field> noSolution = new HashSet<Field>();
		for (Field solution : solutions) {
			GenericGameConfig currentSolutionConfig = new GenericGameConfig(
					solution.getAllCards(), testConfig.getEmptyFieldInstance());
			List<Field> solutionsForCurrent = solver
					.findAllSolutions(currentSolutionConfig);
			List<Field> solutionsWithoutRotations = solver
					.removeRotationBasedDuplicates(solutionsForCurrent);
			if (solutionsWithoutRotations.size() > 1) {
				moreSolutions.put(solution, solutionsWithoutRotations);
			} else if (solutionsWithoutRotations.size() == 1) {
				correctSolutions.add(solution);
			} else {
				noSolution.add(solution);
			}
		}
		assertEquals(noSolution.size() + " out of " + solutions.size()
				+ " with no solutions: " + noSolution, 0, noSolution.size());
		assertEquals(moreSolutions.size() + " failures out of " + solutions.size()
				+ " solutions: " + moreSolutions, 0, moreSolutions.size());

	}

}