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
 */
@XmlEntity
public class BlockStructure implements Serializable {

	private static final long serialVersionUID = -3596585335759817578L;
	
	@XmlAttribute
	@XmlIdKey
	private String name;
	
	@XmlAttribute
	private boolean multiton = false;
	
	@XmlAttribute
	private String blockName;
	
	@XmlAttribute
	private boolean required = false;
		
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
	 * @return the multiton
	 */
	public boolean isMultiton() {
		return multiton;
	}
	
	/**
	 * @param multiton the multiton to set
	 */
	public void setMultiton(boolean multiton) {
		this.multiton = multiton;
	}
	
	/**
	 * @return the blockName
	 */
	public String getBlockName() {
		return blockName;
	}

	/**
	 * @param blockName the blockName to set
	 */
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
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
		BlockStructure other = (BlockStructure) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
