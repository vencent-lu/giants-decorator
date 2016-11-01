/**
 * 
 */
package com.giants.decorator.core.exception.analysis;

import java.text.MessageFormat;

/**
 * @author vencent.lu
 *
 */
public class FunctionUndefinedException extends TemplateAnalysisException {

	private static final long serialVersionUID = -953605056178254476L;
	
	private String functionName;

	/**
	 * @param functionName
	 * @param key
	 * @param content
	 */
	public FunctionUndefinedException(String functionName, String key,
			String content) {
		super(key, content, MessageFormat.format(
				"\"{0}\" function undefined!", functionName));
		this.functionName = functionName;
	}

	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

}
