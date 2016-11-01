/**
 * 
 */
package com.giants.decorator.core.exception;

import java.text.MessageFormat;

/**
 * @author vencent.lu
 *
 */
public class WidgetTemplateUndefinedException extends TemplateException {

	private static final long serialVersionUID = 7669116382764939152L;
	
	private String widgetName;

	/**
	 * @param key
	 * @param content
	 */
	public WidgetTemplateUndefinedException(String key) {
		super(key, key, MessageFormat.format(
				"\"{0}\" widget template undefined!", key));
		this.widgetName = key;
	}

	/**
	 * @return the widgetName
	 */
	protected String getWidgetName() {
		return widgetName;
	}

}
