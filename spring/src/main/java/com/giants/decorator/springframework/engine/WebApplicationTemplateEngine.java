/**
 * 
 */
package com.giants.decorator.springframework.engine;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.context.WebApplicationContext;

import com.giants.decorator.config.TemplateConfig;
import com.giants.decorator.core.Block;
import com.giants.decorator.core.Element;
import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.Template;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;
import com.giants.decorator.html.HtmlTemplateEngine;
import com.giants.xmlmapping.config.exception.XmlMapException;
import com.giants.xmlmapping.exception.XMLParseException;
import com.giants.xmlmapping.exception.XmlDataException;

/**
 * @author vencent.lu
 *
 */
public class WebApplicationTemplateEngine implements HtmlTemplateEngine,
		ApplicationListener<ApplicationEvent> {
	
	private String basePath;
	private String[] configLocations;
	
	private HtmlTemplateEngine htmlTemplateEngine;
	
	@Override
	public void initConfig(String basePath, String relativeBasePath,
			String... configFile) throws XmlMapException, XmlDataException,
			XMLParseException {
		this.htmlTemplateEngine.initConfig(basePath, relativeBasePath, configFile);
	}

	@Override
	public TemplateConfig getTemplateConfig() {
		return this.htmlTemplateEngine.getTemplateConfig();
	}

	@Override
	public String getTemplateRelativePath() {
		return this.htmlTemplateEngine.getTemplateRelativePath();
	}

	@Override
	public Template loadTemplate(String name) throws TemplateException {
		return this.htmlTemplateEngine.loadTemplate(name);
	}

	@Override
	public Template loadWidgetTemplate(String name) throws TemplateException {
		return this.htmlTemplateEngine.loadWidgetTemplate(name);
	}

	@Override
	public Element createElement(String key, String content)
			throws TemplateAnalysisException {
		return this.htmlTemplateEngine.createElement(key, content);
	}

	@Override
	public void removeTemplate(String name) {
		this.htmlTemplateEngine.removeTemplate(name);
	}

	@Override
	public Parameter createParameter(
			com.giants.decorator.config.element.Parameter paramConf,
			String paramValue, Element element)
			throws TemplateAnalysisException {
		return this.htmlTemplateEngine.createParameter(paramConf, paramValue, element);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof ContextRefreshedEvent) {
			ContextRefreshedEvent refreshed = (ContextRefreshedEvent) event;
			WebApplicationContext webApplicationContext = (WebApplicationContext) refreshed
					.getApplicationContext();
			try {
				this.initConfig(webApplicationContext
						.getServletContext().getRealPath(""), this.basePath,
						 this.configLocations);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param basePath the basePath to set
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * @param configLocations the configLocations to set
	 */
	public void setConfigLocations(String[] configLocations) {
		this.configLocations = configLocations;
	}
	
	/**
	 * @param configLocation the configLocation to set
	 */
	public void setConfigLocation(String configLocation) {
		this.configLocations = new String[]{configLocation};
	}

	/**
	 * @return the htmlTemplateEngine
	 */
	public HtmlTemplateEngine getHtmlTemplateEngine() {
		return htmlTemplateEngine;
	}

	/**
	 * @param htmlTemplateEngine the htmlTemplateEngine to set
	 */
	public void setHtmlTemplateEngine(HtmlTemplateEngine htmlTemplateEngine) {
		this.htmlTemplateEngine = htmlTemplateEngine;
	}

	@Override
	public void setProperty(String key, String value) {
		this.htmlTemplateEngine.setProperty(key, value);
	}

	@Override
	public String getProperty(String key) {
		return this.htmlTemplateEngine.getProperty(key);
	}

    @Override
    public Block compileTemplateBlock(Block templateBlock) throws TemplateAnalysisException {
        return this.htmlTemplateEngine.compileTemplateBlock(templateBlock);
    }
	
}
