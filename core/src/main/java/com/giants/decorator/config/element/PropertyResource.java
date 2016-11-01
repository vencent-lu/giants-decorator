/**
 * 
 */
package com.giants.decorator.config.element;

import java.io.Serializable;

import com.giants.xmlmapping.annotation.XmlAttribute;
import com.giants.xmlmapping.annotation.XmlEntity;
import com.giants.xmlmapping.annotation.XmlIdKey;

/**
 * @author vencent.lu
 * 
 * Create Date:2016年7月27日
 *
 */
@XmlEntity
public class PropertyResource implements Serializable {
	private static final long serialVersionUID = 543617225696060186L;
	
	@XmlAttribute
	@XmlIdKey
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
