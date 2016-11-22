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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
		result = prime * result
				+ ((remainingCards == null) ? 0 : remainingCards.hashCode());
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
		PartialSolution other = (PartialSolution) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		if (remainingCards == null) {
			if (other.remainingCards != null)
				return false;
		} else if (!remainingCards.equals(other.remainingCards))
			return false;
		return true;
	}

}
