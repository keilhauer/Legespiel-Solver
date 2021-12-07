package org.whatsoftwarecando.legespiel.fingerprint;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;
import org.whatsoftwarecando.legespiel.xml.GameConfigFromXml;

public class FingerprintTest {

	@Test
	public void testDuckula() {
		test("DuckulaDerVerflixte.config.xml", "ABCD-ABCD-ABCb-AcBd-AdBc-AdCb-BacD-Dcda-abcd");
	}

	@Test
	public void testTomAndJerry() {
		test("DasVerflixteTomAndJerrySpiel.config.xml", "ABCD-ABCD-ABCb-AcBd-AdBc-AdCb-BacD-Dcda-abcd");
	}

	@Test
	public void testCrazy9KetnerOwls() {
		test("Crazy9KetnerOwls.config.xml", "ABCD-ABCD-ABCb-AcBd-AdBc-AdCb-BacD-Dcda-abcd");
	}

	@Test
	public void testDasVerflixteSchildkroetenspiel() {
		test("DasVerflixteSchildkroetenSpiel.config.xml", "ABCD-ABCD-ABCb-AcBd-AdBc-AdCb-BacD-Dcda-abcd");
	}

	@Test
	public void testDieKatzenKnobelei() {
		test("DieKatzenKnobelei.config.xml", "ABCD-ABCD-ABCb-AcBd-AdBc-AdCb-BacD-Dcda-abcd");
	}

	@Test
	public void testEqualFingerprintForSameProblem() {
		String sameFingerprint = "ABCD-ABCD-ABCb-AcBd-AdBc-AdCb-BacD-Dcda-abcd";
		test("DuckulaDerVerflixte.config.xml", sameFingerprint);
		test("DasVerflixteTomAndJerrySpiel.config.xml", sameFingerprint);
		test("Crazy9KetnerOwls.config.xml", sameFingerprint);
		test("DasVerflixteSchildkroetenSpiel.config.xml", sameFingerprint);
		test("DieKatzenKnobelei.config.xml", sameFingerprint);
	}


	@Test
	public void testKnifflidiffels1() {
		test("KnifflidiffelsVersion1.config.xml", "ABCD-ABCD-ABCD-ABCD-ABDC-ABDC-ACDB-ADCB-ADCB");
	}

	@Test
	public void testKnifflidiffels2() {
		test("KnifflidiffelsVersion2.config.xml", "ABCD-ABCD-ABCD-ABCD-ABDC-ABDC-ACDB-ADCB-ADCB");
	}

	@Test
	public void testQuasthoffBeispiel() {
		test("QuasthoffBeispiel.config.xml", "AAaB-CDab-acbd-acdb");
	}

	@Test
	public void testEqualFingerprintForSameProblem2() {
		String sameFingerprint = "ABCD-ABCD-ABCD-ABCD-ABDC-ABDC-ACDB-ADCB-ADCB";
		test("KnifflidiffelsVersion1.config.xml", sameFingerprint);
		test("KnifflidiffelsVersion2.config.xml", sameFingerprint);
	}

	@Test
	public void testKnifflidiffels5() {
		test("KnifflidiffelsVersion5.config.xml", "ABCD-ABCD-ABCD-ABDC-ABDC-ABDC-ACDB-ACDB-ADCB");
	}

	private void test(String configResource, String expectedFingerprint) {
		for (int i = 1; i <= 100; i++) {
			GameConfigFromXml gameConfig = new GameConfigFromXml("configs/" + configResource);
			Fingerprint fingerprint = new Fingerprint(gameConfig.getAvailableCards());
			String fingerprintStr = fingerprint.calculate();
			assertEquals(expectedFingerprint, fingerprintStr);
			Collections.shuffle(gameConfig.getAvailableCards());
		}
	}
}
