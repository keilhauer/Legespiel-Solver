package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on Witches Puzzle (see
 * http://georg.graf.priv.at/programming/witches-puzzle/ )
 */
public class WitchesPuzzleConfig extends GameConfig {

	enum Picture implements IPicture {

		YELLOW_1(1), YELLOW_2(1), GREEN_1(2), GREEN_2(2), RED_1(3), RED_2(3), BLUE_1(4), BLUE_2(4);

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
	protected Field createEmptyField() {
		return new Field(3, 3);
	}

	@Override
	protected ArrayList<Card> createAvailableCards() {
		ArrayList<Card> availableCards = new ArrayList<Card>();
		availableCards.add(new Card(Picture.GREEN_1, Picture.YELLOW_2, Picture.RED_1, Picture.GREEN_2));
		availableCards.add(new Card(Picture.GREEN_1, Picture.RED_2, Picture.BLUE_1, Picture.YELLOW_2));
		availableCards.add(new Card(Picture.YELLOW_1, Picture.BLUE_1, Picture.GREEN_2, Picture.RED_2));
		availableCards.add(new Card(Picture.GREEN_2, Picture.RED_1, Picture.YELLOW_2, Picture.BLUE_1));
		availableCards.add(new Card(Picture.RED_2, Picture.BLUE_2, Picture.BLUE_1, Picture.YELLOW_1));
		availableCards.add(new Card(Picture.BLUE_2, Picture.RED_2, Picture.GREEN_1, Picture.BLUE_1));
		availableCards.add(new Card(Picture.BLUE_2, Picture.RED_1, Picture.YELLOW_2, Picture.GREEN_1));
		availableCards.add(new Card(Picture.RED_2, Picture.GREEN_1, Picture.BLUE_2, Picture.BLUE_1));
		availableCards.add(new Card(Picture.RED_2, Picture.YELLOW_1, Picture.YELLOW_2, Picture.GREEN_1));
		return availableCards;
	}
}
