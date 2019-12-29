package org.whatsoftwarecando.legespiel.graphics;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.DuplicateCardsFinder;
import org.whatsoftwarecando.legespiel.Field;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.Solver;

public class HtmlGenerator {

	private static HtmlGenerator INSTANCE = null;

	private List<Color> duplicateColors = null;

	private HtmlGenerator() {
		duplicateColors = new LinkedList<Color>();
		duplicateColors.add(new Color(208, 240, 192));
		duplicateColors.add(new Color(115, 194, 251));
		duplicateColors.add(new Color(255, 204, 203));
		duplicateColors.add(Color.green);
		duplicateColors.add(Color.blue);
		duplicateColors.add(Color.orange);
	}

	public static synchronized HtmlGenerator getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new HtmlGenerator();
		}
		return INSTANCE;
	}

	public Map<Integer, Color> findDuplicateCards(List<Card> availableCards) {
		System.out.println("Looking for duplicate cards: ");
		boolean foundDuplicateCards = false;
		Map<Integer, Color> duplicateCardColorMap = new HashMap<>();

		Iterator<Color> duplicateColorsItr = duplicateColors.iterator();
		Color duplicateColor = null;
		for (List<Card> duplicates : new DuplicateCardsFinder().findDuplicateCards(availableCards)) {
			if (duplicates.size() > 1) {
				if (duplicateColorsItr.hasNext()) {
					duplicateColor = duplicateColorsItr.next();
				}
				System.out.println(duplicates);
				foundDuplicateCards = true;
				for (Card duplicate : duplicates) {
					duplicateCardColorMap.put(duplicate.getId(), duplicateColor);
				}
			}
		}
		if (!foundDuplicateCards) {
			System.out.println("No duplicate cards found");
		}
		return duplicateCardColorMap;
	}

	public void writeHtml(GameConfig gameConfig, Collection<Field> solutions, String filename, String title)
			throws URISyntaxException, UnsupportedEncodingException, IOException {
		// Preparing HTML-Output
		StringBuffer sb = new StringBuffer();
		for (Field originalSolution : solutions) {
			sb.append(originalSolution.toHtmlString());
		}

		// Writing Html
		Path templateFile = Paths.get(Solver.class.getResource("solutions.template.html").toURI());
		String allSolutionsHtmlTemplate = new String(Files.readAllBytes(templateFile), "UTF-8");
		String allSolutionsHtml = allSolutionsHtmlTemplate
				.replace("%title%", title + " - " + gameConfig.getClass().getSimpleName())
				.replace("%content%", sb.toString());
		Path htmlOutputFile = Paths
				.get("html-output/" + gameConfig.getClass().getSimpleName() + "/" + filename + ".html");
		Files.createDirectories(htmlOutputFile.getParent());
		Files.write(htmlOutputFile, allSolutionsHtml.getBytes("UTF-8"));

		generateImages(gameConfig, findDuplicateCards(gameConfig.getAvailableCards()));
		System.out.println("Written \"" + title + "\" to file: " + htmlOutputFile);
	}

	private void generateImages(GameConfig gameConfig, Map<Integer, Color> duplicateCardColorMap) throws IOException {
		CardToGraphicsConverter cardToGraphics = new CardToGraphicsConverter();
		Font font = cardToGraphics.calculateFont(gameConfig.getAvailableCards());
		for (Card card : gameConfig.getAvailableCards()) {
			byte[] cardImage = cardToGraphics.convert(card, font, duplicateCardColorMap.get(card.getId()), "png");
			Path imageOutputFile = Paths
					.get("html-output/" + gameConfig.getClass().getSimpleName() + "/card" + card.getId() + ".png");
			Files.write(imageOutputFile, cardImage);
		}
	}
}
