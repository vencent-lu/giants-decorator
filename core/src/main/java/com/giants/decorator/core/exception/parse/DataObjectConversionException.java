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
public class DataObjectConversionException extends TemplateParseException {

	private static final long serialVersionUID = 5049526419631382992L;

	/**
	 * @param key key
	 * @param element element
	 * @param dataObj dataObj
	 * @param e throwable
	 */
	public DataObjectConversionException(String key, Element element,
			Object dataObj, Throwable e) {
		super(key, element, dataObj, MessageFormat.format(
				"{0} conversion error!", dataObj), e);
		// TODO Auto-generated constructor stub
	}

}
