/**
 * 
 */
package com.giants.decorator.html.function;

import java.util.List;
import java.util.Map;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.function.FunctionHandler;

/**
 * @author vencent.lu
 *
 * Create Date:2014年6月4日
 */
public class SrcFunctionHandler extends UrlFunctionHandler implements FunctionHandler {

	/* (non-Javadoc)
	 * @see com.giants.decorator.html.UrlFunctionHandler#getFilePath(java.util.Map, java.lang.Object, java.util.List)
	 */
	@Override
	protected String getFilePath(Map<String, Object> globalVarMap,
			Object dataObj, List<Parameter> parameters)
			throws TemplateException {
		return (String)globalVarMap.get("currentTemplate");
	}

	
}
