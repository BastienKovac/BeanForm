package com.beans.form.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class BeanUtils {

	private BeanUtils() {

	}

	public static String formatFieldName(String fieldName) {
		String formattedFieldName = capitalizeFirstLetter(fieldName);
		return formattedFieldName.replaceAll("(\\p{Ll})(\\p{Lu})","$1 $2");
	}

	public static String capitalizeFirstLetter(String original) {
	    if (original == null || original.length() == 0) {
	        return original;
	    }
	    return original.substring(0, 1).toUpperCase() + original.substring(1);
	}

	public static Method getSetter(Class<?> clazz, String fieldName) throws NoSuchMethodException, SecurityException, NoSuchFieldException {
		Class<?> parameterTypes = clazz.getDeclaredField(fieldName).getType();
		return clazz.getMethod("set" + capitalizeFirstLetter(fieldName), parameterTypes);
	}

	public static Method getGetter(Class<?> clazz, String fieldName) throws NoSuchMethodException, SecurityException {
		return clazz.getMethod("get" + capitalizeFirstLetter(fieldName));
	}

	public static Set<Field> getBeanFields(Class<?> clazz) {
		Set<Field> fields = new HashSet<>();
		for (Field field : clazz.getDeclaredFields()) {
			String fieldName = field.getName();
			if (hasGetter(clazz, fieldName) && hasSetter(clazz, fieldName)) {
				fields.add(field);
			}
		}
		return fields;
	}

	public static boolean isBean(Class<?> clazz) {
		return hasDefaultConstructor(clazz);
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

}
