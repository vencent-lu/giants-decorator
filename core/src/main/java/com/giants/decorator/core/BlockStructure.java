/**
 * 
 */
package com.giants.decorator.core;

import java.util.ArrayList;
import java.util.List;

import com.giants.decorator.core.exception.analysis.BlockStructureRepeatException;

/**
 * @author vencent.lu
 *
 */
public class BlockStructure {
	
	private String name;
	
	private boolean multiton = false;
	
	private List<Block> blocks;

	/**
	 * @param name
	 * @param multiton
	 */
	public BlockStructure(String name, boolean multiton) {
		super();
		this.name = name;
		this.multiton = multiton;
	}
	
	public void addBlock(Block block) throws BlockStructureRepeatException {
		if (this.blocks == null) {
			this.blocks = new ArrayList<Block>();
		}
		if (!this.multiton && this.blocks.size() >= 1) {
			throw new BlockStructureRepeatException(block);
		}
		this.blocks.add(block);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the multiton
	 */
	public boolean isMultiton() {
		return multiton;
	}

	/**
	 * @return the blocks
	 */
	public List<Block> getBlocks() {
		return blocks;
	}

}
