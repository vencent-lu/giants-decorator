/**
 * 
 */
package com.giants.decorator.core.function;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.giants.decorator.core.Function;
import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.FunctionParenthesisNotMatchException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;
import com.giants.decorator.core.variable.AbstractElement;

/**
 * @author vencent.lu
 *
 */
public abstract class AbstractFunction extends AbstractElement implements
		Function {

	private static final long serialVersionUID = -6973570613229803925L;
	
	private String functionName;
	private List<Parameter> parameters;
	
	protected abstract Object parse(Map<String, Object> globalVarMap,
			Object dataObj, List<Parameter> parameters)
			throws TemplateException;
	
	/**
	 * 
	 * @param templateEngine template engine
	 * @param key key
	 * @param functionConf function configuration
	 * @throws TemplateAnalysisException template analysis exception
	 */
	public AbstractFunction(TemplateEngine templateEngine, String key,
			com.giants.decorator.config.element.Function functionConf)
			throws TemplateAnalysisException {
		super(templateEngine, key, key);
		this.analysisFunction(key, functionConf);
	}
	
	private void analysisFunction(String key,
			com.giants.decorator.config.element.Function functionConf)
			throws TemplateAnalysisException {
		if (functionConf.getParameters() != null
				&& functionConf.getParameters().size() > 0) {
			StringBuffer functionName = new StringBuffer();
			boolean endName = false;
			StringBuffer param = new StringBuffer();
			int openParenthesisCount = 0;
			int closeParenthesisCount = 0;
			int openBracketCount = 0;
			int closeBracketCount = 0;
			int openArrayCount = 0;
			int closeArrayCount = 0;
			int paramConfIndex = 0;
			for (int i = 0; i < key.length()
					&& paramConfIndex < functionConf.getParameters().size(); i++) {
				char c = key.charAt(i);
				if (!endName && c == '(') {
					endName = true;
				} else if (!endName) {
					functionName.append(c);
				} else {
					if ((c == ','
							&& openParenthesisCount == closeParenthesisCount
							&& openBracketCount == closeBracketCount && openArrayCount == closeArrayCount)
							|| i == key.length() - 1) {
						if (param.length() != 0) {
							this.addParameter(this.templateEngine.createParameter(
									functionConf.getParameters().get(
											paramConfIndex++),
									param.toString(), this));
							param.delete(0, param.length());
						}
						openParenthesisCount = 0;
						closeParenthesisCount = 0;
					} else {
						param.append(c);
					}
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
					&& param.length() != 0) {
				throw new FunctionParenthesisNotMatchException(
						param.toString(), key, key);
			}
			for (int i = paramConfIndex; i < functionConf.getParameters()
					.size(); i++) {
				this.addParameter(this.templateEngine.createParameter(functionConf
						.getParameters().get(i), null, this));
			}
			this.functionName = functionName.toString();
		} else {
			this.functionName = key.split("\\(")[0];
		}
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Function#getFunctionName()
	 */
	@Override
	public String getFunctionName() {
		return this.functionName;
	}

	public List<Parameter> getParameters() {
		return this.parameters;
	}
	
	private void addParameter(Parameter parameter) {
		if (this.parameters == null) {
			this.parameters = new ArrayList<Parameter>();
		}
		this.parameters.add(parameter);
	}
	
	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Element#parseForObject(java.util.Map, java.lang.Object)
	 */
	@Override
	public Object parseForObject(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		return this.parse(globalVarMap, dataObj, this.parameters);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Element#parseForObject(java.lang.Object)
	 */
	@Override
	public Object parseForObject(Object dataObj) throws TemplateException {
		return this.parseForObject(null, dataObj);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Function#call(java.lang.Object)
	 */
	@Override
	public Object call(Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException {
		return this.parse(globalVarMap, dataObj, this.parameters);
	}

}
