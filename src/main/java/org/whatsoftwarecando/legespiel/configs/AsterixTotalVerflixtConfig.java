package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Asterix Total Verflixt": see
 * https://www.spiele4us.de/gesellschaftsspiele-ab-6-jahren/fx-schmid/asterix-total-verflixt-allerlei-spielerei-gebraucht.html
 * for more information
 */
public class AsterixTotalVerflixtConfig extends GameConfig {

	enum Picture implements IPicture {

		ASTERIX_UP(1), ASTERIX_LOW(1), OBELIX_UP(2), OBELIX_LOW(2), DRUID_UP(3), DRUID_LOW(3), SOLDIER_UP(
				4), SOLDIER_LOW(4);

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
		addCard(Picture.OBELIX_UP, Picture.DRUID_UP, Picture.OBELIX_LOW, Picture.ASTERIX_LOW);
		addCard(Picture.SOLDIER_LOW, Picture.ASTERIX_LOW, Picture.ASTERIX_UP, Picture.OBELIX_UP);
		addCard(Picture.DRUID_LOW, Picture.SOLDIER_UP, Picture.OBELIX_LOW, Picture.ASTERIX_UP);
		addCard(Picture.DRUID_LOW, Picture.ASTERIX_UP, Picture.OBELIX_LOW, Picture.DRUID_UP);
		addCard(Picture.SOLDIER_LOW, Picture.ASTERIX_LOW, Picture.DRUID_UP, Picture.OBELIX_UP);
		addCard(Picture.ASTERIX_LOW, Picture.SOLDIER_UP, Picture.OBELIX_LOW, Picture.DRUID_UP);
		addCard(Picture.ASTERIX_UP, Picture.DRUID_UP, Picture.OBELIX_LOW, Picture.SOLDIER_LOW);
		addCard(Picture.OBELIX_LOW, Picture.SOLDIER_LOW, Picture.ASTERIX_UP, Picture.DRUID_UP);
		addCard(Picture.DRUID_LOW, Picture.SOLDIER_UP, Picture.SOLDIER_LOW, Picture.ASTERIX_UP);
	}

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}
}
