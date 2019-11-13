package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * B Dazzle Retriever Scramble Squares according to
 * https://kalmas.net/blog/2014-01-01_scramble-squares/
 */
public class RetrieverScrambleSquaresConfig extends GameConfig {

	enum Picture implements IPicture {

		GOLDEN_1(1), GOLDEN_2(1), BLACK_1(2), BLACK_2(2), YELLOW_1(3), YELLOW_2(3), CHOCOLATE_1(4), CHOCOLATE_2(4);
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
		availableCards.add(new Card(Picture.YELLOW_1, Picture.BLACK_1, Picture.CHOCOLATE_2, Picture.GOLDEN_1));
		availableCards.add(new Card(Picture.GOLDEN_1, Picture.CHOCOLATE_1, Picture.YELLOW_1, Picture.BLACK_2));
		availableCards.add(new Card(Picture.CHOCOLATE_1, Picture.YELLOW_2, Picture.GOLDEN_1, Picture.BLACK_1));
		availableCards.add(new Card(Picture.GOLDEN_2, Picture.CHOCOLATE_1, Picture.YELLOW_1, Picture.BLACK_1));
		availableCards.add(new Card(Picture.BLACK_1, Picture.YELLOW_2, Picture.GOLDEN_1, Picture.CHOCOLATE_1));
		availableCards.add(new Card(Picture.BLACK_2, Picture.GOLDEN_2, Picture.YELLOW_2, Picture.CHOCOLATE_2));
		availableCards.add(new Card(Picture.BLACK_2, Picture.GOLDEN_1, Picture.YELLOW_2, Picture.CHOCOLATE_1));
		availableCards.add(new Card(Picture.CHOCOLATE_2, Picture.YELLOW_1, Picture.GOLDEN_2, Picture.BLACK_1));
		availableCards.add(new Card(Picture.CHOCOLATE_1, Picture.GOLDEN_1, Picture.BLACK_1, Picture.YELLOW_1));
		return availableCards;
	}

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}
}