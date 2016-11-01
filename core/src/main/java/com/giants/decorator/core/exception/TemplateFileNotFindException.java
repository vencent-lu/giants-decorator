/**
 * 
 */
package com.giants.decorator.core.exception;

import java.io.File;
import java.text.MessageFormat;

/**
 * @author vencent.lu
 *
 */
public class TemplateFileNotFindException extends TemplateException {

	private static final long serialVersionUID = 9112775834014550223L;
	
	private File file;
	
	/**
	 * 
	 * @param name
	 * @param file
	 */
	public TemplateFileNotFindException(String name, File file) {
		super(name, file.getName(), MessageFormat.format(
				"Can not find template file \"{0}\"", file.getName()));
		this.file = file;
	}

	/**
	 * @param key
	 * @param content
	 * @param e
	 */
	public TemplateFileNotFindException(String name, File file, Throwable e) {
		super(name, file.getName(), MessageFormat.format(
				"Can not find template file \"{0}\"", file.getName()), e);
		this.file = file;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

}
