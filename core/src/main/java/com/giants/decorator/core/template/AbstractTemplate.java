/**
 * 
 */
package com.giants.decorator.core.template;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giants.decorator.core.Block;
import com.giants.decorator.core.Template;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public abstract class AbstractTemplate implements Template {
    private static final long serialVersionUID = 505489256435811891L;

    protected final Logger   logger = LoggerFactory.getLogger(this.getClass());

    protected TemplateEngine templateEngine;
    
    protected Block block;
	protected long compileTime = 0;	

    /**
	 * 
	 * @param templateEngine template engine
	 */
	public AbstractTemplate(TemplateEngine templateEngine) {		
		this.templateEngine = templateEngine;
	}
	
	@Override
    public String getKey() {
        return this.block.getKey();
    }

    @Override
    public String getContent() {
        return this.block.getContent();
    }
    
	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Template#execute(java.util.Map, java.lang.Object)
	 */
	@Override
	public String execute(Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException {
		return this.block.parse(globalVarMap, dataObj);
	}
	
	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Template#execute(java.lang.Object)
	 */
	@Override
	public String execute(Object dataObj) throws TemplateException {
		return this.execute(null, dataObj);
	}

    @Override
    public void compile() throws TemplateException {
        Block templateBlock = this.createTemplateBlock();
        try {
            this.block = this.templateEngine.compileTemplateBlock(templateBlock);
        } catch (Exception e) {
            this.logger.error(MessageFormat.format("\"{0}\" template compile failure !", templateBlock.getKey()));
            if (e instanceof RejectedExecutionException) {
                e.printStackTrace();
            } else {
                throw e;
            }            
        }
    }
	
	

}
