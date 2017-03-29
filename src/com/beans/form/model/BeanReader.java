package com.beans.form.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class BeanReader {
	
	private BeanReader() {
		
	}
	
	public static LoadedBeanModel readBean(Object o) throws IllegalArgumentException {
		if (!isBean(o)) {
			throw new IllegalArgumentException("The class " + o.getClass().getName() + " is not a Java Bean");
		}
		return null;
	}
	
	private static boolean isBean(Object o) {
		Class<?> clazz = o.getClass();
		for (Field field : clazz.getDeclaredFields()) {
			String fieldName = field.getName();
			if (!hasGetter(clazz, fieldName) || !hasSetter(clazz, fieldName) || !hasDefaultConstructor(clazz)) {
				return false;
			}
		}
		return true;
	}
	
	private static boolean hasDefaultConstructor(Class<?> clazz) {
		for (Constructor<?> constructor : clazz.getConstructors()) {
	        if (constructor.getParameterCount() == 0) { 
	            return true;
	        }
	    }
	    return false;
	}
	
	private static boolean hasGetter(Class<?> clazz, String fieldName) {
		String formattedFieldName = capitalizeFirstLetter(fieldName);
		return containsMethod(clazz, "get" + formattedFieldName) || containsMethod(clazz, "is" + formattedFieldName);
	}
	
	private static boolean hasSetter(Class<?> clazz, String fieldName) {
		String formattedFieldName = capitalizeFirstLetter(fieldName);
		return containsMethod(clazz, "set" + formattedFieldName);
	}
	
	private static boolean containsMethod(Class<?> clazz, String methodName) {
		for (Method method : clazz.getMethods()) {
			if (method.getName().equals(methodName)) {
				return true;
			}
		}
		return false;
	}
	
	private static String capitalizeFirstLetter(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}

}
