/**
 * 
 */
package com.giants.decorator.html.engine.file;

import com.giants.decorator.html.Theme;
import com.giants.decorator.html.ThemeTemplateEngine;
import com.giants.xmlmapping.config.exception.XmlMapException;
import com.giants.xmlmapping.exception.XMLParseException;
import com.giants.xmlmapping.exception.XmlDataException;

/**
 * @author vencent.lu
 *
 */
public class StaticThemeHtmlFileTemplateEngine extends HtmlFileTemplateEngine
		implements ThemeTemplateEngine {
	
	private Theme theme;

	public StaticThemeHtmlFileTemplateEngine() {
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
	public StaticThemeHtmlFileTemplateEngine(String basePath, String relativeBasePath,
			String... configFile) throws XmlMapException, XmlDataException,
			XMLParseException {
		super(basePath, relativeBasePath, configFile);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.MultiThemeEngine#selectThemePath(java.lang.String)
	 */
	@Override
	public void selectTheme(Theme theme) {
		if (this.theme==null ||  !this.theme.equals(theme)) {
			this.theme = theme;
			this.prugeTemplates();
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.giants.decorator.core.engine.AbstractTemplateEngine#buildTemplateName(java.lang.String)
	 */
	@Override
	protected String buildTemplateName(String name) {
		if (this.theme == null) {
			return super.buildTemplateName(name);
		}
		return new StringBuffer(this.theme.getPath()).append('/').append(name)
				.toString();
	}

}
