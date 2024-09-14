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
public class NotSupportParameterTypeException extends TemplateAnalysisException {

	private static final long serialVersionUID = -3560499588061214434L;
	
	private String name;
	private Class<?> type;
	private String value;

	/**
	 * @param parameterConf parameter configuration
	 * @param value value
	 */
	public NotSupportParameterTypeException(Parameter parameterConf,
			String value) {
		super(parameterConf.getName(), value, MessageFormat.format(
				"Parameters not supported {0} !", parameterConf.getType()));
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
