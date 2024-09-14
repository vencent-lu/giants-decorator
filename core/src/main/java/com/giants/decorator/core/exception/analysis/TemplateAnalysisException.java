/**
 * 
 */
package com.giants.decorator.core.exception.analysis;

import java.text.MessageFormat;

import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public abstract class TemplateAnalysisException extends TemplateException {

	private static final long serialVersionUID = 1073830544577845952L;

	/**
	 * @param key key
	 * @param content content
	 * @param message message
	 * @param e throwable
	 */
	public TemplateAnalysisException(String key, String content,
			String message, Throwable e) {
		super(key, content, MessageFormat.format(
				"Template analysis error : {0}", message), e);
	}

	/**
	 * @param key key
	 * @param content content
	 * @param message message
	 */
	public TemplateAnalysisException(String key, String content,
			String message) {
		super(key, content, MessageFormat.format(
				"Template analysis error : {0}", message));
	}
	
}
