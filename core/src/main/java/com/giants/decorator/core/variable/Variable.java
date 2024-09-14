/**
 * 
 */
package com.giants.decorator.core.variable;

import java.util.Map;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public class Variable extends AbstractElement {

	private static final long serialVersionUID = 5880722648949415559L;
	
	private Parameter parameter;
	
	/**
	 * 
	 * @param templateEngine template engine
	 * @param key variable key
	 * @throws TemplateAnalysisException template analysis exception
	 */
	public Variable(TemplateEngine templateEngine, String key)
			throws TemplateAnalysisException {
		super(templateEngine, key, key);
		this.parameter = templateEngine.createParameter(null, key, this);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Element#parseForObject(java.util.Map, java.lang.Object)
	 */
	@Override
	public Object parseForObject(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		return this.parameter.parse(globalVarMap, dataObj);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Element#parseForObject(java.lang.Object)
	 */
	@Override
	public Object parseForObject(Object dataObj) throws TemplateException {
		return this.parameter.parse(null, dataObj);
	}
	
}
