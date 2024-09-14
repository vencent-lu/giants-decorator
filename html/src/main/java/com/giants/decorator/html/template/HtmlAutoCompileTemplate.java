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
import com.giants.decorator.core.template.AbstractAutoCompileTemplate;
import com.giants.decorator.core.template.TplEntity;
import com.giants.decorator.html.HtmlHelper;
import com.giants.decorator.html.HtmlTag;
import com.giants.decorator.html.block.HtmlAutoCompileTemplateBlock;
import com.giants.decorator.html.block.HtmlTemplateBlock;

/**
 * @author vencent.lu
 *
 */
public class HtmlAutoCompileTemplate extends AbstractAutoCompileTemplate implements
		HtmlTemplate {

	private static final long serialVersionUID = -394365137622821812L;
		
	/**
	 * 
	 * @param templateEngine template engine
	 * @param tplEntity tpl entity
	 * @throws TemplateException template exception
	 */
	public HtmlAutoCompileTemplate(TemplateEngine templateEngine,
			TplEntity tplEntity) throws TemplateException {
		super(templateEngine, tplEntity);
	}
	
    @Override
    public Block createTemplateBlock() throws TemplateException {
        return new HtmlAutoCompileTemplateBlock(this.templateEngine, tplEntity.getName(), null, tplEntity.loadContent());
    }

    /* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#getHeadTag()
	 */
	@Override
	public HtmlTag getHeadTag() throws TemplateException {
		this.autoCompile();
		return ((HtmlTemplateBlock)this.block).getHeadTag();
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#getHead()
	 */
	@Override
	public Element getHead() throws TemplateException {
		this.autoCompile();
		return ((HtmlTemplateBlock)this.block).getHead();
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#getBody()
	 */
	@Override
	public Element getBody() throws TemplateException {
		this.autoCompile();
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
