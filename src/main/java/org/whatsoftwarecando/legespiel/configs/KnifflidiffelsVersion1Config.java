package org.whatsoftwarecando.legespiel.configs;

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
	protected void createAvailableCards() {
		addCard(Picture.BALLOONS, Picture.BEAR, Picture.GIRL, Picture.BOY);
		addCard(Picture.BOY, Picture.GIRL, Picture.BEAR, Picture.BALLOONS);
		addCard(Picture.BALLOONS, Picture.BEAR, Picture.GIRL, Picture.BOY);
		addCard(Picture.BALLOONS, Picture.GIRL, Picture.BEAR, Picture.BOY);
		addCard(Picture.BOY, Picture.BEAR, Picture.GIRL, Picture.BALLOONS);
		addCard(Picture.GIRL, Picture.BALLOONS, Picture.BOY, Picture.BEAR);
		addCard(Picture.GIRL, Picture.BEAR, Picture.BALLOONS, Picture.BOY);
		addCard(Picture.GIRL, Picture.BALLOONS, Picture.BEAR, Picture.BOY);
		addCard(Picture.BALLOONS, Picture.GIRL, Picture.BOY, Picture.BEAR);
	}
}
