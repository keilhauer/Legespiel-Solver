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
 * This is to confirm the theoretical result of: 4! : 4 (because there are 4
 * different rotations)
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
		List<List<Card>> result = new DuplicateCardsFinder().findDuplicateCards(picturesForCards);
		System.out.println("Number of different cards: " + result.size());
	}

	private static List<Card> getAvailableCards() {
		List<List<Picture>> picturesForCards = findPermutations(Arrays.asList(Picture.values()));
		List<Card> cards = new LinkedList<Card>();
		for (List<Picture> pictures : picturesForCards) {
			cards.add(new Card(pictures.get(0), pictures.get(1), pictures.get(2), pictures.get(3)));
		}
		return new ArrayList<Card>(cards);
	}

	private static List<List<Picture>> findPermutations(List<Picture> pictures) {
		List<List<Picture>> result = new LinkedList<List<Picture>>();
		if (pictures.size() == 1) {
			result.add(pictures);
		}

		for (Picture picture : pictures) {
			List<Picture> oneRemoved = new LinkedList<Picture>(pictures);
			oneRemoved.remove(picture);
			List<List<Picture>> permutationsWithOneRemoved = findPermutations(oneRemoved);
			for (List<Picture> current : permutationsWithOneRemoved) {
				List<Picture> permutation = new LinkedList<Picture>();
				permutation.add(picture);
				permutation.addAll(current);
				result.add(permutation);
			}
		}
		return result;
	}
}
