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
public class BlockStructureNotSetException extends TemplateAnalysisException {

	private static final long serialVersionUID = 4930406382850221323L;
	
	private Block block;
	private String structureBlockName;

	/**
	 * @param block block
	 * @param structureBlockName structure block name
	 */
	public BlockStructureNotSetException(Block block, String structureBlockName) {
		super(block.getKey(), block.getContent(), MessageFormat.format(
				"\"{0}\" block undefined \"{1}\" structure block not set !",
				block.getKey(), structureBlockName));
		this.block = block;
		this.structureBlockName = structureBlockName;
	}

	/**
	 * @return the block
	 */
	public Block getBlock() {
		return block;
	}

	/**
	 * @return the structureBlockName
	 */
	public String getStructureBlockName() {
		return structureBlockName;
	}

}
