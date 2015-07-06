package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.IGameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Uli Stein: Noch verwzickter". See
 * http://www.lenz-online.de/aboutme/history/ulstein0.htm for more information.
 */
public class UliSteinNochVerzwickterConfig implements IGameConfig {

	static final ArrayList<Card> AVAILABLE_CARDS = new ArrayList<Card>();

	static {
		AVAILABLE_CARDS.add(new Card(Picture.PENGUIN_RIGHT,
				Picture.MOUSE_RIGHT, Picture.CAT_LEFT, Picture.PIG_LEFT));
		AVAILABLE_CARDS.add(new Card(Picture.PIG_LEFT, Picture.CAT_RIGHT,
				Picture.MOUSE_RIGHT, Picture.PENGUIN_LEFT));
		AVAILABLE_CARDS.add(new Card(Picture.CAT_RIGHT, Picture.MOUSE_LEFT,
				Picture.PENGUIN_LEFT, Picture.PIG_RIGHT));
		AVAILABLE_CARDS.add(new Card(Picture.PIG_RIGHT, Picture.PENGUIN_LEFT,
				Picture.MOUSE_RIGHT, Picture.CAT_LEFT));
		AVAILABLE_CARDS.add(new Card(Picture.PENGUIN_RIGHT, Picture.MOUSE_LEFT,
				Picture.CAT_LEFT, Picture.PIG_RIGHT));
		AVAILABLE_CARDS.add(new Card(Picture.PIG_LEFT, Picture.CAT_RIGHT,
				Picture.MOUSE_LEFT, Picture.PENGUIN_RIGHT));
		AVAILABLE_CARDS.add(new Card(Picture.CAT_RIGHT, Picture.PIG_LEFT,
				Picture.PENGUIN_LEFT, Picture.MOUSE_RIGHT));
		AVAILABLE_CARDS.add(new Card(Picture.PIG_LEFT, Picture.PENGUIN_RIGHT,
				Picture.MOUSE_LEFT, Picture.CAT_RIGHT));
		AVAILABLE_CARDS.add(new Card(Picture.PENGUIN_LEFT, Picture.MOUSE_RIGHT,
				Picture.PIG_RIGHT, Picture.CAT_LEFT));
	}

	enum Picture implements IPicture {

		MOUSE_LEFT(1), MOUSE_RIGHT(1), PIG_LEFT(2), PIG_RIGHT(2), CAT_LEFT(3), CAT_RIGHT(
				3), PENGUIN_LEFT(4), PENGUIN_RIGHT(4);

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
