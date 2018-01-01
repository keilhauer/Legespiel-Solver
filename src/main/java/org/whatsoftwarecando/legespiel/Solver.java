package org.whatsoftwarecando.legespiel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.whatsoftwarecando.legespiel.configs.AbsolutKniffligConfig;
import org.whatsoftwarecando.legespiel.graphics.CardToGraphics;

public class Solver {

	private long numberOfTries;

	public static void main(String[] argv) throws IOException, URISyntaxException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		GameConfig gameConfig = null;
		if (argv.length == 0) {
			gameConfig = new AbsolutKniffligConfig();
		} else {
			gameConfig = (GameConfig) Class.forName(AbsolutKniffligConfig.class.getPackage().getName() + "." + argv[0])
					.newInstance();
		}
		System.out.println("Using GameConfig: " + gameConfig.getClass().getSimpleName());
		System.out.println("Looking for duplicate cards: ");
		boolean foundDuplicateCards = false;
		for (List<Card> duplicates : new DuplicateCardsFinder().findDuplicateCards(gameConfig.getAvailableCards())) {
			if (duplicates.size() > 1) {
				System.out.println(duplicates);
				foundDuplicateCards = true;
			}
		}
		if (!foundDuplicateCards) {
			System.out.println("No duplicate cards found");
		}
		long startTime = System.nanoTime();
		Solver solver = new Solver();
		List<Field> solutions = solver.findAllSolutions(gameConfig);

		long timeNeeded = System.nanoTime() - startTime;
		System.out.println("Tried " + solver.numberOfTries() + " card rotations -> Found all " + solutions.size()
				+ " solutions in " + Util.nanosToMilliseconds(timeNeeded) + " ms");
		writeHtml(gameConfig, solutions, "allSolutions", "All Solutions");

		List<Field> originalSolutions = solver.removeRotationBasedDuplicates(solutions);
		System.out.println("Removed rotation based duplicates and other look-alikes -> " + originalSolutions.size()
				+ " original solutions remaining");
		writeHtml(gameConfig, originalSolutions, "originalSolutions", "Original Solutions");
	}

	/**
	 * 
	 * @return number of card rotations tried since the last call of
	 *         findAllSolutions(...)
	 */
	public long numberOfTries() {
		return numberOfTries;
	}

	private static void writeHtml(GameConfig gameConfig, Collection<Field> solutions, String filename, String title)
			throws URISyntaxException, UnsupportedEncodingException, IOException {
		// Preparing HTML-Output
		StringBuffer sb = new StringBuffer();
		for (Field originalSolution : solutions) {
			sb.append(originalSolution.toHtmlString());
		}

		// Writing Html
		Path templateFile = Paths.get(Solver.class.getResource("all-solutions.template.html").toURI());
		String allSolutionsHtmlTemplate = new String(Files.readAllBytes(templateFile), "UTF-8");
		String allSolutionsHtml = allSolutionsHtmlTemplate.replace("%title%", title + " - " + gameConfig.getClass().getSimpleName()).replace("%content%",
				sb.toString());
		Path htmlOutputFile = Paths
				.get("html-output/" + gameConfig.getClass().getSimpleName() + "/" + filename + ".html");
		Files.createDirectories(htmlOutputFile.getParent());
		Files.write(htmlOutputFile, allSolutionsHtml.getBytes("UTF-8"));

		CardToGraphics cardToGraphics = new CardToGraphics();
		for (Card card : gameConfig.getAvailableCards()) {
			byte[] cardImage = cardToGraphics.convert(card, "png");
			Path imageOutputFile = Paths
					.get("html-output/" + gameConfig.getClass().getSimpleName() + "/card" + card.getId() + ".png");
			Files.write(imageOutputFile, cardImage);
		}
		System.out.println("Written \"" + title + "\" to file: " + htmlOutputFile);
	}

	public List<Field> findAllSolutions(GameConfig gameConfig) {
		numberOfTries = 0;
		Field emptyField = gameConfig.createEmptyField();
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
			gameConfig.output("Solutions:");
			partialSolutions = findSolutionsWithOneMoreCard(partialSolutions);
			gameConfig.output("Total: " + partialSolutions.size());
			Set<Field> solutions = new HashSet<Field>();
			for (PartialSolution currentSolution : partialSolutions) {
				solutions.add(currentSolution.getField());
			}
			solutions = gameConfig.filterSolutions(solutions);
			gameConfig.output("Filtered: " + solutions.size());
			return new LinkedList<Field>(solutions);
		} else {
			return findAllSolutions(emptyField, gameConfig.getAvailableCards());
		}

	}

	Set<PartialSolution> findSolutionsWithOneMoreCard(Collection<PartialSolution> partialSolutions) {
		Set<PartialSolution> partialSolutionsWithOneMoreCard = new HashSet<PartialSolution>();

		for (PartialSolution partialSolution : partialSolutions) {
			List<Field> nextPossibleMoves = nextPossibleMoves(partialSolution.getField(),
					partialSolution.getRemainingCards());
			for (Field nextPossibleMove : nextPossibleMoves) {
				List<Card> remaining = removed(nextPossibleMove.getLastCard(), partialSolution.getRemainingCards());
				PartialSolution partialSolutionWithOneMoreCard = new PartialSolution(nextPossibleMove, remaining);
				partialSolutionsWithOneMoreCard.add(partialSolutionWithOneMoreCard);
			}
		}
		return partialSolutionsWithOneMoreCard;
	}

	List<Field> findAllSolutions(Field field, List<Card> cards) {

		List<Field> solutions = new LinkedList<Field>();

		List<Field> nextPossibleMoves = nextPossibleMoves(field, cards);
		for (Field currentMove : nextPossibleMoves) {
			if (currentMove.isFull()) {
				solutions.add(currentMove);
			} else {
				List<Card> remaining = removed(currentMove.getLastCard(), cards);
				solutions.addAll(findAllSolutions(currentMove, remaining));
			}
		}

		return solutions;
	}

	List<Field> nextPossibleMoves(Field field, List<Card> remainingCards) {

		List<Field> fieldsWithOneMoreCard = new LinkedList<Field>();

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

	public List<Field> removeRotationBasedDuplicates(Collection<Field> solutions) {
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
		return new LinkedList<Field>(resultSet);
	}

	static List<Card> removed(Card lastcard, List<Card> cardsLeft) {
		List<Card> result = new LinkedList<Card>();
		for (Card currentcard : cardsLeft) {
			if (lastcard.getId() != currentcard.getId()) {
				result.add(currentcard);
			}
		}
		if (cardsLeft.size() - 1 != result.size()) {
			throw new RuntimeException(lastcard + "was not found in " + cardsLeft);
		}
		return result;
	}

}
