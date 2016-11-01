/**
 * 
 */
package com.giants.decorator.core;

import java.io.Serializable;
import java.util.Map;

import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public interface Element extends Serializable {
	
	TemplateEngine getTemplateEngine();
	
	String getKey();

	String getContent();

	String getTemplateVariable();
	
	Object parseForObject(Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException;
	
	Object parseForObject(Object dataObj) throws TemplateException;

	String parse(Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException;
	
	String parse(Object dataObj) throws TemplateException;
	
}
