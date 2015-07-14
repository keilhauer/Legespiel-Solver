package org.whatsoftwarecando.legespiel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.whatsoftwarecando.legespiel.configs.AbsolutKniffligConfig;

public class Solver {

	private long numberOfTries;

	public static void main(String[] argv) throws IOException,
			URISyntaxException, InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		IGameConfig gameConfig = null;
		if (argv.length == 0) {
			gameConfig = new AbsolutKniffligConfig();
		} else {
			gameConfig = (IGameConfig) Class
					.forName(
							Solver.class.getPackage().getName() + ".configs."
									+ argv[0]).newInstance();
		}
		System.out.println("Using GameConfig: "
				+ gameConfig.getClass().getSimpleName());
		System.out.println("Looking for duplicate cards: ");
		boolean foundDuplicateCards = false;
		for (List<Card> duplicates : new DuplicateCardsFinder()
				.findDuplicateCards(gameConfig.getAvailableCards())) {
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
		System.out.println("Tried " + solver.numberOfTries()
				+ " card rotations -> Found all " + solutions.size()
				+ " solutions in " + Util.nanosToMilliseconds(timeNeeded)
				+ " ms");
		writeHtml(gameConfig, solutions, "allSolutions", "All Solutions");

		List<Field> originalSolutions = solver
				.removeRotationBasedDuplicates(solutions);
		System.out
				.println("Removed rotation based duplicates and other look-alikes -> "
						+ originalSolutions.size()
						+ " original solutions remaining");
		writeHtml(gameConfig, originalSolutions, "allOriginalSolutions",
				"All Original Solutions");
	}

	/**
	 * 
	 * @return number of card rotations tried since the last call of
	 *         findAllSolutions(...)
	 */
	public long numberOfTries() {
		return numberOfTries;
	}

	private static void writeHtml(IGameConfig gameConfig,
			List<Field> solutions, String filename, String title)
			throws URISyntaxException, UnsupportedEncodingException,
			IOException {
		// Preparing HTML-Output
		StringBuffer sb = new StringBuffer();
		for (Field originalSolution : solutions) {
			sb.append(originalSolution.toHtmlString());
		}

		// Writing Html
		Path templateFile = Paths.get(Solver.class.getResource(
				"all-solutions.html.template").toURI());
		String allSolutionsHtmlTemplate = new String(
				Files.readAllBytes(templateFile), "UTF-8");
		String allSolutionsHtml = allSolutionsHtmlTemplate.replace("%title%",
				title).replace("%content%", sb.toString());
		Path htmlOutputFile = Paths.get("html-output/"
				+ gameConfig.getClass().getSimpleName() + "/" + filename
				+ ".html");
		Files.createDirectories(htmlOutputFile.getParent());
		Files.write(htmlOutputFile, allSolutionsHtml.getBytes("UTF-8"));
		System.out.println("Written \"" + title + "\" to file: "
				+ htmlOutputFile);
	}

	public List<Field> findAllSolutions(IGameConfig gameConfig) {
		return findAllSolutionsStart(gameConfig.createEmptyField(),
				gameConfig.getAvailableCards());
	}

	List<Field> findAllSolutionsStart(Field field, List<Card> cards) {
		numberOfTries = 0;
		return findAllSolutions(field, cards);
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

	List<Field> removeRotationBasedDuplicates(List<Field> solutions) {
		LinkedHashSet<Field> resultSet = new LinkedHashSet<Field>(solutions);
		for (Field solution : solutions) {
			if (resultSet.contains(solution)) {
				Field turned90 = solution.turned90DegreesClockwise();
				Field turned180 = turned90.turned90DegreesClockwise();
				Field turned270 = turned180.turned90DegreesClockwise();
				resultSet.remove(turned90);
				resultSet.remove(turned180);
				resultSet.remove(turned270);
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
			throw new RuntimeException(lastcard + "was not found in "
					+ cardsLeft);
		}
		return result;
	}

}
