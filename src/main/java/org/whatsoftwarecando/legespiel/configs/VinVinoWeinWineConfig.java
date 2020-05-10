package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Vin Vino Wein Wine" (see
 * https://www.bonanza.com/items/like/209090358/Vin-Vino-Wein-Wine-Scramble-Squares-Puzzle
 * )
 */
public class VinVinoWeinWineConfig extends GameConfig {

	enum Picture implements IPicture {

		BARREL_UPPER(1), BARREL_LOWER(1), TREE_UPPER(2), TREE_LOWER(2), GREEN_VINE_LEFT(3), GREEN_VINE_RIGHT(
				3), BLUE_VINE_LEFT(4), BLUE_VINE_RIGHT(4);

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
	protected void createAvailableCards() {

		addCard(Picture.GREEN_VINE_RIGHT, Picture.BARREL_UPPER, Picture.TREE_UPPER, Picture.BLUE_VINE_RIGHT);
		addCard(Picture.BLUE_VINE_RIGHT, Picture.TREE_UPPER, Picture.BARREL_UPPER, Picture.GREEN_VINE_LEFT);
		addCard(Picture.BARREL_UPPER, Picture.TREE_UPPER, Picture.GREEN_VINE_LEFT, Picture.BLUE_VINE_RIGHT);
		addCard(Picture.BARREL_LOWER, Picture.TREE_UPPER, Picture.TREE_LOWER, Picture.BLUE_VINE_RIGHT);
		addCard(Picture.BARREL_UPPER, Picture.TREE_LOWER, Picture.GREEN_VINE_RIGHT, Picture.BLUE_VINE_LEFT);
		addCard(Picture.BLUE_VINE_LEFT, Picture.GREEN_VINE_RIGHT, Picture.TREE_UPPER, Picture.BARREL_UPPER);
		addCard(Picture.BLUE_VINE_LEFT, Picture.GREEN_VINE_RIGHT, Picture.GREEN_VINE_LEFT, Picture.BARREL_UPPER);
		addCard(Picture.BLUE_VINE_RIGHT, Picture.GREEN_VINE_RIGHT, Picture.TREE_LOWER, Picture.BARREL_LOWER);
		addCard(Picture.BARREL_LOWER, Picture.TREE_UPPER, Picture.GREEN_VINE_RIGHT, Picture.BLUE_VINE_RIGHT);

	}

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}
}
