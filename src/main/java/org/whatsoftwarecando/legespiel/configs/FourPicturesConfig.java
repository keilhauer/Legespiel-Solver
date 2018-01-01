package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.configs.ExactlyOneSolutionConfig.Pictures;

public class FourPicturesConfig extends GameConfig {

	@Override
	protected Field createEmptyField() {
		return new Field(3, 3);
	}

	@Override
	protected ArrayList<Card> createAvailableCards() {
		ArrayList<Card> availableCards = new ArrayList<Card>();
		availableCards.add(new Card(Pictures.RED, Pictures.RED, Pictures.RED, Pictures.RED));
		availableCards.add(new Card(Pictures.BLUE, Pictures.RED, Pictures.GREEN, Pictures.BLUE));
		availableCards.add(new Card(Pictures.GREEN, Pictures.GREEN, Pictures.GREEN, Pictures.GREEN));
		availableCards.add(new Card(Pictures.RED, Pictures.RED, Pictures.RED, Pictures.BLUE));
		availableCards.add(new Card(Pictures.BLUE, Pictures.RED, Pictures.GREEN, Pictures.GREEN));
		availableCards.add(new Card(Pictures.GREEN, Pictures.GREEN, Pictures.GREEN, Pictures.BLUE));
		availableCards.add(new Card(Pictures.BLUE, Pictures.BLUE, Pictures.BLUE, Pictures.BLUE));
		availableCards.add(new Card(Pictures.GREEN, Pictures.BLUE, Pictures.BLUE, Pictures.BLUE));
		availableCards.add(new Card(Pictures.BLUE, Pictures.BLUE, Pictures.RED, Pictures.RED));
		return availableCards;
	}

}
