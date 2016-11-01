/**
 * 
 */
package com.giants.decorator.core.exception.parse;

import java.text.MessageFormat;

import com.giants.decorator.core.Element;

/**
 * @author vencent.lu
 *
 */
public class AttributeUndefinedException extends TemplateParseException {

	private static final long serialVersionUID = 7283691459733545481L;
	
	private String attributeName;

	/**
	 * @param attributeName
	 * @param element
	 * @param dataObj
	 * @param e
	 */
	public AttributeUndefinedException(String attributeName, Element element,
			Object dataObj, Throwable e) {
		super(element.getKey(), element, dataObj, MessageFormat.format(
				"{0} undefined {1} attribute!", dataObj, attributeName), e);
	}

	/**
	 * @return the attributeName
	 */
	public String getAttributeName() {
		return attributeName;
	}

}
