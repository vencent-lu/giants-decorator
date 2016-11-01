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
public interface Template extends Block {
	
	long getModifyTime();

	void compile() throws TemplateException;

	String execute(Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException;
	
	String execute(Object dataObj) throws TemplateException;

}
