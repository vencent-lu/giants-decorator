/**
 * 
 */
package com.giants.decorator.core.function;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public class DateFormatFunctionHandler implements FunctionHandler {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat();

	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		Date date = (Date) parameters.get(0).parse(globalVarMap, dataObj);
		String format = (String) parameters.get(1).parse(globalVarMap, dataObj);
		dateFormat.applyPattern(format);
		return dateFormat.format(date);
	}

}
