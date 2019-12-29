package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Knifflidiffels". This is version 5 according to
 * https://whatsoftwarecando.org/list-of-scramble-squares-puzzles-with-solutions/
 * 
 * Please find more information on this game and on duplicate cards under
 * http://whatsoftwarecando.org/en/solving-knifflidiffels-legespiel-duplicate-cards/
 */
public class KnifflidiffelsVersion5Config extends GameConfig {

	enum Picture implements IPicture {

		DOG, BOY, GIRL, HORSE;

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
		// Cards 1-3 (identical)
		addCard(Picture.DOG, Picture.BOY, Picture.GIRL, Picture.HORSE);
		addCard(Picture.DOG, Picture.BOY, Picture.GIRL, Picture.HORSE);
		addCard(Picture.DOG, Picture.BOY, Picture.GIRL, Picture.HORSE);

		// Cards 4-6 (identical)
		addCard(Picture.BOY, Picture.GIRL, Picture.DOG, Picture.HORSE);
		addCard(Picture.BOY, Picture.GIRL, Picture.DOG, Picture.HORSE);
		addCard(Picture.BOY, Picture.GIRL, Picture.DOG, Picture.HORSE);

		// Cards 7
		addCard(Picture.BOY, Picture.DOG, Picture.GIRL, Picture.HORSE);

		// Card 8-9 (identical)
		addCard(Picture.DOG, Picture.GIRL, Picture.BOY, Picture.HORSE);
		addCard(Picture.DOG, Picture.GIRL, Picture.BOY, Picture.HORSE);
	}
}
