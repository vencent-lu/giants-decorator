/**
 * 
 */
package com.giants.decorator.core;

import java.util.Map;

import com.giants.decorator.core.exception.TemplateException;


/**
 * @author vencent.lu
 *
 */
public interface Function extends Element {
	
	String getFunctionName();

	Object call(Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException;
	
}
