/**
 * 
 */
package com.giants.decorator.common;

import java.text.SimpleDateFormat;

import com.giants.common.regex.Pattern;

/**
 * @author vencent.lu
 *
 */
public class DecoratorConstants {
		
	public final static String TEMPLATE_BLOCK_REGEX = "<!--\\s*BEGIN\\s*{beginBlockRegex}\\s*-->([\\S\\s]*?)\\s*<!--\\s*END\\s*{blockName}\\s*-->";
	public final static String TEMPLATE_BLOCK_NEST_REGEX = "(<!--\\s*BEGIN\\s*{blockName}\\s*\\:?\\s*[\\S\\s]+?\\s*-->[\\S\\s]*?<!--\\s*END\\s*{blockName}\\s*-->|[\\S\\s])*?";
	public final static String TEMPLATE_BLOCK_BEGIN_REGEX = "<!--\\s*BEGIN\\s*{blockName}\\s*\\:?\\s*[\\S\\s]+?\\s*-->";	
	public final static String TEMPLATE_BLOCK_END_REGEX = "<!--\\s*END\\s*{blockName}\\s*-->";
	public final static String TEMPLATE_BLOCK_FULL_BEGIN_REGEX = "<!--\\s*BEGIN\\s*{beginBlockRegex}\\s*-->";
	public final static String TEMPLATE_VARIABLE_REGEX = "\\$\\{([\\S\\s]*?)\\}";
	public final static String TEMPLATE_VARIABLE_NEST_REGEX = "(\\{[\\S\\s]*?\\}|[\\S\\s])*?";
		
	public final static Pattern TEMPLATE_STRING_PARAM_PATTERN = Pattern.compile("([\'\"])(.*)\\1");
	public final static Pattern TEMPLATE_LONG_PARAM_PATTERN = Pattern.compile("\\-?\\d+");
	public final static Pattern TEMPLATE_DOUBLE_PARAM_PATTERN = Pattern.compile("\\-?\\d+\\.\\d+");
	public final static Pattern TEMPLATE_OBJECT_PARAM_PATTERN = Pattern.compile("\\{(\\w+\\:.+[\\,]?)+\\}");
	public final static Pattern TEMPLATE_DEFINITE_VALUE_PARAM_PATTERN = Pattern.compile("([\"\']).*\\1|\\-?\\d+(\\.\\d+)?|true|false");
	public final static Pattern TEMPLATE_VARIABLE_PARAMETER_PATTERN = Pattern.compile("\\!?[a-zA-Z]{1}[\\w\\.]*\\w+|[a-zA-Z]{1}");
	public final static Pattern TEMPLATE_ARITHMETIC_PARAMETER_PATTERN = Pattern.compile("[\\+\\-]?[a-zA-Z\\d\\(\\)]+(\\.[a-zA-Z\\d\\(\\)]+)?([\\+\\-\\*\\/\\%][a-zA-Z\\d\\(\\)]+(\\.[a-zA-Z\\d\\(\\)]+)?)*");
	public final static Pattern TEMPLATE_GLOBAL_VARIABLE_PARAMETER_PATTERN = Pattern.compile("RQ\\.([a-zA-Z]{1}[\\w\\.]*\\w+|[a-zA-Z]{1})");
	public final static Pattern TEMPLATE_FUNCTION_PARAMETER_PATTERN = Pattern.compile("[a-zA-Z]{1}\\w+\\([\\S\\s]*\\)");
	public final static Pattern TEMPLATE_CONDITION_PARAMETER_PATTERN = Pattern.compile("([^\\;\\:\\[\\]\\<\\>\\=\\!\\&\\|\\s]+)\\s*(([\\<\\>\\=\\!]{1,2})|( is )|( not ))\\s*([^\\;\\:\\[\\]\\<\\>\\=\\&\\|\\s]+)");
	public final static Pattern TEMPLATE_MULTI_CONDITION_PARAMETER_PATTERN = Pattern.compile("([^\\;\\:\\[\\]]+)\\s*((\\&\\&)|(\\|\\|)|(\\s+and\\s+)|(\\s+or\\s+))\\s*([^\\;\\:\\[\\]]+)");
	public final static Pattern TEMPLATE_LIST_PARAMETER_PATTERN = Pattern.compile("\\[.+\\]");
	
	public final static Pattern TEMPLATE_VARIABLE_PATTERN = Pattern.compile(TEMPLATE_VARIABLE_REGEX);
	public final static Pattern TEMPLATE_BLOCK_BEGIN_PATTERN = Pattern.compile("<!--\\s*BEGIN\\s*([\\S\\s]+?)\\s*-->");
	public final static Pattern TEMPLATE_FUNCTION_PATTERN = Pattern.compile("\\w+\\(.*\\)");
	public final static Pattern TEMPLATE_FUNCTION_NAME_PATTERN = Pattern.compile("([\\w]+)\\(.*\\)");
	public final static Pattern TEMPLATE_BLOCK_PARAMETER_PATTERN = Pattern.compile("[\\s\\:]{1}([a-zA-Z]{1}[\\w]*)\\=(([^\\s\\=\\:#]+)|(#\\[[^\\:]+\\]))");
	
	public final static Pattern TEMPLATE_HTML_HEAD_PATTERN = Pattern.compile("<\\s*[Hh][Ee][Aa][Dd]\\s*[\\S\\s]*?>\\s*([\\S\\s]+?)<\\s*/[Hh][Ee][Aa][Dd]\\s*>");
	public final static Pattern TEMPLATE_HTML_BODY_PATTERN = Pattern.compile("<\\s*[Bb][Oo][Dd][Yy]\\s*[\\S\\s]*?>\\s*([\\S\\s]+?)<\\s*/[Bb][Oo][Dd][Yy]\\s*>");
	public final static Pattern TEMPLATE_HTML_TAG_PATTERN = Pattern.compile("\\s*((<\\s*([a-zA-Z]+)\\s*[\\S\\s]*?>([\\S\\s]*?)<\\s*\\/\\3\\s*>)|(<\\s*([a-zA-Z]+)\\s*[\\S\\s]*?\\/?>))\\s*");
	public final static Pattern TEMPLATE_HTML_TAG_ATT_PATTERN = Pattern.compile("\\s*([a-zA-Z\\-]+?)=([\"\'])([\\S\\s]*?)\\2\\s*");
	public final static Pattern TEMPLATE_HTML_URL_PATTERN = Pattern.compile("([\"\'])((\\.\\.\\/)*([^\"\'\\.\\/\\s]+\\/)*[^\"\'\\/\\s]+\\.(gif|png|jpg|js|css|ico|swf))\\1");
	public final static Pattern TEMPLATE_HTML_STYLE_URL_PATTERN = Pattern.compile("url\\(\\s*((\\.\\.\\/)*([^\"\'\\.\\/\\s]+\\/)*[^\"\'\\/\\s]+\\.(gif|png|jpg|ico))\\s*\\)");
	
	public final static SimpleDateFormat 	DATE_FORMAT_FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat DATE_FORMAT_YMD = new SimpleDateFormat("yyyy-MM-dd");

}
