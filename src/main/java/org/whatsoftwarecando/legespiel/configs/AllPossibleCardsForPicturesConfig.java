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

	private int rowsInField;
	private int colsInField;
	private ArrayList<Card> availableCards = new ArrayList<Card>();

	public AllPossibleCardsForPicturesConfig() {
		this(FourPictures.values(), 3, 3, true, true);
	}

	public AllPossibleCardsForPicturesConfig(IPicture[] picturesAvailable, int rowsInField, int colsInField,
			boolean onlyOneSolutionPerCardSet, boolean eliminateDuplicateCards) {
		init(picturesAvailable);
		if (eliminateDuplicateCards) {
			List<List<Card>> duplicates = new DuplicateCardsFinder().findDuplicateCards(availableCards);
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
		System.out.println("Number of available cards: " + this.availableCards.size());
	}

	public enum FourPictures implements IPicture {

		RED, GREEN, BLUE;// , YELLOW;

		@Override
		public boolean matches(IPicture other) {
			return this == other;
		}
	}

	private void init(final IPicture[] picturesAvailable, IPicture... prtialSolution) {
		if (prtialSolution != null && prtialSolution.length == 4) {
			availableCards.add(new Card(prtialSolution));
			return;
		}
		for (IPicture p : picturesAvailable) {
			IPicture[] newPictures = Arrays.copyOf(prtialSolution, prtialSolution.length + 1);
			newPictures[prtialSolution.length] = p;
			init(picturesAvailable, newPictures);
		}
	}

	@Override
	public ArrayList<Card> getAvailableCards() {
		return availableCards;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [availableCards=" + availableCards + "]";
	}

	@Override
	public Field createEmptyField() {
		return new Field(this.rowsInField, this.colsInField);
	}

	@Override
	public List<Field> filterSolutions(List<Field> solutions) {
		Map<Set<Integer>, Field> solutionIdsWithFirstFound = new HashMap<Set<Integer>, Field>();

		Set<Field> solutionSet = new HashSet<Field>();
		for (Field solution : solutions) {
			Set<Integer> solutionIds = idsForField(solution);
			Field alreadyFound = solutionIdsWithFirstFound.put(solutionIds, solution);
			if (alreadyFound == null) {
				solutionSet.add(solution);
			} else {
				solutionSet.remove(alreadyFound);
			}
		}
		return new LinkedList<Field>(solutionSet);
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

	@Override
	public List<PartialSolution> filterPartialSolutions(List<PartialSolution> partialSolutions) {
		Map<Set<Integer>, HashMap<List<IPicture>, Field>> partialSolutionIdsWithBorderlines = new HashMap<Set<Integer>, HashMap<List<IPicture>, Field>>();
		Set<PartialSolution> partialSolutionSet = new HashSet<PartialSolution>();
		for (PartialSolution partialSolution : partialSolutions) {
			Field field = partialSolution.getField();
			Set<Integer> idsForField = idsForField(field);
			HashMap<List<IPicture>, Field> borderlinesWithFirstFound = partialSolutionIdsWithBorderlines
					.get(idsForField);
			if (borderlinesWithFirstFound == null) {
				borderlinesWithFirstFound = new HashMap<List<IPicture>, Field>();
				partialSolutionIdsWithBorderlines.put(idsForField, borderlinesWithFirstFound);
			}
			List<IPicture> borderline = calcBorderline(field);
			Field alreadyFound = borderlinesWithFirstFound.put(borderline, field);
			if (alreadyFound == null) {
				partialSolutionSet.add(partialSolution);
			} else {
				partialSolutionSet.remove(alreadyFound);
			}
		}
		return new LinkedList<PartialSolution>(partialSolutionSet);
	}

	List<IPicture> calcBorderline(Field field) {
		List<IPicture> borderline = new LinkedList<IPicture>();
		if (field.getCurrentCoordinates().getCol() < field.getCols()) {
			borderline.add(field.getCurrentCoordinates().currentCard().getEast());
		}
		CardCoordinate nextCoordinate = field.getCurrentCoordinates();
		for (int i = 1; i <= field.getRows(); i++) {
			nextCoordinate = nextCoordinate.next();
			if (nextCoordinate == null) {
				return borderline;
			}
			Card northCard = nextCoordinate.northCard();
			if (northCard != null) {
				borderline.add(northCard.getSouth());
			}
		}
		return borderline;
	}
	
	@Override
	public boolean isBfsNeeded(){
		return true;
	}
}
