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
public interface BlockHandler {
	
	Object parseOperateObject(Map<String, Object> globalVarMap, Object dataObj,
			Map<String, Parameter> parameterMap) throws TemplateException;

}
