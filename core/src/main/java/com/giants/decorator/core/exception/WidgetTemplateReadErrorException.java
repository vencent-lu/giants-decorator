/**
 * 
 */
package com.giants.decorator.core.exception;

import java.text.MessageFormat;

/**
 * @author vencent.lu
 *
 */
public class WidgetTemplateReadErrorException extends TemplateException {

	private static final long serialVersionUID = 7669116382764939152L;
	
	private String widgetName;
	private String widgetfile;

	/**
	 * @param name
	 * @param file
	 */
	public WidgetTemplateReadErrorException(String name, String file) {
		super(name, file, MessageFormat.format(
				"\"{0}\" widget template read \"{1}\" error!", name, file));
		this.widgetName = name;
		this.widgetfile = file;
	}

	/**
	 * @return the widgetName
	 */
	protected String getWidgetName() {
		return widgetName;
	}

	/**
	 * @return the widgetfile
	 */
	protected String getWidgetfile() {
		return widgetfile;
	}

}
