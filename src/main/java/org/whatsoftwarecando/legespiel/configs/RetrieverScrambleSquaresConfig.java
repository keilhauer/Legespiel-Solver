package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * B Dazzle Retriever Scramble Squares according to
 * https://kalmas.net/blog/2014-01-01_scramble-squares/
 */
public class RetrieverScrambleSquaresConfig extends GameConfig {

	enum Picture implements IPicture {

		GOLDEN_1(1), GOLDEN_2(1), BLACK_1(2), BLACK_2(2), YELLOW_1(3), YELLOW_2(3), CHOCOLATE_1(4), CHOCOLATE_2(4);

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
		addCard(Picture.YELLOW_1, Picture.BLACK_1, Picture.CHOCOLATE_2, Picture.GOLDEN_1);
		addCard(Picture.GOLDEN_1, Picture.CHOCOLATE_1, Picture.YELLOW_1, Picture.BLACK_2);
		addCard(Picture.CHOCOLATE_1, Picture.YELLOW_2, Picture.GOLDEN_1, Picture.BLACK_1);
		addCard(Picture.GOLDEN_2, Picture.CHOCOLATE_1, Picture.YELLOW_1, Picture.BLACK_1);
		addCard(Picture.BLACK_1, Picture.YELLOW_2, Picture.GOLDEN_1, Picture.CHOCOLATE_1);
		addCard(Picture.BLACK_2, Picture.GOLDEN_2, Picture.YELLOW_2, Picture.CHOCOLATE_2);
		addCard(Picture.BLACK_2, Picture.GOLDEN_1, Picture.YELLOW_2, Picture.CHOCOLATE_1);
		addCard(Picture.CHOCOLATE_2, Picture.YELLOW_1, Picture.GOLDEN_2, Picture.BLACK_1);
		addCard(Picture.CHOCOLATE_1, Picture.GOLDEN_1, Picture.BLACK_1, Picture.YELLOW_1);
	}

	@Override
	public byte getNumberOfRows() {
		return 3;
	}

	@Override
	public byte getNumberOfColumns() {
		return 3;
	}
}