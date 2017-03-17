/**
 * 
 */
package com.giants.decorator.core.template;

import java.text.MessageFormat;

import com.giants.decorator.core.TemplateEngine;

/**
 * @author vencent.lu
 *
 */
public abstract class AbstractContentTemplate extends AbstractTemplate {
    private static final long serialVersionUID = 6852595083987118283L;
    
    protected String key;
    protected String content;

    public AbstractContentTemplate(TemplateEngine templateEngine, String key, String content) {
        super(templateEngine);
        this.key = key;
        this.content = content;
        this.logger.info(MessageFormat.format(
                "Create \"{0}\" template success !", key));
    }

}
