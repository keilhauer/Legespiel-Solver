package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Die Schweineknobelei". Find more information here:
 * https://slideplayer.org/slide/666364/
 */
public class DieSchweineknobeleiConfig extends GameConfig {

	enum Picture implements IPicture {

		PINK_UP(1), PINK_LOW(1), STRIPES_UP(2), STRIPES_LOW(2), BLACK_SPOTS_UP(3), BLACK_SPOTS_LOW(3), BLUE_DROPS_UP(
				4), BLUE_DROPS_DOWN(4);

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

		// Identical cards: 3rd, 5th
		availableCards.add(new Card(Picture.PINK_UP, Picture.STRIPES_UP, Picture.STRIPES_UP, Picture.PINK_UP));
		availableCards
				.add(new Card(Picture.BLUE_DROPS_UP, Picture.STRIPES_LOW, Picture.BLACK_SPOTS_UP, Picture.PINK_LOW));
		availableCards.add(new Card(Picture.PINK_UP, Picture.BLACK_SPOTS_LOW, Picture.STRIPES_UP, Picture.PINK_UP));
		availableCards
				.add(new Card(Picture.PINK_LOW, Picture.BLACK_SPOTS_LOW, Picture.BLACK_SPOTS_UP, Picture.STRIPES_UP));
		availableCards.add(new Card(Picture.PINK_UP, Picture.BLACK_SPOTS_LOW, Picture.STRIPES_UP, Picture.PINK_UP));
		availableCards
				.add(new Card(Picture.PINK_LOW, Picture.STRIPES_LOW, Picture.STRIPES_UP, Picture.BLUE_DROPS_DOWN));
		availableCards.add(
				new Card(Picture.STRIPES_LOW, Picture.BLUE_DROPS_DOWN, Picture.BLUE_DROPS_UP, Picture.BLACK_SPOTS_LOW));
		availableCards
				.add(new Card(Picture.PINK_LOW, Picture.BLUE_DROPS_DOWN, Picture.BLACK_SPOTS_UP, Picture.STRIPES_LOW));
		availableCards
				.add(new Card(Picture.BLUE_DROPS_UP, Picture.BLACK_SPOTS_LOW, Picture.PINK_UP, Picture.STRIPES_LOW));
		return availableCards;
	}
}
