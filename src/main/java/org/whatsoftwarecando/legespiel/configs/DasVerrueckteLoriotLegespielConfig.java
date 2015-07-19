package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.IGameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Das verrückte Loriot Legespiel". Find a video of one
 * solution here https://www.youtube.com/watch?v=YvcbBUoSa_U
 */
public class DasVerrueckteLoriotLegespielConfig implements IGameConfig {

	static final ArrayList<Card> AVAILABLE_CARDS = new ArrayList<Card>();

	static {
		AVAILABLE_CARDS.add(new Card(Picture.MAN_1, Picture.WOMAN_GREEN_2,
				Picture.MAN_HAT_1, Picture.WOMAN_BLUE_2));
		AVAILABLE_CARDS.add(new Card(Picture.WOMAN_BLUE_1, Picture.MAN_HAT_2,
				Picture.MAN_2, Picture.WOMAN_GREEN_1));
		AVAILABLE_CARDS.add(new Card(Picture.WOMAN_GREEN_2, Picture.MAN_1,
				Picture.MAN_HAT_1, Picture.WOMAN_BLUE_2));
		
		// cards 4 and 5 are identical
		AVAILABLE_CARDS.add(new Card(Picture.WOMAN_BLUE_1, Picture.WOMAN_GREEN_1,
				Picture.MAN_HAT_2, Picture.MAN_2));
		AVAILABLE_CARDS.add(new Card(Picture.WOMAN_BLUE_1,
				Picture.WOMAN_GREEN_1, Picture.MAN_HAT_2, Picture.MAN_2));
		
		// cards 6 and 7 are identical
		AVAILABLE_CARDS.add(new Card(Picture.WOMAN_GREEN_2, Picture.MAN_HAT_1,
				Picture.MAN_1, Picture.WOMAN_BLUE_2));
		AVAILABLE_CARDS.add(new Card(Picture.WOMAN_GREEN_2, Picture.MAN_HAT_1,
				Picture.MAN_1, Picture.WOMAN_BLUE_2));
		
		AVAILABLE_CARDS.add(new Card(Picture.WOMAN_BLUE_1, Picture.MAN_2,
				Picture.WOMAN_GREEN_1, Picture.WOMAN_GREEN_1));
		AVAILABLE_CARDS.add(new Card(Picture.MAN_1, Picture.MAN_HAT_1,
				Picture.WOMAN_GREEN_2, Picture.WOMAN_BLUE_2));


	}

	enum Picture implements IPicture {

		MAN_1(1), MAN_2(1), MAN_HAT_1(2), MAN_HAT_2(2), WOMAN_GREEN_1(3), WOMAN_GREEN_2(
				3), WOMAN_BLUE_1(4), WOMAN_BLUE_2(4);

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
