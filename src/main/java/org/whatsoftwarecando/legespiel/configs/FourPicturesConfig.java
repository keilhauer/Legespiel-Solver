package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.configs.ExactlyOneSolutionConfig.FourPictures;

public class FourPicturesConfig extends GameConfig {

	private static ArrayList<Card> INSTANCE_AVAILABLE_CARDS;

	@Override
	public synchronized ArrayList<Card> getAvailableCards() {
		if (INSTANCE_AVAILABLE_CARDS == null) {
			INSTANCE_AVAILABLE_CARDS = new ArrayList<Card>();
			INSTANCE_AVAILABLE_CARDS
					.add(new Card(FourPictures.RED, FourPictures.RED, FourPictures.RED, FourPictures.RED));
			INSTANCE_AVAILABLE_CARDS
					.add(new Card(FourPictures.BLUE, FourPictures.RED, FourPictures.GREEN, FourPictures.BLUE));
			INSTANCE_AVAILABLE_CARDS
					.add(new Card(FourPictures.GREEN, FourPictures.GREEN, FourPictures.GREEN, FourPictures.GREEN));
			INSTANCE_AVAILABLE_CARDS
					.add(new Card(FourPictures.RED, FourPictures.RED, FourPictures.RED, FourPictures.BLUE));
			INSTANCE_AVAILABLE_CARDS
					.add(new Card(FourPictures.BLUE, FourPictures.RED, FourPictures.GREEN, FourPictures.GREEN));
			INSTANCE_AVAILABLE_CARDS
					.add(new Card(FourPictures.GREEN, FourPictures.GREEN, FourPictures.GREEN, FourPictures.BLUE));
			INSTANCE_AVAILABLE_CARDS
					.add(new Card(FourPictures.BLUE, FourPictures.BLUE, FourPictures.BLUE, FourPictures.BLUE));
			INSTANCE_AVAILABLE_CARDS
					.add(new Card(FourPictures.GREEN, FourPictures.BLUE, FourPictures.BLUE, FourPictures.BLUE));
			INSTANCE_AVAILABLE_CARDS
					.add(new Card(FourPictures.BLUE, FourPictures.BLUE, FourPictures.RED, FourPictures.RED));
		}
		return INSTANCE_AVAILABLE_CARDS;
	}

	@Override
	public Field createEmptyField() {
		return new Field(3, 3);
	}

}
