package org.whatsoftwarecando.legespiel.fingerprint;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.xml.GameConfigFromXml;

public class CardFingerprintTest {

	@Test
	public void test1() {
		GameConfig gameConfig = load("DieKatzenKnobelei.config.xml");
		for (Card card : gameConfig.getAvailableCards()) {
			CardFingerprint cardFingerprint = new CardFingerprint(card,
					new PictureMapping(gameConfig.getAvailableCards()));
			Set<CardFingerprint> minimalCardFingerprints = cardFingerprint.calculateMinimalComplete();
			minimalCardFingerprints.forEach(m -> System.out.println(m));
		}
	}

	@Test
	public void test2() {
		GameConfig gameConfig = load("AbsolutKnifflig.config.xml");
		for (Card card : gameConfig.getAvailableCards()) {
			CardFingerprint cardFingerprint = new CardFingerprint(card,
					new PictureMapping(gameConfig.getAvailableCards()));
			Set<CardFingerprint> minimalCardFingerprints = cardFingerprint.calculateMinimalComplete();
			minimalCardFingerprints.forEach(m -> System.out.println(m));
		}
	}

	private GameConfig load(String configResource) {
		GameConfigFromXml gameConfig = new GameConfigFromXml("configs/" + configResource);
		return gameConfig;
	}

	@Test
	public void cardFingerprintTestCompare() {
		assertEquals(-32, CardFingerprint.compareFingerprintStrings("A", "a"));
		assertEquals(-32, CardFingerprint.compareFingerprintStrings("AB", "Ab"));
		assertEquals(0, CardFingerprint.compareFingerprintStrings("AB", "AB"));
		assertEquals(32, CardFingerprint.compareFingerprintStrings("a", "A"));
		assertEquals(32, CardFingerprint.compareFingerprintStrings("Ab", "AB"));
		assertEquals(-31, CardFingerprint.compareFingerprintStrings("AC", "Ab"));
		assertEquals(-29, CardFingerprint.compareFingerprintStrings("ABCD", "ABCa"));
	}
}
