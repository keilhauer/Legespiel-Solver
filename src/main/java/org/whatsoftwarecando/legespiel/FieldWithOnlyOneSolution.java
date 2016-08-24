package org.whatsoftwarecando.legespiel;

import java.util.HashSet;
import java.util.SortedSet;
import java.util.TreeSet;

public class FieldWithOnlyOneSolution extends Field {

	private static HashSet<SortedSet<Integer>> solutionIds = new HashSet<SortedSet<Integer>>();

	public FieldWithOnlyOneSolution(int rows, int cols) {
		this(rows, cols, new Card[rows][cols], 1, 0);
	}

	protected FieldWithOnlyOneSolution(int rows, int cols, Card[][] cards, int currentRow, int currentCols) {
		super(rows, cols, cards, currentRow, currentCols);
	}

	@Override
	public Field addedIfFits(Card card) {
		Field field = super.addedIfFits(card);
		if (field == null) {
			return null;
		}

		if (field.isFull()) {
			SortedSet<Integer> idsForField = new TreeSet<Integer>();
			for (int row = 1; row <= field.getRows(); row++) {
				for (int col = 1; col <= field.getCols(); col++) {
					idsForField.add(field.getCard(row, col).getId());
				}
			}
			if (solutionIds.add(idsForField)) {
				return field;
			} else {
				return null;
			}
		}

		return field;
	}

	@Override
	protected Field createField(int currentRow, int currentColumn, Card[][] cardsCopy) {
		return new FieldWithOnlyOneSolution(this.getRows(), this.getCols(), cardsCopy, currentRow, currentColumn);
	}
}
