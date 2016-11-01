/**
 * 
 */
package com.giants.decorator.core.function;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public class JsonObjectFunctionHandler implements FunctionHandler {
	
	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		Object object = parameters.get(0).parse(globalVarMap, dataObj);
		return JSONObject.toJSONString(object);
	}

}
