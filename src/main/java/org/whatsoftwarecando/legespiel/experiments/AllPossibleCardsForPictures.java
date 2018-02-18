package org.whatsoftwarecando.legespiel.experiments;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.DuplicateCardsFinder;
import org.whatsoftwarecando.legespiel.IPicture;

public class AllPossibleCardsForPictures {

	public static List<Card> generateCards(IPicture[] pictures,
			boolean eliminateDuplicateCards) {
		List<Card> availableCards = new LinkedList<Card>();
		generateCardsIncludingDuplicates(availableCards, pictures);
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
		return availableCards;
	}

	private static void generateCardsIncludingDuplicates(
			List<Card> availableCardsResult,
			final IPicture[] picturesAvailable, IPicture... partialCard) {
		if (partialCard != null && partialCard.length == 4) {
			availableCardsResult.add(new Card(partialCard));
			return;
		}
		for (IPicture p : picturesAvailable) {
			IPicture[] newPictures = Arrays.copyOf(partialCard,
					partialCard.length + 1);
			newPictures[partialCard.length] = p;
			generateCardsIncludingDuplicates(availableCardsResult,
					picturesAvailable, newPictures);
		}
	}
}
