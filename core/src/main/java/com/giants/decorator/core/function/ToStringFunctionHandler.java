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
 * Create Date:2014年5月28日
 */
public class ToStringFunctionHandler implements FunctionHandler {

	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		Object object = parameters.get(0).parse(globalVarMap, dataObj);
		if (object == null) {
			return null;
		}
		return object.toString();
	}

}
