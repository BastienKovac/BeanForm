package com.beans.form.model;

public enum StudentCursus {

	MATHEMATICS("Mathematics"),
	COMPUTER_SCIENCE("Computer Science"),
	BIOLOGY("Biology"),
	MANAGEMENT("Management"),
	LAW("Law School");

	private String label;

	private StudentCursus(String label) {
		this.label = label;
	}

	@Override
	public String toString() {
		return label;
	}

}
