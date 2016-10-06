package org.whatsoftwarecando.legespiel;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.configs.AllPossibleCardsForPicturesConfig.FourPictures;
import org.whatsoftwarecando.legespiel.configs.TestGameConfig;

public class FieldWithConditionsTest {

	@Test
	public void testSolution(){
		List<Condition> borderline = new LinkedList<Condition>();
		borderline.add(new Condition(2, 1, FourPictures.BLUE, null));
		borderline.add(new Condition(1, 2, null, FourPictures.GREEN));
		FieldWithConditions fieldWithCondition = new FieldWithConditions(2, 2, 3, borderline);
		List<Card> allCards = new LinkedList<Card>();
		allCards.add(new Card(FourPictures.RED, FourPictures.GREEN, FourPictures.BLUE, FourPictures.RED));
		allCards.add(new Card(FourPictures.BLUE, FourPictures.BLUE, FourPictures.GREEN, FourPictures.GREEN));
		allCards.add(new Card(FourPictures.RED, FourPictures.RED, FourPictures.RED, FourPictures.RED));
		allCards.add(new Card(FourPictures.GREEN, FourPictures.RED, FourPictures.GREEN, FourPictures.GREEN));
		TestGameConfig config = new TestGameConfig(allCards, fieldWithCondition);
		Solver solver = new Solver();
		List<Field> solutions = solver.findAllSolutions(config);
		System.out.println(solutions);
		assertEquals(8, solutions.size());
	}
}
