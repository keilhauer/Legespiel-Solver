package org.whatsoftwarecando.legespiel;

import java.util.List;

public class PartialSolution {

	private final Field field;
	
	private final List<Card> remainingCards;

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
