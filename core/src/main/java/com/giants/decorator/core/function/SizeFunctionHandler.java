/**
 * 
 */
package com.giants.decorator.core.function;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public class SizeFunctionHandler implements FunctionHandler {

	@Override
	public Object execute(TemplateEngine templateEngine, Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		Object value = parameters.get(0).parse(	globalVarMap, dataObj);
		if (value instanceof String) {
			return ((String)value).length();
		} else if (value instanceof Collection) {
			return ((Collection<?>)value).size();
		} else if (value.getClass().isArray()) {
			return ((Object[])value).length;
		} else {
			return 1;
		}
	}

}
