/**
 * 
 */
package com.giants.decorator.html.template;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.giants.decorator.core.Block;
import com.giants.decorator.core.Element;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;
import com.giants.decorator.core.template.AbstractContentTemplate;
import com.giants.decorator.html.HtmlHelper;
import com.giants.decorator.html.HtmlTag;
import com.giants.decorator.html.block.HtmlContentTemplateBlock;
import com.giants.decorator.html.block.HtmlTemplateBlock;

/**
 * @author vencent.lu
 *
 */
public class HtmlContentTemplate extends AbstractContentTemplate implements
		HtmlTemplate {
    private static final long serialVersionUID = -2869810500324861881L;

    /**
	 * 
	 * @param templateEngine
	 * @param key
	 * @param content
	 * @throws TemplateAnalysisException
	 */
	public HtmlContentTemplate(TemplateEngine templateEngine, String key,
			String content) throws TemplateAnalysisException {
		super(templateEngine, key, content);
	}
	
	

    @Override
    public Block createTemplateBlock() throws TemplateException {
        return new HtmlContentTemplateBlock(this.templateEngine, this.key, null, this.content);
    }

    /* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#getHeadTag()
	 */
	@Override
	public HtmlTag getHeadTag() throws TemplateException {
		return ((HtmlTemplateBlock)this.block).getHeadTag();
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#getHead()
	 */
	@Override
	public Element getHead() throws TemplateException {
		return ((HtmlTemplateBlock)this.block).getHead();
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#getBody()
	 */
	@Override
	public Element getBody() throws TemplateException {
		return ((HtmlTemplateBlock)this.block).getBody();
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#execute(javax.servlet.http.HttpServletRequest, java.lang.Object)
	 */
	@Override
	public String execute(HttpServletRequest request, Object dataObj)
			throws TemplateException {
		Map<String, Object> globalVarMap = new HashMap<String, Object>();
		HtmlHelper.addRequestToGlobalVarMap(request, globalVarMap);
		return this.execute(globalVarMap, dataObj);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#execute(javax.servlet.http.HttpServletRequest, java.util.Map, java.lang.Object)
	 */
	@Override
	public String execute(HttpServletRequest request,
			Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException {
		HtmlHelper.addRequestToGlobalVarMap(request, globalVarMap);
		return this.execute(globalVarMap, dataObj);
	}

}
