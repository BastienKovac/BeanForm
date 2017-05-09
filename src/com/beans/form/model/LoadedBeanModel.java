package com.beans.form.model;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LoadedBeanModel {

	private String originalClass;
	private Map<String, Class<?>> fields;
	private Object loadedBean;


	public LoadedBeanModel() {
		this.fields = new HashMap<>();
	}

	public Set<String> getDeclaredFields() {
		return fields.keySet();
	}

	public Class<?> getFieldType(String fieldName) {
		return fields.get(fieldName);
	}

	public Object getLoadedBean() {
		return loadedBean;
	}

	public String getOriginalClass() {
		return originalClass;
	}

	public Object getFieldValue(String fieldName) throws BeanFormException {
		if (!fields.containsKey(fieldName)) {
			throw new BeanFormException("The given field is not part of the loaded bean.");
		}
		try {
			if (fields.get(fieldName).equals(Boolean.class) || fields.get(fieldName).equals(boolean.class)) {
				return getMethod("is" + BeanUtils.capitalizeFirstLetter(fieldName)).invoke(loadedBean);
			} else {
				return getMethod("get" + BeanUtils.capitalizeFirstLetter(fieldName)).invoke(loadedBean);
			}
		} catch (IllegalAccessException e) {
			throw new BeanFormException("The loaded bean is not accessible.");
		} catch (IllegalArgumentException e) {
			throw new BeanFormException("The method for this field couldn't be found.");
		} catch (InvocationTargetException e) {
			throw new BeanFormException("An exception has been thrown by the bean.");
		}
	}

	public void setFieldValue(String fieldName, Object value) throws BeanFormException {
		if (!fields.containsKey(fieldName) || !fields.get(fieldName).equals(value.getClass())) {
			throw new BeanFormException("The given field is not part of the loaded bean.");
		}
		try {
			getMethod("set" + BeanUtils.capitalizeFirstLetter(fieldName)).invoke(loadedBean, value);
		} catch (IllegalAccessException e) {
			throw new BeanFormException("The loaded bean is not accessible.");
		} catch (IllegalArgumentException e) {
			throw new BeanFormException("The method for this field couldn't be found.");
		} catch (InvocationTargetException e) {
			throw new BeanFormException("An exception has been thrown by the bean.");
		}
	}

	public void loadBean(Class<?> clazz) throws BeanFormException {
		if (!BeanUtils.isBean(clazz)) {
			throw new BeanFormException("The class " + clazz.getName() + " is not a Java Bean");
		}
		fields.clear();
		this.originalClass = clazz.getSimpleName();
		try {
			loadedBean = getDefaultConstructor(clazz).newInstance();
			readFields(clazz);
		} catch (InstantiationException e) {
			throw new BeanFormException("The object could not be properly instanciated.");
		} catch (IllegalAccessException e) {
			throw new BeanFormException("The class of the given object is not accessible.");
		} catch (IllegalArgumentException e) {
			throw new BeanFormException("The given constructor is not the default one.");
		} catch (InvocationTargetException e) {
			throw new BeanFormException("An exception has been thrown while constructing the object.");
		}
	}

	private Constructor<?> getDefaultConstructor(Class<?> clazz) throws IllegalArgumentException {
		for (Constructor<?> constructor : clazz.getConstructors()) {
	        if (constructor.getParameterCount() == 0) {
	            return constructor;
	        }
	    }
		throw new IllegalArgumentException();
	}

	private void readFields(Class<?> clazz) {
		for (Field field : clazz.getDeclaredFields()) {
			fields.put(field.getName(), field.getType());
		}
	}

	private Method getMethod(String name) throws IllegalArgumentException {
		for (Method method : loadedBean.getClass().getMethods()) {
			if (method.getName().equals(name)) {
				return method;
			}
		}
		throw new IllegalArgumentException();
	}

}
