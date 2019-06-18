package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Hot Air Balloons Scramble Squares according to
 * https://www.worthpoint.com/worthopedia/scramble-squares-puzzles-lot-hot-air-1849556943
 */
public class HotAirBalloonsScrambleSquaresConfig extends GameConfig {

	enum Picture implements IPicture {

		RAINBOW_SQUARES_UP(1), RAINBOW_SQUARES_LOW(1), EAGLE_LEFT(2), EAGLE_RIGHT(2), VIOLET_YELLOW_LEFT(
				3), VIOLET_YELLOW_RIGHT(3), ZIGZAG_UP(4), ZIGZAG_LOW(4);
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
		availableCards.add(new Card(Picture.VIOLET_YELLOW_LEFT, Picture.RAINBOW_SQUARES_UP, Picture.ZIGZAG_UP,
				Picture.EAGLE_LEFT));
		availableCards.add(new Card(Picture.EAGLE_LEFT, Picture.ZIGZAG_LOW, Picture.VIOLET_YELLOW_LEFT,
				Picture.RAINBOW_SQUARES_UP));
		availableCards.add(new Card(Picture.ZIGZAG_UP, Picture.VIOLET_YELLOW_RIGHT, Picture.RAINBOW_SQUARES_UP,
				Picture.EAGLE_LEFT));
		availableCards.add(new Card(Picture.EAGLE_RIGHT, Picture.RAINBOW_SQUARES_LOW, Picture.ZIGZAG_LOW,
				Picture.VIOLET_YELLOW_LEFT));
		availableCards.add(new Card(Picture.RAINBOW_SQUARES_LOW, Picture.ZIGZAG_UP, Picture.VIOLET_YELLOW_RIGHT,
				Picture.RAINBOW_SQUARES_UP));
		availableCards.add(new Card(Picture.EAGLE_RIGHT, Picture.VIOLET_YELLOW_RIGHT, Picture.VIOLET_YELLOW_LEFT,
				Picture.ZIGZAG_UP));
		availableCards.add(new Card(Picture.VIOLET_YELLOW_RIGHT, Picture.ZIGZAG_UP, Picture.RAINBOW_SQUARES_LOW,
				Picture.EAGLE_RIGHT));
		availableCards.add(
				new Card(Picture.RAINBOW_SQUARES_LOW, Picture.ZIGZAG_LOW, Picture.EAGLE_LEFT, Picture.EAGLE_RIGHT));
		availableCards.add(new Card(Picture.ZIGZAG_UP, Picture.VIOLET_YELLOW_RIGHT, Picture.EAGLE_LEFT,
				Picture.RAINBOW_SQUARES_LOW));
		return availableCards;
	}

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}
}