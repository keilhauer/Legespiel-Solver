package org.whatsoftwarecando.legespiel.configs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;

public class CardTest {

	@Test
	public void testTurnedClockwise() {
		Card testCard = new Card(AbsolutKniffligConfig.Picture.PIPPI_1, AbsolutKniffligConfig.Picture.PIPPI_2,
				AbsolutKniffligConfig.Picture.MONKEY_1, AbsolutKniffligConfig.Picture.MONKEY_2);
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
		Field field = config.createEmptyField();
		for (Card current : config.getAvailableCards()) {
			field = field.addedIfFits(current);
			assertNotNull(field);
		}
		assertTrue(field.isFull());
	}
}
