package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Asterix Kartenlegespiel": see
 * http://www.comedix.de/medien/lit/kartenlegespiel.php for more information
 *
 */
public class AsterixKartenlegespielConfig extends GameConfig {

	enum Picture implements IPicture {

		ASTERIX_UP(1), ASTERIX_LOW(1), OBELIX_UP(2), OBELIX_LOW(2), MIRACULIX_UP(3), MIRACULIX_LOW(3), MAJESTIX_UP(
				4), MAJESTIX_LOW(4);

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
				.add(new Card(Picture.ASTERIX_UP, Picture.MAJESTIX_LOW, Picture.MIRACULIX_LOW, Picture.OBELIX_UP));
		availableCards
				.add(new Card(Picture.OBELIX_LOW, Picture.MIRACULIX_UP, Picture.ASTERIX_LOW, Picture.MAJESTIX_UP));
		availableCards
				.add(new Card(Picture.MAJESTIX_LOW, Picture.ASTERIX_UP, Picture.OBELIX_UP, Picture.MIRACULIX_LOW));
		availableCards
				.add(new Card(Picture.OBELIX_LOW, Picture.MAJESTIX_UP, Picture.MIRACULIX_UP, Picture.ASTERIX_LOW));
		availableCards
				.add(new Card(Picture.MAJESTIX_LOW, Picture.MIRACULIX_LOW, Picture.ASTERIX_UP, Picture.OBELIX_UP));
		availableCards
				.add(new Card(Picture.MIRACULIX_UP, Picture.ASTERIX_LOW, Picture.MAJESTIX_UP, Picture.MAJESTIX_UP));
		availableCards
				.add(new Card(Picture.ASTERIX_UP, Picture.MAJESTIX_LOW, Picture.MIRACULIX_LOW, Picture.OBELIX_UP));
		availableCards
				.add(new Card(Picture.OBELIX_LOW, Picture.MIRACULIX_UP, Picture.ASTERIX_LOW, Picture.MAJESTIX_UP));
		availableCards
				.add(new Card(Picture.MAJESTIX_LOW, Picture.ASTERIX_UP, Picture.MIRACULIX_LOW, Picture.OBELIX_UP));
		return availableCards;
	}

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}
}
