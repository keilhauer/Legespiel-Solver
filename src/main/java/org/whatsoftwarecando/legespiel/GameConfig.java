package org.whatsoftwarecando.legespiel;

import java.util.ArrayList;
import java.util.List;

public abstract class GameConfig {

	public abstract ArrayList<Card> getAvailableCards();

	public abstract Field createEmptyField();
	
	public boolean isBfsNeeded(){
		return false;
	}
	
	public List<PartialSolution> filterPartialSolutions(List<PartialSolution> partialSolutions){
		return partialSolutions;
	}
	
	public List<Field> filterSolutions(List<Field> solutions){
		return solutions;
	}

}