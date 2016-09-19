package org.whatsoftwarecando.legespiel.configs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;
import org.whatsoftwarecando.legespiel.Solver;
import org.whatsoftwarecando.legespiel.configs.AllPossibleCardsForPicturesConfig.FourPictures;

public class AllPossibleCardsForPicturesConfigTest {

	@Test
	public void testCaldBorderline1(){
		Field field = new Field(2, 2);
		field = field.addedIfFits(new Card(FourPictures.GREEN, FourPictures.GREEN, FourPictures.BLUE, FourPictures.GREEN));
		List<IPicture> borderline = new AllPossibleCardsForPicturesConfig().calcBorderline(field);
		assertEquals(2, borderline.size());
		assertEquals(FourPictures.BLUE, borderline.get(0));
		assertEquals(FourPictures.GREEN, borderline.get(1));
	}
	
	@Test
	public void testCaldBorderline2(){
		Field field = new Field(2, 2);
		field = field.addedIfFits(new Card(FourPictures.GREEN, FourPictures.GREEN, FourPictures.BLUE, FourPictures.GREEN));
		field = field.addedIfFits(new Card(FourPictures.GREEN, FourPictures.BLUE, FourPictures.GREEN, FourPictures.RED));
		List<IPicture> borderline = new AllPossibleCardsForPicturesConfig().calcBorderline(field);
		assertEquals(2, borderline.size());
		assertEquals(FourPictures.GREEN, borderline.get(0));
		assertEquals(FourPictures.RED, borderline.get(1));
	}
	
	@Test
	public void testCaldBorderline3(){
		Field field = new Field(2, 2);
		field = field.addedIfFits(new Card(FourPictures.GREEN, FourPictures.GREEN, FourPictures.BLUE, FourPictures.GREEN));
		field = field.addedIfFits(new Card(FourPictures.GREEN, FourPictures.BLUE, FourPictures.GREEN, FourPictures.RED));
		field = field.addedIfFits(new Card(FourPictures.GREEN, FourPictures.RED, FourPictures.RED, FourPictures.BLUE));
		List<IPicture> borderline = new AllPossibleCardsForPicturesConfig().calcBorderline(field);
		assertEquals(2, borderline.size());
		assertEquals(FourPictures.RED, borderline.get(0));
		assertEquals(FourPictures.RED, borderline.get(1));
	}
	
	@Test
	public void reallyOnlyOneSolution() {
		AllPossibleCardsForPicturesConfig testConfig = new AllPossibleCardsForPicturesConfig();
		Solver solver = new Solver();
		List<Field> solutions = solver.findAllSolutions(testConfig);
		Map<Field, List<Field>> failures = new HashMap<Field, List<Field>>();
		for (Field solution : solutions) {
			TestGameConfig currentSolutionConfig = new TestGameConfig(
					solution.getAllCards(), testConfig.createEmptyField());
			solver = new Solver();
//			GameConfig currentSolutionConfig = new AbsolutKniffligConfig();
			List<Field> solutionsForCurrent = solver
					.findAllSolutions(currentSolutionConfig);
			List<Field> solutionsWithoutRotations = solver
					.removeRotationBasedDuplicates(solutionsForCurrent);
			if (solutionsWithoutRotations.size() > 1) {
				failures.put(solution, solutionsWithoutRotations);
			}
		}
		assertTrue(failures.size() + " failures: " + failures,
				failures.isEmpty());
	}

	class TestGameConfig extends GameConfig {

		private final ArrayList<Card> availableCards;
		private final Field emptyField;

		/**
		 * @param availableCards
		 * @param emptyField
		 */
		public TestGameConfig(List<Card> availableCards, Field emptyField) {
			this.availableCards = new ArrayList<Card>(availableCards);
			this.emptyField = emptyField;
		}

		@Override
		public ArrayList<Card> getAvailableCards() {
			return availableCards;
		}

		@Override
		public Field createEmptyField() {
			return new Field(emptyField.getRows(), emptyField.getCols());
		}
	}
}