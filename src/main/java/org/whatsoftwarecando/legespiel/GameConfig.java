package org.whatsoftwarecando.legespiel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class GameConfig {

	protected ArrayList<Card> availableCards = null;

	protected CardCreator cardCreator = null;

	protected abstract void createAvailableCards();

	public GameConfig() {
		this.cardCreator = new CardCreator();
	}

	public final synchronized ArrayList<Card> getAvailableCards() {
		if (availableCards == null) {
			availableCards = new ArrayList<Card>();
			createAvailableCards();
		}
		return availableCards;
	}

	protected abstract Field createEmptyField();

	protected Field emptyField = null;

	public synchronized final Field getEmptyField() {
		if (emptyField == null) {
			emptyField = createEmptyField();
		}
		return emptyField;
	}

	public boolean isBfsNeeded() {
		return false;
	}

	public Set<PartialSolution> filterPartialSolutions(Collection<PartialSolution> partialSolutions) {
		return new HashSet<PartialSolution>(partialSolutions);
	}

	public Set<Field> filterSolutions(Collection<Field> solutions) {
		return new HashSet<Field>(solutions);
	}

	public void output(String str) {
		System.out.println(str);
	}

	protected void addCard(IPicture north, IPicture west, IPicture east, IPicture south) {
		this.availableCards.add(cardCreator.createCard(north, west, east, south));
	}

}