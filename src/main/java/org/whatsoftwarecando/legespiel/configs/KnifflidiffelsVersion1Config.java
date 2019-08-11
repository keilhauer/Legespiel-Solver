package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Knifflidiffels". This is version 1 according to
 * https://whatsoftwarecando.org/list-of-scramble-squares-puzzles-with-solutions/
 */
public class KnifflidiffelsVersion1Config extends GameConfig {

	enum Picture implements IPicture {

		BOY, BALLOONS, GIRL, BEAR;

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

		// Identical cards: 2nd, 9th; 3rd, 5th, 6th, 8th; 4th, 7th
		availableCards.add(new Card(Picture.BALLOONS, Picture.BEAR, Picture.GIRL, Picture.BOY));
		availableCards.add(new Card(Picture.BOY, Picture.GIRL, Picture.BEAR, Picture.BALLOONS));
		availableCards.add(new Card(Picture.BALLOONS, Picture.BEAR, Picture.GIRL, Picture.BOY));
		availableCards.add(new Card(Picture.BALLOONS, Picture.GIRL, Picture.BEAR, Picture.BOY));
		availableCards.add(new Card(Picture.BOY, Picture.BEAR, Picture.GIRL, Picture.BALLOONS));
		availableCards.add(new Card(Picture.GIRL, Picture.BALLOONS, Picture.BOY, Picture.BEAR));
		availableCards.add(new Card(Picture.GIRL, Picture.BEAR, Picture.BALLOONS, Picture.BOY));
		availableCards.add(new Card(Picture.GIRL, Picture.BALLOONS, Picture.BEAR, Picture.BOY));
		availableCards.add(new Card(Picture.BALLOONS, Picture.GIRL, Picture.BOY, Picture.BEAR));
		return availableCards;
	}
}
