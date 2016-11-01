/**
 * 
 */
package com.giants.decorator.html;

import com.giants.decorator.core.Template;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public interface HtmlTemplateEngine extends TemplateEngine {
	
	Template loadWidgetTemplate(String name) throws TemplateException;

}
