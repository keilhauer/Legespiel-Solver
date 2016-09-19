package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.DuplicateCardsFinder;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.Field.CardCoordinate;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;
import org.whatsoftwarecando.legespiel.PartialSolution;

public class AllPossibleCardsForPicturesConfig extends GameConfig {

	private final int rowsInField;
	private final int colsInField;
	private final ArrayList<Card> availableCards = new ArrayList<Card>();

	public AllPossibleCardsForPicturesConfig() {
		this(FourPictures.values(), 3, 2, true, true);
	}

	public AllPossibleCardsForPicturesConfig(IPicture[] picturesAvailable,
			int rowsInField, int colsInField,
			boolean onlyOneSolutionPerCardSet, boolean eliminateDuplicateCards) {
		init(picturesAvailable);
		if (eliminateDuplicateCards) {
			List<List<Card>> duplicates = new DuplicateCardsFinder()
					.findDuplicateCards(availableCards);
			if (duplicates.size() < availableCards.size()) {
				availableCards.clear();
				for (List<Card> currentCardEqClass : duplicates) {
					availableCards.add(currentCardEqClass.get(0));
				}
			}
		}
		this.rowsInField = rowsInField;
		this.colsInField = colsInField;
		System.out.println("Available cards: " + this.availableCards);
		System.out.println("Number of available cards: "
				+ this.availableCards.size());
	}

	public enum FourPictures implements IPicture {

		RED, GREEN, BLUE;// YELLOW;

		@Override
		public boolean matches(IPicture other) {
			return this == other;
		}
	}

	private void init(final IPicture[] picturesAvailable,
			IPicture... partialCard) {
		if (partialCard != null && partialCard.length == 4) {
			availableCards.add(new Card(partialCard));
			return;
		}
		for (IPicture p : picturesAvailable) {
			IPicture[] newPictures = Arrays.copyOf(partialCard,
					partialCard.length + 1);
			newPictures[partialCard.length] = p;
			init(picturesAvailable, newPictures);
		}
	}

