/**
 * 
 */
package com.giants.decorator.core.template;

import java.text.MessageFormat;
import java.util.Map;

import com.giants.decorator.core.Block;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public abstract class AbstractAutoCompileTemplate extends AbstractTemplate {
    private static final long serialVersionUID = -1573868393224231198L;
    
    protected TplEntity tplEntity;

    public AbstractAutoCompileTemplate(TemplateEngine templateEngine, TplEntity tplEntity) {
        super(templateEngine);
        this.tplEntity = tplEntity;
        this.logger.info(MessageFormat.format("Create \"{0}\" template success !", tplEntity.getName()));
    }
    
    /* (non-Javadoc)
     * @see com.giants.decorator.core.Template#execute(java.util.Map, java.lang.Object)
     */
    @Override
    public String execute(Map<String, Object> globalVarMap, Object dataObj)
            throws TemplateException {
        this.autoCompile();
        return this.block.parse(globalVarMap, dataObj);
    }
    
    /* (non-Javadoc)
     * @see com.giants.decorator.core.Template#execute(java.lang.Object)
     */
    @Override
    public String execute(Object dataObj) throws TemplateException {
        return this.execute(null, dataObj);
    }

    protected void autoCompile() throws TemplateException {
        if (this.getModifyTime() != this.compileTime) {
            this.compile();
        }
    }
    
    public long getModifyTime() {
        return this.tplEntity.lastModifiedTime();
    }

    /* (non-Javadoc)
     * @see com.giants.decorator.core.Template#compile()
     */
    @Override
    public void compile() throws TemplateException {
        long modifyTime = this.getModifyTime();
        synchronized (this) {
            if (modifyTime != this.compileTime) {
                Block oldBlock = this.block;
                this.block = null;
                super.compile();
                if (this.block != null) {
                    this.compileTime = modifyTime;
                } else {
                    this.block = oldBlock;
                }                
            }
        }
    }

}
