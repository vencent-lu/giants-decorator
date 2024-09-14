/**
 * 
 */
package com.giants.decorator.core.parameter;

import java.util.Map;

import com.giants.decorator.config.element.Parameter;
import com.giants.decorator.core.Element;
import com.giants.decorator.core.Function;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public class FunctionParameter extends AbstractParameter {

	private static final long serialVersionUID = -2043262535582940592L;
	
	private Function function;

	/**
	 * @param parameterConf parameter conf
	 * @param element element
	 * @param functionBody function body
	 * @throws TemplateAnalysisException template analysis exception
	 */
	public FunctionParameter(Parameter parameterConf, Element element,
			String functionBody) throws TemplateAnalysisException {
		super(parameterConf, element);
		this.function = (Function) element.getTemplateEngine().createElement(
				functionBody, null);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.parameter.AbstractParameter#parseParameter(java.util.Map, java.lang.Object)
	 */
	@Override
	protected Object parseParameter(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		return this.function.call(globalVarMap, dataObj);
	}

}
