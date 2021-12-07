package org.whatsoftwarecando.legespiel.fingerprint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.whatsoftwarecando.legespiel.Card;
import org.whatsoftwarecando.legespiel.IPicture;

public class PictureMapping {

	private PictureMapping parentMapping;

	private Map<IPicture, Character> map;

	Character smallestCharacterLeft;

	private Map<IPicture, IPicture> mappingPictures = null;

	public PictureMapping(List<Card> allAvailableCards) {
		this.smallestCharacterLeft = 'A';
		this.map = new HashMap<>();
		this.mappingPictures = new HashMap<>();

		HashSet<IPicture> pictureSet = new HashSet<>();
		for (Card card : allAvailableCards) {
			pictureSet.add(card.getNorth());
			pictureSet.add(card.getEast());
			pictureSet.add(card.getSouth());
			pictureSet.add(card.getWest());
		}
		List<IPicture> allPictures = new LinkedList<IPicture>(pictureSet);
		for (IPicture picture : allPictures) {
			for (IPicture picture2 : allPictures) {
				if (picture.matches(picture2)) {
					this.mappingPictures.put(picture, picture2);
				}
			}
		}
	}

	public PictureMapping(PictureMapping parentMapping) {
		this.smallestCharacterLeft = parentMapping.smallestCharacterLeft;
		this.map = new HashMap<>();
		this.parentMapping = parentMapping;
		this.mappingPictures = parentMapping.mappingPictures;
	}

	private PictureMapping(Map<IPicture, Character> map, Map<IPicture, IPicture> mappingPictures,
			Character smallestCharacterLeft) {
		this.map = map;
		this.mappingPictures = mappingPictures;
		this.smallestCharacterLeft = smallestCharacterLeft;
	}

	public void put(IPicture picture, Character character) {
		this.map.put(picture, character);
	}

	public Character get(IPicture picture) {
		if (this.parentMapping != null) {
			Character characterFromParentMapping = this.parentMapping.get(picture);
			if (characterFromParentMapping != null) {
				return characterFromParentMapping;
			}
		}
		return this.map.get(picture);
	}

	public IPicture getMappingForPicture(IPicture picture) {
		return this.mappingPictures.get(picture);
	}

	public Character retrieveSmallestCharacterLeft() {
		if (smallestCharacterLeft > 'Z') {
			throw new RuntimeException("Too many pictures!");
		}
		Character result = smallestCharacterLeft;
		smallestCharacterLeft++;
		return result;
	}

	public PictureMapping merged() {
		if (this.parentMapping == null) {
			throw new RuntimeException("No parent mapping!");
		} else {
			Map<IPicture, Character> newMap = new HashMap<>();
			newMap.putAll(this.parentMapping.map);
			newMap.putAll(this.map);
			Map<IPicture, IPicture> newMappingPictures = new HashMap<>();
			newMappingPictures.putAll(this.parentMapping.mappingPictures);
			newMappingPictures.putAll(this.mappingPictures);
			return new PictureMapping(newMap, newMappingPictures, smallestCharacterLeft);
		}
	}
}

