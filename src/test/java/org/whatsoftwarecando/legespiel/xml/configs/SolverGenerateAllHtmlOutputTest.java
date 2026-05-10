package org.whatsoftwarecando.legespiel.xml.configs;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.Solver;
import org.whatsoftwarecando.legespiel.graphics.HtmlGenerator;
import org.whatsoftwarecando.legespiel.xml.GameConfigFromXml;

public class SolverGenerateAllHtmlOutputTest {

	private static final Set<String> IGNORED_XML_FILES = new HashSet<>(Arrays.asList(
			"TheAntTrailsPuzzle.config.xml" // Requires -mx8192m and is very slow
	));

	private static final Set<String> IGNORED_HTML_OUTPUT_DIRS = new HashSet<>(Arrays.asList(
			"FourPicturesConfig" // Java-only test config, no XML equivalent
	));

	/**
	 * Maps XML filenames to output directory names for cases where the automatic
	 * name generation (escaping the XML &lt;name&gt; element) would produce a
	 * different directory than what already exists.
	 */
	private static final Map<String, String> DIRECTORY_NAME_OVERRIDES = new HashMap<>();
	static {
		// Preserved from old class-based generation
		DIRECTORY_NAME_OVERRIDES.put("AbsolutKnifflig.config.xml", "AbsolutKniffligConfig");
		DIRECTORY_NAME_OVERRIDES.put("AsterixKnobelei.config.xml", "AsterixKnobeleiConfig");
		DIRECTORY_NAME_OVERRIDES.put("AsterixTotalVerflixt.config.xml", "AsterixTotalVerflixtConfig");
		DIRECTORY_NAME_OVERRIDES.put("ButterfliesScrambleSquares.config.xml", "ButterfliesScrambleSquaresConfig");
		DIRECTORY_NAME_OVERRIDES.put("CatsScrambleSquares.config.xml", "CatsScrambleSquaresConfig");
		DIRECTORY_NAME_OVERRIDES.put("Crazy9KetnerOwls.config.xml", "Crazy9KetnerOwls");
		DIRECTORY_NAME_OVERRIDES.put("DasVerflixteSchildkroetenSpiel.config.xml", "DasVerflixteSchildkroetenSpielConfig");
		DIRECTORY_NAME_OVERRIDES.put("DasVerrueckteLoriotLegespiel.config.xml", "DasVerrueckteLoriotLegespielConfig");
		DIRECTORY_NAME_OVERRIDES.put("DasVerflixteHundeSpiel.config.xml", "DasVerflixteHundeSpielConfig");
		DIRECTORY_NAME_OVERRIDES.put("DieSchweineknobelei.config.xml", "DieSchweineknobeleiConfig");
		DIRECTORY_NAME_OVERRIDES.put("HotAirBalloonsScrambleSquares.config.xml", "HotAirBalloonsScrambleSquaresConfig");
		DIRECTORY_NAME_OVERRIDES.put("KnifflidiffelsVersion1.config.xml", "KnifflidiffelsVersion1Config");
		DIRECTORY_NAME_OVERRIDES.put("KnifflidiffelsVersion2.config.xml", "KnifflidiffelsVersion2Config");
		DIRECTORY_NAME_OVERRIDES.put("KnifflidiffelsVersion3.config.xml", "KnifflidiffelsVersion3Config");
		DIRECTORY_NAME_OVERRIDES.put("KnifflidiffelsVersion4.config.xml", "KnifflidiffelsVersion4Config");
		DIRECTORY_NAME_OVERRIDES.put("KnifflidiffelsVersion5.config.xml", "KnifflidiffelsVersion5Config");
		DIRECTORY_NAME_OVERRIDES.put("RetrieverScrambleSquares.config.xml", "RetrieverScrambleSquaresConfig");
		DIRECTORY_NAME_OVERRIDES.put("SerengetiScrambleSquares.config.xml", "SerengetiScrambleSquaresConfig");
		DIRECTORY_NAME_OVERRIDES.put("UliSteinNochVerwzickterGehtNicht.config.xml", "UliSteinNochVerwzickterGehtNichtConfig");
		DIRECTORY_NAME_OVERRIDES.put("UliSteinNochVerzwickter.config.xml", "UliSteinNochVerzwickterConfig");
		DIRECTORY_NAME_OVERRIDES.put("VinVinoWeinWine.config.xml", "VinVinoWeinWineConfig");
		DIRECTORY_NAME_OVERRIDES.put("WitchesPuzzle.config.xml", "WitchesPuzzleConfig");
		DIRECTORY_NAME_OVERRIDES.put("NorisPassts.config.xml", "NORIS Passt's");
		DIRECTORY_NAME_OVERRIDES.put("NobsArrows.config.xml", "Nob's Arrows");
		DIRECTORY_NAME_OVERRIDES.put("DolphinScrambleSquares.config.xml", "Dolphin scramble squares");
	}

