package com.giants.decorator.html.config;

import com.giants.decorator.config.TemplateConfig;
import com.giants.decorator.html.config.element.UrlDomainName;
import com.giants.decorator.html.config.element.UrlVersion;
import com.giants.xmlmapping.annotation.XmlAttribute;
import com.giants.xmlmapping.annotation.XmlElement;
import com.giants.xmlmapping.annotation.XmlEntity;

/**
 * 
 * @author vencent.lu
 *
 */
@XmlEntity(name="templateConfig")
public class HtmlTemplateConfig extends TemplateConfig {
	private static final long serialVersionUID = -397744954670528336L;
	
	@XmlAttribute
	private Boolean conversionRelativeURL;
		
	@XmlElement
	private UrlDomainName urlDomainName;
	
	@XmlElement
	private UrlVersion urlVersion;

	public Boolean getConversionRelativeURL() {
		return conversionRelativeURL;
	}

	public void setConversionRelativeURL(Boolean conversionRelativeURL) {
		this.conversionRelativeURL = conversionRelativeURL;
	}

	public UrlDomainName getUrlDomainName() {
		return urlDomainName;
	}

	public void setUrlDomainName(UrlDomainName urlDomainName) {
		this.urlDomainName = urlDomainName;
	}

	public UrlVersion getUrlVersion() {
		return urlVersion;
	}

	public void setUrlVersion(UrlVersion urlVersion) {
		this.urlVersion = urlVersion;
	}	

}
