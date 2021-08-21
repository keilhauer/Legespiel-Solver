package org.whatsoftwarecando.legespiel.xml;

import java.util.Set;

import org.whatsoftwarecando.legespiel.IPicture;

public class Picture implements IPicture {

	private byte id;

	private Set<Byte> matchingIds;

	private String name;

	public Picture(PictureInfo pictureInfo) {
		this.id = pictureInfo.getId();
		this.matchingIds = pictureInfo.getMatchSet();
		this.name = pictureInfo.getName();
	}

	public Picture(byte id, String name, Set<Byte> matchingIds) {
		this.id = id;
		this.matchingIds = matchingIds;
		this.name = name;
	}

	@Override
	public boolean matches(IPicture other) {
		if (other instanceof Picture) {
			Picture otherPicture = (Picture) other;
			return this.matchingIds.contains(otherPicture.getId());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Picture other = (Picture) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public byte getId() {
		return id;
	}

	public String name() {
		return name;
	}

	public Set<Byte> getMatchingIds() {
		return matchingIds;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
