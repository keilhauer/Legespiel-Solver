package org.whatsoftwarecando.legespiel.fingerprint;

import java.util.HashSet;
import java.util.Set;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.IPicture;

public class CardFingerprint implements Comparable<CardFingerprint> {

	private Card card;

	private PictureMapping pictureMapping;

	private String fingerprint;

	public CardFingerprint(Card card, PictureMapping pictureMapping) {
		this(card, pictureMapping, null);
	}

	public CardFingerprint(Card card, PictureMapping pictureMapping, String fingerprint) {
		this.card = card;
		this.pictureMapping = pictureMapping;
		this.fingerprint = fingerprint;
	}

	public Set<CardFingerprint> calculateMinimalComplete() {
		Card currentCard = card;
		Set<CardFingerprint> minimalCardFingerprints = new HashSet<>();
		String minimalFingerprint = null;
		int numberOfTurns = 0;
		do {
			PictureMapping currentMappingForCompleteness = new PictureMapping(pictureMapping);
			completePicture(currentMappingForCompleteness, currentCard.getNorth());
			completePicture(currentMappingForCompleteness, currentCard.getEast());
			completePicture(currentMappingForCompleteness, currentCard.getSouth());
			completePicture(currentMappingForCompleteness, currentCard.getWest());
			String currentFingerprint = calculateFingerprint(currentCard, currentMappingForCompleteness);
			int compareFingerprints = 1;
			if (minimalFingerprint == null
					|| (compareFingerprints = compareFingerprintStrings(currentFingerprint, minimalFingerprint)) <= 0) {
				if (compareFingerprints < 0 && !minimalCardFingerprints.isEmpty()) {
					minimalCardFingerprints.clear();
				}
				minimalFingerprint = currentFingerprint;
				minimalCardFingerprints.add(
						new CardFingerprint(currentCard, currentMappingForCompleteness.merged(), currentFingerprint));

			}
			currentCard = currentCard.turned90DegreesClockwise();
			numberOfTurns++;
		} while (numberOfTurns <= 3);

		return minimalCardFingerprints;
	}

	private void completePicture(PictureMapping currentAdditionalMappingForCompleteness, IPicture picture) {
		Character charForPicture = currentAdditionalMappingForCompleteness.get(picture);
		if (charForPicture == null) {
			charForPicture = currentAdditionalMappingForCompleteness.retrieveSmallestCharacterLeft();
			currentAdditionalMappingForCompleteness.put(picture, charForPicture);
			IPicture matchingPicture = currentAdditionalMappingForCompleteness.getMappingForPicture(picture);
			if (matchingPicture != null && !matchingPicture.equals(picture)) {
				currentAdditionalMappingForCompleteness.put(matchingPicture, Character.toLowerCase(charForPicture));
			}
		}
	}

	private String calculateFingerprint(Card card, PictureMapping pictureMapping) {
		StringBuilder sb = new StringBuilder();
		appendFor(card.getNorth(), pictureMapping, sb);
		appendFor(card.getEast(), pictureMapping, sb);
		appendFor(card.getSouth(), pictureMapping, sb);
		appendFor(card.getWest(), pictureMapping, sb);
		return sb.toString();
	}

	private void appendFor(IPicture picture, PictureMapping pictureMapping, StringBuilder sb) {
		Character pictureChar = pictureMapping.get(picture);
		if (pictureChar == null) {
			sb.append("<" + picture.toString() + ">");
		} else {
			sb.append(pictureChar);
		}
	}

	PictureMapping getPictureMapping() {
		return pictureMapping;
	}

	String getFingerprint() {
		return fingerprint;
	}

	Card getCard() {
		return card;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((card == null) ? 0 : card.hashCode());
		result = prime * result + ((fingerprint == null) ? 0 : fingerprint.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CardFingerprint other = (CardFingerprint) obj;
		if (card == null) {
			if (other.card != null)
				return false;
		} else if (!card.equals(other.card))
			return false;
		if (fingerprint == null) {
			if (other.fingerprint != null)
				return false;
		} else if (!fingerprint.equals(other.fingerprint))
			return false;
		return true;
	}

	@Override
	public String toString() {
		if (fingerprint == null) {
			return CardFingerprint.class.getSimpleName() + " definition: " + this.card;
		} else {
			return CardFingerprint.class.getSimpleName() + ": " + getFingerprint();
		}

	}

	@Override
	public int compareTo(CardFingerprint o) {
		return compareFingerprintStrings(this.getFingerprint(), o.getFingerprint());
	}

	static int compareFingerprintStrings(String fingerprint1, String fingerprint2) {
		return fingerprint1.compareTo(fingerprint2);
	}
}
