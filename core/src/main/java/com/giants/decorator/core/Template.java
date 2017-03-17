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
public interface Template extends Serializable {
    
    Block createTemplateBlock() throws TemplateException;
    
    String getKey();

    String getContent();
	
	//long getModifyTime();

	void compile() throws TemplateException;

	String execute(Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException;
	
	String execute(Object dataObj) throws TemplateException;

}
