package org.whatsoftwarecando.legespiel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.whatsoftwarecando.legespiel.configs.CardTest;
import org.whatsoftwarecando.legespiel.configs.PictureTest;

@RunWith(Suite.class)
@Suite.SuiteClasses(value={FieldTest.class, PictureTest.class, CardTest.class, SolverTest.class, FieldWithConditionsTest.class})
public class TestSuite {

}
