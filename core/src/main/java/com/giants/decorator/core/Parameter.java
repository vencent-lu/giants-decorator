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
public interface Parameter extends Serializable {

	String getName();

	Class<?> getType();

	Element getElement();

	Object parse(Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException;

}
