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
public class ValueOfFunctionHandler implements FunctionHandler {

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.function.FunctionHandler#execute(com.giants.decorator.core.TemplateEngine, java.util.Map, java.lang.Object, java.util.List)
	 */
	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		String type = (String) parameters.get(0).parse(globalVarMap,dataObj);
		String value = (String) parameters.get(1).parse(globalVarMap,dataObj);
		switch (type) {
		case "int":
			return Integer.valueOf(value).intValue();
		case "long":
			return Long.valueOf(value).longValue();
		case "float":
			return Float.valueOf(value).floatValue();
		case "double":
			return Double.valueOf(value).doubleValue();
		case "short":
			return Short.valueOf(value).shortValue();
		case "byte":
			return Byte.valueOf(value).byteValue();
		case "string":
			return value;
		default:
			break;
		}
		return null;
	}

}
