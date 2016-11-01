/**
 * 
 */
package com.giants.decorator.html.template;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.giants.common.regex.Matcher;
import com.giants.decorator.common.DecoratorConstants;
import com.giants.decorator.core.Element;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;
import com.giants.decorator.core.template.ContentTemplate;
import com.giants.decorator.html.HtmlHelper;
import com.giants.decorator.html.HtmlTag;

/**
 * @author vencent.lu
 *
 */
public class HtmlContentTemplate extends ContentTemplate implements
		HtmlTemplate {

	private static final long serialVersionUID = -3648621329009228003L;
	
	private HtmlTag headTag;

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

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.block.AbstractBlock#analysis(java.lang.String)
	 */
	@Override
	public void analysis(String template) throws TemplateAnalysisException {
		template = this.analysisHtmlHead(template);
		template = this.analysisHtmlBody(template);
		super.analysis(template);
	}
	
	private String analysisHtmlHead(String template) throws TemplateAnalysisException {
		Matcher headMatcher = DecoratorConstants.TEMPLATE_HTML_HEAD_PATTERN
				.matcher(template);
		if (headMatcher.find()) {
			String headContent = headMatcher.group(1);
			template = template.replace(
					headContent,
					new StringBuffer("<!-- BEGIN tag : head -->\n").append(
							headContent).append("<!-- END tag -->\n"));
			this.headTag = new HtmlTag("head");
			Matcher tagMatcher = DecoratorConstants.TEMPLATE_HTML_TAG_PATTERN.matcher(headContent);
			while (tagMatcher.find()) {
				String tagName = tagMatcher.group(6);
				if (tagName == null) {
					tagName = tagMatcher.group(3);
				}
				String tagBody = tagMatcher.group(1);				
				String tagContent = tagMatcher.group(4);				
				HtmlTag tag = new HtmlTag(tagName);
				if (tagContent !=null && StringUtils.isNotEmpty(tagContent.trim())) {
					tag.setContent(this.templateEngine
							.createElement(
									new StringBuffer("tag:").append(tagName)
											.toString(), tagContent));
					tag.setTagName(new StringBuffer(tag.getTagName()).append("-body").toString());
				}
				Matcher attMatcher = DecoratorConstants.TEMPLATE_HTML_TAG_ATT_PATTERN.matcher(tagBody);
				while (attMatcher.find()) {
					String attName = attMatcher.group(1);
					String attValue = attMatcher.group(3);
					Matcher varMatcher = DecoratorConstants.TEMPLATE_VARIABLE_PATTERN
							.matcher(attValue);
					if (varMatcher.find()) {
						StringBuffer varStrBuff = new StringBuffer(varMatcher.group(1));
						tag.addAttribute(
								attName,
								this.templateEngine.createElement(
										varStrBuff.toString(), null));
					} else {
						tag.addAttribute(attName, this.templateEngine
								.createElement(
										new StringBuffer("\"").append(attValue)
												.append("\"").toString(), null));
					}
				}
				this.headTag.addChildrenTag(tag);
			}
		}
		return template;
	}
	
	private String analysisHtmlBody(String template) {
		Matcher bodyMatcher = DecoratorConstants.TEMPLATE_HTML_BODY_PATTERN
				.matcher(template);
		if (bodyMatcher.find()) {
			String bodyContent = bodyMatcher.group(1);
			template = template.replace(
					bodyContent,
					new StringBuffer("<!-- BEGIN tag : body -->\n").append(
							bodyContent).append("<!-- END tag -->\n"));
		}
		return template;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#getHeadTag()
	 */
	@Override
	public HtmlTag getHeadTag() throws TemplateException {
		return this.headTag;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#getHead()
	 */
	@Override
	public Element getHead() throws TemplateException {
		return this.getElement("tag:head");
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#getBody()
	 */
	@Override
	public Element getBody() throws TemplateException {
		return this.getElement("tag:body");
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
