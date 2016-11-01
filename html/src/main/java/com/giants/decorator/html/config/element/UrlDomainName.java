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
 * Create Date:2014年5月27日
 */
@XmlEntity
public class UrlDomainName implements Serializable {

	private static final long serialVersionUID = -9056464734092682130L;
	
	@XmlAttribute
	private Boolean whetherAddDomainName;
	
	@XmlAttribute
	private String rules;
	
	private Pattern rulesPattern;
	
	@XmlManyElement
	private List<UrlDomainNameRules> urlDomainNameRules;

	/**
	 * @return the whetherAddDomainName
	 */
	public Boolean getWhetherAddDomainName() {
		return whetherAddDomainName;
	}

	/**
	 * @param whetherAddDomainName the whetherAddDomainName to set
	 */
	public void setWhetherAddDomainName(Boolean whetherAddDomainName) {
		this.whetherAddDomainName = whetherAddDomainName;
	}

	/**
	 * @return the rules
	 */
	public String getRules() {
		return rules;
	}

	/**
	 * @param rules the rules to set
	 */
	public void setRules(String rules) {
		this.rules = rules;
		this.rulesPattern = Pattern.compile(this.rules);
	}
	
	/**
	 * @return the rulesPattern
	 */
	public Pattern getRulesPattern() {
		return rulesPattern;
	}

	/**
	 * @return the urlDomainNameRules
	 */
	public List<UrlDomainNameRules> getUrlDomainNameRules() {
		return urlDomainNameRules;
	}

	/**
	 * @param urlDomainNameRules the urlDomainNameRules to set
	 */
	public void setUrlDomainNameRules(List<UrlDomainNameRules> urlDomainNameRules) {
		this.urlDomainNameRules = urlDomainNameRules;
	}
	
	

}
