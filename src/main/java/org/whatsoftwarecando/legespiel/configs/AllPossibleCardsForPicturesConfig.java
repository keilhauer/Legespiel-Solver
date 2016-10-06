package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Condition;
import org.whatsoftwarecando.legespiel.DuplicateCardsFinder;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.Field.CardCoordinate;
import org.whatsoftwarecando.legespiel.FieldWithConditions;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;
import org.whatsoftwarecando.legespiel.PartialSolution;
import org.whatsoftwarecando.legespiel.Solver;

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
	public Set<Field> filterSolutions(Collection<Field> solutions) {
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
		Set<Field> result = new HashSet<Field>();
		for (Field currentSolution : solutionIdsWithFirstFound.values()) {
			if (currentSolution != null) {
				result.add(currentSolution);
			}
		}
		return result;
		// return solutions;
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
		Map<Set<Integer>, HashMap<List<Condition>, PartialSolution>> partialSolutionIdsWithBorderlines = new HashMap<Set<Integer>, HashMap<List<Condition>, PartialSolution>>();

		for (PartialSolution partialSolution : partialSolutions) {
			Set<Integer> idsForField = idsForField(partialSolution.getField());
			HashMap<List<Condition>, PartialSolution> borderlinesWithFirstFound = partialSolutionIdsWithBorderlines
					.get(idsForField);
			if (partialSolutionIdsWithBorderlines.containsKey(idsForField)) {
				if (borderlinesWithFirstFound == null) {
					continue;
				}
			}
			if (borderlinesWithFirstFound == null) {
				borderlinesWithFirstFound = new HashMap<List<Condition>, PartialSolution>();
				partialSolutionIdsWithBorderlines.put(idsForField,
						borderlinesWithFirstFound);
			}
			List<Condition> borderline = calcBorderline(partialSolution
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
		for (HashMap<List<Condition>, PartialSolution> partialSolutionsForIds : partialSolutionIdsWithBorderlines
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
	public Set<PartialSolution> filterPartialSolutions(
			Collection<PartialSolution> partialSolutions) {
		Set<PartialSolutionId> alreadyCalculated = new HashSet<PartialSolutionId>();

		Solver solver = new Solver();
		int count = 0;
		int countEasy = 0;
		double numberOfSolutions = 0;
		Set<PartialSolution> filtered = new HashSet<PartialSolution>();
		for (PartialSolution partialSolution : partialSolutions) {
			Field currentField = partialSolution.getField();
			Set<Integer> idsForField = idsForField(currentField);

			List<Condition> borderline = calcBorderline(currentField);
			PartialSolutionId partialSolutionId = new PartialSolutionId(
					idsForField, borderline);
			if (!alreadyCalculated.contains(partialSolutionId)) {
				List<Card> allCards = currentField.getAllCards();
				FieldWithConditions fieldWithConditions = new FieldWithConditions(
						currentField.getRows(), currentField.getCols(),
						allCards.size(), borderline);
				Set<Field> solutions = new HashSet<Field>(
						solver.findAllSolutions(new TestGameConfig(allCards,
								fieldWithConditions)));
				if (solutions.size() == 1) {
					filtered.add(partialSolution);
				}
				numberOfSolutions += solutions.size();
				alreadyCalculated.add(partialSolutionId);
				if (++count % 1000 == 0) {
					System.out.print("#");
				}
			} else {
				if (++countEasy % 1000 == 0) {
					System.out.print("!");
				}
			}

		}
		System.out.println("Average number of solutions: "+(numberOfSolutions / count));
		return filtered;
		// return partialSolutions;
	}

	private class PartialSolutionId {

		Set<Integer> idsForField;
		List<Condition> conditions;

		/**
		 * @param idsForField
		 * @param conditions
		 */
		public PartialSolutionId(Set<Integer> idsForField,
				List<Condition> conditions) {
			this.idsForField = idsForField;
			this.conditions = conditions;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result
					+ ((conditions == null) ? 0 : conditions.hashCode());
			result = prime * result
					+ ((idsForField == null) ? 0 : idsForField.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PartialSolutionId other = (PartialSolutionId) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (conditions == null) {
				if (other.conditions != null)
					return false;
			} else if (!conditions.equals(other.conditions))
				return false;
			if (idsForField == null) {
				if (other.idsForField != null)
					return false;
			} else if (!idsForField.equals(other.idsForField))
				return false;
			return true;
		}

		private AllPossibleCardsForPicturesConfig getOuterType() {
			return AllPossibleCardsForPicturesConfig.this;
		}

	}

	List<Condition> calcBorderline(Field field) {
		List<Condition> borderline = new LinkedList<Condition>();
		CardCoordinate coordinate = field.getCurrentCoordinates();
		if (field.getCurrentCoordinates().getCol() < field.getCols()) {
			Condition condition = new Condition(coordinate.getRow(),
					coordinate.getCol(), coordinate.currentCard().getEast(),
					null);
			borderline.add(condition);
		}
		for (int i = 1; i <= field.getCols(); i++) {
			coordinate = coordinate.next();
			if (coordinate == null) {
				return borderline;
			}
			Card northCard = coordinate.northCard();
			if (northCard != null) {
				Condition condition = new Condition(coordinate.getRow() - 1,
						coordinate.getCol(), null, northCard.getSouth());
				borderline.add(condition);
			}
		}
		return borderline;
	}

	@Override
	public boolean isBfsNeeded() {
		return true;
	}
}
