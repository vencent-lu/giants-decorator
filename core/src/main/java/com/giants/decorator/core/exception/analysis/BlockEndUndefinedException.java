/**
 * 
 */
package com.giants.decorator.core.exception.analysis;

import java.text.MessageFormat;

/**
 * @author vencent.lu
 *
 */
public class BlockEndUndefinedException extends TemplateAnalysisException {

	private static final long serialVersionUID = -8312678262084567778L;
	
	private String blockName;

	/**
	 * @param blockName
	 * @param key
	 * @param content
	 */
	public BlockEndUndefinedException(String blockName, String key, String content) {
		super(key, content, MessageFormat.format(
				"\"{0}\" block the end tag undefined!", blockName));
		this.blockName = blockName;
	}

	/**
	 * @return the blockName
	 */
	public String getBlockName() {
		return blockName;
	}

}
