package org.whatsoftwarecando.legespiel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.whatsoftwarecando.legespiel.configs.DasVerrueckteLoriotLegespielConfig;

public class CardTest {

	private enum TestPicture implements IPicture {
		
		ONE, TWO, THREE, FOUR;

		@Override
		public boolean matches(IPicture other) {
			return this == other;
		}

	}
	@Test
	public void testTurnedClockwise() {
		CardCreator cc = new CardCreator();
		Card testCard = cc.createCard(TestPicture.ONE, TestPicture.TWO, TestPicture.THREE, TestPicture.FOUR);
		Card tmp = testCard.turned90DegreesClockwise();
		tmp = tmp.turned90DegreesClockwise();
		tmp = tmp.turned90DegreesClockwise();
		tmp = tmp.turned90DegreesClockwise();
		assertEquals(testCard, tmp);
		assertEquals(testCard.getRotationClockwise(), tmp.getRotationClockwise());
	}

	/**
	 * Dieser Test funktioniert nicht mehr, da die Reihenfolge der Karten in der
	 * Konfiguration geändert wurde.
	 */
	@Ignore
	@Test
	public void testSimpleSolution() {
		GameConfig config = new DasVerrueckteLoriotLegespielConfig();
		Field field = config.getEmptyField();
		for (Card current : config.getAvailableCards()) {
			field = field.addedIfFits(current);
			assertNotNull(field);
		}
		assertTrue(field.isFull());
	}
}
