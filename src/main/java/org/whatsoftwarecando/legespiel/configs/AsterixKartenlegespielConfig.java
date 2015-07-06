package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.IGameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Asterix Kartenlegespiel": see
 * http://www.comedix.de/medien/lit/kartenlegespiel.php for more information
 *
 */
public class AsterixKartenlegespielConfig implements IGameConfig {

	static final ArrayList<Card> AVAILABLE_CARDS = new ArrayList<Card>();

	static {
		AVAILABLE_CARDS.add(new Card(Picture.ASTERIX_UPPER,
				Picture.MAJESTIX_LOWER, Picture.MIRACULIX_LOWER,
				Picture.OBELIX_UPPER));
		AVAILABLE_CARDS.add(new Card(Picture.OBELIX_LOWER,
				Picture.MIRACULIX_UPPER, Picture.ASTERIX_LOWER,
				Picture.MAJESTIX_UPPER));
		AVAILABLE_CARDS.add(new Card(Picture.MAJESTIX_LOWER,
				Picture.ASTERIX_UPPER, Picture.OBELIX_UPPER,
				Picture.MIRACULIX_LOWER));
		AVAILABLE_CARDS.add(new Card(Picture.OBELIX_LOWER,
				Picture.MAJESTIX_UPPER, Picture.MIRACULIX_UPPER,
				Picture.ASTERIX_LOWER));
		AVAILABLE_CARDS.add(new Card(Picture.MAJESTIX_LOWER,
				Picture.MIRACULIX_LOWER, Picture.ASTERIX_UPPER,
				Picture.OBELIX_UPPER));
		AVAILABLE_CARDS.add(new Card(Picture.MIRACULIX_UPPER,
				Picture.ASTERIX_LOWER, Picture.MAJESTIX_UPPER,
				Picture.MAJESTIX_UPPER));
		AVAILABLE_CARDS.add(new Card(Picture.ASTERIX_UPPER,
				Picture.MAJESTIX_LOWER, Picture.MIRACULIX_LOWER,
				Picture.OBELIX_UPPER));
		AVAILABLE_CARDS.add(new Card(Picture.OBELIX_LOWER,
				Picture.MIRACULIX_UPPER, Picture.ASTERIX_LOWER,
				Picture.MAJESTIX_UPPER));
		AVAILABLE_CARDS.add(new Card(Picture.MAJESTIX_LOWER,
				Picture.ASTERIX_UPPER, Picture.MIRACULIX_LOWER,
				Picture.OBELIX_UPPER));
	}

	enum Picture implements IPicture {

		ASTERIX_UPPER(1), ASTERIX_LOWER(1), OBELIX_UPPER(2), OBELIX_LOWER(2), MIRACULIX_UPPER(
				3), MIRACULIX_LOWER(3), MAJESTIX_UPPER(4), MAJESTIX_LOWER(4);

		private final int pairNumber;

		private Picture(int pairNumber) {
			this.pairNumber = pairNumber;
		}

		@Override
		public boolean matches(IPicture other) {
			if (other instanceof Picture) {
				return this.pairNumber == ((Picture) other).pairNumber
						&& this != other;
			} else {
				return false;
			}

		}
	}

	@Override
	public ArrayList<Card> getAvailableCards() {
		return AVAILABLE_CARDS;
	}

	@Override
	public Field createEmptyField() {
		return new Field(3, 3);
	}
}
