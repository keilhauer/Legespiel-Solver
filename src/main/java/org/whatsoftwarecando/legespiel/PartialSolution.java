package org.whatsoftwarecando.legespiel;

import java.util.List;

public class PartialSolution {

	private Field field;
	
	private List<Card> remainingCards;

	public PartialSolution(Field field, List<Card> remainingCards) {
		this.field = field;
		this.remainingCards = remainingCards;
	}

	public Field getField() {
		return field;
	}

	public List<Card> getRemainingCards() {
		return remainingCards;
	}
	
	
}
