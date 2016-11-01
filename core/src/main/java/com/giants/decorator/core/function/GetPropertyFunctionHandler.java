/**
 * 
 */
package com.giants.decorator.core.function;

import java.util.List;
import java.util.Map;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public class GetPropertyFunctionHandler implements FunctionHandler {

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.function.FunctionHandler#execute(com.giants.decorator.core.TemplateEngine, java.util.Map, java.lang.Object, java.util.List)
	 */
	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		String key = (String)parameters.get(0).parse(globalVarMap, dataObj);
		return templateEngine.getProperty(key);
	}

}
