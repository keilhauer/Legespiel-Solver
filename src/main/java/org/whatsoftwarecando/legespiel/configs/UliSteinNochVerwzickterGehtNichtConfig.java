package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Uli Stein: Noch verwzickter geht nicht!". See
 * http://www.gerdkoch.de/nochverwzickter/index.html for more information.
 */
public class UliSteinNochVerwzickterGehtNichtConfig extends GameConfig {

	enum Picture implements IPicture {

		FOOTBALL_LEFT(1), FOOTBALL_RIGHT(1), TEA_LEFT(2), TEA_RIGHT(2), GLASS_LEFT(3), GLASS_RIGHT(3), REACHING_LEFT(
				4), REACHING_RIGHT(4);

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
		availableCards
				.add(new Card(Picture.GLASS_RIGHT, Picture.REACHING_RIGHT, Picture.FOOTBALL_LEFT, Picture.TEA_LEFT));
		availableCards
				.add(new Card(Picture.TEA_LEFT, Picture.FOOTBALL_RIGHT, Picture.REACHING_RIGHT, Picture.GLASS_LEFT));
		availableCards
				.add(new Card(Picture.FOOTBALL_RIGHT, Picture.REACHING_LEFT, Picture.GLASS_LEFT, Picture.TEA_RIGHT));
		availableCards
				.add(new Card(Picture.TEA_RIGHT, Picture.GLASS_LEFT, Picture.REACHING_RIGHT, Picture.FOOTBALL_LEFT));
		availableCards
				.add(new Card(Picture.GLASS_RIGHT, Picture.REACHING_LEFT, Picture.FOOTBALL_LEFT, Picture.TEA_RIGHT));
		availableCards
				.add(new Card(Picture.TEA_LEFT, Picture.FOOTBALL_RIGHT, Picture.REACHING_LEFT, Picture.GLASS_RIGHT));
		availableCards
				.add(new Card(Picture.FOOTBALL_RIGHT, Picture.TEA_LEFT, Picture.GLASS_LEFT, Picture.REACHING_RIGHT));
		availableCards
				.add(new Card(Picture.TEA_LEFT, Picture.GLASS_RIGHT, Picture.REACHING_LEFT, Picture.FOOTBALL_RIGHT));
		availableCards
				.add(new Card(Picture.GLASS_LEFT, Picture.REACHING_RIGHT, Picture.TEA_RIGHT, Picture.FOOTBALL_LEFT));
		return availableCards;
	}
}
