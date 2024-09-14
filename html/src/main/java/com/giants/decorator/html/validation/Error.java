/**
 * 
 */
package com.giants.decorator.html.validation;

/**
 * @author vencent.lu
 *
 */
public class Error {
	
	private String key;
	private String message;
	private boolean fieldError = true;
	
	/**
	 * @param key key
	 * @param message message
	 */
	public Error(String key, String message) {
		super();
		this.key = key;
		this.message = message;
	}

	/**
	 * @param key key
	 * @param message message
	 * @param fieldError field error
	 */
	public Error(String key, String message, boolean fieldError) {
		super();
		this.key = key;
		this.message = message;
		this.fieldError = fieldError;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the fieldError
	 */
	public final boolean isFieldError() {
		return fieldError;
	}

}
