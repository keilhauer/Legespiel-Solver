package org.whatsoftwarecando.legespiel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FieldWithConditions extends Field {

	private final int rowComplete;
	private final int colComplete;
	private int cardsUntilFull;
	
	Map<Integer, Map<Integer, Condition>> conditions;

	public FieldWithConditions(int rows, int cols, int cardsUntilFull,
			List<Condition> conditions) {
		super(rows, cols);
		this.rowComplete = (cardsUntilFull - 1) / cols + 1;
		this.colComplete = (cardsUntilFull - 1) % cols + 1;
		this.cardsUntilFull = cardsUntilFull;
		this.conditions = new HashMap<Integer, Map<Integer, Condition>>();

		for (Condition currentCondition : conditions) {
			addCondition(currentCondition.getRow(), currentCondition.getCol(),
					currentCondition);
		}
	}
	
	@Override
	public int getCardsUntilFull() {
		return cardsUntilFull;
	}

	private FieldWithConditions(int rows, int cols, int rowComplete,
			int colComplete, Card[][] cardsCopy, int currentRow,
			int currentColumn, Map<Integer, Map<Integer, Condition>> conditions) {
		super(rows, cols, cardsCopy, currentRow, currentColumn);
		this.rowComplete = rowComplete;
		this.colComplete = colComplete;
		this.conditions = conditions;
	}

	@Override
	public boolean isFull() {
		return this.getCard(rowComplete, colComplete) != null;
	}

	public void addCondition(int row, int col, Condition condition) {
		Map<Integer, Condition> conditionsForRow = this.conditions.get(row);
		if (conditionsForRow == null) {
			conditionsForRow = new HashMap<Integer, Condition>();
			conditions.put(row, conditionsForRow);
		}
		Condition oldCondition = conditionsForRow.get(col);
		if (oldCondition == null) {
			conditionsForRow.put(col, condition);
		} else {
			oldCondition.add(condition);
		}

	}

	@Override
	public Field addedIfFits(Card card) {
		FieldWithConditions field = (FieldWithConditions) super
				.addedIfFits(card);
		if (field == null) {
			return null;
		}

		Map<Integer, Condition> conditionsForRow = field.conditions.get(field
				.getCurrentCoordinates().getRow());
		if (conditionsForRow == null) {
			return field;
		}
		Condition conditionForCurrentCoordinate = conditionsForRow.get(field
				.getCurrentCoordinates().getCol());
		if (conditionForCurrentCoordinate == null) {
			return field;
		}
		if (conditionForCurrentCoordinate.matches(card)) {
			return field;
		} else {
			return null;
		}

	}

	@Override
	protected FieldWithConditions createField(int currentRow,
			int currentColumn, Card[][] cardsCopy) {
		return new FieldWithConditions(this.getRows(), this.getCols(),
				this.rowComplete, this.colComplete, cardsCopy, currentRow,
				currentColumn, this.conditions);
	}
}
