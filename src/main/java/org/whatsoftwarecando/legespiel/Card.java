package org.whatsoftwarecando.legespiel;

public class Card implements Comparable<Card>{

	private static int ID_COUNTER = 1;

	private final int id;
	private final IPicture north;
	private final IPicture west;
	private final IPicture east;
	private final IPicture south;
	private final int rotationClockwise;

	public Card(IPicture north, IPicture west, IPicture east, IPicture south) {
		this(ID_COUNTER++, north, west, east, south, 0);
	}

	public Card(IPicture[] pictures) {
		this(pictures[0], pictures[1], pictures[2], pictures[3]);
	}

	private Card(int id, IPicture north, IPicture west, IPicture east,
			IPicture south, int rotationClockwise) {
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
		return new Card(this.id, this.west, this.south, this.north, this.east,
				(this.rotationClockwise + 90) % 360);
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
		if (east != other.east)
			return false;
		if (north != other.north)
			return false;
		if (south != other.south)
			return false;
		if (west != other.west)
			return false;
		return true;
	}

	public String toHtmlString() {
		String rotationDesc = "r" + this.rotationClockwise;
		return "<img src=\"card" + this.id + ".png\" class =\"" + rotationDesc
				+ "\" alt=\"" + id + "-" + rotationDesc + "\">";
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", north=" + north + ", west=" + west
				+ ", east=" + east + ", south=" + south + "]";
	}

	@Override
	public int compareTo(Card o) {
		int compareIds = new Integer(this.getId()).compareTo(o.getId());
		if(compareIds != 0){
			return compareIds;
		}
		return new Integer(this.getRotationClockwise()).compareTo(o.getRotationClockwise());
	}

}
