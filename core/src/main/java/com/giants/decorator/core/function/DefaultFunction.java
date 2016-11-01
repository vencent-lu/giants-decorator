/**
 * 
 */
package com.giants.decorator.core.function;

import java.util.List;
import java.util.Map;

import com.giants.decorator.config.element.Function;
import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public class DefaultFunction extends AbstractFunction {

	private static final long serialVersionUID = 6232393500652427639L;
	
	private FunctionHandler functionHandler;

	/**
	 * 
	 * @param templateEngine
	 * @param key
	 * @param functionConf
	 * @param functionHandler
	 * @throws TemplateAnalysisException
	 */
	public DefaultFunction(TemplateEngine templateEngine, String key, Function functionConf, FunctionHandler functionHandler)
			throws TemplateAnalysisException {
		super(templateEngine, key, functionConf);		
		this.functionHandler = functionHandler;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.function.AbstractFunction#parse(java.util.Map, java.lang.Object, java.util.List)
	 */
	@Override
	protected Object parse(Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		return this.functionHandler.execute(this.templateEngine, globalVarMap,
				dataObj, parameters);
	}

}
