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
public class ParameterFormatException extends TemplateAnalysisException {

	private static final long serialVersionUID = 4223098551129197400L;
	
	private String name;
	private Class<?> type;
	private String value;
	
	/**
	 * @param parameterConf
	 * @param value
	 */
	public ParameterFormatException(Parameter parameterConf, String value) {
		super(
				parameterConf.getName(),
				value,
				MessageFormat
						.format("{0} parameter value \"{1}\" format error, Correct type parameters for {2}",
								parameterConf.getName(), value,
								parameterConf.getType()));
		this.name = parameterConf.getName();
		this.type = parameterConf.getType();
		this.value = value;
	}

	/**
	 * @param parameterConf
	 * @param value
	 * @param e
	 */
	public ParameterFormatException(Parameter parameterConf, String value,
			Throwable e) {
		super(
				parameterConf.getName(),
				value,
				MessageFormat
						.format("{0} parameter value \"{1}\" format error, Correct type parameters for {2}",
								parameterConf.getName(), value,
								parameterConf.getType()), e);
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}

}
