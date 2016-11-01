/**
 * 
 */
package com.giants.decorator.core.exception.analysis;

import java.text.MessageFormat;

/**
 * @author vencent.lu
 *
 */
public class FunctionHandlerInitException extends TemplateAnalysisException {

	private static final long serialVersionUID = 3442234451312185670L;
	
	private String functionName;
	private Class<?> handlerClass;

	/**
	 * @param functionName
	 * @param handlerClass
	 * @param key
	 * @param content
	 * @param e
	 */
	public FunctionHandlerInitException(String functionName,
			Class<?> handlerClass, String key, String content, Throwable e) {
		super(key, content, MessageFormat.format(
				"{0} initialization \"{1}\" function error!",
				handlerClass, functionName), e);
		this.functionName = functionName;
		this.handlerClass = handlerClass;
	}

	/**
	 * @return the functionName
	 */
	public String getFunctionName() {
		return functionName;
	}

	/**
	 * @return the handlerClass
	 */
	public Class<?> getHandlerClass() {
		return handlerClass;
	}

}
