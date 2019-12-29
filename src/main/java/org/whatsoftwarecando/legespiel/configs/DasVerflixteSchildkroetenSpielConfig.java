package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on Das verflixte Schildkrötenspiel (see
 * https://www.youtube.com/watch?v=p5Pzv99DnKI )
 */
public class DasVerflixteSchildkroetenSpielConfig extends GameConfig {

	enum Picture implements IPicture {

		RED_YELLOW_UP(1), RED_YELLOW_LOW(1), RED_GREEN_UP(2), RED_GREEN_LOW(2), RED_BLACK_UP(3), RED_BLACK_LOW(
				3), GREEN_YELLOW_UP(4), GREEN_YELLOW_LOW(4);

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

		// Duplicate cards: 3rd, 8th
		addCard(Picture.RED_YELLOW_LOW, Picture.RED_GREEN_LOW, Picture.RED_BLACK_UP, Picture.GREEN_YELLOW_UP);
		addCard(Picture.RED_GREEN_LOW, Picture.RED_BLACK_LOW, Picture.GREEN_YELLOW_UP, Picture.RED_YELLOW_UP);
		addCard(Picture.RED_GREEN_LOW, Picture.GREEN_YELLOW_LOW, Picture.RED_BLACK_UP, Picture.RED_YELLOW_UP);
		addCard(Picture.GREEN_YELLOW_LOW, Picture.RED_YELLOW_LOW, Picture.RED_BLACK_UP, Picture.RED_GREEN_UP);
		addCard(Picture.RED_YELLOW_LOW, Picture.RED_BLACK_LOW, Picture.GREEN_YELLOW_UP, Picture.RED_GREEN_UP);
		addCard(Picture.RED_YELLOW_LOW, Picture.GREEN_YELLOW_LOW, Picture.GREEN_YELLOW_UP, Picture.RED_GREEN_UP);
		addCard(Picture.RED_GREEN_LOW, Picture.RED_YELLOW_LOW, Picture.GREEN_YELLOW_UP, Picture.RED_BLACK_UP);
		addCard(Picture.RED_GREEN_LOW, Picture.GREEN_YELLOW_LOW, Picture.RED_BLACK_UP, Picture.RED_YELLOW_UP);
		addCard(Picture.RED_GREEN_LOW, Picture.RED_BLACK_LOW, Picture.RED_BLACK_UP, Picture.RED_YELLOW_UP);
	}

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}
}
