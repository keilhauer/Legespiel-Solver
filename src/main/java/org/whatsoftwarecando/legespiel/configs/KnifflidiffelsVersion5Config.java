package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Knifflidiffels". This is version 5 according to
 * https://whatsoftwarecando.org/list-of-scramble-squares-puzzles-with-solutions/
 * 
 * Please find more information on this game and on duplicate cards under
 * http://whatsoftwarecando.org/en/solving-knifflidiffels-legespiel-duplicate-cards/
 */
public class KnifflidiffelsVersion5Config extends GameConfig {

	enum Picture implements IPicture {

		DOG, BOY, GIRL, HORSE;

		@Override
		public boolean matches(IPicture other) {
			return this == other;
		}
	}

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}

	@Override
	protected ArrayList<Card> createAvailableCards() {
		ArrayList<Card> availableCards = new ArrayList<Card>();

		// Cards 1-3 (identical)
		availableCards.add(new Card(Picture.DOG, Picture.BOY, Picture.GIRL, Picture.HORSE));
		availableCards.add(new Card(Picture.DOG, Picture.BOY, Picture.GIRL, Picture.HORSE));
		availableCards.add(new Card(Picture.DOG, Picture.BOY, Picture.GIRL, Picture.HORSE));

		// Cards 4-6 (identical)
		availableCards.add(new Card(Picture.BOY, Picture.GIRL, Picture.DOG, Picture.HORSE));
		availableCards.add(new Card(Picture.BOY, Picture.GIRL, Picture.DOG, Picture.HORSE));
		availableCards.add(new Card(Picture.BOY, Picture.GIRL, Picture.DOG, Picture.HORSE));

		// Cards 7
		availableCards.add(new Card(Picture.BOY, Picture.DOG, Picture.GIRL, Picture.HORSE));

		// Card 8-9 (identical)
		availableCards.add(new Card(Picture.DOG, Picture.GIRL, Picture.BOY, Picture.HORSE));
		availableCards.add(new Card(Picture.DOG, Picture.GIRL, Picture.BOY, Picture.HORSE));
		return availableCards;
	}
}
