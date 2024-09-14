/**
 * 
 */
package com.giants.decorator.core.parameter;

import java.util.Map;

import com.giants.decorator.core.Element;
import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.parse.ParameterNotAllowNullException;
import com.giants.decorator.core.exception.parse.ParameterValueAndTypeMismatchException;

/**
 * @author vencent.lu
 *
 */
public abstract class AbstractParameter implements Parameter {

	private static final long serialVersionUID = -8126982300249575737L;

	private String name;
	private Class<?> type;
	private boolean allowNull = true;
	private Element element;
	
	protected abstract Object parseParameter(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException;
	
	/**
	 * @param parameterConf parameter configuration
	 * @param element element
	 */
	public AbstractParameter(
			com.giants.decorator.config.element.Parameter parameterConf,
			Element element) {
		super();
		if (parameterConf != null) {
			this.name = parameterConf.getName();
			this.type = parameterConf.getType();
			this.allowNull = parameterConf.isAllowNull();
		}
		this.element = element;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Parameter#getName()
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Parameter#getType()
	 */
	@Override
	public Class<?> getType() {
		return this.type;
	}

	/**
	 * @param type the type to set
	 */
	protected void setType(Class<?> type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Parameter#getElement()
	 */
	@Override
	public Element getElement() {
		return this.element;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Parameter#parse(java.util.Map, java.lang.Object)
	 */
	@Override
	public Object parse(Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException {
		Object result = this.parseParameter(globalVarMap, dataObj);
		if (result instanceof Element) {
			result = ((Element)result).parse(globalVarMap, dataObj);
		}
		if (result == null && !this.isAllowNull()) {
			throw new ParameterNotAllowNullException(this, result);
		}
		if (this.getType() == null) {
			return result;
		} else if (result instanceof Number) {
			if (this.getType() == Integer.class) {
				return ((Number)result).intValue();
			} else if (this.getType() == Long.class) {
				return ((Number)result).longValue();
			} else if (this.getType() == Float.class) {
				return ((Number)result).floatValue();
			} else if (this.getType() == Double.class) {
				return ((Number)result).doubleValue();
			} else if (this.getType() == Short.class) {
				return ((Number)result).shortValue();			
			} else if (this.getType() == Byte.class) {
				return ((Number)result).byteValue();
			} else {
				try {
					return this.getType().cast(result);
				} catch (ClassCastException e) {
					throw new ParameterValueAndTypeMismatchException(this, result, e);
				}
			}
		} else {
			try {
				return this.getType().cast(result);
			} catch (ClassCastException e) {
				throw new ParameterValueAndTypeMismatchException(this, result, e);
			}
		}
	}

	/**
	 * @return the allowEmpty
	 */
	public boolean isAllowNull() {
		return allowNull;
	}
	
}
