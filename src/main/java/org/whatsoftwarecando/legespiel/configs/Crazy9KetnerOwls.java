package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on Crazy9 Ketner Owls (see
 * http://heye-puzzle.de/product/crazy9-ketner-owls/ ). More pictures here:
 * https://goldkindchen.blogspot.com/2017/03/produktvorstellung-crazy-9-das.html
 */
public class Crazy9KetnerOwls extends GameConfig {

	enum Picture implements IPicture {

		PURPLE_1(1), PURPLE_2(1), PINK_1(2), PINK_2(2), GREEN_1(3), GREEN_2(3), BLUE_1(4), BLUE_2(4);

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
		// Duplicate cards: 3rd, 8th
		availableCards.add(new Card(Picture.GREEN_1, Picture.PURPLE_1, Picture.BLUE_2, Picture.PINK_2));
		availableCards.add(new Card(Picture.PURPLE_1, Picture.BLUE_1, Picture.PINK_2, Picture.GREEN_2));
		availableCards.add(new Card(Picture.PURPLE_1, Picture.PINK_1, Picture.BLUE_2, Picture.GREEN_2));
		availableCards.add(new Card(Picture.PINK_1, Picture.GREEN_1, Picture.BLUE_2, Picture.PURPLE_2));
		availableCards.add(new Card(Picture.GREEN_1, Picture.BLUE_1, Picture.PINK_2, Picture.PURPLE_2));
		availableCards.add(new Card(Picture.GREEN_1, Picture.PINK_1, Picture.PINK_2, Picture.PURPLE_2));
		availableCards.add(new Card(Picture.PURPLE_1, Picture.GREEN_1, Picture.PINK_2, Picture.BLUE_2));
		availableCards.add(new Card(Picture.PURPLE_1, Picture.PINK_1, Picture.BLUE_2, Picture.GREEN_2));
		availableCards.add(new Card(Picture.PURPLE_1, Picture.BLUE_1, Picture.BLUE_2, Picture.GREEN_2));
		return availableCards;
	}

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}
}
