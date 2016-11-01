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
import com.giants.decorator.core.template.AutoCompileTemplate;
import com.giants.decorator.core.template.TplEntity;
import com.giants.decorator.html.HtmlHelper;
import com.giants.decorator.html.HtmlTag;
import com.giants.decorator.html.config.HtmlTemplateConfig;

/**
 * @author vencent.lu
 *
 */
public class HtmlAutoCompileTemplate extends AutoCompileTemplate implements
		HtmlTemplate {

	private static final long serialVersionUID = -394365137622821812L;
	
	private HtmlTag headTag;
	
	/**
	 * 
	 * @param templateEngine
	 * @param tplEntity
	 * @throws TemplateException
	 */
	public HtmlAutoCompileTemplate(TemplateEngine templateEngine,
			TplEntity tplEntity) throws TemplateException {
		super(templateEngine, tplEntity);
	}
	
	private class FindHeadBody {
		private boolean find = false;

		private boolean isFind() {
			return find;
		}

		private void setFind(boolean find) {
			this.find = find;
		}
		
	}
	
	/* (non-Javadoc)
	 * @see com.giants.decorator.core.block.AbstractBlock#analysis(java.lang.String)
	 */
	@Override
	public void analysis(String template) throws TemplateAnalysisException {
		FindHeadBody find = new FindHeadBody();
		template = this.analysisHtmlHead(template, find);
		template = this.analysisHtmlBody(template, find);
		if (!find.isFind()) {
			if (StringUtils.isNotEmpty(this.templateEngine
					.getTemplateRelativePath())
					&& ((HtmlTemplateConfig) this.templateEngine
							.getTemplateConfig()).getConversionRelativeURL()) {
				template = this.addUrlFunction(template);
			}
		}
		super.analysis(template);
	}
	
	private String analysisHtmlHead(String template, FindHeadBody find) throws TemplateAnalysisException {
		Matcher headMatcher = DecoratorConstants.TEMPLATE_HTML_HEAD_PATTERN
				.matcher(template);
		Boolean conversionRelativeURL = ((HtmlTemplateConfig) this.templateEngine
				.getTemplateConfig()).getConversionRelativeURL();
		if (headMatcher.find()) {
			find.setFind(true);
			String headContent = headMatcher.group(1);
			if (StringUtils.isNotEmpty(this.templateEngine
					.getTemplateRelativePath())
					&& conversionRelativeURL) {
				template = template.replace(
						headContent,
						new StringBuffer("<!-- BEGIN tag : head -->\n").append(
								this.addUrlFunction(headContent)).append(
								"<!-- END tag -->\n"));
			} else {
				template = template.replace(headContent, new StringBuffer(
						"<!-- BEGIN tag : head -->\n").append(headContent)
						.append("<!-- END tag -->\n"));
			}
			this.headTag = new HtmlTag("head");
			Matcher tagMatcher = DecoratorConstants.TEMPLATE_HTML_TAG_PATTERN.matcher(headContent);
			while (tagMatcher.find()) {
				String tagName = tagMatcher.group(6);
				if (tagName == null) {
					tagName = tagMatcher.group(3);
				}
				String tagBody = tagMatcher.group(1);
				String tagContent = tagMatcher.group(4);
				if (tagContent !=null) {
					tagBody = tagBody.replace(tagContent, "");
				}
				if (StringUtils.isNotEmpty(this.templateEngine.getTemplateRelativePath())
						&& conversionRelativeURL) {
					tagBody = this.addUrlFunctionOfHeadTag(tagBody);
				}								
				HtmlTag tag = new HtmlTag(tagName);
				if (tagContent !=null && StringUtils.isNotEmpty(tagContent.trim())) {
					if (StringUtils.isNotEmpty(this.templateEngine.getTemplateRelativePath()) 
							&& conversionRelativeURL) {
						tagContent = this.addUrlFunction(tagContent);
					}
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
	
	private String analysisHtmlBody(String template, FindHeadBody find) {
		Matcher bodyMatcher = DecoratorConstants.TEMPLATE_HTML_BODY_PATTERN
				.matcher(template);
		if (bodyMatcher.find()) {
			find.setFind(true);
			String bodyContent = bodyMatcher.group(1);
			if (StringUtils.isNotEmpty(this.templateEngine.getTemplateRelativePath()) 
					&& ((HtmlTemplateConfig) this.templateEngine
							.getTemplateConfig()).getConversionRelativeURL()) {
				template = template.replace(
						bodyContent,
						new StringBuffer("<!-- BEGIN tag : body -->\n").append(
								this.addUrlFunction(bodyContent)).append(
								"<!-- END tag -->\n"));
			} else {
				template = template.replace(bodyContent, new StringBuffer(
						"<!-- BEGIN tag : body -->\n").append(bodyContent)
						.append("<!-- END tag -->\n"));
			}
		}
		return template;
	}
	
	private String addUrlFunction(String htmlContent) {
		Matcher urlMatcher = DecoratorConstants.TEMPLATE_HTML_URL_PATTERN.matcher(htmlContent);
		Matcher styleUrlMatcher = DecoratorConstants.TEMPLATE_HTML_STYLE_URL_PATTERN.matcher(htmlContent);
		while (urlMatcher.find()) {
			String patternUrl = urlMatcher.group();
			String url = urlMatcher.group(2);
			char quotes = patternUrl.charAt(0);
			StringBuffer urlFunBf = new StringBuffer();
			urlFunBf.append(quotes).append("\\${url('&temp&").append(url).append("','")
					.append(this.getKey()).append("')}").append(quotes);
			htmlContent = htmlContent.replaceFirst(
					this.conversionUrlRegex(patternUrl), urlFunBf.toString());
		}
		while (styleUrlMatcher.find()) {
			String url = styleUrlMatcher.group(1);
			StringBuffer urlFunBf = new StringBuffer();
			urlFunBf.append("url(").append("\\${url('&temp&").append(url).append("','")
					.append(this.getKey()).append("')}").append(")");
			htmlContent = htmlContent.replaceFirst(
					this.conversionUrlRegex(styleUrlMatcher.group()),
					urlFunBf.toString());
		}
		return htmlContent.replace("&temp&", "");
	}
	
	private String addUrlFunctionOfHeadTag(String htmlTagBody) {
		Matcher urlMatcher = DecoratorConstants.TEMPLATE_HTML_URL_PATTERN.matcher(htmlTagBody);		
		while (urlMatcher.find()) {
			String patternUrl = urlMatcher.group();
			String url = urlMatcher.group(2);
			char quotes = patternUrl.charAt(0);
			StringBuffer urlFunBf = new StringBuffer();
			urlFunBf.append(quotes).append("\\${url('&temp&").append(url).append("','")
					.append(this.getKey()).append("')}").append(quotes);
			htmlTagBody = htmlTagBody.replaceFirst(
					this.conversionUrlRegex(patternUrl), urlFunBf.toString());
		}
		return htmlTagBody.replace("&temp&", "");
	}
	
	private String conversionUrlRegex(String patternUrl) {
		return patternUrl.replace(".", "\\.").replace("/", "\\/")
				.replace("(", "\\(").replace(")", "\\)").replace("-", "\\-");
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#getHeadTag()
	 */
	@Override
	public HtmlTag getHeadTag() throws TemplateException {
		this.autoCompile();
		return this.headTag;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#getHead()
	 */
	@Override
	public Element getHead() throws TemplateException {
		this.autoCompile();
		return this.getElement("tag:head");
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.html.HtmlTemplate#getBody()
	 */
	@Override
	public Element getBody() throws TemplateException {
		this.autoCompile();
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
