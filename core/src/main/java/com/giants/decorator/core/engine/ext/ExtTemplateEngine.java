/**
 * 
 */
package com.giants.decorator.core.engine.ext;

import com.giants.decorator.config.TemplateConfig;
import com.giants.decorator.core.Template;
import com.giants.decorator.core.engine.AbstractTemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.TemplateNotFindException;
import com.giants.decorator.core.template.AutoCompileTemplate;
import com.giants.decorator.core.template.TplEntity;
import com.giants.xmlmapping.config.exception.XmlMapException;
import com.giants.xmlmapping.exception.XMLParseException;
import com.giants.xmlmapping.exception.XmlDataException;

/**
 * @author vencent.lu
 *
 */
public class ExtTemplateEngine extends AbstractTemplateEngine {
	
	private TplService tplService;

	public ExtTemplateEngine() {
		super();
	}

	/**
	 * @param basePath
	 * @param relativeBasePath
	 * @param configFile
	 * @throws XmlMapException
	 * @throws XmlDataException
	 * @throws XMLParseException
	 */
	public ExtTemplateEngine(String basePath, String relativeBasePath,
			String... configFile) throws XmlMapException, XmlDataException,
			XMLParseException {
		super(basePath, relativeBasePath, configFile);
	}
	
	@Override
	protected Class<?> getTemplateConfigClass() {
		return TemplateConfig.class;
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
		return new AutoCompileTemplate(this, tplEntity);
	}

	/**
	 * @param tplService the tplService to set
	 */
	public void setTplService(TplService tplService) {
		this.tplService = tplService;
	}
	
}
