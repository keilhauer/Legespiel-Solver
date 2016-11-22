package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Knifflidiffels". This is version 5 according to
 * http://www.juliesdiddlsamling.dk/spil/spil01.htm#table3
 * 
 * Please find more information on this game and on duplicate cards under
 * http://whatsoftwarecando.org/en/solving-knifflidiffels-legespiel-duplicate-cards/
 */
public class KnifflidiffelsVersion5Config extends GameConfig {

	static final ArrayList<Card> AVAILABLE_CARDS = new ArrayList<Card>();

	static {
		// Cards 1-3 (identical)
		AVAILABLE_CARDS.add(new Card(Picture.DOG, Picture.BOY, Picture.GIRL,
				Picture.HORSE));
		AVAILABLE_CARDS.add(new Card(Picture.DOG, Picture.BOY, Picture.GIRL,
				Picture.HORSE));
		AVAILABLE_CARDS.add(new Card(Picture.DOG, Picture.BOY, Picture.GIRL,
				Picture.HORSE));

		// Cards 4-6 (identical)
		AVAILABLE_CARDS.add(new Card(Picture.BOY, Picture.GIRL, Picture.DOG,
				Picture.HORSE));
		AVAILABLE_CARDS.add(new Card(Picture.BOY, Picture.GIRL, Picture.DOG,
				Picture.HORSE));
		AVAILABLE_CARDS.add(new Card(Picture.BOY, Picture.GIRL, Picture.DOG,
				Picture.HORSE));

		// Cards 7
		AVAILABLE_CARDS.add(new Card(Picture.BOY, Picture.DOG, Picture.GIRL,
				Picture.HORSE));

		// Card 8-9 (identical)
		AVAILABLE_CARDS.add(new Card(Picture.DOG, Picture.GIRL, Picture.BOY,
				Picture.HORSE));
		AVAILABLE_CARDS.add(new Card(Picture.DOG, Picture.GIRL, Picture.BOY,
				Picture.HORSE));
	}

	enum Picture implements IPicture {

		DOG, BOY, GIRL, HORSE;

		@Override
		public boolean matches(IPicture other) {
			return this == other;
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
