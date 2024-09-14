/**
 * 
 */
package com.giants.decorator.core.parameter;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.giants.decorator.core.Element;
import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.ParameterFormatException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public class ArithmeticParameter extends AbstractParameter {

	private static final long serialVersionUID = -8932589444686442163L;
	
	private Parameter leftNumParameter;
	private char operator;
	private Parameter rightNumParameter;

	/**
	 * @param parameterConf parameter conf
	 * @param element element
	 * @param arithmeticString arithmetic string
	 * @throws TemplateAnalysisException template analysis exception
	 */
	public ArithmeticParameter(
			com.giants.decorator.config.element.Parameter parameterConf,
			Element element, String arithmeticString) throws TemplateAnalysisException {
		super(parameterConf, element);
		if (parameterConf == null) {
			parameterConf = new com.giants.decorator.config.element.Parameter();
			parameterConf.setType(Number.class);
		}
		int openParenthesisCount = 0;
		int closeParenthesisCount = 0;
		char c;
		String leftArithmeticStr = null;
		String rightArithmeticStr = null;
		Integer priorityIndex = null;
		for (int i = arithmeticString.length() -1; i >= 0; i--) {
			c = arithmeticString.charAt(i);
			if (c == '(') {
				openParenthesisCount++;
			}
			if (c == ')') {
				closeParenthesisCount++;
			}
			if (openParenthesisCount == closeParenthesisCount) {
				if (c == '+' || c == '-') {
					leftArithmeticStr = arithmeticString.substring(0, i);
					rightArithmeticStr = arithmeticString.substring(i+1, arithmeticString.length());
					if (StringUtils.isEmpty(leftArithmeticStr)) {
						leftArithmeticStr = "0";
					}
					if (rightArithmeticStr.startsWith("(") && rightArithmeticStr.endsWith(")")) {
						rightArithmeticStr = rightArithmeticStr.substring(1, rightArithmeticStr.length()-1);
					}
					this.leftNumParameter = this.createNumberParameter(leftArithmeticStr);
					this.operator = c;
					this.rightNumParameter = this.createNumberParameter(rightArithmeticStr);
					leftArithmeticStr = null;
					rightArithmeticStr = null;
					break;
				} else if ((c == '*' || c == '/' || c == '%') && priorityIndex == null) {
					priorityIndex = i;
				} else if (i == 0 && priorityIndex != null) {
					leftArithmeticStr = arithmeticString.substring(0, priorityIndex);
					rightArithmeticStr = arithmeticString.substring(priorityIndex+1, arithmeticString.length());
					if (StringUtils.isEmpty(leftArithmeticStr)) {
						leftArithmeticStr = "0";
					}
					if (rightArithmeticStr.startsWith("(") && rightArithmeticStr.endsWith(")")) {
						rightArithmeticStr = rightArithmeticStr.substring(1, rightArithmeticStr.length()-1);
					}
					this.leftNumParameter = this.createNumberParameter(leftArithmeticStr);
					this.operator = arithmeticString.charAt(priorityIndex);
					this.rightNumParameter = this.createNumberParameter(rightArithmeticStr);
					leftArithmeticStr = null;
					rightArithmeticStr = null;
					break;
				}
			}			
			
			if (i == 0) {
				if (openParenthesisCount == closeParenthesisCount
						&& arithmeticString.startsWith("(")
						&& arithmeticString.endsWith(")")) {
					arithmeticString = arithmeticString.substring(1,
							arithmeticString.length() - 1);
					i = arithmeticString.length() -1;
					leftArithmeticStr = null;
					rightArithmeticStr = null;
					priorityIndex = null;
				} else {
					throw new ParameterFormatException(parameterConf,
							arithmeticString);
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
		Object leftObj = (Object)this.leftNumParameter.parse(globalVarMap, dataObj);
		Object rightObj = (Object)this.rightNumParameter.parse(globalVarMap, dataObj);
		
		Long leftLong = null;
		Double leftDouble = null;		
		if (leftObj.getClass() == Integer.class
				|| leftObj.getClass() == Long.class
				|| leftObj.getClass() == Short.class
				|| leftObj.getClass() == Byte.class) {
			leftLong = ((Number) leftObj).longValue();
		} else if (leftObj.getClass() == Double.class
				|| leftObj.getClass() == Float.class
				|| leftObj.getClass() == BigDecimal.class) {
			leftDouble = ((Number) leftObj).doubleValue();
		}
		
		Long rightLong = null;
		Double rightDouble = null;
		if (rightObj.getClass() == Integer.class
				|| rightObj.getClass() == Long.class
				|| rightObj.getClass() == Short.class
				|| rightObj.getClass() == Byte.class) {
			rightLong = ((Number) rightObj).longValue();
		} else if (rightObj.getClass() == Double.class
				|| rightObj.getClass() == Float.class
				|| rightObj.getClass() == BigDecimal.class) {
			rightDouble = ((Number) rightObj).doubleValue();
		}
		
		switch(this.operator) {
			case '+' :
				if (leftLong != null && rightLong != null) {
					return leftLong + rightLong;
				} else if (leftLong != null && rightLong == null) {
					return leftLong + rightDouble;
				} else if (leftLong == null && rightLong != null) {
					return leftDouble + rightLong;
				} else if (leftLong == null && rightLong == null) {
					return leftDouble + rightDouble;
				}
			case '-' :
				if (leftLong != null && rightLong != null) {
					return leftLong - rightLong;
				} else if (leftLong != null && rightLong == null) {
					return leftLong - rightDouble;
				} else if (leftLong == null && rightLong != null) {
					return leftDouble - rightLong;
				} else if (leftLong == null && rightLong == null) {
					return leftDouble - rightDouble;
				}
			case '*' :
				if (leftLong != null && rightLong != null) {
					return leftLong * rightLong;
				} else if (leftLong != null && rightLong == null) {
					return leftLong * rightDouble;
				} else if (leftLong == null && rightLong != null) {
					return leftDouble * rightLong;
				} else if (leftLong == null && rightLong == null) {
					return leftDouble * rightDouble;
				}
			case '/' :
				if (leftLong != null && rightLong != null) {
					return leftLong / rightLong;
				} else if (leftLong != null && rightLong == null) {
					return leftLong / rightDouble;
				} else if (leftLong == null && rightLong != null) {
					return leftDouble / rightLong;
				} else if (leftLong == null && rightLong == null) {
					return leftDouble / rightDouble;
				}
			case '%' :
				if (leftLong != null && rightLong != null) {
					return leftLong % rightLong;
				} else if (leftLong != null && rightLong == null) {
					return leftLong % rightDouble;
				} else if (leftLong == null && rightLong != null) {
					return leftDouble % rightLong;
				} else if (leftLong == null && rightLong == null) {
					return leftDouble % rightDouble;
				}
			default :
				return null;
		}
	}
	
	private Parameter createNumberParameter(String paramStr)
			throws TemplateAnalysisException {
		com.giants.decorator.config.element.Parameter paramConf = new com.giants.decorator.config.element.Parameter();
		paramConf.setType(Number.class);
		paramConf.setAllowNull(false);
		paramConf.setName(paramStr);
		return this.getElement().getTemplateEngine()
				.createParameter(paramConf, paramStr, this.getElement());
	}

}
