/**
 * 
 */
package com.giants.decorator.core.parameter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.giants.common.beanutils.BeanUtils;
import com.giants.decorator.core.Element;
import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.ParameterFormatException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;
import com.giants.decorator.core.exception.parse.ParameterValueAndTypeMismatchException;

/**
 * @author vencent.lu
 *
 */
public class ObjectParameter extends AbstractParameter {

	private static final long serialVersionUID = -8556156594835736041L;
	
	private Map<String, Parameter> propertyMap;

	/**
	 * @param parameterConf parameter conf
	 * @param element element
	 * @param objectStr object string
	 * @throws TemplateAnalysisException template analysis exception
	 */
	public ObjectParameter(
			com.giants.decorator.config.element.Parameter parameterConf,
			Element element, String objectStr) throws TemplateAnalysisException {
		super(parameterConf, element);
		this.propertyMap = new HashMap<String, Parameter>();
		int openParenthesisCount = 0;
		int closeParenthesisCount = 0;
		int openBracketCount = 0;
		int closeBracketCount = 0;
		int openArrayCount = 0;
		int closeArrayCount = 0;
		StringBuffer property = new StringBuffer();
		char c;
		String propertyName = null;
		String propertyValue = null;
		for (int i = 1; i < objectStr.length(); i++) {
			c = objectStr.charAt(i);
			if (c == ':' && propertyName == null) {
				propertyName = property.toString();
				property.delete(0, property.length());
			} else if ((c == ','
					&& openParenthesisCount == closeParenthesisCount
					&& openBracketCount == closeBracketCount
					&& openArrayCount == closeArrayCount)
					|| i == objectStr.length() - 1) {
				if (propertyName != null){
					propertyValue = property.toString();
					property.delete(0, property.length());
					if (this.getType() == null || this.getType() == Map.class
							|| this.getType() == Object.class) {
						this.propertyMap.put(propertyName, element.getTemplateEngine()
								.createParameter(null, propertyValue,
										element));
					} else {				
						try {
							com.giants.decorator.config.element.Parameter paramConf = new com.giants.decorator.config.element.Parameter();
							paramConf.setType(this.getType().getDeclaredField(
									propertyName).getType());
							this.propertyMap.put(propertyName, element.getTemplateEngine()
									.createParameter(paramConf, propertyValue,
											element));
						} catch (Exception e) {
							throw new ParameterFormatException(parameterConf, objectStr, e);
						}
					}
					propertyName = null;
					propertyValue = null;
				}
			} else {
				property.append(c);
			}
			if (i != objectStr.length() - 1) {
				if (c == '(') {
					openParenthesisCount++;
				}
				if (c == ')') {
					closeParenthesisCount++;
				}
				if (c == '{') {
					openBracketCount++;
				}
				if (c == '}') {
					closeBracketCount++;
				}
				if (c == '[') {
					openArrayCount++;
				}
				if (c == ']') {
					closeArrayCount++;
				}
			}
		}
		if (openParenthesisCount != closeParenthesisCount
				|| openBracketCount != closeBracketCount
				|| openArrayCount != closeArrayCount) {
			throw new ParameterFormatException(parameterConf, objectStr);
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.parameter.AbstractParameter#parseParameter(java.util.Map, java.lang.Object)
	 */
	@Override
	protected Object parseParameter(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		if (this.getType() == Map.class || this.getType() == null) {
			Map<String, Object> map = new HashMap<String, Object>();
			Iterator<String> keyIt = this.propertyMap.keySet().iterator();
			while (keyIt.hasNext()) {
				String key = keyIt.next();
				map.put(key,
						this.propertyMap.get(key).parse(globalVarMap, dataObj));
			}
			return map;
		} else {
			try {
				Object obj = this.getType().getConstructor().newInstance();
				Iterator<String> keyIt = this.propertyMap.keySet().iterator();
				while (keyIt.hasNext()) {
					String key = keyIt.next();
					BeanUtils.copyProperty(obj, key, this.propertyMap.get(key)
							.parse(globalVarMap, dataObj));
				}
				return obj;
			} catch (Exception e) {
				throw new ParameterValueAndTypeMismatchException(this, dataObj, e);
			}
		}
	}

}
