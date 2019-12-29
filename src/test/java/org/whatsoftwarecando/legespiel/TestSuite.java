package org.whatsoftwarecando.legespiel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.whatsoftwarecando.legespiel.configs.ExactlyOneSolutionConfigTest;
import org.whatsoftwarecando.legespiel.configs.PictureTest;
import org.whatsoftwarecando.legespiel.experiments.CombinationsTest;
import org.whatsoftwarecando.legespiel.graphics.CardToGraphicsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(value = { CardTest.class, CardToGraphicsTest.class, CombinationsTest.class,
		ExactlyOneSolutionConfigTest.class, FieldTest.class,
		FieldWithConditionsTest.class, PictureTest.class, SolverTest.class })
public class TestSuite {

}
