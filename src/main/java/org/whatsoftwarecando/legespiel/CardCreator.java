package org.whatsoftwarecando.legespiel;

public class CardCreator {
	int idCounter = -1;

	public CardCreator() {
		this.idCounter = 1;
	}

	public Card createCard(IPicture north, IPicture west, IPicture east, IPicture south) {
		Card r0 = new Card(this.idCounter++, north, west, east, south, 0);
		Card r1 = r0.turned90DegreesClockwise();
		Card r2 = r1.turned90DegreesClockwise();
		Card r3 = r2.turned90DegreesClockwise();
		r0.setAllRotations(new Card[] { r0, r1, r2, r3 });
		return r0;
	}
}
