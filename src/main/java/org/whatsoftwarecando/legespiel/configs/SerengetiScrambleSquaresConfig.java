package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * see https://www.youtube.com/watch?v=Mux4NpO_fsA
 */
public class SerengetiScrambleSquaresConfig extends GameConfig {

	enum Picture implements IPicture {

		GIRAFFE_UP(1), GIRAFFE_LOW(1), RHINO_UP(2), RHINO_LOW(2), ZEBRA_UP(3), ZEBRA_LOW(3), ELEPHANT_UP(
				4), ELEPHANT_LOW(4);

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
		addCard(Picture.RHINO_LOW, Picture.ZEBRA_LOW, Picture.GIRAFFE_UP, Picture.ELEPHANT_UP);
		addCard(Picture.ELEPHANT_LOW, Picture.GIRAFFE_LOW, Picture.ZEBRA_LOW, Picture.RHINO_UP);
		addCard(Picture.GIRAFFE_LOW, Picture.ZEBRA_UP, Picture.ELEPHANT_LOW, Picture.RHINO_LOW);
		addCard(Picture.ELEPHANT_LOW, Picture.ZEBRA_LOW, Picture.GIRAFFE_LOW, Picture.RHINO_UP);
		addCard(Picture.RHINO_LOW, Picture.GIRAFFE_UP, Picture.ZEBRA_UP, Picture.ELEPHANT_UP);
		addCard(Picture.RHINO_UP, Picture.ZEBRA_LOW, Picture.GIRAFFE_UP, Picture.ELEPHANT_LOW);
		addCard(Picture.RHINO_LOW, Picture.ELEPHANT_LOW, Picture.ZEBRA_LOW, Picture.RHINO_UP);
		addCard(Picture.ELEPHANT_LOW, Picture.ZEBRA_UP, Picture.GIRAFFE_UP, Picture.ZEBRA_LOW);
		addCard(Picture.ELEPHANT_UP, Picture.GIRAFFE_LOW, Picture.GIRAFFE_UP, Picture.RHINO_UP);
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