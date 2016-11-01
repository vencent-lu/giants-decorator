/**
 * 
 */
package com.giants.decorator.core.exception.analysis;

import java.text.MessageFormat;

/**
 * @author vencent.lu
 *
 */
public class BlockHandlerInitException extends TemplateAnalysisException {

	private static final long serialVersionUID = -7678664427528473428L;
	
	private String blockName;
	private Class<?> handlerClass;

	/**
	 * @param key
	 * @param content
	 * @param message
	 * @param e
	 */
	public BlockHandlerInitException(String blockName, Class<?> handlerClass,
			String key, String content, Throwable e) {
		super(key, content, MessageFormat.format(
				"{0} initialization \"{1}\" block error!", handlerClass,
				blockName), e);
		this.blockName = blockName;
		this.handlerClass = handlerClass;
	}

	/**
	 * @return the blockName
	 */
	public String getBlockName() {
		return blockName;
	}

	/**
	 * @return the handlerClass
	 */
	public Class<?> getHandlerClass() {
		return handlerClass;
	}

}
