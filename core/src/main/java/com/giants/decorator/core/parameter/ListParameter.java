/**
 * 
 */
package com.giants.decorator.core.parameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.giants.decorator.core.Element;
import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.ParameterFormatException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public class ListParameter extends AbstractParameter {

	private static final long serialVersionUID = -7760238870425648084L;
	
	private List<Parameter> arrayElements;

	/**
	 * @param parameterConf
	 * @param element
	 * @param listStr
	 * @throws TemplateAnalysisException
	 */
	public ListParameter(
			com.giants.decorator.config.element.Parameter parameterConf,
			Element element, String listStr) throws TemplateAnalysisException {
		super(parameterConf, element);
		this.arrayElements = new ArrayList<Parameter>();
		int openParenthesisCount = 0;
		int closeParenthesisCount = 0;
		int openBracketCount = 0;
		int closeBracketCount = 0;
		int openArrayCount = 0;
		int closeArrayCount = 0;
		StringBuffer param = new StringBuffer();
		char c;
		for (int i=1; i < listStr.length(); i++) {
			c = listStr.charAt(i);
			if ((c == ',' && openParenthesisCount == closeParenthesisCount
					&& openBracketCount == closeBracketCount && openArrayCount == closeArrayCount)
					|| i == listStr.length() - 1) {
				this.arrayElements.add(element.getTemplateEngine().createParameter(null,
						param.toString(), element));
				param.delete(0, param.length());
			} else {
				param.append(c);
			}
			if (i != listStr.length() - 1) {
				if (c == '(') {
					openParenthesisCount++;
				}
				if (c == ')') {
					closeParenthesisCount++;
				}
				if (c == '{') {
					openBracketCount++;
				}
				if (c == '}') {
					closeBracketCount++;
				}
				if (c == '[') {
					openArrayCount++;
				}
				if (c == ']') {
					closeArrayCount++;
				}
			}
		}
		if (openParenthesisCount != closeParenthesisCount
				|| openBracketCount != closeBracketCount
				|| openArrayCount != closeArrayCount) {
			throw new ParameterFormatException(parameterConf, listStr);
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.parameter.AbstractParameter#parseParameter(java.util.Map, java.lang.Object)
	 */
	@Override
	protected Object parseParameter(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		List<Object> list = new ArrayList<Object>();
		for (Parameter parameter : this.arrayElements) {
			list.add(parameter.parse(globalVarMap, dataObj));
		}
		return list;
	}

}
