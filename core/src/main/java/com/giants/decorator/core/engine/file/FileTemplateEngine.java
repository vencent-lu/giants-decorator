/**
 * 
 */
package com.giants.decorator.core.engine.file;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import com.giants.decorator.config.TemplateConfig;
import com.giants.decorator.core.Template;
import com.giants.decorator.core.engine.AbstractTemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.TemplateFileNotFindException;
import com.giants.decorator.core.template.AutoCompileTemplate;
import com.giants.xmlmapping.config.exception.XmlMapException;
import com.giants.xmlmapping.exception.XMLParseException;
import com.giants.xmlmapping.exception.XmlDataException;

/**
 * @author vencent.lu
 *
 */
public class FileTemplateEngine extends AbstractTemplateEngine {
	
	public FileTemplateEngine() {
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
	public FileTemplateEngine(String basePath, String relativeBasePath,
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
		File file = null;
		try {
			file = new File(new StringBuffer(this.templateBasePath).append("/")
					.append(name).toString());
			return new AutoCompileTemplate(this, new FileTpl(name, file));
		} catch (Exception e) {
			this.logger.error(MessageFormat.format("Create \"{0}\" template failed!", name),e);
			if (e instanceof InvocationTargetException) {
				throw (TemplateException) ((InvocationTargetException) e)
						.getTargetException();
			} else {
				throw new TemplateFileNotFindException(name, file, e);
			}
		}
	}

}
