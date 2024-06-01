package org.whatsoftwarecando.legespiel.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.whatsoftwarecando.legespiel.GameConfig;
import org.whatsoftwarecando.legespiel.IPicture;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GameConfigFromXml extends GameConfig {

	private byte numberOfRows;
	private byte numberOfColumns;
	private String name;
	private String description;

	private List<Picture> pictures;

	public GameConfigFromXml(String xmlConfigOnClasspath) {
		InputSource is = new InputSource(this.getClass().getResourceAsStream(xmlConfigOnClasspath));
		read(is);
	}

	public GameConfigFromXml(InputSource is) {
		read(is);
	}

	protected void read(InputSource is) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Load the input XML document, parse it and return an instance of the
			// Document class.
			Document document = builder.parse(is);
			Map<Byte, IPicture> picturesMap = new HashMap<>();
			this.pictures = new LinkedList<>();
			Element documentElement = document.getDocumentElement();
			documentElement.normalize();
			NodeList nameNodes = documentElement.getElementsByTagName("name");
			if (nameNodes.getLength() > 0) {
				this.name = nameNodes.item(0).getTextContent();
			}
			NodeList descriptionNodes = documentElement.getElementsByTagName("description");
			if (descriptionNodes.getLength() > 0) {
				Node node = descriptionNodes.item(0);
				this.description = node.getTextContent().replace('\n', ' ').replaceAll("[\\r\\t]", "");
			}
			this.numberOfRows = Byte
					.valueOf(documentElement.getElementsByTagName("numberOfRows").item(0).getTextContent());
			this.numberOfColumns = Byte
					.valueOf(documentElement.getElementsByTagName("numberOfColumns").item(0).getTextContent());
			Element picturesElement = (Element) documentElement.getElementsByTagName("pictures").item(0);

			NodeList pictureElementList = picturesElement.getElementsByTagName("picture");
			for (int i = 0; i < pictureElementList.getLength(); i++) {
				Element pictureElement = (Element) pictureElementList.item(i);
				byte pictureId = Byte.valueOf(pictureElement.getAttribute("id"));
				String pictureName = pictureElement.getAttribute("name");
				String matchWithIdsStr = pictureElement.getAttribute("matchWithIds");
				Set<Byte> matchWithIds = new HashSet<>();
				StringTokenizer st = new StringTokenizer(matchWithIdsStr, ",", false);
				while (st.hasMoreElements()) {
					String currentIdStr = st.nextToken();
					currentIdStr = currentIdStr.trim();
					if (!currentIdStr.isEmpty()) {
						Byte currentId = Byte.valueOf(currentIdStr);
						matchWithIds.add(currentId);
					}

				}
				Picture picture = new Picture(pictureId, pictureName, matchWithIds);
				this.pictures.add(picture);
				picturesMap.put(pictureId, picture);
			}

			Element cardsElement = (Element) documentElement.getElementsByTagName("cards").item(0);
			NodeList nodeElementList = cardsElement.getElementsByTagName("card");
			this.availableCards = new ArrayList<>();
			for (int i = 0; i < nodeElementList.getLength(); i++) {
				Element cardElement = (Element) nodeElementList.item(i);
				byte northPictureId = Byte.valueOf(cardElement.getAttribute("north"));
				byte westPictureId = Byte.valueOf(cardElement.getAttribute("west"));
				byte eastPictureId = Byte.valueOf(cardElement.getAttribute("east"));
				byte southPictureId = Byte.valueOf(cardElement.getAttribute("south"));
				this.addCard(picturesMap.get(northPictureId), picturesMap.get(westPictureId),
						picturesMap.get(eastPictureId), picturesMap.get(southPictureId));
			}
			if (availableCards.size() < (numberOfRows * numberOfColumns)) {
				throw new RuntimeException("Not enough cards for dimensions");
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void createAvailableCards() {
	}

	@Override
	public byte getNumberOfRows() {
		return numberOfRows;
	}

	@Override
	public byte getNumberOfColumns() {
		return numberOfColumns;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	@Override
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

}
