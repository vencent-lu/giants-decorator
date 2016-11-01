/**
 * 
 */
package com.giants.decorator.core.block;

import java.util.Map;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public class TagBlockHandler implements BlockHandler {

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.block.BlockHandler#parseOperateObject(java.util.Map, java.lang.Object, java.util.Map)
	 */
	@Override
	public Object parseOperateObject(Map<String, Object> globalVarMap,
			Object dataObj, Map<String, Parameter> parameterMap)
			throws TemplateException {
		return dataObj;
	}

}
