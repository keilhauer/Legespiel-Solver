package org.whatsoftwarecando.legespiel;

import java.util.ArrayList;
import java.util.List;

public class Util {

	public static double nanosToMilliseconds(long nanos){
		return Math.round(nanos / 100000.0) / 10.0;
	}
	
	public static void main(String[] argv){
		System.out.println(nanosToMilliseconds(1500000));
	}

	public static List<Card> removed(Card lastcard, List<Card> cardsLeft) {
		ArrayList<Card> result = new ArrayList<Card>(cardsLeft.size() - 1);
		for (Card currentcard : cardsLeft) {
			if (lastcard.getId() != currentcard.getId()) {
				result.add(currentcard);
			}
		}
		if (cardsLeft.size() - 1 != result.size()) {
			throw new RuntimeException(lastcard + "was not found in " + cardsLeft);
		}
		return result;
	}
}
