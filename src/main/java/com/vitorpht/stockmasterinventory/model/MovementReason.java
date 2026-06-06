package com.vitorpht.stockmasterinventory.model;

public enum MovementReason {

	INITIAL_STOCK("Initial Stock"),
	STOCK_ENTRY("Stock Entry"),
	STOCK_EXIT("Stock Exit");

	private final String label;

	MovementReason(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

}
