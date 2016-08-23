package org.whatsoftwarecando.legespiel.experiments;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;

public class FieldWithOnlyOneSolution extends Field {

	public FieldWithOnlyOneSolution(int rows, int cols) {
		super(rows, cols);
	}

	public FieldWithOnlyOneSolution(int rows, int cols, Card[][] cards) {
		super(rows, cols, cards);
	}

	@Override
	public Field addedIfFits(Card card) {
		Field field = super.addedIfFits(card);
		if(field == null){
			return null;
		}
		
		for(int row = 1; row < this.getRows(); row++){
			for(int col = 1; col < this.getCols(); col++){
				Card currentCard = this.getCard(row, col);
				if(currentCard == null){
					return field;
				}
				
				Field alternativeSolution = null;
				int turn = 0;
				do{
					
				}while(turn < 3);
				
//				Field alternativeSolution = this.addedIfFits(row, col, card);
				if(alternativeSolution != null){
					alternativeSolution = super.addedIfFits(currentCard);
					if(alternativeSolution != null){
						return null;
					}
				}
			}
		}
		throw new RuntimeException("Field must not be full!");
	}
	
	private FieldWithOnlyOneSolution addedIfFits(int row, int col, Card card){
		Card oldCard = this.getCard(row, col);
		if(col > 1){
			if(card.getWest() != oldCard.getWest()){
				return null;
			}
		}
		if(row > 1){
			if(card.getNorth() != oldCard.getNorth()){
				return null;
			}
		}
		if(col < this.getCols()){
			Card cardToTheEast = this.getCard(row, col + 1);
			if(cardToTheEast != null && card.getEast() != oldCard.getEast()){
				return null;
			}
		}
		if(row < this.getRows()){
			Card cardToTheSouth = this.getCard(row + 1, col);
			if(cardToTheSouth != null && card.getSouth() != oldCard.getSouth()){
				return null;
			}
		}
		
		FieldWithOnlyOneSolution copy = new FieldWithOnlyOneSolution(this.getRows(), this.getCols(), this.copyCardArray());
		copy.setCard(row, col, card);
		return copy;
	}
	
	protected void setCard(int row, int col, Card card) {
		this.cards[row - 1][col - 1] = card;
	}
}
