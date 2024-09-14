/**
 * 
 */
package com.giants.decorator.core.template;

import com.giants.decorator.core.Block;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.block.TemplateBlock;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public class AutoCompileTemplate extends AbstractAutoCompileTemplate {

	private static final long serialVersionUID = 3109517161322501264L;
	
	/**
	 * 
	 * @param templateEngine template engine
	 * @param tplEntity tpl entity
	 * @throws TemplateException template exception
	 */
	public AutoCompileTemplate(TemplateEngine templateEngine,
			TplEntity tplEntity) throws TemplateException {
		super(templateEngine, tplEntity);
	}	

	@Override
    public Block createTemplateBlock() throws TemplateException {
        return new TemplateBlock(this.templateEngine, tplEntity.getName(), null, tplEntity.loadContent());
    }

}
