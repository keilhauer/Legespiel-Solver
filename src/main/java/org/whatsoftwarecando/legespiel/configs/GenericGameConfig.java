package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;
import java.util.List;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;

public class GenericGameConfig extends GameConfig {

	/**
	 * @param availableCards
	 * @param emptyField
	 */
	public GenericGameConfig(List<Card> availableCards, Field emptyField) {
		this.availableCards = new ArrayList<Card>(availableCards);
		this.emptyField = emptyField;
	}

	@Override
	protected Field createEmptyField() {
		return emptyField;
	}

	@Override
	public boolean isBfsNeeded() {
		return false;
	}

	@Override
	public void output(String str) {
	}

	@Override
	protected void createAvailableCards() {
		// Nothing to do.
	}
}
