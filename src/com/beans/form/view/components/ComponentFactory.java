package com.beans.form.view.components;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.beans.form.model.BeanUtils;
import com.beans.form.model.LoadedBeanModel;

public class ComponentFactory {

	private ComponentFactory() {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static JComponent getComponentFor(LoadedBeanModel bean, String fieldName) {
		Class<?> clazz = bean.getFieldType(fieldName);
		if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
			return buildCheckBox(fieldName, bean);
		} else if (isNumber(clazz)) {
			return buildTextField((Class<? extends Number>) clazz, fieldName, bean);
		} else if (isEnum(clazz)) {
			return buildComboBox((Class<? extends Enum>) clazz, bean);
		} else if (clazz.equals(String.class)) {
			return buildTextField(fieldName, bean);
		}
		return null;
	}

	private static JCheckBox buildCheckBox(String fieldName, LoadedBeanModel bean) {
		JCheckBox cb = new JCheckBox(BeanUtils.formatFieldName(fieldName));
		return cb;
	}

	private static JTextField buildTextField(Class<? extends Number> fieldClass, String fieldName, LoadedBeanModel bean) {
		return new NumberTextField(fieldClass);
	}

	private static JTextField buildTextField(String fieldName, LoadedBeanModel bean) {
		JTextField tf = new JTextField();
		tf.setColumns(9);
		return tf;
	}

	private static <E extends Enum<E>> JComboBox<E> buildComboBox(Class<E> type, LoadedBeanModel bean) {
		return new JComboBox<>(type.getEnumConstants());
	}

	private static boolean isEnum(Class<?> clazz) {
		return clazz.isEnum();
	}

	private static boolean isNumber(Class<?> clazz) {
		return isDouble(clazz) || isInteger(clazz) || isLong(clazz) || isFloat(clazz) || isShort(clazz) || isByte(clazz);
	}

	private static boolean isDouble(Class<?> clazz) {
		return clazz.equals(Double.class) || clazz.equals(double.class);
	}

	private static boolean isInteger(Class<?> clazz) {
		return clazz.equals(Integer.class) || clazz.equals(int.class);
	}

	private static boolean isLong(Class<?> clazz) {
		return clazz.equals(Long.class) || clazz.equals(long.class);
	}

	private static boolean isFloat(Class<?> clazz) {
		return clazz.equals(Float.class) || clazz.equals(float.class);
	}

	private static boolean isShort(Class<?> clazz) {
		return clazz.equals(Short.class) || clazz.equals(short.class);
	}

	private static boolean isByte(Class<?> clazz) {
		return clazz.equals(Byte.class) || clazz.equals(byte.class);
	}

}
