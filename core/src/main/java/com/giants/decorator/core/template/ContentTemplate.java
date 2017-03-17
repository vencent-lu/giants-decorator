/**
 * 
 */
package com.giants.decorator.core.template;

import com.giants.decorator.core.Block;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.block.TemplateBlock;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public class ContentTemplate extends AbstractContentTemplate {
    private static final long serialVersionUID = 8081436972516632280L;

    /**
	 * 
	 * @param templateEngine
	 * @param key
	 * @param content
	 * @throws TemplateAnalysisException
	 */
	public ContentTemplate(TemplateEngine templateEngine, String key,
			String content) throws TemplateAnalysisException {
	    super(templateEngine, key, content);
	}

	@Override
    public Block createTemplateBlock() throws TemplateException {
	    return new TemplateBlock(this.templateEngine, this.key, null, this.content);
    }
	

}
