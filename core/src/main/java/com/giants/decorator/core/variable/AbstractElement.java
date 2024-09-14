/**
 * 
 */
package com.giants.decorator.core.variable;

import java.util.Map;

import com.giants.decorator.core.Element;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public abstract class AbstractElement implements Element {

	private static final long serialVersionUID = -6268148604815030146L;
		
	protected TemplateEngine templateEngine;
	
	private String key;
	private String content;
		
	/**
	 * 
	 * @param templateEngine template engine
	 * @param key the key
	 * @param content content
	 */
	public AbstractElement(TemplateEngine templateEngine, String key,
			String content) {
		super();
		this.templateEngine = templateEngine;
		this.key = key;
		this.content = content;
	}
	
	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Element#getTemplateEngine()
	 */
	@Override
	public TemplateEngine getTemplateEngine() {
		return this.templateEngine;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Element#getKey()
	 */
	@Override
	public String getKey() {
		return key;
	}
	
	/*
	 * (non-Javadoc) 
	 * @see com.giants.decorator.core.Element#getTemplateVariable()
	 */
	@Override
	public String getTemplateVariable() {
		return new StringBuffer("${").append(this.key).append("}").toString();
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Element#getContent()
	 */
	@Override
	public String getContent() {
		return this.content;
	}

	/**
	 * @param content the content to set
	 */
	protected void setContent(String content) {
		this.content = content;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Element#parse(java.util.Map, java.lang.Object)
	 */
	@Override
	public String parse(Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException {
		Object result = this.parseForObject(globalVarMap, dataObj);
		if (result == null) {
			return "";
		}
		if (result instanceof Element) {
			return ((Element)result).parse(globalVarMap, dataObj);
		}
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Element#parse(java.lang.Object)
	 */
	@Override
	public String parse(Object dataObj) throws TemplateException {
		return this.parse(null, dataObj);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		AbstractElement other = (AbstractElement) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}	

}
