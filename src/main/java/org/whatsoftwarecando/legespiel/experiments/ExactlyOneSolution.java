package org.whatsoftwarecando.legespiel.experiments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.CardCreator;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.IPicture;
import org.whatsoftwarecando.legespiel.Solver;
import org.whatsoftwarecando.legespiel.configs.GenericGameConfig;

public class ExactlyOneSolution {

	private static Field emptyField = new Field(3, 3);
	private volatile static int combinationNumber = 0;

	public enum Pictures implements IPicture {

		RED, GREEN, BLUE;

		@Override
		public boolean matches(IPicture other) {
			return this == other;
		}
	}

	public static void main(String[] argv) {
		ArrayList<Card> availableCards = new ArrayList<Card>(
				AllPossibleCardsForPictures.generateCards(Pictures.values(), true, new CardCreator()));
		System.out.println(availableCards.size() + " available Cards: " + availableCards);
		Combinations combinations = new Combinations(9, availableCards.size());
		Solver solver = new Solver();
		System.out.println("Number of possible combinations: " + combinations.getTotalNumberOfCombinations());
		
		StreamSupport.stream(combinations.spliterator(), true)
				.forEach(activeIndexes -> checkSolution(availableCards, activeIndexes, solver));
	}

	private static int checkSolution(ArrayList<Card> availableCards, List<Integer> activeIndexes, Solver solver) {
		System.out.println(++combinationNumber);
		long start = System.currentTimeMillis();
		ArrayList<Card> currentCards = new ArrayList<Card>();
		for (Integer currentIndex : activeIndexes) {
			currentCards.add(availableCards.get(currentIndex));
		}
		GenericGameConfig gameConfig = new GenericGameConfig(currentCards, emptyField);
		/*
		 * TODO: findAllSolutions should terminate as soon as it has found 2 original
		 * solutions
		 */
		List<Field> allSolutions = solver.findAllSolutions(gameConfig);
		List<Field> filtered = solver.removeRotationBasedDuplicates(allSolutions);
		if (filtered.size() == 1) {
			System.out.println("One Solution for " + currentCards);
		}
		System.out.println("Time: " + (System.currentTimeMillis() - start) + "ms");
		return combinationNumber;
	}

}
