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
public class ContainsFunctionHandler implements FunctionHandler {

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.function.FunctionHandler#execute(com.giants.decorator.core.TemplateEngine, java.util.Map, java.lang.Object, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		Collection<Object> collection = (Collection<Object>) parameters.get(0)
				.parse(globalVarMap, dataObj);
		Object object = parameters.get(1).parse(globalVarMap, dataObj);
		return collection.contains(object);
	}

}
