package org.whatsoftwarecando.legespiel;

public class Condition {

	private final int row;
	private final int col;
	private IPicture east;
	private IPicture south;

	/**
	 * 
	 * @param row
	 * @param col
	 * @param east
	 * @param south
	 */
	public Condition(int row, int col, IPicture east, IPicture south) {
		this.row = row;
		this.col = col;
		this.east = east;
		this.south = south;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public IPicture getEast() {
		return east;
	}

	public IPicture getSouth() {
		return south;
	}

	public void add(Condition condition) {
		if (condition.getEast() != null) {
			this.east = condition.getEast();
		}
		if (condition.getSouth() != null) {
			this.south = condition.getSouth();
		}
	}

	public boolean matches(Card card) {
		if (east != null && !card.getEast().equals(east)) {
			return false;
		}
		if (south != null && !card.getSouth().equals(south)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + ((east == null) ? 0 : east.hashCode());
		result = prime * result + row;
		result = prime * result + ((south == null) ? 0 : south.hashCode());
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
		Condition other = (Condition) obj;
		if (col != other.col)
			return false;
		if (east == null) {
			if (other.east != null)
				return false;
		} else if (!east.equals(other.east))
			return false;
		if (row != other.row)
			return false;
		if (south == null) {
			if (other.south != null)
				return false;
		} else if (!south.equals(other.south))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Condition [row=" + row + ", col=" + col + ", east=" + east
				+ ", south=" + south + "]";
	}

}