/**
 * 
 */
package com.giants.decorator.html.function;

import java.util.List;
import java.util.Map;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.function.FunctionHandler;
import com.giants.decorator.html.HtmlTag;
import com.giants.decorator.html.HtmlTemplateEngine;
import com.giants.decorator.html.template.HtmlTemplate;

/**
 * @author vencent.lu
 *
 */
public class GetWidgetCodeFunctionHandler implements FunctionHandler {

	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		String widgetName = (String) parameters.get(0).parse(globalVarMap,
				dataObj);
		String type = (String) parameters.get(1).parse(globalVarMap, dataObj);
		HtmlTag headTag = ((HtmlTemplate) ((HtmlTemplateEngine) templateEngine)
				.loadWidgetTemplate(widgetName)).getHeadTag();
		StringBuffer resultBf = new StringBuffer();
		for (HtmlTag htmlTag : headTag.getChildrenTag(type)) {
			if (htmlTag.getContent() != null) {
				resultBf.append(htmlTag.getContent().parse(globalVarMap, dataObj));
			}
		}
		return resultBf;
	}

}
