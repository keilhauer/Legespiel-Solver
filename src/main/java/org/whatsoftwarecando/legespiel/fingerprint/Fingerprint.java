package org.whatsoftwarecando.legespiel.fingerprint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.IPicture;
import org.whatsoftwarecando.legespiel.Util;

public class Fingerprint {

	private ArrayList<Card> cards;

	private PictureMapping pictureMapping;

	private ArrayList<Card> cardsInFingerprintOrder;

	public Fingerprint(Collection<Card> allCardsAvailable) {
		this.cards = new ArrayList<>(allCardsAvailable);
		this.pictureMapping = new PictureMapping(this.cards);
	}

	public String calculate() {
		CalculationResult calculationResult = calculateInternal(this.cards, new ArrayList<>(), this.pictureMapping);
		this.cardsInFingerprintOrder = calculationResult.getCardsInFingerprintOrder();
		this.pictureMapping = calculationResult.getPictureMapping();
		return this.getFingerprint();
	}

	private class CalculationResult {
		private ArrayList<Card> cardsInFingerprintOrder;
		private PictureMapping pictureMapping;

		private CalculationResult(ArrayList<Card> cardsInFingerprintOrder, PictureMapping pictureMapping) {
			this.cardsInFingerprintOrder = cardsInFingerprintOrder;
			this.pictureMapping = pictureMapping;
		}

		ArrayList<Card> getCardsInFingerprintOrder() {
			return cardsInFingerprintOrder;
		}

		PictureMapping getPictureMapping() {
			return pictureMapping;
		}

	}

	private CalculationResult calculateInternal(List<Card> cardsLeft, ArrayList<Card> cardsInFingerprintOrder,
			PictureMapping pictureMapping) {
		if (cardsLeft.isEmpty()) {
			return new CalculationResult(cardsInFingerprintOrder, pictureMapping);
		}
		Set<CardFingerprint> bestCardFingerprints = new HashSet<>();
		for (Card currentCard : cardsLeft) {
			CardFingerprint currentCardFingerprintDefinition = new CardFingerprint(currentCard, pictureMapping);
			Set<CardFingerprint> currentMinimalCardFingerprints = currentCardFingerprintDefinition
					.calculateMinimalComplete();
			int currentCompared = 1;
			if (bestCardFingerprints.isEmpty()
					|| (currentCompared = currentMinimalCardFingerprints.iterator().next()
							.compareTo(bestCardFingerprints.iterator().next())) <= 0) {
				if (currentCompared < 0 && !bestCardFingerprints.isEmpty()) {
					bestCardFingerprints.clear();
				}
				bestCardFingerprints.addAll(currentMinimalCardFingerprints);
			}
		}
		CalculationResult bestCalculationResult = null;
		String bestFingerprint = null;
		for (CardFingerprint currentBest : bestCardFingerprints) {
			ArrayList<Card> newCardsInFingerprintOrder = new ArrayList<>(cardsInFingerprintOrder);
			newCardsInFingerprintOrder.add(currentBest.getCard());
			CalculationResult currentCalculationResult = calculateInternal(
					Util.removed(currentBest.getCard(), cardsLeft), newCardsInFingerprintOrder,
					currentBest.getPictureMapping());
			String currentFingerprint = getFingerprint(currentCalculationResult.getCardsInFingerprintOrder(),
					currentCalculationResult.getPictureMapping());
			if (bestCalculationResult == null
					|| CardFingerprint.compareFingerprintStrings(currentFingerprint, bestFingerprint) < 0) {
				bestCalculationResult = currentCalculationResult;
				bestFingerprint = currentFingerprint;
			}
		}
		return bestCalculationResult;

	}

	private String getFingerprint(List<Card> cardsInFingerprintOrder, PictureMapping pictureMapping) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for (Card card : cardsInFingerprintOrder) {
			if (first) {
				first = false;
			} else {
				sb.append("-");
			}
			appendFor(card.getNorth(), pictureMapping, sb);
			appendFor(card.getEast(), pictureMapping, sb);
			appendFor(card.getSouth(), pictureMapping, sb);
			appendFor(card.getWest(), pictureMapping, sb);
		}
		return sb.toString();
	}

	public String getFingerprint() {
		return getFingerprint(this.cardsInFingerprintOrder, this.pictureMapping);
	}

	private void appendFor(IPicture picture, PictureMapping pictureMapping, StringBuilder sb) {
		Character pictureChar = pictureMapping.get(picture);
		if (pictureChar == null) {
			sb.append("<" + picture.toString() + ">");
		} else {
			sb.append(pictureChar);
		}
	}

	@Override
	public String toString() {
		if (cardsInFingerprintOrder == null) {
			return Fingerprint.class.getSimpleName() + " not yet calculated for: " + this.cards;
		} else {
			return Fingerprint.class.getSimpleName() + ": " + getFingerprint();
		}

	}


}
