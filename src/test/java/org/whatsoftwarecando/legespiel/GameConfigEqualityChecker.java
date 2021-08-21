package org.whatsoftwarecando.legespiel;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GameConfigEqualityChecker {

	public static void testSolutionsMatches(GameConfig gameConfig, GameConfig gameConfig2) {
		Solver solver = new Solver(gameConfig);
		Solver solver2 = new Solver(gameConfig2);

		Collection<Field> solutions = solver.findAllSolutions();
		Collection<Field> solutions2 = solver2.findAllSolutions();

		assertEquals(solutions.size(), solutions2.size());

		List<Field> originalSolutions = solver.removeRotationBasedDuplicates();
		List<Field> originalSolutions2 = solver2.removeRotationBasedDuplicates();
		assertEquals(originalSolutions.size(), originalSolutions2.size());

		Iterator<Field> originalSolutions1Itr = originalSolutions.iterator();
		Iterator<Field> originalSolutions2Itr = originalSolutions2.iterator();
		while (originalSolutions1Itr.hasNext()) {
			Field currentOriginalSolutions1 = originalSolutions1Itr.next();
			Field currentOriginalSolutions2 = originalSolutions2Itr.next();
			sameSolution(currentOriginalSolutions1, currentOriginalSolutions2);
		}
	}

	private static void sameSolution(Field currentOriginalSolutions1, Field currentOriginalSolutions2) {
		List<Card> cards1 = currentOriginalSolutions1.getAllCards();
		List<Card> cards2 = currentOriginalSolutions2.getAllCards();
		Iterator<Card> card1Itr = cards1.iterator();
		Iterator<Card> card2Itr = cards2.iterator();
		while (card1Itr.hasNext()) {
			Card card1 = card1Itr.next();
			Card card2 = card2Itr.next();
			assertEquals(card1.getId(), card2.getId());
			assertEquals(card1.getRotationClockwise(), card2.getRotationClockwise());
		}
	}
}
