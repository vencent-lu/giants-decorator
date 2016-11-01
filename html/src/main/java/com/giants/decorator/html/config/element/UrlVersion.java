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
 *Create Date:2016年7月27日
 */
@XmlEntity
public class UrlVersion implements Serializable {
	private static final long serialVersionUID = 2943286830336339461L;
	
	@XmlAttribute
	private boolean whetherAddVersion = true;
	
	@XmlAttribute
	private String paramName = "v";
		
	@XmlAttribute
	private String rules;
	
	private Pattern rulesPattern;
	
	@XmlManyElement
	private List<UrlVersionRules> urlVersionRules;

	public boolean isWhetherAddVersion() {
		return whetherAddVersion;
	}

	public void setWhetherAddVersion(boolean whetherAddVersion) {
		this.whetherAddVersion = whetherAddVersion;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
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

	public List<UrlVersionRules> getUrlVersionRules() {
		return urlVersionRules;
	}

	public void setUrlVersionRules(List<UrlVersionRules> urlVersionRules) {
		this.urlVersionRules = urlVersionRules;
	}	

}
