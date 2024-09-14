/**
 * 
 */
package com.giants.decorator.core.exception.analysis;

import java.text.MessageFormat;

import com.giants.decorator.core.Block;

/**
 * @author vencent.lu
 *
 */
public class BlockStructureRepeatException extends TemplateAnalysisException {

	private static final long serialVersionUID = 177916710622699384L;
	
	private Block block;

	/**
	 * @param block block
	 */
	public BlockStructureRepeatException(Block block) {
		super(block.getKey(), block.getContent(), MessageFormat.format(
				"\"{0}\" block structure  repeat!", block.getKey()));
		this.block = block;
	}

	/**
	 * @return the block
	 */
	public Block getBlock() {
		return block;
	}

}
