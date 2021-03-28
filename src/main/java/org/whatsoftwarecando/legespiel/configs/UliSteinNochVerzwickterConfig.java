package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;

/**
 * Configuration based on "Uli Stein: Noch verwzickter". See
 * http://www.lenz-online.de/aboutme/history/ulstein0.htm for more information.
 */
public class UliSteinNochVerzwickterConfig extends GameConfig {

	enum Picture implements IPicture {

		MOUSE_LEFT(1), MOUSE_RIGHT(1), PIG_LEFT(2), PIG_RIGHT(2), CAT_LEFT(3), CAT_RIGHT(3), PENGUIN_LEFT(
				4), PENGUIN_RIGHT(4);

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
	public byte getNumberOfRows() {
		return 3;
	}

	@Override
	public byte getNumberOfColumns() {
		return 3;
	}

	@Override
	protected void createAvailableCards() {
		addCard(Picture.PENGUIN_RIGHT, Picture.MOUSE_RIGHT, Picture.CAT_LEFT, Picture.PIG_LEFT);
		addCard(Picture.PIG_LEFT, Picture.CAT_RIGHT, Picture.MOUSE_RIGHT, Picture.PENGUIN_LEFT);
		addCard(Picture.CAT_RIGHT, Picture.MOUSE_LEFT, Picture.PENGUIN_LEFT, Picture.PIG_RIGHT);
		addCard(Picture.PIG_RIGHT, Picture.PENGUIN_LEFT, Picture.MOUSE_RIGHT, Picture.CAT_LEFT);
		addCard(Picture.PENGUIN_RIGHT, Picture.MOUSE_LEFT, Picture.CAT_LEFT, Picture.PIG_RIGHT);
		addCard(Picture.PIG_LEFT, Picture.CAT_RIGHT, Picture.MOUSE_LEFT, Picture.PENGUIN_RIGHT);
		addCard(Picture.CAT_RIGHT, Picture.PIG_LEFT, Picture.PENGUIN_LEFT, Picture.MOUSE_RIGHT);
		addCard(Picture.PIG_LEFT, Picture.PENGUIN_RIGHT, Picture.MOUSE_LEFT, Picture.CAT_RIGHT);
		addCard(Picture.PENGUIN_LEFT, Picture.MOUSE_RIGHT, Picture.PIG_RIGHT, Picture.CAT_LEFT);
	}

}