	@Override
	public ArrayList<Card> getAvailableCards() {
		return availableCards;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [availableCards="
				+ availableCards + "]";
	}

	@Override
	public Field createEmptyField() {
		return new Field(this.rowsInField, this.colsInField);
	}

	@Override
	public List<Field> filterSolutions(List<Field> solutions) {
		Map<Set<Integer>, Field> solutionIdsWithFirstFound = new HashMap<Set<Integer>, Field>();

		for (Field solution : solutions) {
			Set<Integer> solutionIds = idsForField(solution);
			if (solutionIdsWithFirstFound.containsKey(solutionIds)) {
				Field alreadyFound = solutionIdsWithFirstFound.get(solutionIds);
				if (alreadyFound == null) {
					continue;
				}
				if (solution.equals(alreadyFound)) {
					continue;
				}
				Field solution90 = solution.turned90DegreesClockwise();
				if (solution90.equals(alreadyFound)) {
					continue;
				}
				Field solution180 = solution90.turned90DegreesClockwise();
				if (solution180.equals(alreadyFound)) {
					continue;
				}
				Field solution270 = solution180.turned90DegreesClockwise();
				if (solution270.equals(alreadyFound)) {
					continue;
				}
				solutionIdsWithFirstFound.put(solutionIds, null);
			} else {
				solutionIdsWithFirstFound.put(solutionIds, solution);
			}

		}
		List<Field> result = new LinkedList<Field>();
		for (Field currentSolution : solutionIdsWithFirstFound.values()) {
			if (currentSolution != null) {
				result.add(currentSolution);
			}
		}
		return result;
	}

	private Set<Integer> idsForField(Field field) {
		HashSet<Integer> idsForField = new HashSet<Integer>();
		for (int row = 1; row <= field.getRows(); row++) {
			for (int col = 1; col <= field.getCols(); col++) {
				Card card = field.getCard(row, col);
				if (card == null) {
					return idsForField;
				}
				idsForField.add(card.getId());
			}
		}
		return idsForField;
	}

	public List<PartialSolution> filterPartialSolutionsWrong(
			List<PartialSolution> partialSolutions) {
		Map<Set<Integer>, HashMap<List<IPicture>, PartialSolution>> partialSolutionIdsWithBorderlines = new HashMap<Set<Integer>, HashMap<List<IPicture>, PartialSolution>>();

		for (PartialSolution partialSolution : partialSolutions) {
			Set<Integer> idsForField = idsForField(partialSolution.getField());
			HashMap<List<IPicture>, PartialSolution> borderlinesWithFirstFound = partialSolutionIdsWithBorderlines
					.get(idsForField);
			if (partialSolutionIdsWithBorderlines.containsKey(idsForField)) {
				if (borderlinesWithFirstFound == null) {
					continue;
				}
			}
			if (borderlinesWithFirstFound == null) {
				borderlinesWithFirstFound = new HashMap<List<IPicture>, PartialSolution>();
				partialSolutionIdsWithBorderlines.put(idsForField,
						borderlinesWithFirstFound);
			}
			List<IPicture> borderline = calcBorderline(partialSolution
					.getField());
			if (borderlinesWithFirstFound.containsKey(borderline)) {
				PartialSolution alreadyFound = borderlinesWithFirstFound
						.get(borderline);
				if (alreadyFound != null) {
					if (!partialSolution.getField().equals(
							alreadyFound.getField())) {
						partialSolutionIdsWithBorderlines
								.put(idsForField, null);
					}
				}
			} else {
				borderlinesWithFirstFound.put(borderline, partialSolution);
			}
		}
		LinkedList<PartialSolution> result = new LinkedList<PartialSolution>();
		for (HashMap<List<IPicture>, PartialSolution> partialSolutionsForIds : partialSolutionIdsWithBorderlines
				.values()) {
			if (partialSolutionsForIds != null) {
				for (PartialSolution currentPartialSolution : partialSolutionsForIds
						.values()) {
					if (currentPartialSolution != null) {
						result.add(currentPartialSolution);
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<PartialSolution> filterPartialSolutions(
			List<PartialSolution> partialSolutions) {
		Map<Set<Integer>, HashMap<List<IPicture>, PartialSolution>> partialSolutionIdsWithBorderlines = new HashMap<Set<Integer>, HashMap<List<IPicture>, PartialSolution>>();

		for (PartialSolution partialSolution : partialSolutions) {
			Set<Integer> idsForField = idsForField(partialSolution.getField());
//			if(idsForField.size() == 2 && idsForField.contains(14) && idsForField.contains(17)){
//				System.out.println("!");
//			}
			HashMap<List<IPicture>, PartialSolution> borderlinesWithFirstFound = partialSolutionIdsWithBorderlines
					.get(idsForField);
			if (borderlinesWithFirstFound == null) {
				borderlinesWithFirstFound = new HashMap<List<IPicture>, PartialSolution>();
				partialSolutionIdsWithBorderlines.put(idsForField,
						borderlinesWithFirstFound);
			}
			List<IPicture> borderline = calcBorderline(partialSolution
					.getField());
			if (borderlinesWithFirstFound.containsKey(borderline)) {
				PartialSolution alreadyFound = borderlinesWithFirstFound
						.get(borderline);
				if (alreadyFound != null) {
					if (!partialSolution.getField().equals(
							alreadyFound.getField())) {
						borderlinesWithFirstFound.put(borderline, null);
					}
				}
			} else {
				borderlinesWithFirstFound.put(borderline, partialSolution);
			}
		}
		LinkedList<PartialSolution> result = new LinkedList<PartialSolution>();
		for (HashMap<List<IPicture>, PartialSolution> partialSolutionsForIds : partialSolutionIdsWithBorderlines
				.values()) {
			for (PartialSolution currentPartialSolution : partialSolutionsForIds
					.values()) {
				if (currentPartialSolution != null) {
					result.add(currentPartialSolution);
				}
			}
		}
		return result;
	}

	List<IPicture> calcBorderline(Field field) {
		List<IPicture> borderline = new LinkedList<IPicture>();
		if (field.getCurrentCoordinates().getCol() < field.getCols()) {
			borderline.add(field.getCurrentCoordinates().currentCard()
					.getEast());
		}
		CardCoordinate coordinate = field.getCurrentCoordinates();
		for (int i = 1; i <= field.getCols(); i++) {
			coordinate = coordinate.next();
			if (coordinate == null) {
				return borderline;
			}
			Card northCard = coordinate.northCard();
			if (northCard != null) {
				borderline.add(northCard.getSouth());
			}
		}
		return borderline;
	}

	@Override
	public boolean isBfsNeeded() {
		return true;
	}
}
