/**
 * 
 */
package com.giants.decorator.html.config.element;

import java.io.Serializable;
import java.util.List;

import com.giants.common.regex.Pattern;
import com.giants.xmlmapping.annotation.XmlAttribute;
import com.giants.xmlmapping.annotation.XmlEntity;
import com.giants.xmlmapping.annotation.XmlManyElement;

/**
 * @author vencent.lu
 *
 * Create Date:2014年5月26日
 */
@XmlEntity
public class UrlDomainNameRules implements Serializable {

	private static final long serialVersionUID = -9056464734092682130L;
	
	@XmlAttribute
	private String themePath;
	
	@XmlAttribute
	private String rules;
	
	private Pattern rulesPattern;
		
	@XmlManyElement
	private List<DomainName> domainNames;
		
	public String getThemePath() {
		return themePath;
	}

	public void setThemePath(String themePath) {
		this.themePath = themePath;
	}

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
		this.rulesPattern = Pattern.compile(this.rules);
	}	

	public Pattern getRulesPattern() {
		return rulesPattern;
	}

	public List<DomainName> getDomainNames() {
		return domainNames;
	}

	public void setDomainNames(List<DomainName> domainNames) {
		this.domainNames = domainNames;
	}

}
