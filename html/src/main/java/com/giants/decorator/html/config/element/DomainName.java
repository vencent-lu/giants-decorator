/**
 * 
 */
package com.giants.decorator.html.config.element;

import java.io.Serializable;

import com.giants.xmlmapping.annotation.XmlAttribute;
import com.giants.xmlmapping.annotation.XmlEntity;

/**
 * @author vencent.lu
 *
 * Create Date:2014年5月26日
 */
@XmlEntity
public class DomainName implements Serializable {

	private static final long serialVersionUID = 6257861152555533122L;
	
	@XmlAttribute
	private String value;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
