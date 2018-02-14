package org.whatsoftwarecando.legespiel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class GameConfig {

	private ArrayList<Card> AVAILABLE_CARDS_INSTANCE = null;

	protected abstract ArrayList<Card> createAvailableCards();

	public synchronized ArrayList<Card> getAvailableCardsInstance() {
		if (AVAILABLE_CARDS_INSTANCE == null) {
			AVAILABLE_CARDS_INSTANCE = createAvailableCards();
		}
		return AVAILABLE_CARDS_INSTANCE;
	}

	protected abstract Field createEmptyField();

	private Field EMPTY_FIELD_INSTANCE = null;

	public synchronized final Field getEmptyFieldInstance() {
		if (EMPTY_FIELD_INSTANCE == null) {
			EMPTY_FIELD_INSTANCE = createEmptyField();
		}
		return EMPTY_FIELD_INSTANCE;
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

}