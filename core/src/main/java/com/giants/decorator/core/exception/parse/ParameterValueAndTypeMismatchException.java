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
public class ParameterValueAndTypeMismatchException extends
		TemplateParseException {

	private static final long serialVersionUID = 6265659263692564442L;
	
	private Parameter parameter;

	/**
	 * @param parameter parameter
	 * @param dataObj data object
	 * @param e throwable
	 */
	public ParameterValueAndTypeMismatchException(Parameter parameter,
			Object dataObj, Throwable e) {
		super(
				parameter.getName(),
				parameter.getElement(),
				dataObj,
				MessageFormat
						.format("\"{0}\" parameter value and type mismatch ,\"{1}\" can not convert to \"{2}\" !",
								parameter.getName(), dataObj,
								parameter.getType()), e);
		this.parameter = parameter;
	}
	
	/**
	 * @param parameter parameter
	 * @param dataObj data object
	 */
	public ParameterValueAndTypeMismatchException(Parameter parameter,
			Object dataObj) {
		super(
				parameter.getName(),
				parameter.getElement(),
				dataObj,
				MessageFormat
						.format("\"{0}\" parameter value and type mismatch ,\"{1}\" can not convert to \"{2}\" !",
								parameter.getName(), dataObj,
								parameter.getType()));
		this.parameter = parameter;
	}

	/**
	 * @return the parameter
	 */
	public Parameter getParameter() {
		return parameter;
	}

}
