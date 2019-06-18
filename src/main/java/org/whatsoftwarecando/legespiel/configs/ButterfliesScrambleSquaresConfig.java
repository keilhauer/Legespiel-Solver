package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Butterflies Scramble Squares according to
 * https://www.worthpoint.com/worthopedia/scramble-squares-puzzles-lot-hot-air-1849556943
 */
public class ButterfliesScrambleSquaresConfig extends GameConfig {

	enum Picture implements IPicture {

		RED_UP(1), RED_LOW(1), ORANGE_UP(2), ORANGE_LOW(2), YELLOW_BLACK_UP(3), YELLOW_BLACK_LOW(3), BLUE_UP(
				4), BLUE_LOW(4);
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
		availableCards.add(new Card(Picture.RED_LOW, Picture.ORANGE_LOW, Picture.YELLOW_BLACK_UP, Picture.BLUE_LOW));
		availableCards.add(new Card(Picture.RED_UP, Picture.YELLOW_BLACK_LOW, Picture.BLUE_LOW, Picture.ORANGE_LOW));
		availableCards.add(new Card(Picture.RED_LOW, Picture.BLUE_UP, Picture.YELLOW_BLACK_UP, Picture.ORANGE_UP));
		availableCards.add(new Card(Picture.BLUE_UP, Picture.RED_UP, Picture.ORANGE_LOW, Picture.YELLOW_BLACK_LOW));
		availableCards.add(new Card(Picture.ORANGE_UP, Picture.BLUE_UP, Picture.YELLOW_BLACK_UP, Picture.RED_LOW));
		availableCards.add(new Card(Picture.ORANGE_LOW, Picture.YELLOW_BLACK_LOW, Picture.BLUE_LOW, Picture.RED_UP));
		availableCards
				.add(new Card(Picture.YELLOW_BLACK_UP, Picture.YELLOW_BLACK_LOW, Picture.ORANGE_LOW, Picture.BLUE_UP));
		availableCards.add(new Card(Picture.RED_UP, Picture.ORANGE_UP, Picture.ORANGE_UP, Picture.BLUE_LOW));
		availableCards.add(new Card(Picture.RED_LOW, Picture.BLUE_LOW, Picture.YELLOW_BLACK_LOW, Picture.RED_UP));
		return availableCards;
	}

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}
}