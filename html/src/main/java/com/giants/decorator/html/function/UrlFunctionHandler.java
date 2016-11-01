/**
 * 
 */
package com.giants.decorator.html.function;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.giants.decorator.config.element.DomainName;
import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.function.FunctionHandler;
import com.giants.decorator.html.config.HtmlTemplateConfig;
import com.giants.decorator.html.config.element.UrlDomainNameRules;
import com.giants.decorator.html.config.element.UrlVersionRules;

/**
 * @author vencent.lu
 *
 */
public class UrlFunctionHandler implements FunctionHandler {
			
	private String getDomainName(TemplateEngine templateEngine, String url) {
		HtmlTemplateConfig htmlTemplateConfig = (HtmlTemplateConfig)templateEngine.getTemplateConfig();
		if (htmlTemplateConfig.getUrlDomainName() == null
				|| !htmlTemplateConfig.getUrlDomainName()
						.getWhetherAddDomainName()) {
			return null;
		}
		if (htmlTemplateConfig.getUrlDomainName()
				.getRulesPattern().matches(url)) {
			for (UrlDomainNameRules urlDomainNameRules : htmlTemplateConfig.getUrlDomainName()
					.getUrlDomainNameRules()) {
				if (urlDomainNameRules.getRulesPattern().matches(url)) {
					List<DomainName> domainNames = urlDomainNameRules
							.getDomainNames();
					if (CollectionUtils.isEmpty(domainNames)) {
						return null;
					} else if (domainNames.size() == 1) {
						return domainNames.get(0).getValue();
					} else {
						return domainNames.get(
								Math.abs(url.hashCode() % domainNames.size()))
								.getValue();
					}
				}
			}
		}
		return null;
	}
	
	private String addVersion(TemplateEngine templateEngine, String url) {
		HtmlTemplateConfig htmlTemplateConfig = (HtmlTemplateConfig)templateEngine.getTemplateConfig();
		if (htmlTemplateConfig.getUrlVersion() == null
				|| !htmlTemplateConfig.getUrlVersion()
						.isWhetherAddVersion()) {
			return url;
		}
		if (htmlTemplateConfig.getUrlVersion().getRulesPattern() == null
				|| htmlTemplateConfig.getUrlVersion().getRulesPattern()
						.matches(url)) {
			for (UrlVersionRules urlVersionRules : htmlTemplateConfig.getUrlVersion().getUrlVersionRules()) {
				if (urlVersionRules.getRulesPattern() == null || urlVersionRules.getRulesPattern().matches(url)) {
					String version = templateEngine.getProperty(urlVersionRules.getPropertyName());
					if (StringUtils.isNotEmpty(version)) {
						return new StringBuilder(url)
								.append(url.indexOf('?') == -1 ? '?' : '&')
								.append(htmlTemplateConfig.getUrlVersion()
										.getParamName()).append('=')
								.append(version).toString();
					} else {
						return url;
					}
				}
			}
		}
		return url;
	}
	
	protected String getFilePath(Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
		return (String)parameters.get(1).parse(globalVarMap, dataObj);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.function.FunctionHandler#execute(java.util.Map, java.lang.Object, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object execute(TemplateEngine templateEngine,
			Map<String, Object> globalVarMap, Object dataObj,
			List<Parameter> parameters) throws TemplateException {
				
		String url = (String)parameters.get(0).parse(globalVarMap, dataObj);
		String filePath = this.getFilePath(globalVarMap, dataObj, parameters);
		String servletPath = (String)(((Map<String, Object>)globalVarMap.get("request")).get("servletPath"));
		if (url == null) {
			url = "";
		}
		if (url.startsWith("http")) {
			return url;
		}
		url.replace("\\", "/");
		if (url.startsWith("./")) {
			url = url.replaceFirst("\\.\\/", "");
		} else if (url.startsWith("/")) {
			url = url.replaceFirst("\\/", "");
		}
		
		if (url.length()!=0) {
			url = this.addVersion(templateEngine, url);
		}
		
		StringBuilder urlSb = new StringBuilder();		
		if (filePath != null) {
			if (filePath.startsWith("/")) {
				filePath = filePath.replaceFirst("\\/", "");
			}
			String[] files = filePath.split("\\/");
			int urlJumpLevel = StringUtils.countMatches(url, "../");
			if (urlJumpLevel < files.length) {
				if (StringUtils.isNotEmpty(templateEngine
						.getTemplateRelativePath())) {
					urlSb.append(templateEngine.getTemplateRelativePath());
				}
				for (int i = 0; i < files.length - 1 - urlJumpLevel; i++) {
					urlSb.append(files[i]).append("/");
				}
			} else if (StringUtils.isNotEmpty(templateEngine
					.getTemplateRelativePath())) {
				String[] templatePath = templateEngine
						.getTemplateRelativePath().split("\\/");
				for (int i = 0; i < templatePath.length - urlJumpLevel
						+ files.length - 1; i++) {
					urlSb.append(templatePath[i]).append("/");
				}
			}
		}
		
		urlSb.append(url.replace("../", ""));
		
		String domainName = this.getDomainName(templateEngine, urlSb.toString());
		if (domainName!=null) {
			return new StringBuffer("http://").append(domainName).append('/').append(urlSb);
		} else {
			StringBuffer returnUrlSb = new StringBuffer();
			if (servletPath.startsWith("/")) {
				servletPath = servletPath.replaceFirst("\\/", "");
			}		
			int servletLevel = StringUtils.countMatches(servletPath, "/");
			for (int i = 0; i < servletLevel; i++) {
				returnUrlSb.append("../");
			}
			returnUrlSb.append(urlSb);
			return returnUrlSb;
		}
	}

}
