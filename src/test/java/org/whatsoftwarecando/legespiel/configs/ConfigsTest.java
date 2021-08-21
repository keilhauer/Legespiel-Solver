package org.whatsoftwarecando.legespiel.configs;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.GameConfigEqualityChecker;
import org.whatsoftwarecando.legespiel.xml.GameConfigFromXml;

public class ConfigsTest {

	@Test
	public void testEqualsXml() {
		equalConfigs(AsterixKnobeleiConfig.class, "configs/AsterixKnobelei.config.xml");
		equalConfigs(AsterixTotalVerflixtConfig.class, "configs/AsterixTotalVerflixt.config.xml");
		equalConfigs(ButterfliesScrambleSquaresConfig.class, "configs/ButterfliesScrambleSquares.config.xml");
		equalConfigs(CatsScrambleSquaresConfig.class, "configs/CatsScrambleSquares.config.xml");
		equalConfigs(Crazy9KetnerOwls.class, "configs/Crazy9KetnerOwls.config.xml");
		equalConfigs(DasVerflixteHundeSpielConfig.class, "configs/DasVerflixteHundeSpiel.config.xml");
		equalConfigs(DasVerflixteSchildkroetenSpielConfig.class, "configs/DasVerflixteSchildkroetenSpiel.config.xml");
		equalConfigs(DasVerrueckteLoriotLegespielConfig.class, "configs/DasVerrueckteLoriotLegespiel.config.xml");
		equalConfigs(DieSchweineknobeleiConfig.class, "configs/DieSchweineknobelei.config.xml");
		equalConfigs(HotAirBalloonsScrambleSquaresConfig.class, "configs/HotAirBalloonsScrambleSquares.config.xml");
		equalConfigs(KnifflidiffelsVersion1Config.class, "configs/KnifflidiffelsVersion1.config.xml");
		equalConfigs(KnifflidiffelsVersion2Config.class, "configs/KnifflidiffelsVersion2.config.xml");
		equalConfigs(KnifflidiffelsVersion3Config.class, "configs/KnifflidiffelsVersion3.config.xml");
		equalConfigs(KnifflidiffelsVersion4Config.class, "configs/KnifflidiffelsVersion4.config.xml");
		equalConfigs(KnifflidiffelsVersion5Config.class, "configs/KnifflidiffelsVersion5.config.xml");
		equalConfigs(AbsolutKniffligConfig.class, "configs/AbsolutKnifflig.config.xml");
		equalConfigs(RetrieverScrambleSquaresConfig.class, "configs/RetrieverScrambleSquares.config.xml");
		equalConfigs(SerengetiScrambleSquaresConfig.class, "configs/SerengetiScrambleSquares.config.xml");
		equalConfigs(UliSteinNochVerwzickterGehtNichtConfig.class,
				"configs/UliSteinNochVerwzickterGehtNicht.config.xml");
		equalConfigs(UliSteinNochVerzwickterConfig.class, "configs/UliSteinNochVerzwickter.config.xml");
		equalConfigs(VinVinoWeinWineConfig.class, "configs/VinVinoWeinWine.config.xml");
		equalConfigs(WitchesPuzzleConfig.class, "configs/WitchesPuzzle.config.xml");

	}

	private void equalConfigs(Class<? extends GameConfig> clazz, String xmlName) {
		GameConfig gameConfig1 = null;
		try {
			gameConfig1 = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		GameConfig gameConfig2 = new GameConfigFromXml(xmlName);

		GameConfigEqualityChecker.testSolutionsMatches(gameConfig1, gameConfig2);

	}
}
