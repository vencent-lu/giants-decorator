/**
 * 
 */
package com.giants.decorator.core.exception;

import java.text.MessageFormat;

/**
 * @author vencent.lu
 *
 */
public abstract class TemplateException extends Exception {

	private static final long serialVersionUID = 6136275352224391165L;
	
	private String key;
	private String content;
	
	/**
	 * @param key
	 * @param content
	 * @param message
	 */
	public TemplateException(String key, String content, String message) {
		super(MessageFormat.format(
				"{0} \n Details as follows\n key: {1} \n content: \n{2}",
				message, key, content));
		this.key = key;
		this.content = content;
	}

	/**
	 * @param key
	 * @param content
	 * @param message
	 * @param e
	 */
	public TemplateException(String key,
			String content, String message, Throwable e) {
		super(MessageFormat.format(
				"{0} \n Details as follows:\n key: {1} \n content: {2}",
				message, key, content), e);
		this.key = key;
		this.content = content;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	
}