	@Test
	public void testAllXmlConfigs() throws Exception {
		URL configsDirUrl = GameConfigFromXml.class.getResource("configs");
		File[] xmlFiles = new File(configsDirUrl.toURI())
				.listFiles((dir, name) -> name.endsWith(".xml"));
		Arrays.sort(xmlFiles, Comparator.comparing(File::getName));

		for (File xmlFile : xmlFiles) {
			String filename = xmlFile.getName();
			if (IGNORED_XML_FILES.contains(filename)) {
				continue;
			}
			String override = DIRECTORY_NAME_OVERRIDES.get(filename);
			GameConfig gameConfig;
			if (override != null) {
				final String outputDir = override;
				gameConfig = new GameConfigFromXml("configs/" + filename) {
					@Override
					public String getOutputDirectoryName() {
						return outputDir;
					}
				};
			} else {
				try {
					gameConfig = new GameConfigFromXml("configs/" + filename);
				} catch (RuntimeException r) {
					throw new RuntimeException("Problem creating game config from XML: " + filename, r);
				}
			}
			runSolver(gameConfig);
		}
	}

	@Test
	public void testAllHtmlOutputDirsHaveXmlConfig() throws Exception {
		File htmlOutputDir = new File("html-output");
		if (!htmlOutputDir.exists()) {
			fail("directory html-output does not exist!");
		}

		URL configsDirUrl = GameConfigFromXml.class.getResource("configs");
		File[] xmlFiles = new File(configsDirUrl.toURI())
				.listFiles((dir, name) -> name.endsWith(".xml"));

		Set<String> expectedDirs = new HashSet<>();
		for (File xmlFile : xmlFiles) {
			String filename = xmlFile.getName();
			if (IGNORED_XML_FILES.contains(filename)) {
				continue;
			}
			String override = DIRECTORY_NAME_OVERRIDES.get(filename);
			if (override != null) {
				expectedDirs.add(override);
			} else {
				expectedDirs.add(new GameConfigFromXml("configs/" + filename).getOutputDirectoryName());
			}
		}

		File[] subdirs = htmlOutputDir.listFiles(File::isDirectory);
		List<String> orphanedDirs = new ArrayList<>();
		for (File subdir : subdirs) {
			if (!IGNORED_HTML_OUTPUT_DIRS.contains(subdir.getName())
					&& !expectedDirs.contains(subdir.getName())) {
				orphanedDirs.add(subdir.getName());
			}
		}
		Collections.sort(orphanedDirs);

		if (!orphanedDirs.isEmpty()) {
			Assert.fail("html-output directories with no corresponding XML config: " + orphanedDirs);
		}
	}

	private void runSolver(GameConfig gameConfig) throws URISyntaxException, IOException {
		Solver solver = new Solver(gameConfig);
		Collection<Field> solutions = solver.findAllSolutions();
		HtmlGenerator.getInstance().writeHtml(gameConfig, solutions, "allSolutions", "All Solutions");
		List<Field> originalSolutions = solver.removeRotationBasedDuplicates();
		HtmlGenerator.getInstance().writeHtml(gameConfig, originalSolutions, "originalSolutions", "Original Solutions");
	}

}
