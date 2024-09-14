/**
 * 
 */
package com.giants.decorator.core.parameter;

import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.giants.common.regex.Matcher;
import com.giants.decorator.common.DecoratorConstants;
import com.giants.decorator.config.element.Parameter;
import com.giants.decorator.core.Element;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.ParameterFormatException;
import com.giants.decorator.core.exception.parse.AttributeUndefinedException;

/**
 * @author vencent.lu
 *
 */
public class GlobalVariableParameter extends AbstractParameter {

	private static final long serialVersionUID = -6345734373127238397L;
	
	private String[] levelVariables;

	/**
	 * @param parameterConf parameter conf
	 * @param element element
	 * @param varkey var key
	 * @throws ParameterFormatException parameter format exception
	 */
	public GlobalVariableParameter(Parameter parameterConf, Element element,
			String varkey) throws ParameterFormatException {
		super(parameterConf, element);
		Matcher varKeyMatcher = DecoratorConstants.TEMPLATE_GLOBAL_VARIABLE_PARAMETER_PATTERN
				.matcher(varkey);
		if (varKeyMatcher.find()) {
			this.levelVariables = varKeyMatcher.group(1).split("[\\s.]+");
		} else {
			throw new ParameterFormatException(parameterConf, varkey);
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.parameter.AbstractParameter#parseParameter(java.util.Map, java.lang.Object)
	 */
	@Override
	protected Object parseParameter(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		Object varDataObj = globalVarMap;
		if (this.levelVariables != null && varDataObj != null) {
			for (String levelVar : this.levelVariables) {
				try {
					varDataObj = PropertyUtils	.getProperty(varDataObj, levelVar);
					if (varDataObj == null) {
						return null;
					}
				} catch (Exception e) {
					throw new AttributeUndefinedException(levelVar, this.getElement(), varDataObj, e);
				}
			}
		}
		return varDataObj;
	}

}
