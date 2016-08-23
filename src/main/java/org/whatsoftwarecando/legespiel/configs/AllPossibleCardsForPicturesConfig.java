package org.whatsoftwarecando.legespiel.experiments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.IPicture;

public class CardFactory {
	
	private static CardFactory INSTANCE = null;
	
	public synchronized static CardFactory getInstance(){
		if(INSTANCE == null){
			INSTANCE = new CardFactory();
		}
		return INSTANCE;
	}
	
	private CardFactory(){
		init(new Picture[]{});
	}
	
	enum Picture implements IPicture{
		
		RED, GREEN, BLUE, YELLOW;

		@Override
		public boolean matches(IPicture other) {
			return this == other;
		}
	}
	
	private List<Card> availableCards = new ArrayList<Card>();
	
	private void init(Picture... pictures){
		if(pictures != null && pictures.length == 4){
			availableCards.add(new Card(pictures));
			return;
		}
		for(Picture p : Picture.values()){
			Picture[] newPictures = Arrays.copyOf(pictures, pictures.length + 1);
			newPictures[pictures.length] = p;
			init(newPictures);
		}
	}

	public List<Card> getAvailableCards() {
		return availableCards;
	}

	@Override
	public String toString() {
		return "CardFactory [availableCards=" + availableCards + "]";
	}
	
	public static void main(String[] argv){
		System.out.println(CardFactory.getInstance());
		System.out.println(CardFactory.getInstance().availableCards.size());
	}
}
