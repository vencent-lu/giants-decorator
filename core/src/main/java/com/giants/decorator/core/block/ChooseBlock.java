/**
 * 
 */
package com.giants.decorator.core.block;

import java.util.Map;

import com.giants.decorator.core.Block;
import com.giants.decorator.core.BlockStructure;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public class ChooseBlock extends AbstractBlock {

	private static final long serialVersionUID = 2625511314110628560L;

	/**
	 * 
	 * @param templateEngine template engine
	 * @param key block key
	 * @param blockConf block config
	 * @param blockTemplate block template
	 * @throws TemplateAnalysisException template analysis exception
	 */
	public ChooseBlock(TemplateEngine templateEngine, String key,
			com.giants.decorator.config.element.Block blockConf,
			String blockTemplate) throws TemplateAnalysisException {
		super(templateEngine, key, blockConf, blockTemplate);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.block.AbstractBlock#parseOperateObject(java.util.Map, java.lang.Object)
	 */
	@Override
	protected Object parseOperateObject(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		return dataObj;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.block.AbstractBlock#parseBlock(java.util.Map, java.lang.Object, java.lang.Object)
	 */
	@Override
	protected String parseBlock(Map<String, Object> globalVarMap,
			Object dataObj, Object blockObj) throws TemplateException {
		String parseSingleton = this.parseSingleton(globalVarMap, dataObj,
				blockObj);
		if (parseSingleton == null) {
			return null;
		}
		BlockStructure whenBlocks = this.getBlockStructure("whens");
		BlockStructure otherwiseBlock = this.getBlockStructure("otherwise");
		boolean executiveBranch = false;
		for (Block block : whenBlocks.getBlocks()) {			
			if (!executiveBranch) {
				String blockParse = (String) block.parseForObject(globalVarMap,
						blockObj);
				if (blockParse != null) {
					parseSingleton = parseSingleton.replace(
							block.getTemplateVariable(), blockParse);
					executiveBranch = true;
				} else {
					parseSingleton = parseSingleton.replace(
							block.getTemplateVariable(), "");
				}
			} else {
				parseSingleton = parseSingleton.replace(
						block.getTemplateVariable(), "");
			}
		}
		
		if (otherwiseBlock != null && otherwiseBlock.getBlocks() != null) {
			Block otherwise = otherwiseBlock.getBlocks().get(0);					
			if (executiveBranch) {
				parseSingleton = parseSingleton.replace(
						otherwise.getTemplateVariable(), "");
			} else {
				String blockParse = (String)otherwise.parseForObject(globalVarMap, blockObj);
				parseSingleton = parseSingleton.replace(
						otherwise.getTemplateVariable(), blockParse);				
			}
		} 
		return parseSingleton.trim();
	}

}
