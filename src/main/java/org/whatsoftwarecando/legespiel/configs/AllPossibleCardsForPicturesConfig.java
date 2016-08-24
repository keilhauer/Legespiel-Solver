package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.DuplicateCardsFinder;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.FieldWithOnlyOneSolution;
import org.whatsoftwarecando.legespiel.IGameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

public class AllPossibleCardsForPicturesConfig implements IGameConfig {

	private int rowsInField;
	private int colsInField;
	private boolean onlyOneSolutionPerCardSet;
	private ArrayList<Card> availableCards = new ArrayList<Card>();

	public AllPossibleCardsForPicturesConfig(){
		this(FourPictures.values(), 2, 2, true, true);
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
		this.onlyOneSolutionPerCardSet = onlyOneSolutionPerCardSet;
		System.out.println(this.availableCards);
		System.out.println(this.availableCards.size());
	}

	public enum FourPictures implements IPicture {

		RED, GREEN, BLUE;//, YELLOW;

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

	public static void main(String[] argv) {
		AllPossibleCardsForPicturesConfig cardFactory = new AllPossibleCardsForPicturesConfig(FourPictures.values(), 3,
				3, true, true);
		System.out.println(cardFactory);
		System.out.println(cardFactory.getAvailableCards().size());
	}

	@Override
	public Field createEmptyField() {
		if (onlyOneSolutionPerCardSet) {
			return new FieldWithOnlyOneSolution(this.rowsInField, this.colsInField);
		} else {
			return new Field(this.rowsInField, this.colsInField);
		}
	}
}
