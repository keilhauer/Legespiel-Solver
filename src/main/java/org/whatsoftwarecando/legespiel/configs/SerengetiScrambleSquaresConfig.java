package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * see https://www.youtube.com/watch?v=Mux4NpO_fsA
 */
public class SerengetiScrambleSquaresConfig extends GameConfig {

	enum Picture implements IPicture {

		GIRAFFE_UP(1), GIRAFFE_LOW(1), RHINO_UP(2), RHINO_LOW(2), ZEBRA_UP(3), ZEBRA_LOW(3), ELEPHANT_UP(
				4), ELEPHANT_LOW(4);

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
		availableCards.add(new Card(Picture.RHINO_LOW, Picture.ZEBRA_LOW, Picture.GIRAFFE_UP, Picture.ELEPHANT_UP));
		availableCards.add(new Card(Picture.ELEPHANT_LOW, Picture.GIRAFFE_LOW, Picture.ZEBRA_LOW, Picture.RHINO_UP));
		availableCards.add(new Card(Picture.GIRAFFE_LOW, Picture.ZEBRA_UP, Picture.ELEPHANT_LOW, Picture.RHINO_LOW));
		availableCards.add(new Card(Picture.ELEPHANT_LOW, Picture.ZEBRA_LOW, Picture.GIRAFFE_LOW, Picture.RHINO_UP));
		availableCards.add(new Card(Picture.RHINO_LOW, Picture.GIRAFFE_UP, Picture.ZEBRA_UP, Picture.ELEPHANT_UP));
		availableCards.add(new Card(Picture.RHINO_UP, Picture.ZEBRA_LOW, Picture.GIRAFFE_UP, Picture.ELEPHANT_LOW));
		availableCards.add(new Card(Picture.RHINO_LOW, Picture.ELEPHANT_LOW, Picture.ZEBRA_LOW, Picture.RHINO_UP));
		availableCards.add(new Card(Picture.ELEPHANT_LOW, Picture.ZEBRA_UP, Picture.GIRAFFE_UP, Picture.ZEBRA_LOW));
		availableCards.add(new Card(Picture.ELEPHANT_UP, Picture.GIRAFFE_LOW, Picture.GIRAFFE_UP, Picture.RHINO_UP));
		return availableCards;
	}

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}
}