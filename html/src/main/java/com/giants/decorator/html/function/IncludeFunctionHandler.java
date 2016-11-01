/**
 * 
 */
package com.giants.decorator.html.function;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.Template;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.function.FunctionHandler;
import com.giants.decorator.html.exception.NotHtmlTemplateException;
import com.giants.decorator.html.template.HtmlTemplate;

/**
 * @author vencent.lu
 *
 */
public class IncludeFunctionHandler implements FunctionHandler {

	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		String file = (String)parameters.get(0).parse(globalVarMap, dataObj);
		String component = (String)parameters.get(1).parse(globalVarMap, dataObj);
		Template template = templateEngine.loadTemplate(file);
		if (StringUtils.isNotEmpty(component)) {
			if (template instanceof HtmlTemplate) {
				HtmlTemplate htmlTemplate = (HtmlTemplate)template;
				if (component.equals("head")) {
					return htmlTemplate.getHead().parse(globalVarMap, dataObj);
				} else if (component.equals("body")) {
					return htmlTemplate.getBody().parse(globalVarMap, dataObj);
				} else {
					return null;
				}
			} else {
				throw new NotHtmlTemplateException(template);
			}
		} else {
			return template.execute(globalVarMap, dataObj);
		}
	}

}
