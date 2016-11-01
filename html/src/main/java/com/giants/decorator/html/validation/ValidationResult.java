/**
 * 
 */
package com.giants.decorator.html.validation;

import java.util.LinkedHashMap;

/**
 * @author vencent.lu
 *
 */
public class ValidationResult extends LinkedHashMap<String, Error> {
	
	private static final long serialVersionUID = 811550864726383667L;
	
	public ValidationResult() {
		super();
	}

	public void addError(Error error) {
		this.put(error.getKey(), error);
	}
	
}
