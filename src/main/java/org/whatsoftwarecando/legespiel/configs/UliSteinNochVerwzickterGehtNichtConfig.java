package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Uli Stein: Noch verwzickter geht nicht!". See
 * http://www.gerdkoch.de/nochverwzickter/index.html for more information.
 */
public class UliSteinNochVerwzickterGehtNichtConfig extends GameConfig {

	enum Picture implements IPicture {

		FOOTBALL_LEFT(1), FOOTBALL_RIGHT(1), TEA_LEFT(2), TEA_RIGHT(2), GLASS_LEFT(3), GLASS_RIGHT(3), REACHING_LEFT(
				4), REACHING_RIGHT(4);

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
	protected Field createEmptyField() {
		return new Field(3, 3);
	}

	@Override
	protected void createAvailableCards() {
		addCard(Picture.GLASS_RIGHT, Picture.REACHING_RIGHT, Picture.FOOTBALL_LEFT, Picture.TEA_LEFT);
		addCard(Picture.TEA_LEFT, Picture.FOOTBALL_RIGHT, Picture.REACHING_RIGHT, Picture.GLASS_LEFT);
		addCard(Picture.FOOTBALL_RIGHT, Picture.REACHING_LEFT, Picture.GLASS_LEFT, Picture.TEA_RIGHT);
		addCard(Picture.TEA_RIGHT, Picture.GLASS_LEFT, Picture.REACHING_RIGHT, Picture.FOOTBALL_LEFT);
		addCard(Picture.GLASS_RIGHT, Picture.REACHING_LEFT, Picture.FOOTBALL_LEFT, Picture.TEA_RIGHT);
		addCard(Picture.TEA_LEFT, Picture.FOOTBALL_RIGHT, Picture.REACHING_LEFT, Picture.GLASS_RIGHT);
		addCard(Picture.FOOTBALL_RIGHT, Picture.TEA_LEFT, Picture.GLASS_LEFT, Picture.REACHING_RIGHT);
		addCard(Picture.TEA_LEFT, Picture.GLASS_RIGHT, Picture.REACHING_LEFT, Picture.FOOTBALL_RIGHT);
		addCard(Picture.GLASS_LEFT, Picture.REACHING_RIGHT, Picture.TEA_RIGHT, Picture.FOOTBALL_LEFT);
	}
}
