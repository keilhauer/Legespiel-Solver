package org.whatsoftwarecando.legespiel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.whatsoftwarecando.legespiel.configs.AbsolutKniffligConfig;
import org.whatsoftwarecando.legespiel.graphics.HtmlGenerator;
import org.whatsoftwarecando.legespiel.xml.GameConfigFromXml;

public class Solver {

	private long numberOfTries;

	private GameConfig gameConfig;

	private Collection<Field> solutions;

	private boolean searchLimitReached;

	public Solver(GameConfig gameConfig) {
		this.gameConfig = gameConfig;
		if (gameConfig.isFilterLookAlikesDuringSearch()) {
			this.solutions = new LinkedHashSet<>();
		} else {
			this.solutions = new LinkedList<>();
		}
		this.searchLimitReached = false;
	}

	public static void main(String[] argv) throws IOException, URISyntaxException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		GameConfig gameConfig = null;
		if (argv.length == 0) {
			throw new RuntimeException("Parameter is missing!");
		} else if (argv[0].endsWith(".xml")) {
			gameConfig = new GameConfigFromXml("configs/" + argv[0]);
		} else {
			gameConfig = (GameConfig) Class.forName(AbsolutKniffligConfig.class.getPackage().getName() + "." + argv[0])
					.newInstance();
		}
		gameConfig.output("Using GameConfig: " + gameConfig.getClass().getSimpleName());
		Solver solver = new Solver(gameConfig);
		Collection<Field> solutions = solver.findAllSolutions();

		HtmlGenerator.getInstance().writeHtml(gameConfig, solutions, "allSolutions", "All Solutions");

		List<Field> originalSolutions = solver.removeRotationBasedDuplicates();

