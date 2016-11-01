/**
 * 
 */
package com.giants.decorator.core.template;

import java.io.Serializable;

import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public interface TplEntity extends Serializable {
	
	String getName();
	
	String loadContent() throws TemplateException;
	
	long lastModifiedTime();
	
}
