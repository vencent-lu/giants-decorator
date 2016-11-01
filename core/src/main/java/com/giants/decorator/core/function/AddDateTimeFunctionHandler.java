/**
 * 
 */
package com.giants.decorator.core.function;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public class AddDateTimeFunctionHandler implements FunctionHandler {

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.function.FunctionHandler#execute(java.util.Map, java.lang.Object, java.util.List)
	 * calendarField y:年 M:月 d:日 E:星期 H:小时 m:分 s:秒 S:毫秒
	 */
	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		Date date = (Date) parameters.get(0).parse(globalVarMap, dataObj);
		String calendarField = (String) parameters.get(1).parse(globalVarMap, dataObj);
		Integer increment = (Integer) parameters.get(2).parse(globalVarMap, dataObj);
		char cf = calendarField.charAt(0);
		switch (cf) {
			case 'y':
				return DateUtils.addYears(date, increment);
			case 'M':
				return DateUtils.addMonths(date, increment);
			case 'd':
				return DateUtils.addDays(date, increment);
			case 'E':
				return DateUtils.addWeeks(date, increment);
			case 'H':
				return DateUtils.addHours(date, increment);
			case 'm':
				return DateUtils.addMinutes(date, increment);
			case 's':
				return DateUtils.addSeconds(date, increment);
			case 'S':
				return DateUtils.addMilliseconds(date, increment);
			default:
				return date;
		}
	}

}
