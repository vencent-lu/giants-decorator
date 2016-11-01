/**
 * 
 */
package com.giants.decorator.core.parameter;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.giants.common.regex.Matcher;
import com.giants.decorator.common.DecoratorConstants;
import com.giants.decorator.core.Element;
import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.ParameterFormatException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public class ConditionParameter extends AbstractParameter {

	private static final long serialVersionUID = -5448622435393996598L;
	
	private Parameter leftParam;
	private String judgment;
	private Parameter rightParam;

	/**
	 * @param parameterConf
	 * @param element
	 * @throws TemplateAnalysisException 
	 */
	public ConditionParameter(
			com.giants.decorator.config.element.Parameter parameterConf,
			Element element, String condition) throws TemplateAnalysisException {
		super(parameterConf, element);
		if (parameterConf == null) {
			parameterConf = new com.giants.decorator.config.element.Parameter();
			parameterConf.setType(Boolean.class);
		}
		Matcher matcher = DecoratorConstants.TEMPLATE_CONDITION_PARAMETER_PATTERN
				.matcher(condition);
		if (matcher.find()) {
			String leftParam = matcher.group(1);
			String judgment = matcher.group(2);
			String rightParam = matcher.group(6);
			if (judgment.equals("=") || judgment.equals("=!") || judgment.equals(">>")
					|| judgment.equals("<<") || judgment.equals("=>")
					|| judgment.equals("=<")) {
				throw new ParameterFormatException(parameterConf, condition);
			}			
			if ((judgment.equals(" is ") || judgment.equals(" not "))
					&& !rightParam.equals("empty") && !rightParam.equals("null")) {
				throw new ParameterFormatException(parameterConf, condition);
			}
			if (rightParam.equals("null") && !judgment.equals("==") && !judgment.equals("!=")) {				
				throw new ParameterFormatException(parameterConf, condition);
			}
			com.giants.decorator.config.element.Parameter paramConf = null;
			if (judgment.equals(">") || judgment.equals(">=")
					|| judgment.equals("<") || judgment.equals("<=")) {
				paramConf = new com.giants.decorator.config.element.Parameter();
				paramConf.setType(Number.class);
			}
			this.leftParam = element.getTemplateEngine().createParameter(paramConf, leftParam, element);
			this.judgment = judgment;
			if (rightParam.equals("empty")) {
				this.rightParam = element.getTemplateEngine().createParameter(paramConf,
						new StringBuffer("\"").append(rightParam).append("\"")
								.toString(), element);
			} else {
				this.rightParam = element.getTemplateEngine().createParameter(paramConf,
						rightParam, element);
			}
		} else {
			throw new ParameterFormatException(parameterConf, condition);
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.parameter.AbstractParameter#parseParameter(java.util.Map, java.lang.Object)
	 */
	@Override
	protected Object parseParameter(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		Object leftObj = this.leftParam.parse(globalVarMap, dataObj);
		Object rightObj = this.rightParam.parse(globalVarMap, dataObj);
		
		Long leftLong = null;
		Double leftDouble = null;
		if (leftObj != null) {
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
		}
		
		Long rightLong = null;
		Double rightDouble = null;
		if (rightObj != null) {
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
		}
		
		if (this.judgment.equals(" is ") || this.judgment.equals(" not ")) {
			if (rightObj == null
					|| (rightObj.equals("empty") && !(leftObj instanceof String))) {
				return this.judgment.equals(" is ") ? leftObj == null
						: leftObj != null;
			} else if (rightObj.equals("empty")) {
				return this.judgment.equals(" is ") ? StringUtils
						.isEmpty((String) leftObj) : StringUtils
						.isNotEmpty((String) leftObj);
			} else {
				return false;
			}
		} else if (rightObj == null) {
			return this.judgment.equals("==") ? leftObj == null
					: leftObj != null;
		} else if (this.judgment.equals("==")) {
			if (leftObj instanceof Number && rightObj instanceof Number) {
				return (leftLong != null ? leftLong : leftDouble) == (rightLong != null ? rightLong
						: rightDouble);
			} else {
				return leftObj != null ? leftObj.getClass() == rightObj
						.getClass() ? leftObj.equals(rightObj) : false : false;
			}
		} else if (this.judgment.equals("!=")) {
			if (leftObj instanceof Number && rightObj instanceof Number) {
				return (leftLong != null ? leftLong : leftDouble) != (rightLong != null ? rightLong
						: rightDouble);
			} else {
				return leftObj != null ? leftObj.getClass() == rightObj
						.getClass() ? !leftObj.equals(rightObj) : true : true;
			}
		} else if (this.judgment.equals(">")) {
			return (leftLong != null ? leftLong : leftDouble) > (rightLong != null ? rightLong
					: rightDouble);
		} else if (this.judgment.equals("<")) {
			return (leftLong != null ? leftLong : leftDouble) < (rightLong != null ? rightLong
					: rightDouble);
		} else if (this.judgment.equals(">=")) {
			return (leftLong != null ? leftLong : leftDouble) >= (rightLong != null ? rightLong
					: rightDouble);
		} else if (this.judgment.equals("<=")) {
			return (leftLong != null ? leftLong : leftDouble) <= (rightLong != null ? rightLong
					: rightDouble);
		}
		return false;
	}

}
