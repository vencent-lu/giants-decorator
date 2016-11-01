/**
 * 
 */
package com.giants.decorator.core.exception.analysis;

import java.text.MessageFormat;

/**
 * @author vencent.lu
 *
 */
public class BlockUndefinedException extends TemplateAnalysisException {

	private static final long serialVersionUID = -3354139542443426557L;
	
	private String blockName;

	/**
	 * @param blockName
	 * @param key
	 * @param content
	 */
	public BlockUndefinedException(String blockName, String key,
			String content) {
		super(key, content, MessageFormat.format(
				"\"{0}\" block undefined!", blockName));
		this.blockName = blockName;
	}

	/**
	 * @return the blockName
	 */
	public String getBlockName() {
		return blockName;
	}

}
