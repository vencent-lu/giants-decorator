/**
 * 
 */
package com.giants.decorator.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.giants.decorator.core.Element;

/**
 * @author vencent.lu
 *
 */
public class HtmlTag {
	
	private String tagName;
	private Map<String, Element> attributes;
	private Map<String, List<HtmlTag>> childrenTags;
	private Element content;
	
	/**
	 * @param name
	 */
	public HtmlTag(String tagName) {
		super();
		this.tagName = tagName;
	}

	/**
	 * @param name
	 * @param content
	 */
	public HtmlTag(String tagName, Element content) {
		super();
		this.tagName = tagName;
		this.content = content;
	}
	
	public void addAttribute(String name, Element attribute) {
		if (this.attributes == null) {
			this.attributes = new HashMap<String, Element>();
		}
		this.attributes.put(name, attribute);
	}
	
	public void addChildrenTag(HtmlTag htmlTag) {
		if (this.childrenTags == null) {
			this.childrenTags = new HashMap<String, List<HtmlTag>>();
		}
		if (this.childrenTags.get(htmlTag.getTagName()) == null) {
			this.childrenTags.put(htmlTag.getTagName(), new ArrayList<HtmlTag>());
		}
		this.childrenTags.get(htmlTag.getTagName()).add(htmlTag);
	}
	
	public List<HtmlTag> getChildrenTag(String tagName) {
		if (this.childrenTags == null) {
			return null;
		}
		return this.childrenTags.get(tagName);
	}

	/**
	 * @return the name
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param name the name to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * @return the content
	 */
	public Element getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(Element content) {
		this.content = content;
	}

	/**
	 * @return the attributes
	 */
	public Map<String, Element> getAttributes() {
		return attributes;
	}

	/**
	 * @return the childrenTags
	 */
	public Map<String, List<HtmlTag>> getChildrenTags() {
		return childrenTags;
	}

}
