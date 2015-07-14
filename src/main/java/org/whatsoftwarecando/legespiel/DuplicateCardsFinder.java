package org.whatsoftwarecando.legespiel;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DuplicateCardsFinder {

	public List<List<Card>> findDuplicateCards(List<Card> cards) {
		List<List<Card>> result = new LinkedList<List<Card>>();
		findDuplicates(cards, result);
		return result;
	}
	
	private void findDuplicates(List<Card> cards, List<List<Card>> result) {
		List<Card> cardsReduced = new LinkedList<Card>(cards);
		Card firstCard = cardsReduced.remove(0);
		Set<Card> cardsToBeRemoved = new HashSet<Card>();
		cardsToBeRemoved.add(firstCard);
		Card turned90 = firstCard.turned90DegreesClockwise();
		cardsToBeRemoved.add(turned90);
		Card turned180 = turned90.turned90DegreesClockwise();
		cardsToBeRemoved.add(turned180);
		Card turned270 = turned180.turned90DegreesClockwise();
		cardsToBeRemoved.add(turned270);
		cardsReduced.removeAll(cardsToBeRemoved);
		List<Card> currentEq = new LinkedList<Card>(cards);
		currentEq.removeAll(cardsReduced);
		result.add(currentEq);
		if (!cardsReduced.isEmpty()) {
			findDuplicates(cardsReduced, result);
		}
	}
}
