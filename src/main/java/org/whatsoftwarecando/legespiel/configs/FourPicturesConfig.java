package org.whatsoftwarecando.legespiel.configs;

import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.configs.ExactlyOneSolutionConfig.Pictures;

public class FourPicturesConfig extends GameConfig {

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
		addCard(Pictures.RED, Pictures.RED, Pictures.RED, Pictures.RED);
		addCard(Pictures.BLUE, Pictures.RED, Pictures.GREEN, Pictures.BLUE);
		addCard(Pictures.GREEN, Pictures.GREEN, Pictures.GREEN, Pictures.GREEN);
		addCard(Pictures.RED, Pictures.RED, Pictures.RED, Pictures.BLUE);
		addCard(Pictures.BLUE, Pictures.RED, Pictures.GREEN, Pictures.GREEN);
		addCard(Pictures.GREEN, Pictures.GREEN, Pictures.GREEN, Pictures.BLUE);
		addCard(Pictures.BLUE, Pictures.BLUE, Pictures.BLUE, Pictures.BLUE);
		addCard(Pictures.GREEN, Pictures.BLUE, Pictures.BLUE, Pictures.BLUE);
		addCard(Pictures.BLUE, Pictures.BLUE, Pictures.RED, Pictures.RED);
	}

}
