package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Absolut knifflig!". See
 * https://whatsoftwarecando.org/backtracking-nursery-solve-scramble-squares/
 * for more information.
 */
public class AbsolutKniffligConfig extends GameConfig {

	enum Picture implements IPicture {

		PIPPI_1(1), PIPPI_2(1), MONKEY_1(2), MONKEY_2(2), HORSE_1(3), HORSE_2(3);

		private final int pairNumber;

		private Picture(int pairNumber) {
			this.pairNumber = pairNumber;
		}

		@Override
		public boolean matches(IPicture other) {
			if (other instanceof Picture) {
				return this.pairNumber == ((Picture) other).pairNumber && this != other;
			} else {
				return false;
			}

		}
	}

	@Override
	protected ArrayList<Card> createAvailableCards() {
		ArrayList<Card> availableCards = new ArrayList<Card>();
		availableCards.add(new Card(Picture.MONKEY_2, Picture.PIPPI_1, Picture.HORSE_2, Picture.PIPPI_1));
		availableCards.add(new Card(Picture.MONKEY_2, Picture.HORSE_1, Picture.PIPPI_1, Picture.MONKEY_2));
		availableCards.add(new Card(Picture.HORSE_2, Picture.PIPPI_2, Picture.PIPPI_1, Picture.MONKEY_2));
		availableCards.add(new Card(Picture.PIPPI_2, Picture.HORSE_2, Picture.HORSE_1, Picture.MONKEY_1));
		availableCards.add(new Card(Picture.MONKEY_1, Picture.HORSE_2, Picture.PIPPI_2, Picture.HORSE_2));
		availableCards.add(new Card(Picture.MONKEY_1, Picture.PIPPI_1, Picture.HORSE_1, Picture.PIPPI_1));
		availableCards.add(new Card(Picture.MONKEY_2, Picture.MONKEY_1, Picture.HORSE_1, Picture.PIPPI_2));
		availableCards.add(new Card(Picture.HORSE_1, Picture.HORSE_2, Picture.MONKEY_1, Picture.PIPPI_2));
		availableCards.add(new Card(Picture.PIPPI_2, Picture.HORSE_1, Picture.MONKEY_2, Picture.MONKEY_1));
		return availableCards;
	}

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}
}
