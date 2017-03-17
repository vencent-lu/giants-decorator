/**
 * 
 */
package com.giants.decorator.html.block;

import com.giants.decorator.config.element.Block;
import com.giants.decorator.core.Element;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.block.TemplateBlock;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;
import com.giants.decorator.html.HtmlTag;

/**
 * @author vencent.lu
 *
 */
public abstract class HtmlTemplateBlock extends TemplateBlock {
    private static final long serialVersionUID = -1706346141871302752L;
    
    protected HtmlTag headTag;

    public HtmlTemplateBlock(TemplateEngine templateEngine, String key, Block blockConf, String blockTemplate)
            throws TemplateAnalysisException {
        super(templateEngine, key, blockConf, blockTemplate);
    }

    
        
    public HtmlTag getHeadTag() {
        return headTag;
    }
    
    public Element getHead() throws TemplateException {
        return this.getElement("tag:head");
    }
    
    public Element getBody() throws TemplateException {
        return this.getElement("tag:body");
    }
    
    

}
