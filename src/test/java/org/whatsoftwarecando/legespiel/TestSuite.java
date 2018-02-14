package org.whatsoftwarecando.legespiel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.whatsoftwarecando.legespiel.configs.CardTest;
import org.whatsoftwarecando.legespiel.configs.PictureTest;
import org.whatsoftwarecando.legespiel.experiments.CombinationsTest;
import org.whatsoftwarecando.legespiel.graphics.CardToGraphicsTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(value = { FieldTest.class, PictureTest.class, CardTest.class, SolverTest.class,
		FieldWithConditionsTest.class, CardToGraphicsTest.class, CombinationsTest.class })
public class TestSuite {

}
