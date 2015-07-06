package org.whatsoftwarecando.legespiel.configs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.configs.AbsolutKniffligConfig.Picture;

public class PictureTest {

	@Test
	public void testMatches(){
		assertFalse(Picture.MONKEY_1.matches(Picture.HORSE_1));
		assertFalse(Picture.MONKEY_2.matches(Picture.HORSE_1));
		assertFalse(Picture.MONKEY_1.matches(Picture.MONKEY_1));
		assertTrue(Picture.MONKEY_1.matches(Picture.MONKEY_2));
	}

}
