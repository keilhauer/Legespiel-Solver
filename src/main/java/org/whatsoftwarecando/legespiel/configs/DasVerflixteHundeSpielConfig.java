package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on Das verflixte Schildkrötenspiel (see
 * https://prlbr.de/2011/08/hundepuzzle/
 */
public class DasVerflixteHundeSpielConfig extends GameConfig {

	enum Picture implements IPicture {

		BLACK_FRONT(1), BLACK_BACK(1), WHITE_BROWN_FRONT(2), WHITE_BROWN_BACK(2), BLACK_BROWN_FRONT(
				3), BLACK_BROWN_BACK(3), BLACK_WHITE_FRONT(4), BLACK_WHITE_BACK(4);

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
		addCard(Picture.BLACK_FRONT, Picture.WHITE_BROWN_FRONT, Picture.WHITE_BROWN_BACK, Picture.BLACK_WHITE_BACK);
		addCard(Picture.BLACK_BROWN_FRONT, Picture.WHITE_BROWN_FRONT, Picture.BLACK_WHITE_BACK,
				Picture.WHITE_BROWN_BACK);
		addCard(Picture.BLACK_FRONT, Picture.WHITE_BROWN_FRONT, Picture.BLACK_BROWN_BACK, Picture.BLACK_WHITE_BACK);
		addCard(Picture.BLACK_FRONT, Picture.BLACK_BROWN_FRONT, Picture.BLACK_WHITE_BACK, Picture.BLACK_BACK);
		addCard(Picture.BLACK_WHITE_FRONT, Picture.WHITE_BROWN_FRONT, Picture.BLACK_BROWN_BACK, Picture.BLACK_BACK);
		addCard(Picture.WHITE_BROWN_FRONT, Picture.BLACK_BROWN_FRONT, Picture.BLACK_BACK, Picture.BLACK_WHITE_BACK);
		addCard(Picture.WHITE_BROWN_FRONT, Picture.BLACK_BROWN_FRONT, Picture.BLACK_WHITE_BACK,
				Picture.WHITE_BROWN_BACK);
		addCard(Picture.BLACK_WHITE_FRONT, Picture.BLACK_BROWN_FRONT, Picture.WHITE_BROWN_BACK, Picture.BLACK_BACK);
		addCard(Picture.BLACK_WHITE_FRONT, Picture.BLACK_BROWN_FRONT, Picture.BLACK_BROWN_BACK, Picture.BLACK_BACK);
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
