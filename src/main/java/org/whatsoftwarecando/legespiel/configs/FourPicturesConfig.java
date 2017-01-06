package org.whatsoftwarecando.legespiel.configs;

import java.util.ArrayList;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.configs.ExactlyOneSolutionConfig.FourPictures;

public class FourPicturesConfig extends GameConfig{

	@Override
	public ArrayList<Card> getAvailableCards() {
		ArrayList<Card> result = new ArrayList<Card>();
		result.add(new Card(FourPictures.RED, FourPictures.RED, FourPictures.RED, FourPictures.RED));
		result.add(new Card(FourPictures.BLUE, FourPictures.RED, FourPictures.GREEN, FourPictures.BLUE));
		result.add(new Card(FourPictures.GREEN, FourPictures.GREEN, FourPictures.GREEN, FourPictures.GREEN));
		result.add(new Card(FourPictures.RED, FourPictures.RED, FourPictures.RED, FourPictures.BLUE));
		result.add(new Card(FourPictures.BLUE, FourPictures.RED, FourPictures.GREEN, FourPictures.GREEN));
		result.add(new Card(FourPictures.GREEN, FourPictures.GREEN, FourPictures.GREEN, FourPictures.BLUE));
		result.add(new Card(FourPictures.BLUE, FourPictures.BLUE, FourPictures.BLUE, FourPictures.BLUE));
		result.add(new Card(FourPictures.GREEN, FourPictures.BLUE, FourPictures.BLUE, FourPictures.BLUE));
		result.add(new Card(FourPictures.BLUE, FourPictures.BLUE, FourPictures.RED, FourPictures.RED));
		return result;
	}

	@Override
	public Field createEmptyField() {
		return new Field(3, 3);
	}

}
