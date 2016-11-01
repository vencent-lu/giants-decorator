/**
 * 
 */
package com.giants.decorator.html.config.element;

import java.io.Serializable;

import com.giants.common.regex.Pattern;
import com.giants.xmlmapping.annotation.XmlAttribute;
import com.giants.xmlmapping.annotation.XmlEntity;

/**
 * @author vencent.lu
 * 
 * Create Date:2016年7月27日
 *
 */
@XmlEntity
public class UrlVersionRules implements Serializable {
	private static final long serialVersionUID = -6585892429546303546L;
	
	@XmlAttribute
	private String rules;
	
	private Pattern rulesPattern;
	
	@XmlAttribute
	private String propertyName;

	public String getRules() {
		return rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
		this.rulesPattern = Pattern.compile(this.rules);
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Pattern getRulesPattern() {
		return rulesPattern;
	}
	
	

}
