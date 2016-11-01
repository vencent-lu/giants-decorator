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
import com.giants.decorator.html.HtmlTag;
import com.giants.decorator.html.exception.NotHtmlTemplateException;
import com.giants.decorator.html.template.HtmlTemplate;

/**
 * @author vencent.lu
 *
 */
public class GetHeadTagsFunctionHandler implements FunctionHandler {

	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		String file = (String) parameters.get(0).parse(globalVarMap, dataObj);
		String tag = (String) parameters.get(1).parse(globalVarMap, dataObj);
		Template template = templateEngine.loadTemplate(file);
		if (!(template instanceof HtmlTemplate)) {
			throw new NotHtmlTemplateException(template);
		}
		HtmlTag headTag = ((HtmlTemplate) template).getHeadTag();
		if (StringUtils.isNotEmpty(tag)) {
			if (headTag.getChildrenTag(tag) != null
					&& headTag.getChildrenTag(tag).size() == 1) {
				return headTag.getChildrenTag(tag).get(0);
			} else {
				return headTag.getChildrenTag(tag);
			}
		} else {
			return headTag;
		}
	}

}
