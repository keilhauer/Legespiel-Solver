package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on Das verflixte Schildkrötenspiel (see
 * https://www.youtube.com/watch?v=p5Pzv99DnKI )
 */
public class DasVerflixteSchildkroetenSpielConfig extends GameConfig {

	enum Picture implements IPicture {

		RED_YELLOW_UPPER(1), RED_YELLOW_LOWER(1), RED_GREEN_UPPER(2), RED_GREEN_LOWER(2), RED_BLACK_UPPER(
				3), RED_BLACK_LOWER(3), GREEN_YELLOW_UPPER(4), GREEN_YELLOW_LOWER(4);

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
		// Duplicate cards: 3rd, 8th
		availableCards.add(new Card(Picture.RED_YELLOW_LOWER, Picture.RED_GREEN_LOWER, Picture.RED_BLACK_UPPER,
				Picture.GREEN_YELLOW_UPPER));
		availableCards.add(new Card(Picture.RED_GREEN_LOWER, Picture.RED_BLACK_LOWER, Picture.GREEN_YELLOW_UPPER,
				Picture.RED_YELLOW_UPPER));
		availableCards.add(new Card(Picture.RED_GREEN_LOWER, Picture.GREEN_YELLOW_LOWER, Picture.RED_BLACK_UPPER,
				Picture.RED_YELLOW_UPPER));
		availableCards.add(new Card(Picture.GREEN_YELLOW_LOWER, Picture.RED_YELLOW_LOWER, Picture.RED_BLACK_UPPER,
				Picture.RED_GREEN_UPPER));
		availableCards.add(new Card(Picture.RED_YELLOW_LOWER, Picture.RED_BLACK_LOWER, Picture.GREEN_YELLOW_UPPER,
				Picture.RED_GREEN_UPPER));
		availableCards.add(new Card(Picture.RED_YELLOW_LOWER, Picture.GREEN_YELLOW_LOWER, Picture.GREEN_YELLOW_UPPER,
				Picture.RED_GREEN_UPPER));
		availableCards.add(new Card(Picture.RED_GREEN_LOWER, Picture.RED_YELLOW_LOWER, Picture.GREEN_YELLOW_UPPER,
				Picture.RED_BLACK_UPPER));
		availableCards.add(new Card(Picture.RED_GREEN_LOWER, Picture.GREEN_YELLOW_LOWER, Picture.RED_BLACK_UPPER,
				Picture.RED_YELLOW_UPPER));
		availableCards.add(new Card(Picture.RED_GREEN_LOWER, Picture.RED_BLACK_LOWER, Picture.RED_BLACK_UPPER,
				Picture.RED_YELLOW_UPPER));
		return availableCards;
	}

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}
}
