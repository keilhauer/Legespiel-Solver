package org.whatsoftwarecando.legespiel.experiments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.DuplicateCardsFinder;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * This is in experiment based on Knifflidiffels Version 5 to find out how many
 * different cards you can create, that have all of 4 possible pictures on them
 * and are different (even when rotating them).
 * 
 * This is to confirm the theoretical result of: 4! : 4 (because there are 4 different
 * rotations)
 * 
 * @author Andreas
 *
 */
public class HowManyPermutations {

	static final ArrayList<Card> AVAILABLE_CARDS = new ArrayList<Card>();

	enum Picture implements IPicture {

		DOG, BOY, GIRL, HORSE;

		@Override
		public boolean matches(IPicture other) {
			return this == other;
		}
	}

	public static void main(String[] argv) {
		List<Card> picturesForCards = getAvailableCards();
		List<List<Card>> result = new DuplicateCardsFinder()
				.findDuplicateCards(picturesForCards);
		System.out.println("Number of different cards: " + result.size());
	}

	private static List<Card> getAvailableCards() {
		List<List<IPicture>> picturesForCards = findPermutations(Arrays
				.asList(Picture.values()));
		List<Card> cards = new LinkedList<Card>();
		for (List<IPicture> pictures : picturesForCards) {
			cards.add(new Card(pictures.get(0), pictures.get(1), pictures
					.get(2), pictures.get(3)));
		}
		return new ArrayList<Card>(cards);
	}

	private static List<List<IPicture>> findPermutations(List<IPicture> pictures) {
		List<List<IPicture>> result = new LinkedList<List<IPicture>>();
		if (pictures.size() == 1) {
			result.add(pictures);
		}

		for (IPicture picture : pictures) {
			List<IPicture> oneRemoved = new LinkedList<IPicture>(pictures);
			oneRemoved.remove(picture);
			List<List<IPicture>> permutationsWithOneRemoved = findPermutations(oneRemoved);
			for (List<IPicture> current : permutationsWithOneRemoved) {
				List<IPicture> permutation = new LinkedList<IPicture>();
				permutation.add(picture);
				permutation.addAll(current);
				result.add(permutation);
			}
		}
		return result;
	}
}
