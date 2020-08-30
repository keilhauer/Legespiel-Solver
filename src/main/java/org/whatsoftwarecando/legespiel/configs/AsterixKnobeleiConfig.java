package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Asterix-Knobelei": see
 * http://www.comedix.de/medien/lit/kartenlegespiel.php for more information
 *
 */
public class AsterixKnobeleiConfig extends GameConfig {

	enum Picture implements IPicture {

		ASTERIX_UP(1), ASTERIX_LOW(1), OBELIX_UP(2), OBELIX_LOW(2), DRUID_UP(3), DRUID_LOW(3), CHIEF_UP(4), CHIEF_LOW(
				4);

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
		addCard(Picture.ASTERIX_UP, Picture.CHIEF_LOW, Picture.DRUID_LOW, Picture.OBELIX_UP);
		addCard(Picture.OBELIX_LOW, Picture.DRUID_UP, Picture.ASTERIX_LOW, Picture.CHIEF_UP);
		addCard(Picture.CHIEF_LOW, Picture.ASTERIX_UP, Picture.OBELIX_UP, Picture.DRUID_LOW);
		addCard(Picture.OBELIX_LOW, Picture.CHIEF_UP, Picture.DRUID_UP, Picture.ASTERIX_LOW);
		addCard(Picture.CHIEF_LOW, Picture.DRUID_LOW, Picture.ASTERIX_UP, Picture.OBELIX_UP);
		addCard(Picture.DRUID_UP, Picture.ASTERIX_LOW, Picture.CHIEF_UP, Picture.CHIEF_UP);
		addCard(Picture.ASTERIX_UP, Picture.CHIEF_LOW, Picture.DRUID_LOW, Picture.OBELIX_UP);
		addCard(Picture.OBELIX_LOW, Picture.DRUID_UP, Picture.ASTERIX_LOW, Picture.CHIEF_UP);
		addCard(Picture.CHIEF_LOW, Picture.ASTERIX_UP, Picture.DRUID_LOW, Picture.OBELIX_UP);
	}

	@Override
	protected byte getNumberOfRows() {
		return 3;
	}

	@Override
	protected byte getNumberOfColumns() {
		return 3;
	}

}
