package com.beans.form.view.components;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import com.beans.form.model.BeanUtils;
import com.beans.form.model.LoadedBeanModel;

public class ComponentFactory {
	
	private ComponentFactory() {
		
	}
	
	public static JComponent getComponentFor(LoadedBeanModel bean, String fieldName) {
		Class<?> clazz = bean.getFieldType(fieldName);
		if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
			return buildCheckBox(bean, fieldName);
		}
		if (clazz.equals(String.class) || isNumber(clazz)) {
			return buildTextField(bean, fieldName);
		}
		return null;
	}
	
	private static JCheckBox buildCheckBox(LoadedBeanModel bean, String fieldName) {
		JCheckBox cb = new JCheckBox(BeanUtils.formatFieldName(fieldName));
		return cb;
	}
	
	private static JTextField buildTextField(LoadedBeanModel bean, String fieldName) {
		JTextField tf = new JTextField();
		tf.setColumns(9);
		return tf;
	}
	
	private static boolean isNumber(Class<?> clazz) {
		return isDouble(clazz) || isInteger(clazz) || isLong(clazz) || isFloat(clazz) || isShort(clazz);
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

}
