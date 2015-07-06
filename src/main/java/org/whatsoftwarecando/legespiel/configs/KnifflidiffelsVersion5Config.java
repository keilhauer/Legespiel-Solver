package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.IGameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Knifflidiffels". This is version 5 according to
 * http://www.juliesdiddlsamling.dk/spil/spil01.htm#table3
 */
public class KnifflidiffelsVersion5Config implements IGameConfig {

	static final ArrayList<Card> AVAILABLE_CARDS = new ArrayList<Card>();

	static {
		//Cards 1-3 (identical)
		AVAILABLE_CARDS.add(new Card(Picture.MOUSE, Picture.BOY,
				Picture.GIRL, Picture.HORSE));
		AVAILABLE_CARDS.add(new Card(Picture.MOUSE, Picture.BOY,
				Picture.GIRL, Picture.HORSE));
		AVAILABLE_CARDS.add(new Card(Picture.MOUSE, Picture.BOY,
				Picture.GIRL, Picture.HORSE));
		
		//Cards 4-6 (identical)
		AVAILABLE_CARDS.add(new Card(Picture.BOY, Picture.GIRL,
				Picture.MOUSE, Picture.HORSE));
		AVAILABLE_CARDS.add(new Card(Picture.BOY, Picture.GIRL,
				Picture.MOUSE, Picture.HORSE));
		AVAILABLE_CARDS.add(new Card(Picture.BOY, Picture.GIRL,
				Picture.MOUSE, Picture.HORSE));
		
		//Cards 7-8 (identical)
		AVAILABLE_CARDS.add(new Card(Picture.BOY, Picture.MOUSE,
				Picture.GIRL, Picture.HORSE));
		AVAILABLE_CARDS.add(new Card(Picture.BOY, Picture.MOUSE,
				Picture.GIRL, Picture.HORSE));
		
		//Card 9
		AVAILABLE_CARDS.add(new Card(Picture.MOUSE, Picture.GIRL, Picture.BOY,
				Picture.HORSE));
	}

	enum Picture implements IPicture {

		MOUSE, BOY, GIRL, HORSE;

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
