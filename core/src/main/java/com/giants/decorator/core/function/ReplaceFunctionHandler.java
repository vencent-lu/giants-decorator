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
public class ReplaceFunctionHandler implements FunctionHandler {

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		Object valueObj = parameters.get(0).parse(globalVarMap, dataObj);
		String value = null;
		if (valueObj instanceof String) {
			value = (String)valueObj;
		} else {
			value = valueObj.toString();
		}
				
		Map<String, String> replaceMap = (Map<String, String>) parameters
				.get(1).parse(globalVarMap, dataObj);
		if (replaceMap.get(value) != null) {
			return replaceMap.get(value);
		} else if (replaceMap.get("other") != null) {
			return replaceMap.get("other");
		} else {
			return value;
		}
	}

}
