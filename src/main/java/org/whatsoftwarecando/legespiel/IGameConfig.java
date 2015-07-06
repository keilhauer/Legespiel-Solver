package org.whatsoftwarecando.legespiel;

import java.util.ArrayList;

public interface IGameConfig {

	public abstract ArrayList<Card> getAvailableCards();

	public abstract Field createEmptyField();

}