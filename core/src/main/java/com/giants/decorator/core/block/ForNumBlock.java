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
public class ForNumBlock extends AbstractBlock {

	private static final long serialVersionUID = 6013669836103222276L;

	/**
	 * 
	 * @param templateEngine template engine
	 * @param key block key
	 * @param blockConf block config
	 * @param blockTemplate block template
	 * @throws TemplateAnalysisException template analysis exception
	 */
	public ForNumBlock(TemplateEngine templateEngine, String key,
			Block blockConf, String blockTemplate)
			throws TemplateAnalysisException {
		super(templateEngine, key, blockConf, blockTemplate);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.block.AbstractBlock#parseOperateObject(java.util.Map, java.lang.Object)
	 */
	@Override
	protected Object parseOperateObject(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		return this.getParameter("start").parse(globalVarMap, dataObj);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.block.AbstractBlock#parseBlock(java.util.Map, java.lang.Object, java.lang.Object)
	 */
	@Override
	protected String parseBlock(Map<String, Object> globalVarMap,
			Object dataObj, Object blockObj) throws TemplateException {
		Long num = (Long)blockObj;
		Long endNum = (Long)this.getParameter("end").parse(globalVarMap, dataObj);
		StringBuffer outBlockCode = new StringBuffer();
		while (num <= endNum) {
			outBlockCode.append(this.parseSingleton(globalVarMap, dataObj, num++));
		}
		return outBlockCode.toString().trim();
	}

}
