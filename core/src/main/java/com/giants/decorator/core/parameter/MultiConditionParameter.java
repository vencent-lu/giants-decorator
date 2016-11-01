/**
 * 
 */
package com.giants.decorator.core.parameter;

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
public class MultiConditionParameter extends AbstractParameter {
	
	private static final long serialVersionUID = 5755919569404249111L;
	
	private Parameter leftParam;
	private String xor;
	private Parameter rightParam;

	public MultiConditionParameter(
			com.giants.decorator.config.element.Parameter parameterConf,
			Element element, String multiCondition)
			throws TemplateAnalysisException {
		super(parameterConf, element);
		if (parameterConf == null) {
			parameterConf = new com.giants.decorator.config.element.Parameter();
			parameterConf.setType(Boolean.class);
		}
		int openParenthesisCount = 0;
		int closeParenthesisCount = 0;
		char c;
		String leftConditionStr = null;
		String rightConditionStr = null;
		for (int i = multiCondition.length() -1; i >= 0; i--) {
			c = multiCondition.charAt(i);
			if (c == '(') {
				openParenthesisCount++;
			} else if (c == ')') {
				closeParenthesisCount++;
			}
			if (openParenthesisCount == closeParenthesisCount) {
				switch (c) {
				case '&':
					if (multiCondition.charAt(i-1) != '&') {
						throw new ParameterFormatException(parameterConf, multiCondition);
					}
					this.xor = "&&";
					break;
				case '|':
					if (multiCondition.charAt(i-1) != '|') {
						throw new ParameterFormatException(parameterConf, multiCondition);
					}
					this.xor = "||";
					break;
				case ' ':
					if (multiCondition.substring(i-4, i+1).toLowerCase().equals(" and ")){
						this.xor = "and";
					} else if (multiCondition.substring(i-3, i+1).toLowerCase().equals(" or ")){
						this.xor = "or";
					}					
					break;
				default:
					break;
				}
				if (this.xor != null) {
					rightConditionStr = multiCondition.substring(i+1, multiCondition.length());
					if (this.xor.equals("&&") || this.xor.equals("||")) {
						leftConditionStr = multiCondition.substring(0, i-1);
					} else if (this.xor.equals("and")) {
						leftConditionStr = multiCondition.substring(0, i-4);
					} else {
						leftConditionStr = multiCondition.substring(0, i-3);
					}
					this.leftParam = this.createBooleanParameter(leftConditionStr);
					this.rightParam = this.createBooleanParameter(rightConditionStr);
					break;
				}
			}
			
			if (i == 0) {
				if (openParenthesisCount == closeParenthesisCount
						&& multiCondition.startsWith("(")
						&& multiCondition.endsWith(")")) {
					multiCondition = multiCondition.substring(1,
							multiCondition.length() - 1);
					i = multiCondition.length() -1;
					leftConditionStr = null;
					rightConditionStr = null;
				} else {
					throw new ParameterFormatException(parameterConf, multiCondition);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.parameter.AbstractParameter#parseParameter(java.util.Map, java.lang.Object)
	 */
	@Override
	protected Object parseParameter(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {		
		if (this.xor.equals("&&") || this.xor.equals("and")) {
			return (Boolean) this.leftParam.parse(globalVarMap, dataObj)
					&& (Boolean) this.rightParam.parse(globalVarMap, dataObj);
		} else if (this.xor.equals("||") || this.xor.equals("or")) {
			return (Boolean) this.leftParam.parse(globalVarMap, dataObj)
					|| (Boolean) this.rightParam.parse(globalVarMap, dataObj);
		}
		return false;
	}
	
	private Parameter createBooleanParameter(String paramStr)
			throws TemplateAnalysisException {
		com.giants.decorator.config.element.Parameter paramConf = new com.giants.decorator.config.element.Parameter();
		paramConf.setType(Boolean.class);
		paramConf.setAllowNull(false);
		paramConf.setName(paramStr);
		return this.getElement().getTemplateEngine()
				.createParameter(paramConf, paramStr, this.getElement());
	}

}
