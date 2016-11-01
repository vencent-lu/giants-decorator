/**
 * 
 */
package com.giants.decorator.core.function;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public class StrFormatFunctionHandler implements FunctionHandler {

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		String string = (String)parameters.get(0).parse(globalVarMap, dataObj);
		Map<String,Object> arguments = (Map<String,Object>)parameters.get(1).parse(globalVarMap, dataObj);
		Iterator<String> argKeys = arguments.keySet().iterator();
		while (argKeys.hasNext()) {
			String key = argKeys.next();
			Object value = arguments.get(key);
			string = string.replace(
					new StringBuffer("{").append(key).append("}"),
					value != null ? value.toString() : "");
		}
		return string;
	}

}
