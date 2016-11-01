/**
 * 
 */
package com.giants.decorator.core.engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

/**
 * @author vencent.lu
 * 
 * Create Date:2016年7月27日
 *
 */
public class PropertiesFile implements Serializable {
	private static final long serialVersionUID = 8031916871948969306L;
	
	private File file;
	private long loadTime = -1;
	
	public PropertiesFile(File file) {
		super();
		this.file = file;
	}
	
	public Properties loadProperties() throws IOException {
		if (this.loadTime == this.file.lastModified()) {
			return null;
		}
		Properties properties = new Properties();
		InputStream inputStream = new FileInputStream(file);
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			throw e;
		} finally {
			inputStream.close();
		}
		return properties;
	}
	
	public String getFileName() {
		return this.file.getName();
	}

}
