package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Knifflidiffels". This is version 2 according to
 * https://whatsoftwarecando.org/list-of-scramble-squares-puzzles-with-solutions/
 */
public class KnifflidiffelsVersion2Config extends GameConfig {

	enum Picture implements IPicture {

		BOY, BUNNY, GIRL, BEAR;

		@Override
		public boolean matches(IPicture other) {
			return this == other;
		}
	}

	@Override
	protected byte getNumberOfRows() {
		return 3;
	}

	@Override
	protected byte getNumberOfColumns() {
		return 3;
	}

	@Override
	protected void createAvailableCards() {
		addCard(Picture.BOY, Picture.BEAR, Picture.BUNNY, Picture.GIRL);
		addCard(Picture.BOY, Picture.BUNNY, Picture.BEAR, Picture.GIRL);
		addCard(Picture.GIRL, Picture.BEAR, Picture.BOY, Picture.BUNNY);
		addCard(Picture.GIRL, Picture.BOY, Picture.BEAR, Picture.BUNNY);
		addCard(Picture.GIRL, Picture.BEAR, Picture.BOY, Picture.BUNNY);
		addCard(Picture.BUNNY, Picture.BOY, Picture.BEAR, Picture.GIRL);
		addCard(Picture.BUNNY, Picture.BEAR, Picture.BOY, Picture.GIRL);
		addCard(Picture.BUNNY, Picture.BOY, Picture.BEAR, Picture.GIRL);
		addCard(Picture.GIRL, Picture.BEAR, Picture.BUNNY, Picture.BOY);
	}
}
