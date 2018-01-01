package org.whatsoftwarecando.legespiel;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.configs.ExactlyOneSolutionConfig.Pictures;
import org.whatsoftwarecando.legespiel.configs.GenericGameConfig;

public class FieldWithConditionsTest {

	@Test
	public void testSolution(){
		List<Condition> borderline = new LinkedList<Condition>();
		borderline.add(new Condition(2, 1, Pictures.BLUE, null));
		borderline.add(new Condition(1, 2, null, Pictures.GREEN));
		FieldWithConditions fieldWithCondition = new FieldWithConditions(2, 2, 3, borderline);
		List<Card> allCards = new LinkedList<Card>();
		allCards.add(new Card(Pictures.RED, Pictures.GREEN, Pictures.BLUE, Pictures.RED));
		allCards.add(new Card(Pictures.BLUE, Pictures.BLUE, Pictures.GREEN, Pictures.GREEN));
		allCards.add(new Card(Pictures.RED, Pictures.RED, Pictures.RED, Pictures.RED));
		allCards.add(new Card(Pictures.GREEN, Pictures.RED, Pictures.GREEN, Pictures.GREEN));
		GenericGameConfig config = new GenericGameConfig(allCards, fieldWithCondition);
		Solver solver = new Solver();
		List<Field> solutions = solver.findAllSolutions(config);
		System.out.println(solutions);
		assertEquals(8, solutions.size());
	}
}
