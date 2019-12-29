package org.whatsoftwarecando.legespiel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.configs.AbsolutKniffligConfig;
import org.whatsoftwarecando.legespiel.configs.AsterixKnobeleiConfig;
import org.whatsoftwarecando.legespiel.configs.UliSteinNochVerwzickterGehtNichtConfig;

public class FieldTest {

	@Test
	public void testAddAbsolutKnifflig() {
		Field board = new Field(3, 3);
		int count = 0;
		for (Card card : new AbsolutKniffligConfig().getAvailableCards()) {
			board = board.addedIfFits(card);
			count++;
			if (count <= 8) {
				assertTrue(board != null);
			} else {
				assertEquals(null, board);
			}
		}
	}

	@Test
	public void testAddAsterix() {
		AsterixKnobeleiConfig config = new AsterixKnobeleiConfig();
		Field board = config.getEmptyField();
		for (Card card : config.getAvailableCards()) {
			board = board.addedIfFits(card);
		}
		assertTrue(board.isFull());
	}

	@Test
	public void testAddUliStein() {
		Field board = new Field(3, 3);
		for (Card card : new UliSteinNochVerwzickterGehtNichtConfig().getAvailableCards()) {
			board = board.addedIfFits(card);
		}
		assertTrue(board.isFull());
	}

	@Test
	public void testTurnedClockwise360() {
		Field board = new Field(3, 3, new AbsolutKniffligConfig().getAvailableCards());
		Field boardTurned = board.copy();
		boardTurned = boardTurned.turned90DegreesClockwise();
		boardTurned = boardTurned.turned90DegreesClockwise();
		boardTurned = boardTurned.turned90DegreesClockwise();
		boardTurned = boardTurned.turned90DegreesClockwise();
		assertEquals(board, boardTurned);
	}

	@Test
	public void testTurnedClockwise90() {
		List<Card> allCards = new AbsolutKniffligConfig().getAvailableCards();
		Field field = new Field(3, 3, allCards);
		Field boardTurned90 = field.copy();
		boardTurned90 = boardTurned90.turned90DegreesClockwise();
		assertEquals(field.getCard(1, 1).turned90DegreesClockwise(), boardTurned90.getCard(1, 3));
		assertEquals(field.getCard(1, 2).turned90DegreesClockwise(), boardTurned90.getCard(2, 3));
		assertEquals(field.getCard(1, 3).turned90DegreesClockwise(), boardTurned90.getCard(3, 3));
		assertEquals(field.getCard(2, 1).turned90DegreesClockwise(), boardTurned90.getCard(1, 2));
		assertEquals(field.getCard(2, 2).turned90DegreesClockwise(), boardTurned90.getCard(2, 2));
		assertEquals(field.getCard(2, 3).turned90DegreesClockwise(), boardTurned90.getCard(3, 2));
		assertEquals(field.getCard(3, 1).turned90DegreesClockwise(), boardTurned90.getCard(1, 1));
		assertEquals(field.getCard(3, 2).turned90DegreesClockwise(), boardTurned90.getCard(2, 1));
		assertEquals(field.getCard(3, 3).turned90DegreesClockwise(), boardTurned90.getCard(3, 1));
	}

	@Test
	public void testTurnNonSquareField() {
		List<Card> allCards = new AbsolutKniffligConfig().getAvailableCards();
		Field field = new Field(2, 3, allCards.subList(0, 6));
		Field boardTurned90 = field.copy();
		boardTurned90 = boardTurned90.turned90DegreesClockwise();
		assertEquals(field.getCard(1, 1).turned90DegreesClockwise(), boardTurned90.getCard(1, 2));
		assertEquals(field.getCard(1, 2).turned90DegreesClockwise(), boardTurned90.getCard(2, 2));
		assertEquals(field.getCard(1, 3).turned90DegreesClockwise(), boardTurned90.getCard(3, 2));
		assertEquals(field.getCard(2, 1).turned90DegreesClockwise(), boardTurned90.getCard(1, 1));
		assertEquals(field.getCard(2, 2).turned90DegreesClockwise(), boardTurned90.getCard(2, 1));
		assertEquals(field.getCard(2, 3).turned90DegreesClockwise(), boardTurned90.getCard(3, 1));
	}

}
