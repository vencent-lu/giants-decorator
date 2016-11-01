/**
 * 
 */
package com.giants.decorator.html.function;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.function.FunctionHandler;

/**
 * @author vencent.lu
 *
 */
public class UrlEncoderFunctionHandler implements FunctionHandler {

	@Override
	public Object execute(TemplateEngine templateEngine, Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		String str = (String)parameters.get(0).parse(globalVarMap, dataObj);
		String enc = (String)parameters.get(1).parse(globalVarMap, dataObj);
		
		if (StringUtils.isEmpty(enc)) {
			enc = "UTF-8";
		}
		
		try {
			return URLEncoder.encode(str, enc);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

}
