package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * see https://www.flickr.com/photos/greatshotsbyjf/6649252505
 */
public class CatsScrambleSquaresConfig extends GameConfig {

	enum Picture implements IPicture {

		WHITE_GRAY_UP(1), WHITE_GRAY_LOW(1), BROWN_UP(2), BROWN_LOW(2), WHITE_RED_UP(3), WHITE_RED_LOW(3), BLACK_LEFT(
				4), BLACK_RIGHT(4);

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
		availableCards
				.add(new Card(Picture.BROWN_LOW, Picture.WHITE_GRAY_LOW, Picture.BLACK_LEFT, Picture.WHITE_RED_LOW));
		availableCards
				.add(new Card(Picture.WHITE_RED_UP, Picture.BLACK_RIGHT, Picture.WHITE_GRAY_LOW, Picture.BROWN_UP));
		availableCards
				.add(new Card(Picture.BLACK_RIGHT, Picture.WHITE_GRAY_UP, Picture.WHITE_RED_UP, Picture.BLACK_RIGHT));
		availableCards
				.add(new Card(Picture.WHITE_RED_UP, Picture.WHITE_GRAY_UP, Picture.BLACK_RIGHT, Picture.BROWN_UP));
		availableCards
				.add(new Card(Picture.BROWN_LOW, Picture.BLACK_LEFT, Picture.WHITE_GRAY_UP, Picture.WHITE_RED_LOW));
		availableCards
				.add(new Card(Picture.BLACK_LEFT, Picture.WHITE_GRAY_LOW, Picture.BROWN_UP, Picture.WHITE_RED_UP));
		availableCards
				.add(new Card(Picture.BROWN_LOW, Picture.WHITE_RED_UP, Picture.WHITE_GRAY_LOW, Picture.BLACK_LEFT));
		availableCards
				.add(new Card(Picture.WHITE_RED_UP, Picture.WHITE_GRAY_UP, Picture.BROWN_UP, Picture.WHITE_GRAY_LOW));
		availableCards.add(new Card(Picture.WHITE_RED_LOW, Picture.BROWN_LOW, Picture.BROWN_LOW, Picture.BLACK_LEFT));
		return availableCards;
	}

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}
}