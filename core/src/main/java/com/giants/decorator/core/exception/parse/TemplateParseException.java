/**
 * 
 */
package com.giants.decorator.core.exception.parse;

import java.text.MessageFormat;

import com.giants.decorator.core.Element;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public abstract class TemplateParseException extends TemplateException {

	private static final long serialVersionUID = 1769045318096767985L;
	
	private Element element;
	private Object dataObj;

	/**
	 * @param key
	 * @param element
	 * @param dataObj
	 * @param message
	 * @param e
	 */
	public TemplateParseException(String key, Element element,
			Object dataObj, String message, Throwable e) {
		super(key, element.getContent(), MessageFormat.format(
				"Template parse error : {0} \n Parse object : {1}", message,
				dataObj), e);
		this.element = element;
		this.dataObj = dataObj;
	}

	/**
	 * @param key
	 * @param element
	 * @param dataObj
	 * @param message
	 */
	public TemplateParseException(String key, Element element,
			Object dataObj, String message) {
		super(key, element.getContent(), MessageFormat.format(
				"Template parse error : {0} \n Parse object : {1}", message,
				dataObj));
		this.element = element;
		this.dataObj = dataObj;
	}

	/**
	 * @return the dataObj
	 */
	public Object getDataObj() {
		return dataObj;
	}

	/**
	 * @return the element
	 */
	public Element getElement() {
		return element;
	}


}
