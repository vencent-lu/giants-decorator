/**
 * 
 */
package com.giants.decorator.core.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public class ListUnionFunctionHandler implements FunctionHandler {

	@SuppressWarnings("unchecked")
	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		List<Object> arrayList = (List<Object>) parameters.get(0).parse(
				globalVarMap, dataObj);
		List<Object> result = new ArrayList<Object>();
		for (Object object : arrayList) {
			if (object instanceof List) {
				result = ListUtils.union(result, (List<?>)object);
			} else if (object != null) {
				result.add(object);
			}
		}
		if (result.isEmpty()) {
			return null;
		} else {
			return result;
		}
	}

}
