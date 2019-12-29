package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Knifflidiffels". This is version 4 according to
 * https://whatsoftwarecando.org/list-of-scramble-squares-puzzles-with-solutions/
 */
public class KnifflidiffelsVersion4Config extends GameConfig {

	enum Picture implements IPicture {

		BOY, GIRL, DOG, SHEEPS;

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

		// Cards 1-4 (identical)
		addCard(Picture.DOG, Picture.BOY, Picture.SHEEPS, Picture.GIRL);
		addCard(Picture.DOG, Picture.BOY, Picture.SHEEPS, Picture.GIRL);
		addCard(Picture.DOG, Picture.BOY, Picture.SHEEPS, Picture.GIRL);
		addCard(Picture.DOG, Picture.BOY, Picture.SHEEPS, Picture.GIRL);

		// Cards 5-6 (identical)
		addCard(Picture.GIRL, Picture.BOY, Picture.SHEEPS, Picture.DOG);
		addCard(Picture.GIRL, Picture.BOY, Picture.SHEEPS, Picture.DOG);

		// Cards 7-8 (identical)
		addCard(Picture.SHEEPS, Picture.BOY, Picture.GIRL, Picture.DOG);
		addCard(Picture.SHEEPS, Picture.BOY, Picture.GIRL, Picture.DOG);

		// Card 9
		addCard(Picture.BOY, Picture.SHEEPS, Picture.DOG, Picture.GIRL);
	}
}
