package com.beans.form.view.components;

import java.awt.Color;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;

@SuppressWarnings("serial")
public class NumberTextField extends JFormattedTextField {

	private Class<? extends Number> numberClass;

	public NumberTextField(Class<? extends Number> numberClass) {
		this.numberClass = numberClass;
		setColumns(9);
		setInputVerifier(new NumberInputVerifier());
	}

	private class NumberInputVerifier extends InputVerifier {

		@Override
		public boolean verify(JComponent input) {
			if (input instanceof JFormattedTextField) {
				JFormattedTextField ftf = (JFormattedTextField) input;
				if (isParsePossible(ftf.getText())) {
					ftf.setBackground(Color.WHITE);
					return true;
				} else {
					ftf.setBackground(Color.RED);
					return false;
				}
			}
			return false;
		}

		@Override
		public boolean shouldYieldFocus(JComponent input) {
			return verify(input);
		}

		private boolean isParsePossible(String input) {
			boolean isParsePossible = true;
			try {
				if (numberClass.equals(Integer.class) || numberClass.equals(int.class)) {
					Integer.parseInt(input);
				} else if (numberClass.equals(Double.class) || numberClass.equals(double.class)) {
					Double.parseDouble(input);
				} else if (numberClass.equals(Long.class) || numberClass.equals(long.class)) {
					Long.parseLong(input);
				} else if (numberClass.equals(Byte.class) || numberClass.equals(byte.class)) {
					Byte.parseByte(input);
				} else if (numberClass.equals(Float.class) || numberClass.equals(float.class)) {
					Float.parseFloat(input);
				} else if (numberClass.equals(Short.class) || numberClass.equals(short.class)) {
					Short.parseShort(input);
				}
			} catch (NumberFormatException e) {
				isParsePossible = false;
			}
			return isParsePossible;
		}

	}

}
