package org.whatsoftwarecando.legespiel.configs;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Condition;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.Field.CardCoordinate;
import org.whatsoftwarecando.legespiel.FieldWithConditions;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;
import org.whatsoftwarecando.legespiel.PartialSolution;
import org.whatsoftwarecando.legespiel.Solver;
import org.whatsoftwarecando.legespiel.experiments.AllPossibleCardsForPictures;

public class ExactlyOneSolutionConfig extends GameConfig {

	private final int rowsInField;
	private final int colsInField;
	private final boolean eliminateDuplicateCards;

	public ExactlyOneSolutionConfig() {
		this(3, 3, true);
	}

	private ExactlyOneSolutionConfig(int rowsInField, int colsInField, boolean eliminateDuplicateCards) {
		this.rowsInField = rowsInField;
		this.colsInField = colsInField;
		this.eliminateDuplicateCards = eliminateDuplicateCards;
		this.output("Available cards: " + this.getAvailableCards());
		this.output("Number of available cards: " + this.getAvailableCards().size());
	}

	public enum Pictures implements IPicture {

		RED, GREEN, BLUE; // YELLOW;

		@Override
		public boolean matches(IPicture other) {
			return this == other;
		}
	}

	@Override
	protected void createAvailableCards() {
		List<Card> allCards = AllPossibleCardsForPictures.generateCards(Pictures.values(), eliminateDuplicateCards,
				this.cardCreator);
		this.getAvailableCards().addAll(allCards);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [availableCards=" + this.getAvailableCards() + "]";
	}

	@Override
	protected Field createEmptyField() {
		return new Field(this.rowsInField, this.colsInField);
	}

	@Override
	public Set<Field> filterSolutions(Collection<Field> solutions) {
		Map<Set<Integer>, Field> solutionIdsWithFirstFound = new HashMap<Set<Integer>, Field>();

		int countFiltered = 0;
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
			if (++countFiltered % 1000 == 0) {
				System.out.print("#");
			}
		}

		this.output("\nFirst filter done.");
		countFiltered = 0;
		Set<Field> noDuplicates = new HashSet<Field>();
		for (Field currentSolution : solutionIdsWithFirstFound.values()) {
			if (currentSolution != null) {
				noDuplicates.add(currentSolution);
				if (++countFiltered % 1000 == 0) {
					System.out.print("#");
				}
			}
		}
		this.output("\nDuplicates removed.");
		this.output("Solutions left: " + noDuplicates.size());
		Set<Field> onlyOneSolution = new HashSet<Field>();
		int countCorrect = 0;
		int countIncorrect = 0;
		for (Field solution : noDuplicates) {
			GenericGameConfig currentSolutionConfig = new GenericGameConfig(solution.getAllCards(),
					this.getEmptyField());
			Solver solver = new Solver(currentSolutionConfig);
			solver.findAllSolutions();
			List<Field> solutionsWithoutRotations = solver.removeRotationBasedDuplicates();
			if (solutionsWithoutRotations.size() == 1) {
				if (++countCorrect % 1000 == 0) {
					System.out.print("1");
				}
				onlyOneSolution.add(solution);
			} else {
				if (++countIncorrect % 1000 == 0) {
					System.out.print("m");
				}
			}
		}
		this.output("\nExtracted all configurations with exactly one solution.");

		return onlyOneSolution;
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

	private static int count;
	private static int countEasy;
	private static int countWithOne;
	private static int countWithMore;
	private static double numberOfSolutions;
	private static Set<PartialSolution> filtered;
	private static Map<PartialSolutionId, PartialSolution> alreadyCalculated;

	private static final Object workLock = new Object();

	@Override
	public Set<PartialSolution> filterPartialSolutions(Collection<PartialSolution> partialSolutions) {

		alreadyCalculated = Collections.synchronizedMap(new HashMap<PartialSolutionId, PartialSolution>());
		count = 0;
		countEasy = 0;
		partialSolutions.parallelStream().forEach(partialSolution -> filterOne(partialSolution));

		this.output("\nFirst filter done.");
		countEasy = 0;
		countWithOne = 0;
		countWithMore = 0;
		numberOfSolutions = 0;
		filtered = Collections.synchronizedSet(new HashSet<PartialSolution>());

		alreadyCalculated.values().parallelStream().forEach((PartialSolution current) -> filterTwo(current));

		this.output("\nAverage number of solutions: " + (numberOfSolutions / count));
		return filtered;
	}

	private void filterOne(PartialSolution partialSolution) {
		Field currentField = partialSolution.getField();
		Set<Integer> idsForField = idsForField(currentField);

		List<Condition> borderline = calcBorderline(currentField);
		PartialSolutionId partialSolutionId = new PartialSolutionId(idsForField, borderline);
		synchronized (workLock) {
			if (alreadyCalculated.containsKey(partialSolutionId)) {
				alreadyCalculated.put(partialSolutionId, null);
				if (++countEasy % 1000 == 0) {
					System.out.print("!");
				}
			} else {
				alreadyCalculated.put(partialSolutionId, partialSolution);
				if (++count % 1000 == 0) {
					System.out.print("#");
				}
			}
		}
	}

	private void filterTwo(PartialSolution current) {
		if (current == null) {
			if (++countEasy % 1000 == 0) {
				System.out.print("!");
			}
		} else {
			Field currentField = current.getField();
			List<Card> allCards = currentField.getAllCards();
			List<Condition> borderline = calcBorderline(currentField);
			FieldWithConditions fieldWithConditions = new FieldWithConditions(currentField.getRows(),
					currentField.getCols(), allCards.size(), borderline);
			Solver solver = new Solver(new GenericGameConfig(allCards, fieldWithConditions));
			Set<Field> solutions = new HashSet<Field>(
					solver.findAllSolutions());
			if (solutions.size() == 1) {
				filtered.add(current);
				if (++countWithOne % 1000 == 0) {
					System.out.print("o");
				}
			} else {
				if (++countWithMore % 1000 == 0) {
					System.out.print("m");
				}
			}

			numberOfSolutions += solutions.size();

		}
	}

	private class PartialSolutionId {

		Set<Integer> idsForField;
		List<Condition> conditions;

		/**
		 * @param idsForField
		 * @param conditions
		 */
		public PartialSolutionId(Set<Integer> idsForField, List<Condition> conditions) {
			this.idsForField = idsForField;
			this.conditions = conditions;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((conditions == null) ? 0 : conditions.hashCode());
			result = prime * result + ((idsForField == null) ? 0 : idsForField.hashCode());
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

		private ExactlyOneSolutionConfig getOuterType() {
			return ExactlyOneSolutionConfig.this;
		}

	}

	List<Condition> calcBorderline(Field field) {
		List<Condition> borderline = new LinkedList<Condition>();
		CardCoordinate coordinate = field.getCurrentCoordinates();
		if (field.getCurrentCoordinates().getCol() < field.getCols()) {
			Condition condition = new Condition(coordinate.getRow(), coordinate.getCol(),
					coordinate.currentCard().getEast(), null);
			borderline.add(condition);
		}
		for (int i = 1; i <= field.getCols(); i++) {
			coordinate = coordinate.next();
			if (coordinate == null) {
				return borderline;
			}
			Card northCard = coordinate.northCard();
			if (northCard != null) {
				Condition condition = new Condition(coordinate.getRow() - 1, coordinate.getCol(), null,
						northCard.getSouth());
				borderline.add(condition);
			}
		}
		return borderline;
	}

	@Override
	public boolean isBfsNeeded() {
		return true;
	}

	@Override
	public boolean isFilterLookAlikesDuringSearch() {
		return true;
	}

}
