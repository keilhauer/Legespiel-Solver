package org.whatsoftwarecando.legespiel.graphics;

import java.io.IOException;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.configs.Crazy9KetnerOwls;

public class CardToGraphicsTest {

	@Test
	public void test() throws IOException {
		Card card = new Crazy9KetnerOwls().getAvailableCardsInstance().get(0);
		new CardToGraphics().convert(card, "png");
	}
}
