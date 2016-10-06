package org.whatsoftwarecando.legespiel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class GameConfig {

	public abstract ArrayList<Card> getAvailableCards();

	public abstract Field createEmptyField();
	
	public boolean isBfsNeeded(){
		return false;
	}
	
	public Set<PartialSolution> filterPartialSolutions(Collection<PartialSolution> partialSolutions){
		return new HashSet<PartialSolution>(partialSolutions);
	}
	
	public Set<Field> filterSolutions(Collection<Field> solutions){
		return new HashSet<Field>(solutions);
	}
	
	public void output(String str){
		System.out.println(str);
	}

}