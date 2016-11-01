/**
 * 
 */
package com.giants.decorator.core.parameter;

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import com.giants.common.regex.Matcher;
import com.giants.decorator.common.DecoratorConstants;
import com.giants.decorator.core.Element;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;
import com.giants.decorator.core.exception.analysis.NotSupportParameterTypeException;
import com.giants.decorator.core.exception.analysis.ParameterFormatException;

/**
 * @author vencent.lu
 *
 */
public class DefiniteValueParameter extends AbstractParameter {

	private static final long serialVersionUID = 2353043442160613087L;
	
	private Object value;

	/**
	 * @param type
	 * @param value
	 * @param allowNull
	 * @param element
	 * @throws TemplateAnalysisException 
	 */
	public DefiniteValueParameter(
			com.giants.decorator.config.element.Parameter parameterConf,
			Element element, String value) throws TemplateAnalysisException {
		super(parameterConf, element);
		if (value.equals("null")) {
			this.value = null;
		} else {
			try {
				if (parameterConf == null || parameterConf.getType() == Object.class) {
					if (!DecoratorConstants.TEMPLATE_STRING_PARAM_PATTERN
							.matches(value)) {
						if (DecoratorConstants.TEMPLATE_LONG_PARAM_PATTERN.matches(value)) {
							this.value = Long.parseLong(value);
						} else if (DecoratorConstants.TEMPLATE_DOUBLE_PARAM_PATTERN.matches(value)) {
							this.value = Double.parseDouble(value);
						} else if (value.equals("true") || value.equals("false")) {
							this.value = Boolean.parseBoolean(value);
						} else {
							throw new ParameterFormatException(parameterConf, value);
						}
					} else {
						Matcher matcher = DecoratorConstants.TEMPLATE_STRING_PARAM_PATTERN
								.matcher(value);
						if (matcher.find()) {
							this.value = matcher.group(2);
						} else {
							this.value = "";
						}
					}
				} else if (parameterConf.getType() == Byte.class) {
					this.value = Byte.parseByte(value);
				} else if (parameterConf.getType() == Short.class) {
					this.value = Short.parseShort(value);
				} else if (parameterConf.getType() == Integer.class) {
					this.value = Integer.parseInt(value);
				} else if (parameterConf.getType() == String.class) {
					if (!DecoratorConstants.TEMPLATE_STRING_PARAM_PATTERN
							.matches(value)) {
						throw new ParameterFormatException(parameterConf,
								value, null);
					}
					Matcher matcher = DecoratorConstants.TEMPLATE_STRING_PARAM_PATTERN
							.matcher(value);
					if (matcher.find()) {
						this.value = matcher.group(2);
					} else {
						this.value = "";
					}
				} else if (parameterConf.getType() == Long.class) {
					this.value = Long.parseLong(value);
				} else if (parameterConf.getType() == Double.class) {
					this.value = Double.parseDouble(value);
				} else if (parameterConf.getType() == Float.class) {
					this.value = Float.parseFloat(value);
				} else if (parameterConf.getType() == Boolean.class) {
					this.value = Boolean.parseBoolean(value);
				} else if (parameterConf.getType() == Date.class) {
					Matcher matcher = DecoratorConstants.TEMPLATE_STRING_PARAM_PATTERN
							.matcher(value);
					if (matcher.find()) {
						String dateValue = matcher.group(2).trim();
						try {
							this.value = DecoratorConstants.DATE_FORMAT_FULL.parse(dateValue);
						} catch (ParseException e) {
							try {
								this.value = DecoratorConstants.DATE_FORMAT_YMD.parse(dateValue);
							} catch (ParseException e1) {
								throw new ParameterFormatException(parameterConf, value);
							}
						}
					} else {
						throw new ParameterFormatException(parameterConf, value);
					}
				} else if (parameterConf.getType() == Number.class) {
					if (DecoratorConstants.TEMPLATE_LONG_PARAM_PATTERN.matches(value)) {
						this.value = Long.parseLong(value);
					} else if (DecoratorConstants.TEMPLATE_DOUBLE_PARAM_PATTERN.matches(value)) {
						this.value = Double.parseDouble(value);
					} else {
						throw new ParameterFormatException(parameterConf, value);
					}
				} else {
					throw new NotSupportParameterTypeException(parameterConf, value);
				}
			} catch (NumberFormatException e) {
				throw new ParameterFormatException(parameterConf, value, e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.parameter.AbstractParameter#parseParameter(java.util.Map, java.lang.Object)
	 */
	@Override
	protected Object parseParameter(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		return this.value;
	}
	
}
