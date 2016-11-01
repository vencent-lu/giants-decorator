/**
 * 
 */
package com.giants.decorator.core.block;

import java.util.Map;

import com.giants.decorator.config.element.Block;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public class DefaultBlock extends AbstractBlock {

	private static final long serialVersionUID = -7251360855788486112L;
	
	private BlockHandler blockHandler;

	/**
	 * 
	 * @param templateEngine
	 * @param key
	 * @param blockConf
	 * @param blockTemplate
	 * @param blockHandler
	 * @throws TemplateAnalysisException
	 */
	public DefaultBlock(TemplateEngine templateEngine, String key,
			Block blockConf, String blockTemplate, BlockHandler blockHandler)
			throws TemplateAnalysisException {
		super(templateEngine, key, blockConf, blockTemplate);
		this.blockHandler = blockHandler;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.block.AbstractBlock#parseOperateObject(java.util.Map, java.lang.Object)
	 */
	@Override
	protected Object parseOperateObject(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		return blockHandler.parseOperateObject(globalVarMap, dataObj,
				this.getParameterMap());
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
		return parseSingleton.trim();
	}

}
