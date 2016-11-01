/**
 * 
 */
package com.giants.decorator.core.function;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public class StrLimitFunctionHandler implements FunctionHandler {

	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		String string = (String)parameters.get(0).parse(globalVarMap, dataObj);
		Integer threshold = (Integer)parameters.get(1).parse(globalVarMap, dataObj);
		String postfix = (String)parameters.get(2).parse(globalVarMap, dataObj);		
		if (string.length() > threshold) {
			StringBuffer result = new StringBuffer();
			result.append(string.substring(0,threshold));
			if (StringUtils.isNotEmpty(postfix)) {
				result.append(postfix);
			}
			return result.toString();
		} else {
			return string;
		}
	}

}
