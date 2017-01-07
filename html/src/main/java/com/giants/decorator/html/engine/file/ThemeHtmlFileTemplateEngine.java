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
public class ThemeHtmlFileTemplateEngine extends HtmlFileTemplateEngine
		implements ThemeTemplateEngine {
	
	private static final ThreadLocal<Theme> themeLocal = new ThreadLocal<Theme>();

	public ThemeHtmlFileTemplateEngine() {
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
	public ThemeHtmlFileTemplateEngine(String basePath,
			String relativeBasePath, String... configFile)
			throws XmlMapException, XmlDataException, XMLParseException {
		super(basePath, relativeBasePath, configFile);
	}

	@Override
	public void selectTheme(Theme theme) {
		themeLocal.set(theme);
	}

	@Override
	public Theme getCurrentTheme() {
		return themeLocal.get();
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.engine.AbstractTemplateEngine#buildTemplateName(java.lang.String)
	 */
	@Override
	protected String buildTemplateName(String name) {
		Theme theme = themeLocal.get();
		if (theme == null) {
			return super.buildTemplateName(name);
		}
		return new StringBuffer(theme.getPath()).append('/').append(name)
				.toString();
	}

}
