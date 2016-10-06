package org.whatsoftwarecando.legespiel;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Field implements Comparable<Field> {

	private final Card[][] cards;

	private final int rows;
	private final int cols;

	private CardCoordinate currentCoordinates;

	public CardCoordinate getCurrentCoordinates() {
		return currentCoordinates;
	}

	public Field(int rows, int cols) {
		this(rows, cols, new Card[rows][cols]);
	}

	private Field(int rows, int cols, Card[][] cards) {
		this(rows, cols, cards, 1, 0);
	}

	protected Field(int rows, int cols, Card[][] cards, int currentRow,
			int currentColumn) {
		this.rows = rows;
		this.cols = cols;
		this.cards = cards;
		this.currentCoordinates = new CardCoordinate(this, currentRow,
				currentColumn);
	}

	Field(int rows, int cols, List<Card> allCards) {
		this.rows = rows;
		this.cols = cols;
		this.cards = new Card[rows][cols];
		Iterator<Card> cardItr = allCards.iterator();
		outer: for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				if (cardItr.hasNext()) {
					this.cards[row][col] = cardItr.next();
				} else {
					break outer;
				}
			}
		}
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public boolean isFull() {
		return this.cards[this.rows - 1][this.cols - 1] != null;
	}

	public int size() {
		return this.rows * this.cols;
	}

	public Card getCard(int row, int col) {
		return this.cards[row - 1][col - 1];
	}

	List<Card> getRow(int row) {
		List<Card> result = new LinkedList<Card>();
		for (int col = 1; col <= this.cols; col++) {
			Card current = getCard(row, col);
			result.add(current);
		}
		return result;
	}

	List<Card> getCol(int col) {
		List<Card> result = new LinkedList<Card>();
		for (int row = 1; row <= row; row++) {
			Card current = getCard(row, col);
			result.add(current);
		}
		return result;
	}

	/**
	 * @param card
	 * @return The board with the card added or null if the card does not fit
	 */
	public Field addedIfFits(Card card) {

		CardCoordinate nextCoord = this.currentCoordinates.next();

		Card northCard = nextCoord.northCard();
		if (northCard != null) {
			if (!northCard.getSouth().matches(card.getNorth())) {
				return null;
			}
		}
		Card westCard = nextCoord.westCard();
		if (westCard != null) {
			if (!westCard.getEast().matches(card.getWest())) {
				return null;
			}
		}
		int nextRow = nextCoord.getRow();
		int nextColumn = nextCoord.getCol();
		Card[][] cardsCopy = new Card[this.rows][this.cols];
		outer: for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.cols; col++) {
				if (row == nextRow - 1 && col == nextColumn - 1) {
					cardsCopy[row][col] = card;
					break outer;
				} else {
					cardsCopy[row][col] = this.cards[row][col];
				}
			}
		}

		return createField(nextRow, nextColumn, cardsCopy);
	}

	public class CardCoordinate {

		private final Field field;
		private final int row;
		private final int col;

		CardCoordinate(Field field, int row, int col) {
			this.field = field;
			this.row = row;
			this.col = col;
		}

		public Field getField() {
			return field;
		}

		public int getRow() {
			return row;
		}

		public int getCol() {
			return col;
		}

		public Card currentCard() {
			return this.field.getCard(row, col);
		}

		public Card northCard() {
			if (this.row > 1) {
				return this.field.getCard(this.row - 1, this.col);
			} else {
				return null;
			}
		}

		Card southCard() {
			if (this.row < this.field.getRows()) {
				return this.field.getCard(this.row + 1, this.col);
			} else {
				return null;
			}
		}

		Card westCard() {
			if (this.col > 1) {
				return this.field.getCard(this.row, this.col - 1);
			} else {
				return null;
			}
		}

		Card eastCard() {
			if (this.col < this.field.getCols()) {
				return this.field.getCard(this.row, this.col + 1);
			} else {
				return null;
			}
		}

		public CardCoordinate next() {
			int nextCol = this.col + 1;
			int nextRow = this.row;
			if (nextCol > this.field.getCols()) {
				nextCol = 1;
				nextRow++;
				if (nextRow > this.field.getRows()) {
					return null;
				}
			}
			return new CardCoordinate(field, nextRow, nextCol);
		}
	}

	protected Field createField(int currentRow, int currentColumn,
			Card[][] cardsCopy) {
		return new Field(this.rows, this.cols, cardsCopy, currentRow,
				currentColumn);
	}

	Field copy() {
		Card[][] cardsCopy = copyCardArray();
		return new Field(this.rows, this.cols, cardsCopy);
	}

	protected Card[][] copyCardArray() {
		Card[][] cardsCopy = new Card[this.rows][this.cols];
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.cols; col++) {
				cardsCopy[row][col] = this.cards[row][col];
			}
		}
		return cardsCopy;
	}

	public Card getLastCard() {
		return this.currentCoordinates.currentCard();
	}

	/**
	 * Rotating the whole board by 90 degrees clockwise means that the first row
	 * become the last column and the second row becomes the last but one column
	 * and so on. Of course the individual cards also have to be rotated by the
	 * same angle.
	 * 
	 * @return the Board rotated by 90 degrees clockwise
	 */
	public Field turned90DegreesClockwise() {
		Card[][] resultCards = new Card[this.cols][this.rows];
		for (int r = 1; r <= this.rows; r++) {
			List<Card> row = this.getRow(r);
			int resultColumn = (this.rows + 1) - r;
			int rowCount = 1;
			for (Card card : row) {
				resultCards[rowCount - 1][resultColumn - 1] = card
						.turned90DegreesClockwise();
				rowCount++;
			}
		}
		return new Field(this.cols, this.rows, resultCards);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(cards);
		result = prime * result + cols;
		result = prime * result + rows;
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
		Field other = (Field) obj;
		if (cols != other.cols)
			return false;
		if (rows != other.rows)
			return false;
		if (!Arrays.deepEquals(cards, other.cards))
			return false;
		return true;
	}

	public String toHtmlString() {
		StringBuffer result = new StringBuffer();
		result.append("<table>\n");
		for (int r = 1; r <= this.rows; r++) {
			result.append("\t<tr>\n");
			for (int c = 1; c <= this.cols; c++) {
				result.append("\t\t<td>" + this.getCard(r, c).toHtmlString()
						+ "</td>\n");
			}
			result.append("\t</tr>\n");
		}
		result.append("</table>\n");
		return result.toString();
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(this.getClass().getSimpleName() + " [rows=" + rows
				+ ", cols=" + cols + ":\n");
		for (int row = 0; row < this.rows; row++) {
			for (int col = 0; col < this.cols; col++) {
				sb.append(this.cards[row][col]);
				if (col + 1 < this.cols) {
					sb.append(", ");
				}
			}
			sb.append("\n");
		}
		sb.append("]");
		return sb.toString();
	}

	public boolean isQuadratic() {
		return this.cols == this.rows;
	}

	public List<Card> getAllCards() {
		List<Card> result = new LinkedList<Card>();
		for (int row = 1; row <= this.getRows(); row++) {
			for (int col = 1; col <= this.getCols(); col++) {
				Card currentCard = this.getCard(row, col);
				if (currentCard != null) {
					result.add(currentCard);
				}
			}
		}
		return result;
	}

	@Override
	public int compareTo(Field o) {
		for (int row = 1; row <= this.getRows(); row++) {
			for (int col = 1; col <= this.getCols(); col++) {
				Card currentCard = this.getCard(row, col);
				Card currentOtherCard = o.getCard(row, col);
				int cardsCompared = currentCard.compareTo(currentOtherCard);
				if (cardsCompared != 0) {
					return cardsCompared;
				}
			}
		}
		return 0;
	}

	public int getCardsUntilFull() {
		return this.getRows() * this.getCols();
	}

}
