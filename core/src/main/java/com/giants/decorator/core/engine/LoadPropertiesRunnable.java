/**
 * 
 */
package com.giants.decorator.core.engine;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giants.decorator.core.TemplateEngine;

/**
 * @author vencent.lu
 * 
 * Create Date:2016年7月27日
 *
 */
public class LoadPropertiesRunnable implements Runnable {
	
	protected final Logger   logger = LoggerFactory.getLogger(this.getClass());
	
	private TemplateEngine templateEngine;
	private Collection<PropertiesFile> propertiesFiles;
	
	public LoadPropertiesRunnable(TemplateEngine templateEngine,
			Collection<PropertiesFile> propertiesFiles) {
		super();
		this.templateEngine = templateEngine;
		this.propertiesFiles = propertiesFiles;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(true) {
			for (PropertiesFile propertiesFile : propertiesFiles) {
				try {
					Properties properties = propertiesFile.loadProperties();
					if (properties != null) {
						Set<Entry<Object, Object>> entries = properties.entrySet();
						for (Entry<Object, Object> entry : entries) {
							this.templateEngine.setProperty((String)entry.getKey(), (String)entry.getValue());
						}
					}
				} catch (IOException e) {
					this.logger
							.error(MessageFormat
									.format("Load Properties failure, propertiesFileName:{0} !",
											propertiesFile.getFileName()), e);
				}
			}
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				this.logger.error("LoadPropertiesRunnable run error!", e);
			}
		}
	}

}
