/**
 * 
 */
package com.giants.decorator.config.element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.giants.common.regex.Pattern;
import com.giants.xmlmapping.annotation.XmlAttribute;
import com.giants.xmlmapping.annotation.XmlEntity;
import com.giants.xmlmapping.annotation.XmlIdKey;

/**
 * @author vencent.lu
 *
 */
@XmlEntity
public class Layout implements Serializable {

	private static final long serialVersionUID = -5906093072770395703L;
	
	@XmlAttribute
	@XmlIdKey
	private String name;
	
	@XmlAttribute
	private String layoutFile;
	
	@XmlAttribute
	private String rules;
	
	private Pattern rulesPattern;
	
	@XmlAttribute
	private String excludeNames;
	
	private List<String> excludes;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the filterRules
	 */
	public String getRules() {
		return rules;
	}
	
	/**
	 * @param filterRules the rules to set
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
	 * @return the excludeNames
	 */
	public String getExcludeNames() {
		return excludeNames;
	}
	
	/**
	 * @param excludeNames the excludeNames to set
	 */
	public void setExcludeNames(String excludeNames) {
		this.excludeNames = excludeNames;
		if (this.excludeNames != null) {
			this.excludes = new ArrayList<String>();
			for (String exclude : this.excludeNames.split(",")) {
				this.excludes.add(exclude);
			}
		}
	}

	/**
	 * @return the excludes
	 */
	public List<String> getExcludes() {
		return excludes;
	}

	/**
	 * @return the layoutFile
	 */
	public String getLayoutFile() {
		return layoutFile;
	}

	/**
	 * @param layoutFile the layoutFile to set
	 */
	public void setLayoutFile(String layoutFile) {
		this.layoutFile = layoutFile;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Layout other = (Layout) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}
