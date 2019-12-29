package org.whatsoftwarecando.legespiel;

public class CardCreator {
	int idCounter = -1;

	public CardCreator() {
		this.idCounter = 1;
	}

	public Card createCard(IPicture north, IPicture west, IPicture east, IPicture south) {
		return new Card(this.idCounter++, north, west, east, south, 0);
	}
}
