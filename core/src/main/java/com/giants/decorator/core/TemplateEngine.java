/**
 * 
 */
package com.giants.decorator.core;

import com.giants.decorator.config.TemplateConfig;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;
import com.giants.xmlmapping.config.exception.XmlMapException;
import com.giants.xmlmapping.exception.XMLParseException;
import com.giants.xmlmapping.exception.XmlDataException;

/**
 * @author vencent.lu
 *
 */
public interface TemplateEngine {
	
	void initConfig(String basePath, String relativeBasePath,
			String... configFile) throws XmlMapException, XmlDataException,
			XMLParseException;
	
    Block compileTemplateBlock(Block templateBlock) throws TemplateAnalysisException;

	TemplateConfig getTemplateConfig();

	String getTemplateRelativePath();
	
	Template loadTemplate(String name) throws TemplateException;	

	Element createElement(String key, String content)
			throws TemplateAnalysisException;

	void removeTemplate(String name);

	Parameter createParameter(
			com.giants.decorator.config.element.Parameter paramConf,
			String paramValue, Element element)
			throws TemplateAnalysisException;
	
	void setProperty(String key, String value);
	
	String getProperty(String key);

}
