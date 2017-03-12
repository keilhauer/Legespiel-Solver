package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on Crazy9 Ketner Owls (see http://heye-puzzle.de/product/crazy9-ketner-owls/ )
 */
public class Crazy9KetnerOwls extends GameConfig{

	static final ArrayList<Card> AVAILABLE_CARDS = new ArrayList<Card>();

	static {
		AVAILABLE_CARDS.add(new Card(Picture.BLUE_2, Picture.PURPLE_1,
				Picture.GREEN_2, Picture.BLUE_1));
		AVAILABLE_CARDS.add(new Card(Picture.PINK_2, Picture.GREEN_1,
				Picture.PURPLE_2, Picture.BLUE_1));
		AVAILABLE_CARDS.add(new Card(Picture.BLUE_2, Picture.PURPLE_1,
				Picture.GREEN_2, Picture.PINK_1));
		AVAILABLE_CARDS.add(new Card(Picture.BLUE_2, Picture.GREEN_1,
				Picture.PINK_2, Picture.PURPLE_1));
		AVAILABLE_CARDS.add(new Card(Picture.BLUE_2, Picture.PINK_1,
				Picture.PURPLE_2, Picture.GREEN_1));
		AVAILABLE_CARDS.add(new Card(Picture.PINK_2, Picture.PURPLE_1,
				Picture.BLUE_2, Picture.GREEN_1));
		AVAILABLE_CARDS.add(new Card(Picture.PURPLE_2, Picture.PINK_2,
				Picture.PINK_1, Picture.GREEN_1));
		AVAILABLE_CARDS.add(new Card(Picture.GREEN_2, Picture.PINK_2,
				Picture.BLUE_1, Picture.PURPLE_1));
		AVAILABLE_CARDS.add(new Card(Picture.GREEN_2, Picture.BLUE_2,
				Picture.PINK_1, Picture.PURPLE_1));
	}

	enum Picture implements IPicture {

		PURPLE_1(1), PURPLE_2(1), PINK_1(2), PINK_2(2), GREEN_1(3), GREEN_2(3), BLUE_1(4), BLUE_2(4);

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
