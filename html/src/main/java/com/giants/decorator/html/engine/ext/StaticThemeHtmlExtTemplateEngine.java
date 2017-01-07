/**
 * 
 */
package com.giants.decorator.html.engine.ext;

import com.giants.decorator.html.Theme;
import com.giants.decorator.html.ThemeTemplateEngine;
import com.giants.xmlmapping.config.exception.XmlMapException;
import com.giants.xmlmapping.exception.XMLParseException;
import com.giants.xmlmapping.exception.XmlDataException;

/**
 * @author vencent.lu
 *
 */
public class StaticThemeHtmlExtTemplateEngine extends HtmlExtTemplateEngine
		implements ThemeTemplateEngine {
	
	private Theme theme;

	public StaticThemeHtmlExtTemplateEngine() {
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
	public StaticThemeHtmlExtTemplateEngine(String basePath,
			String relativeBasePath, String... configFile)
			throws XmlMapException, XmlDataException, XMLParseException {
		super(basePath, relativeBasePath, configFile);
	}

	
	@Override
	public void selectTheme(Theme theme) {
		if (this.theme == null || !this.theme.equals(theme)) {
			this.theme = theme;
			this.prugeTemplates();
		}
	}
	
	@Override
	public Theme getCurrentTheme() {
		return this.theme;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.engine.AbstractTemplateEngine#buildTemplateName(java.lang.String)
	 */
	@Override
	protected String buildTemplateName(String name) {
		name = super.buildTemplateName(name);
		if (this.theme == null) {
			return name;
		}
		return new StringBuffer(this.theme.getPath()).append('/').append(name)
				.toString();
	}
	
	

}
