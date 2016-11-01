/**
 * 
 */
package com.giants.decorator.html.function;

import java.util.List;
import java.util.Map;

import com.giants.common.tools.Page;
import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.parse.ParameterNotAllowNullException;
import com.giants.decorator.core.function.FunctionHandler;
import com.giants.decorator.html.HtmlTemplateEngine;
import com.giants.decorator.html.template.HtmlTemplate;

/**
 * @author vencent.lu
 *
 */
public class PagingFunctionHandler implements FunctionHandler {

	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		String pagingWidget = (String) parameters.get(0).parse(globalVarMap,
				dataObj);
		String pageActionUrl = (String) parameters.get(1).parse(globalVarMap,
				dataObj);
		Integer pageNumCount = (Integer) parameters.get(2).parse(
				globalVarMap, dataObj);
		if (pageNumCount == null) {
			pageNumCount = -1;
		}
		Page<?> page = (Page<?>) parameters.get(3).parse(globalVarMap, dataObj);
		if (page == null) {
			try {
				page = (Page<?>) dataObj;
			} catch (ClassCastException e) {
				throw new ParameterNotAllowNullException(parameters.get(2),
						dataObj);
			}
		}
		page.setPageNumCount(pageNumCount);
		page.setActionUrl(pageActionUrl);
		return ((HtmlTemplate) ((HtmlTemplateEngine) templateEngine)
				.loadWidgetTemplate(pagingWidget)).getBody().parse(
				globalVarMap, page);
	}

}
