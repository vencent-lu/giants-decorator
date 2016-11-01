/**
 * 
 */
package com.giants.decorator.core.exception.analysis;

import java.text.MessageFormat;

/**
 * @author vencent.lu
 *
 */
public class BlockParameterNotSetException extends TemplateAnalysisException {

	private static final long serialVersionUID = 165645363217944611L;
	
	private String blockName;
	private String paramName;

	/**
	 * @param blockName
	 * @param paramName
	 * @param key
	 * @param content
	 */
	public BlockParameterNotSetException(String blockName, String paramName,
			String key, String content) {
		super(key, content, MessageFormat.format(
				"\"{0}\" block undefined \"{1}\" parameter not set !", blockName,
				paramName));
		this.blockName = blockName;
		this.paramName = paramName;
	}

	/**
	 * @return the blockName
	 */
	public String getBlockName() {
		return blockName;
	}

	/**
	 * @return the paramName
	 */
	public String getParamName() {
		return paramName;
	}

}
