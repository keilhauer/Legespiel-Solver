package org.whatsoftwarecando.legespiel;

public class Card implements Comparable<Card> {

	private final int id;
	private final IPicture north;
	private final IPicture west;
	private final IPicture east;
	private final IPicture south;
	private final int rotationClockwise;

	Card(int id, IPicture north, IPicture west, IPicture east, IPicture south, int rotationClockwise) {
		super();
		this.id = id;
		this.north = north;
		this.west = west;
		this.east = east;
		this.south = south;
		this.rotationClockwise = rotationClockwise;
	}

	public int getId() {
		return id;
	}

	public IPicture getNorth() {
		return north;
	}

	public IPicture getWest() {
		return west;
	}

	public IPicture getEast() {
		return east;
	}

	public IPicture getSouth() {
		return south;
	}

	public int getRotationClockwise() {
		return rotationClockwise;
	}

	public Card turned90DegreesClockwise() {
		return new Card(this.id, this.west, this.south, this.north, this.east, (this.rotationClockwise + 90) % 360);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((east == null) ? 0 : east.hashCode());
		result = prime * result + ((north == null) ? 0 : north.hashCode());
		result = prime * result + ((south == null) ? 0 : south.hashCode());
		result = prime * result + ((west == null) ? 0 : west.hashCode());
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
		Card other = (Card) obj;
		if (east == null) {
			if (other.east != null)
				return false;
		} else if (!east.equals(other.east))
			return false;
		if (north == null) {
			if (other.north != null)
				return false;
		} else if (!north.equals(other.north))
			return false;
		if (south == null) {
			if (other.south != null)
				return false;
		} else if (!south.equals(other.south))
			return false;
		if (west == null) {
			if (other.west != null)
				return false;
		} else if (!west.equals(other.west))
			return false;
		return true;
	}

	public String toHtmlString() {
		String rotationDesc = "r" + this.rotationClockwise;
		return "<img src=\"card" + this.id + ".png\" class =\"" + rotationDesc + "\" alt=\"" + id + "-" + rotationDesc
				+ "\">";
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", north=" + north + ", west=" + west + ", east=" + east + ", south=" + south
				+ ", rotationClockwise=" + rotationClockwise + "]";
	}

	@Override
	public int compareTo(Card o) {
		int compareIds = new Integer(this.getId()).compareTo(o.getId());
		if (compareIds != 0) {
			return compareIds;
		}
		return new Integer(this.getRotationClockwise()).compareTo(o.getRotationClockwise());
	}

}
