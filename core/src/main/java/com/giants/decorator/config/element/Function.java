/**
 * 
 */
package com.giants.decorator.config.element;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.giants.xmlmapping.annotation.XmlAttribute;
import com.giants.xmlmapping.annotation.XmlEntity;
import com.giants.xmlmapping.annotation.XmlIdKey;
import com.giants.xmlmapping.annotation.XmlManyElement;

/**
 * @author vencent.lu
 *
 */
@XmlEntity
public class Function implements Serializable {

	private static final long serialVersionUID = -2722257977754551784L;
	
	@XmlAttribute
	@XmlIdKey
	private String name;
	
	@XmlAttribute
	private Class<?> tagHandlerClass;
	
	@XmlManyElement
	private List<Parameter> parameters;	
	private Map<String,Parameter> parameterMap;
	
	public Parameter getParameter(String name) {
		if (this.parameterMap == null) {
			return null;
		}
		return this.parameterMap.get(name);
	}
	
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
	 * @return the tagHandlerClass
	 */
	public Class<?> getTagHandlerClass() {
		return tagHandlerClass;
	}
	/**
	 * @param tagHandlerClass the tagHandlerClass to set
	 */
	public void setTagHandlerClass(Class<?> tagHandlerClass) {
		this.tagHandlerClass = tagHandlerClass;
	}
	/**
	 * @return the parameters
	 */
	public List<Parameter> getParameters() {
		return parameters;
	}
	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
		if (CollectionUtils.isNotEmpty(this.parameters)) {
			this.parameterMap = new HashMap<String,Parameter>();
			for (Parameter parameter : this.parameters) {
				this.parameterMap.put(parameter.getName(), parameter);
			}
		}
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
		Function other = (Function) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}		

}
