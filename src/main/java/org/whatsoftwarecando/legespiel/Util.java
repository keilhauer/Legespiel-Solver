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

	public static List<Card> removed(Card cardToBeRemoved, List<Card> cards) {
		ArrayList<Card> result = new ArrayList<Card>(cards.size() - 1);
		for (Card currentcard : cards) {
			if (cardToBeRemoved.getId() != currentcard.getId()) {
				result.add(currentcard);
			}
		}
		if (cards.size() - 1 != result.size()) {
			throw new RuntimeException(cardToBeRemoved + "was not found in " + cards);
		}
		return result;
	}
}
