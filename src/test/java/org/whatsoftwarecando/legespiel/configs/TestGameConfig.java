package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;
import java.util.List;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;

public class TestGameConfig extends GameConfig {

	private final ArrayList<Card> availableCards;
	private final Field emptyField;

	/**
	 * @param availableCards
	 * @param emptyField
	 */
	public TestGameConfig(List<Card> availableCards, Field emptyField) {
		this.availableCards = new ArrayList<Card>(availableCards);
		this.emptyField = emptyField;
	}

	@Override
	public ArrayList<Card> getAvailableCards() {
		return availableCards;
	}

	@Override
	public Field createEmptyField() {
		return emptyField;
	}
}
