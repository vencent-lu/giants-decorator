/**
 * 
 */
package com.giants.decorator.core.exception.analysis;

import java.text.MessageFormat;

import com.giants.decorator.config.element.Parameter;

/**
 * @author vencent.lu
 *
 */
public class ParameterIllegalException extends TemplateAnalysisException {

	private static final long serialVersionUID = -8848843337820846524L;
	
	private String name;
	private Class<?> type;
	private String value;

	/**
	 * @param parameterConf parameter config
	 * @param value parameter value
	 */
	public ParameterIllegalException(Parameter parameterConf, String value) {
		super(
				parameterConf.getName(),
				value,
				MessageFormat
						.format("{0} parameter value \"{1}\" illegal, Correct type parameters for {2}",
								parameterConf.getName(), value,
								parameterConf.getType()));
		this.name = parameterConf.getName();
		this.type = parameterConf.getType();
		this.value = value;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

}
