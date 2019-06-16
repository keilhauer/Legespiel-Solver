package org.whatsoftwarecando.legespiel.graphics;

import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.configs.Crazy9KetnerOwls;

public class CardToGraphicsTest {

	@Test
	public void test() throws IOException {
		ArrayList<Card> availableCards = new Crazy9KetnerOwls().getAvailableCardsInstance();
		Font font = new CardToGraphics().calculateFont(availableCards);
		Card card = availableCards.get(0);
		new CardToGraphics().convert(card, font, "png");
	}
}
