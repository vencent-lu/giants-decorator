/**
 * 
 */
package com.giants.decorator.html;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;

import com.giants.decorator.config.element.Layout;
import com.giants.decorator.core.Template;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.html.exception.NotHtmlTemplateException;
import com.giants.decorator.html.template.HtmlTemplate;

/**
 * @author vencent.lu
 *
 */
public class DecoratorLayout {
	
	private TemplateEngine templateEngine;
	
	public DecoratorLayout() {
		super();
	}

	public DecoratorLayout(TemplateEngine templateEngine) {
		super();
		this.templateEngine = templateEngine;
	}

	public String renderView(String viewName, Object dataObj)
			throws TemplateException {
		return this.renderView(viewName, (Map<String, Object>)null, dataObj);
	}
	
	public String renderView(String viewName,
			HttpServletRequest request,
			Object dataObj) throws TemplateException {
		return this.renderView(viewName, request, null, dataObj);
	}
	
	public String renderView(String viewName,
			Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException {
		String layout = this.findLayout(viewName);
		if (globalVarMap == null) {
			globalVarMap = new HashMap<String, Object>();
		}
		globalVarMap.put("currentTemplate", viewName);
		if (layout != null) {	
			return this.templateEngine.loadTemplate(layout).execute(
					globalVarMap, dataObj);
		} else {
			return this.templateEngine.loadTemplate(viewName).execute(
					globalVarMap, dataObj);
		}
	}
	
	public String renderView(String viewName,
			HttpServletRequest request, Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		String layout = this.findLayout(viewName);
		if (globalVarMap == null) {
			globalVarMap = new HashMap<String, Object>();
		}
		String name = null;
		if (layout != null) {
			globalVarMap.put("currentTemplate", viewName);
			name = layout;
		} else {
			name = viewName;
		}
		Template template = this.templateEngine.loadTemplate(name);
		if (template instanceof HtmlTemplate) {
			return ((HtmlTemplate)template).execute(request, globalVarMap, dataObj);
		} else {
			throw new NotHtmlTemplateException(template);
		}		
	}
	
	private String findLayout(String viewName) {
		if (CollectionUtils.isNotEmpty(this.templateEngine.getTemplateConfig()
				.getLayouts())) {
			for (Layout layout : this.templateEngine.getTemplateConfig()
					.getLayouts()) {
				if (layout.getRulesPattern().matches(viewName)
						&& (layout.getExcludes() == null || !layout
								.getExcludes().contains(viewName))) {
					return layout.getLayoutFile();
				}
			}
		}
		return null;
	}

	/**
	 * @param templateEngine the templateEngine to set
	 */
	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}
	
	

}
