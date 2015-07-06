package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.IGameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Knifflidiffels". This is version 4 according to
 * http://www.juliesdiddlsamling.dk/spil/spil01.htm#table3
 */
public class KnifflidiffelsVersion4Config implements IGameConfig {

	static final ArrayList<Card> AVAILABLE_CARDS = new ArrayList<Card>();

	static {
		// Cards 1-4 (identical)
		AVAILABLE_CARDS.add(new Card(Picture.HORSE, Picture.BOY,
				Picture.SHEEPS, Picture.GIRL));
		AVAILABLE_CARDS.add(new Card(Picture.HORSE, Picture.BOY,
				Picture.SHEEPS, Picture.GIRL));
		AVAILABLE_CARDS.add(new Card(Picture.HORSE, Picture.BOY,
				Picture.SHEEPS, Picture.GIRL));
		AVAILABLE_CARDS.add(new Card(Picture.HORSE, Picture.BOY,
				Picture.SHEEPS, Picture.GIRL));

		// Cards 5-6 (identical)
		AVAILABLE_CARDS.add(new Card(Picture.GIRL, Picture.BOY, Picture.SHEEPS,
				Picture.HORSE));
		AVAILABLE_CARDS.add(new Card(Picture.GIRL, Picture.BOY, Picture.SHEEPS,
				Picture.HORSE));

		// Cards 7-8 (identical)
		AVAILABLE_CARDS.add(new Card(Picture.SHEEPS, Picture.BOY, Picture.GIRL,
				Picture.HORSE));
		AVAILABLE_CARDS.add(new Card(Picture.SHEEPS, Picture.BOY, Picture.GIRL,
				Picture.HORSE));

		//Card 9
		AVAILABLE_CARDS.add(new Card(Picture.BOY, Picture.SHEEPS,
				Picture.HORSE, Picture.GIRL));

	}

	enum Picture implements IPicture {

		BOY, GIRL, HORSE, SHEEPS;

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
