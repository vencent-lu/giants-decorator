/**
 * 
 */
package com.giants.decorator.html.engine.ext;

import com.giants.decorator.core.Template;
import com.giants.decorator.core.engine.ext.TplService;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.TemplateNotFindException;
import com.giants.decorator.core.template.TplEntity;
import com.giants.decorator.html.engine.AbstractHtmlTemplateEngine;
import com.giants.decorator.html.template.HtmlAutoCompileTemplate;
import com.giants.xmlmapping.config.exception.XmlMapException;
import com.giants.xmlmapping.exception.XMLParseException;
import com.giants.xmlmapping.exception.XmlDataException;

/**
 * @author vencent.lu
 *
 */
public class HtmlExtTemplateEngine extends AbstractHtmlTemplateEngine {
	
	private TplService tplService;

	public HtmlExtTemplateEngine() {
		super();
	}

	/**
	 * @param basePath base path
	 * @param relativeBasePath relative base path
	 * @param configFile config file
	 * @throws XmlMapException xml map exception
	 * @throws XmlDataException xml data exception
	 * @throws XMLParseException xml parse exception
	 */
	public HtmlExtTemplateEngine(String basePath, String relativeBasePath,
			String... configFile) throws XmlMapException, XmlDataException,
			XMLParseException {
		super(basePath, relativeBasePath, configFile);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.engine.AbstractTemplateEngine#createTemplate(java.lang.String)
	 */
	@Override
	protected Template createTemplate(String name) throws TemplateException {
		TplEntity tplEntity = this.tplService.buildTplEntity(name);
		if (tplEntity == null) {
			throw new TemplateNotFindException(name);
		}
		return new HtmlAutoCompileTemplate(this, tplEntity);
	}
	
	/**
	 * @param tplService the tplService to set
	 */
	public void setTplService(TplService tplService) {
		this.tplService = tplService;
	}

}
