/**
 * 
 */
package com.giants.decorator.core.exception.parse;

import java.text.MessageFormat;

import com.giants.decorator.core.Parameter;

/**
 * @author vencent.lu
 *
 */
public class ParameterNotAllowNullException extends TemplateParseException {

	private static final long serialVersionUID = -6537244566181984013L;
	
	private Parameter parameter;

	/**
	 * @param parameter parameter
	 * @param dataObj data object
	 */
	public ParameterNotAllowNullException(Parameter parameter, Object dataObj) {
		super(parameter.getElement().getKey(), parameter.getElement(), dataObj,
				MessageFormat.format("\"{0}\" parameter not allow null !",
						parameter.getName()));
		this.parameter = parameter;
	}

	/**
	 * @return the parameter
	 */
	public Parameter getParameter() {
		return parameter;
	}

}
