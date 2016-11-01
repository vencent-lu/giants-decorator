/**
 * 
 */
package com.giants.decorator.core.template;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giants.decorator.core.Template;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.block.AbstractBlock;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public abstract class AbstractTemplate extends AbstractBlock implements Template {

	private static final long serialVersionUID = -5445601752029299469L;
	
	protected final Logger   logger = LoggerFactory.getLogger(this.getClass());

	protected long compileTime;

	/**
	 * 
	 * @param templateEngine
	 * @param key
	 * @param template
	 * @param compileTime
	 * @throws TemplateAnalysisException
	 */
	public AbstractTemplate(TemplateEngine templateEngine, String key,
			String template, long compileTime) throws TemplateAnalysisException {
		// super(templateEngine, key, null, htmlTemplate.replaceAll(">\\s*<",
		// "><"));
		super(templateEngine, key, null, template);
		this.compileTime = compileTime;
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
		return this.parseSingleton(globalVarMap, dataObj, blockObj);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Template#execute(java.util.Map, java.lang.Object)
	 */
	@Override
	public String execute(Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException {
		this.autoCompile();
		return super.parse(globalVarMap, dataObj);
	}
	
	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Template#execute(java.lang.Object)
	 */
	@Override
	public String execute(Object dataObj) throws TemplateException {
		return this.execute(null, dataObj);
	}

	protected void autoCompile() throws TemplateException {
		if (this.getModifyTime() != this.compileTime) {
			this.compile();
		}
	}

}
