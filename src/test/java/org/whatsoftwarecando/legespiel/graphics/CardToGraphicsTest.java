package org.whatsoftwarecando.legespiel.graphics;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.configs.Crazy9KetnerOwls;

public class CardToGraphicsTest {

	@Test
	public void test() throws IOException {
		List<Card> availableCards = new Crazy9KetnerOwls().getAvailableCards();
		Font font = new CardToGraphicsConverter().calculateFont(availableCards);
		Card card = availableCards.get(0);
		new CardToGraphicsConverter().convert(card, font, Color.red, "png");
	}
}
