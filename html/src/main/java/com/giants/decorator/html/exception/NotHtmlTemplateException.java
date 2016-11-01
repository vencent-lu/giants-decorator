/**
 * 
 */
package com.giants.decorator.html.exception;

import java.text.MessageFormat;

import com.giants.decorator.core.Template;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public class NotHtmlTemplateException extends TemplateException {

	private static final long serialVersionUID = -7269635241384866432L;
	
	private Template template;

	/**
	 * @param key
	 * @param content
	 * @param message
	 */
	public NotHtmlTemplateException(Template template) {
		super(template.getKey(), template.getContent(), MessageFormat.format(
				"\"{0}\" Not html template !", template.getKey()));
		this.template = template;
	}

	/**
	 * @return the template
	 */
	public Template getTemplate() {
		return template;
	}	

}
