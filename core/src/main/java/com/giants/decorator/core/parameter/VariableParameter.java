/**
 * 
 */
package com.giants.decorator.core.parameter;

import java.util.Map;

import com.giants.common.beanutils.PropertyUtils;
import com.giants.decorator.config.element.Parameter;
import com.giants.decorator.core.Element;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.parse.AttributeUndefinedException;
import com.giants.decorator.core.exception.parse.ParameterValueAndTypeMismatchException;

/**
 * @author vencent.lu
 *
 */
public class VariableParameter extends AbstractParameter {

	private static final long serialVersionUID = 7975375876140861974L;
	
	private String[] levelVariables;
	private boolean isNegation = false;
	
	/**
	 * @param parameterConf parameter conf
	 * @param element element
	 * @param varkey var key
	 */
	public VariableParameter(Parameter parameterConf, Element element,
			String varkey) {
		super(parameterConf, element);
		if (!varkey.startsWith("!")) {
			this.levelVariables = varkey.split("[\\s.]+");
		} else {
			this.isNegation = true;			
			this.levelVariables = varkey.replaceFirst("!", "").split("[\\s.]+");
			if (this.getType() == null) {
				this.setType(Boolean.class);
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.parameter.AbstractParameter#parseParameter(java.util.Map, java.lang.Object)
	 */
	@Override
	protected Object parseParameter(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		Object varDataObj = dataObj;
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
		if (!this.isNegation) {
			return varDataObj;
		} else if (varDataObj instanceof Boolean) {
			return !(Boolean)varDataObj;
		} else {
			throw new ParameterValueAndTypeMismatchException(this, varDataObj);
		}
	}

}
