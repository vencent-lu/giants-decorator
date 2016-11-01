/**
 * 
 */
package com.giants.decorator.core.function;

import java.util.List;
import java.util.Map;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.parse.ParameterValueAndTypeMismatchException;

/**
 * @author vencent.lu
 *
 * Create Date:2014年3月19日
 */
public class GetArrayElementFunctionHandler implements FunctionHandler {

	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		Object value = parameters.get(0).parse(globalVarMap, dataObj);
		Integer index = (Integer) parameters.get(1).parse(globalVarMap, dataObj);
		if (value instanceof List) {
			return ((List<?>) value).get(index);
		} else if (value.getClass().isArray()) {
			return ((Object[]) value)[index];
		} else {
			throw new ParameterValueAndTypeMismatchException(parameters.get(0),
					value);
		}
	}

}
