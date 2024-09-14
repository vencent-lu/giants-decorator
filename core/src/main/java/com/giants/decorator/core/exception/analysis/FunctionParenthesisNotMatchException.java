/**
 * 
 */
package com.giants.decorator.core.exception.analysis;

import java.text.MessageFormat;

/**
 * @author vencent.lu
 *
 */
public class FunctionParenthesisNotMatchException extends
		TemplateAnalysisException {

	private static final long serialVersionUID = -2784266661789919235L;
	
	private String functionBody;

	/**
	 * @param functionBody function body
	 * @param key key
	 * @param content content
	 */
	public FunctionParenthesisNotMatchException(String functionBody,
			String key, String content) {
		super(key, content, MessageFormat.format(
				"\"{0}\" Function parenthesis can't match", functionBody));
		this.functionBody = functionBody;
	}

	/**
	 * @return the functionBody
	 */
	public String getFunctionBody() {
		return functionBody;
	}

}
