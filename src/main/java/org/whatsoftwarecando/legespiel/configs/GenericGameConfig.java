package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;
import java.util.List;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;

public class GenericGameConfig extends GameConfig {

	private final ArrayList<Card> availableCards;
	private final Field emptyField;

	/**
	 * @param availableCards
	 * @param emptyField
	 */
	public GenericGameConfig(List<Card> availableCards, Field emptyField) {
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
	
	@Override
	public boolean isBfsNeeded(){
		return false;
	}
	
	@Override
	public void output(String str){
	}
}
