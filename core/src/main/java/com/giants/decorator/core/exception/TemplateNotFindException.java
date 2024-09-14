/**
 * 
 */
package com.giants.decorator.core.exception;

import java.text.MessageFormat;

/**
 * @author vencent.lu
 *
 */
public class TemplateNotFindException extends TemplateException {

	private static final long serialVersionUID = 4170342118021609913L;

	/**
	 * @param name name
	 */
	public TemplateNotFindException(String name) {
		super(name, null, MessageFormat.format(
				"Can not find template \"{0}\"", name));
	}

}
