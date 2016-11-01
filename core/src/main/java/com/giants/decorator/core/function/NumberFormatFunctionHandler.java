/**
 * 
 */
package com.giants.decorator.core.function;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 * 1、“0”——表示一位数值;如没有，显示0。如“0000.0000”，整数位>4，按实际输出，<4前面补0，凑足4位。
 * 2、“#”——表示任何位数的整数。如没有，则不显示。在小数点模式后使用，只表示一位小数;四舍五入。如：
 *  				# 无小数，小数部分四舍五入。
 *  				.# 整数部分不变，一位小数，四舍五入。
 *  				.## 整数部分不变，二位小数，四舍五入。
 *  3、“.”——表示小数点模式。
 *  4、“，”与模式“0”一起使用，表示逗号。
 */
public class NumberFormatFunctionHandler implements FunctionHandler {
	
	private static DecimalFormat numFormat = new DecimalFormat();

	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		Number num = (Number)parameters.get(0).parse(globalVarMap, dataObj);
		String format = (String)parameters.get(1).parse(globalVarMap, dataObj);
		numFormat.applyPattern(format);
		return numFormat.format(num);
	}

}
