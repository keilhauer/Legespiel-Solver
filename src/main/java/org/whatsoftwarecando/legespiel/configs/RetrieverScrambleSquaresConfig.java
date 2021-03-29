package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * B Dazzle Retriever Scramble Squares according to
 * https://kalmas.net/blog/2014-01-01_scramble-squares/
 */
public class RetrieverScrambleSquaresConfig extends GameConfig {

	enum Picture implements IPicture {

		WITH_STICK_FRONT(1), WITH_STICK_BACK(1), BLACK_FRONT(2), BLACK_BACK(2), PANTING_FRONT(3), PANGTIN_BACK(3), BROWN_FRONT(4), BROWN_BACK(4);

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
		addCard(Picture.PANTING_FRONT, Picture.BLACK_FRONT, Picture.BROWN_BACK, Picture.WITH_STICK_FRONT);
		addCard(Picture.WITH_STICK_FRONT, Picture.BROWN_FRONT, Picture.PANTING_FRONT, Picture.BLACK_BACK);
		addCard(Picture.BROWN_FRONT, Picture.PANGTIN_BACK, Picture.WITH_STICK_FRONT, Picture.BLACK_FRONT);
		addCard(Picture.WITH_STICK_BACK, Picture.BROWN_FRONT, Picture.PANTING_FRONT, Picture.BLACK_FRONT);
		addCard(Picture.BLACK_FRONT, Picture.PANGTIN_BACK, Picture.WITH_STICK_FRONT, Picture.BROWN_FRONT);
		addCard(Picture.BLACK_BACK, Picture.WITH_STICK_BACK, Picture.PANGTIN_BACK, Picture.BROWN_BACK);
		addCard(Picture.BLACK_BACK, Picture.WITH_STICK_FRONT, Picture.PANGTIN_BACK, Picture.BROWN_FRONT);
		addCard(Picture.BROWN_BACK, Picture.PANTING_FRONT, Picture.WITH_STICK_BACK, Picture.BLACK_FRONT);
		addCard(Picture.BROWN_FRONT, Picture.WITH_STICK_FRONT, Picture.BLACK_FRONT, Picture.PANTING_FRONT);
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