/**
 * 
 */
package com.giants.decorator.html.engine;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

import com.giants.decorator.config.TemplateConfig;
import com.giants.decorator.config.element.Widget;
import com.giants.decorator.core.Template;
import com.giants.decorator.core.engine.AbstractTemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.WidgetTemplateReadErrorException;
import com.giants.decorator.core.exception.WidgetTemplateUndefinedException;
import com.giants.decorator.html.HtmlTemplateEngine;
import com.giants.decorator.html.config.HtmlTemplateConfig;
import com.giants.decorator.html.template.HtmlContentTemplate;
import com.giants.xmlmapping.config.exception.XmlMapException;
import com.giants.xmlmapping.exception.XMLParseException;
import com.giants.xmlmapping.exception.XmlDataException;

/**
 * @author vencent.lu
 *
 */
public abstract class AbstractHtmlTemplateEngine extends AbstractTemplateEngine
		implements HtmlTemplateEngine {	

	public AbstractHtmlTemplateEngine() {
		super();
	}

	public AbstractHtmlTemplateEngine(String basePath, String relativeBasePath,
			String... configFile) throws XmlMapException, XmlDataException,
			XMLParseException {
		super(basePath, relativeBasePath, configFile);
	}
	
	@Override
	protected Class<?> getTemplateConfigClass() {
		return HtmlTemplateConfig.class;
	}

	@Override
	protected void addTemplateConfigItems(TemplateConfig destTemplateConfig,
			TemplateConfig origTemplateConfig) {
		super.addTemplateConfigItems(destTemplateConfig, origTemplateConfig);
		HtmlTemplateConfig dest = (HtmlTemplateConfig)destTemplateConfig;
		HtmlTemplateConfig orig = (HtmlTemplateConfig)origTemplateConfig;
		if (orig.getConversionRelativeURL() != null) {
			dest.setConversionRelativeURL(orig.getConversionRelativeURL());
		}
		if (orig.getUrlDomainName() != null) {
			dest.setUrlDomainName(orig.getUrlDomainName());
		}
		if (orig.getUrlVersion() != null) {
			dest.setUrlVersion(orig.getUrlVersion());
		}
	}

	@Override
	public Template loadWidgetTemplate(String name) throws TemplateException {
		if (this.getTemplateConfig().getWidget(name) == null) {
			throw new WidgetTemplateUndefinedException(name);
		}
		Template widgetTemplate = this.getTemplate(name);
		if (widgetTemplate == null) {
			Widget widget = this.getTemplateConfig().getWidget(name);
			if (widget.getWidgetFile().startsWith("classpath:")){				
				try {
					InputStream inputTemplate = AbstractTemplateEngine.class
							.getClassLoader().getResourceAsStream(
									widget.getWidgetFile().replace(
											"classpath:", ""));
					StringBuffer templateContentBf = new StringBuffer();
					byte[] buf = new byte[1024];
					while(inputTemplate.read(buf) != -1) {
						templateContentBf.append(new String(buf,"UTF-8"));
						buf = new byte[1024];
					}
					inputTemplate.close();
					widgetTemplate = new HtmlContentTemplate(this,name, templateContentBf.toString());
					this.putTemplate(name, widgetTemplate);
				} catch (IOException e) {
					this.logger.error(MessageFormat.format("Create \"{0}\" widget failed!", name),e);
					throw new WidgetTemplateReadErrorException(name, widget.getWidgetFile());
				}
			} else {
				return this.loadTemplate(widget.getWidgetFile());
			}
		}
		return widgetTemplate;
	}

}
