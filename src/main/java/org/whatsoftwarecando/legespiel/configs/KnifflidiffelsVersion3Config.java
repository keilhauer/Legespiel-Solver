package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Knifflidiffels". This is version 3 according to
 * https://whatsoftwarecando.org/list-of-scramble-squares-puzzles-with-solutions/
 */
public class KnifflidiffelsVersion3Config extends GameConfig {

	enum Picture implements IPicture {

		BOY, BUNNY, GIRL, BEAR;

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

		availableCards.add(new Card(Picture.BEAR, Picture.BOY, Picture.GIRL, Picture.BUNNY));
		availableCards.add(new Card(Picture.BOY, Picture.GIRL, Picture.BEAR, Picture.BUNNY));
		availableCards.add(new Card(Picture.BOY, Picture.BEAR, Picture.GIRL, Picture.BUNNY));
		availableCards.add(new Card(Picture.BUNNY, Picture.BEAR, Picture.GIRL, Picture.BOY));
		availableCards.add(new Card(Picture.BUNNY, Picture.GIRL, Picture.BEAR, Picture.BOY));
		availableCards.add(new Card(Picture.BUNNY, Picture.BEAR, Picture.GIRL, Picture.BOY));
		availableCards.add(new Card(Picture.BOY, Picture.GIRL, Picture.BEAR, Picture.BUNNY));
		availableCards.add(new Card(Picture.BOY, Picture.BEAR, Picture.BUNNY, Picture.GIRL));
		availableCards.add(new Card(Picture.BOY, Picture.BUNNY, Picture.BEAR, Picture.GIRL));
		return availableCards;
	}
}
