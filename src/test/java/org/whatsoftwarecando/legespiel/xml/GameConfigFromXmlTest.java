package org.whatsoftwarecando.legespiel.xml;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.GameConfigEqualityChecker;
import org.whatsoftwarecando.legespiel.configs.AbsolutKniffligConfig;

public class GameConfigFromXmlTest {

	@Test
	public void testReadAbsolutKniffligXml() {
		GameConfigFromXml gameConfig = new GameConfigFromXml("configs/AbsolutKnifflig.config.xml");
		assertEquals(3, gameConfig.getNumberOfRows());
		assertEquals(3, gameConfig.getNumberOfColumns());
		assertEquals(9, gameConfig.getAvailableCards().size());
	}

	@Test
	public void testSolutionsMatchesAbsolutKnifflig() {
		GameConfigEqualityChecker.testSolutionsMatches(new AbsolutKniffligConfig(),
				new GameConfigFromXml("configs/AbsolutKnifflig.config.xml"));
	}

}
