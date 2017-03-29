package com.beans.form.model;

public class BeanFormException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public BeanFormException() {
		super();
	}

	public BeanFormException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BeanFormException(String message, Throwable cause) {
		super(message, cause);
	}

	public BeanFormException(String message) {
		super(message);
	}

	public BeanFormException(Throwable cause) {
		super(cause);
	}

}
