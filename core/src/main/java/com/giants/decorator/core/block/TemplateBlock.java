/**
 * 
 */
package com.giants.decorator.core.block;

import java.util.Map;

import com.giants.decorator.config.element.Block;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public class TemplateBlock extends AbstractBlock {
    
    private static final long serialVersionUID = 6752362696389927750L;

    public TemplateBlock(TemplateEngine templateEngine, String key, Block blockConf, String blockTemplate)
            throws TemplateAnalysisException {
        super(templateEngine, key, blockConf, blockTemplate, false);
    }

    /* (non-Javadoc)
     * @see com.giants.decorator.core.block.AbstractBlock#parseOperateObject(java.util.Map, java.lang.Object)
     */
    @Override
    protected Object parseOperateObject(Map<String, Object> globalVarMap, Object dataObj) throws TemplateException {
        return dataObj;
    }

    /* (non-Javadoc)
     * @see com.giants.decorator.core.block.AbstractBlock#parseBlock(java.util.Map, java.lang.Object, java.lang.Object)
     */
    @Override
    protected String parseBlock(Map<String, Object> globalVarMap, Object dataObj, Object blockObj)
            throws TemplateException {
        return this.parseSingleton(globalVarMap, dataObj, blockObj);
    }

}