		HtmlGenerator.getInstance().writeHtml(gameConfig, originalSolutions, "originalSolutions", "Original Solutions");
	}

	/**
	 * 
	 * @return number of card rotations tried since the last call of
	 *         findAllSolutions(...)
	 */
	public long getNumberOfTries() {
		return numberOfTries;
	}

	public boolean isSearchLimitReached() {
		return searchLimitReached;
	}

	public Collection<Field> findAllSolutions() {
		long startTime = System.nanoTime();
		try {
			numberOfTries = 0;
			Field emptyField = gameConfig.getEmptyField();
			if (gameConfig.isBfsNeeded()) {
				PartialSolution startingConfig = new PartialSolution(emptyField, gameConfig.getAvailableCards());
				Set<PartialSolution> partialSolutions = new HashSet<PartialSolution>();
				partialSolutions.add(startingConfig);
				int cardsUntilFull = emptyField.getCardsUntilFull();
				for (int i = 1; i < cardsUntilFull; i++) {
					gameConfig.output("Partial solutions with " + i + " cards:");
					partialSolutions = findSolutionsWithOneMoreCard(partialSolutions);
					gameConfig.output("Total: " + partialSolutions.size());
					partialSolutions = gameConfig.filterPartialSolutions(partialSolutions);
					gameConfig.output("Filtered: " + partialSolutions.size());
				}
				gameConfig.output("Solutions: ");
				partialSolutions = findSolutionsWithOneMoreCard(partialSolutions);
				gameConfig.output("Total: " + partialSolutions.size());
				for (PartialSolution currentSolution : partialSolutions) {
					addSolution(currentSolution.getField());
				}
				solutions = gameConfig.filterSolutions(solutions);
				gameConfig.output("Filtered: " + solutions.size());
				return new LinkedList<Field>(solutions);
			} else {
				findAllSolutions(emptyField, gameConfig.getAvailableCards());
				if (solutions instanceof List) {
					return solutions;
				} else {
					return new LinkedList<Field>(solutions);
				}
			}
		} finally {
			long timeNeeded = System.nanoTime() - startTime;
			if (this.isSearchLimitReached()) {
				gameConfig.output("Tried " + this.getNumberOfTries() + " card rotations => Found (maybe not all) "
						+ solutions.size() + " solutions in " + Util.nanosToMilliseconds(timeNeeded) + " ms");
			} else {
				gameConfig.output("Tried " + this.getNumberOfTries() + " card rotations => Found all "
						+ solutions.size() + " solutions in " + Util.nanosToMilliseconds(timeNeeded) + " ms");
			}
			if (!gameConfig.isFilterLookAlikesDuringSearch()) {
				gameConfig.output("Measure of difficulty: " + this.getNumberOfTries() / (double) solutions.size());
			}

		}

	}

	private void addSolution(Field solution) {
		this.solutions.add(solution);
		if (this.solutions.size() >= gameConfig.getMaxNumberOfSolutionsSearchLimit()
				&& this.searchLimitReached == false) {
			this.searchLimitReached = true;
			gameConfig.output("Search limit for number of solutions (" + gameConfig.getMaxNumberOfSolutionsSearchLimit()
					+ ") reached!");
		}
	}

	Set<PartialSolution> findSolutionsWithOneMoreCard(Collection<PartialSolution> partialSolutions) {
		Set<PartialSolution> partialSolutionsWithOneMoreCard = new HashSet<PartialSolution>();

		for (PartialSolution partialSolution : partialSolutions) {
			Collection<Field> nextPossibleMoves = nextPossibleMoves(partialSolution.getField(),
					partialSolution.getRemainingCards());
			for (Field nextPossibleMove : nextPossibleMoves) {
				List<Card> remaining = Util.removed(nextPossibleMove.getLastCard(),
						partialSolution.getRemainingCards());
				PartialSolution partialSolutionWithOneMoreCard = new PartialSolution(nextPossibleMove, remaining);
				partialSolutionsWithOneMoreCard.add(partialSolutionWithOneMoreCard);
			}
		}
		return partialSolutionsWithOneMoreCard;
	}

	void findAllSolutions(Field field, List<Card> cards) {
		if (searchLimitReached) {
			return;
		}
		Collection<Field> nextPossibleMoves = nextPossibleMoves(field, cards);
		for (Field currentMove : nextPossibleMoves) {
			if (currentMove.isFull()) {
				addSolution(currentMove);
			} else {
				List<Card> remainingCards = Util.removed(currentMove.getLastCard(), cards);
				findAllSolutions(currentMove, remainingCards);
			}
		}

	}

	Collection<Field> nextPossibleMoves(Field field, List<Card> remainingCards) {
		Collection<Field> fieldsWithOneMoreCard = null;
		if (gameConfig.isFilterLookAlikesDuringSearch()) {
			fieldsWithOneMoreCard = new LinkedHashSet<>();
		} else {
			fieldsWithOneMoreCard = new LinkedList<>();
		}
		if (numberOfTries >= gameConfig.getMaxNumberOfTries()) {
			if (this.searchLimitReached == false) {
				this.searchLimitReached = true;
				gameConfig
						.output("Search limit for number of tries (" + gameConfig.getMaxNumberOfTries() + ") reached!");
			}
			return fieldsWithOneMoreCard;
		}
		for (Card card : remainingCards) {
			Field addedUnturned = field.addedIfFits(card);
			numberOfTries++;
			if (addedUnturned != null) {
				fieldsWithOneMoreCard.add(addedUnturned);
			}
			for (int turn = 1; turn <= 3; turn++) {
				card = card.turned90DegreesClockwise();
				Field addedTurned = field.addedIfFits(card);
				numberOfTries++;
				if (addedTurned != null) {
					fieldsWithOneMoreCard.add(addedTurned);
				}
			}
		}

		return fieldsWithOneMoreCard;
	}

	public List<Field> removeRotationBasedDuplicates() {
		LinkedHashSet<Field> resultSet = new LinkedHashSet<Field>(solutions);
		for (Field solution : solutions) {
			if (resultSet.contains(solution)) {
				Field turned90 = solution.turned90DegreesClockwise();
				Field turned180 = turned90.turned90DegreesClockwise();
				Field turned270 = turned180.turned90DegreesClockwise();
				if (!turned90.equals(solution)) {
					resultSet.remove(turned90);
				}
				if (!turned180.equals(solution)) {
					resultSet.remove(turned180);
				}
				if (!turned270.equals(solution)) {
					resultSet.remove(turned270);
				}
			}
		}
		gameConfig.output("Removed rotation based duplicates and other look-alikes => " + resultSet.size()
				+ " original solutions remaining");
		return new LinkedList<Field>(resultSet);
	}

	public static List<Field> beautifySolutions(Collection<Field> solutions) {
		List<Field> result = new LinkedList<Field>();
		for (Field solution : solutions) {
			if (solution.isFull()) {
				result.add(solution.beautify());
			} else {
				result.add(solution);
			}
		}
		return result;
	}

}
