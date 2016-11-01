/**
 * 
 */
package com.giants.decorator.html.template;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.giants.decorator.core.Element;
import com.giants.decorator.core.Template;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.html.HtmlTag;

/**
 * @author vencent.lu
 *
 */
public interface HtmlTemplate extends Template {
	
	HtmlTag getHeadTag() throws TemplateException;
	
	Element getHead() throws TemplateException;
	
	Element getBody() throws TemplateException;
	
	String execute(HttpServletRequest request, Object dataObj)
			throws TemplateException;
	
	String execute(HttpServletRequest request,
			Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException;

}
