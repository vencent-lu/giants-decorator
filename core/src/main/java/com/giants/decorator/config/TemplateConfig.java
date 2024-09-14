/**
 * 
 */
package com.giants.decorator.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.giants.decorator.config.element.Block;
import com.giants.decorator.config.element.CompileThreadPool;
import com.giants.decorator.config.element.Layout;
import com.giants.decorator.config.element.Function;
import com.giants.decorator.config.element.PropertyResource;
import com.giants.decorator.config.element.Widget;
import com.giants.xmlmapping.annotation.XmlAttribute;
import com.giants.xmlmapping.annotation.XmlElement;
import com.giants.xmlmapping.annotation.XmlEntity;
import com.giants.xmlmapping.annotation.XmlIdKey;
import com.giants.xmlmapping.annotation.XmlManyElement;

/**
 * @author vencent.lu
 *
 */
@XmlEntity
public class TemplateConfig implements Serializable {

	private static final long serialVersionUID = 2327999966355919186L;
	
	@XmlAttribute
	@XmlIdKey
	private String name;
	
	@XmlElement
	private CompileThreadPool compileThreadPool;
		
	@XmlManyElement
	private List<PropertyResource> propertyResources;
		
	@XmlManyElement
	private List<Block> blocks;
	
	@XmlManyElement
	private List<Function> functions;
	
	@XmlManyElement
	private List<Layout> layouts;
	
	@XmlManyElement
	private List<Widget> widgets;	
	
	private Map<String,Block> blockMap;
	
	private Map<String,Function> functionMap;
	
	private Map<String,Widget> widgetMap;
	
	public void copyCompileThreadPool(CompileThreadPool compileThreadPool) {
	    if (compileThreadPool != null) {
	        if (this.compileThreadPool != null) {
	            if (compileThreadPool.getCorePoolSize() != null) {
	                this.compileThreadPool.setCorePoolSize(compileThreadPool.getCorePoolSize());
	            }
	            if (compileThreadPool.getMaximumPoolSize() != null) {
	                this.compileThreadPool.setMaximumPoolSize(compileThreadPool.getMaximumPoolSize());
	            }
	            if (compileThreadPool.getKeepAliveTime() != null) {
	                this.compileThreadPool.setKeepAliveTime(compileThreadPool.getKeepAliveTime());
	            }
	            if (compileThreadPool.getQueueSize() != null) {
	                this.compileThreadPool.setQueueSize(compileThreadPool.getQueueSize());
	            }
	            if (compileThreadPool.getStackSize() != null) {
	                this.compileThreadPool.setStackSize(compileThreadPool.getStackSize());
	            }
	        } else {
	            this.compileThreadPool = compileThreadPool;
	        }
	    }
	}
	
	public void addAllPropertyResources(List<PropertyResource> propertyResources) {
		if (this.propertyResources == null) {
			this.setPropertyResources(propertyResources);
		} else {
			this.propertyResources.addAll(propertyResources);
		}
	}
	
	public Block getBlock(String name) {
		if (this.blockMap == null) {
			return null;
		}
		return this.blockMap.get(name);
	}
	
	public void addAllBlocks(List<Block> blocks) {
		if (this.blocks == null) {
			this.setBlocks(blocks);			
		} else {
			this.blocks.addAll(blocks);
			for (Block block : blocks) {
				this.blockMap.put(block.getName(), block);
			}
		}
	}
	
	public Function getFunction(String name) {
		if (this.functionMap == null) {
			return null;
		}
		return this.functionMap.get(name);
	}
	
	public void addAllFunctions(List<Function> functions) {
		if (this.functions == null) {
			this.setFunctions(functions);
		} else {
			this.functions.addAll(functions);
			for (Function function : functions) {
				this.functionMap.put(function.getName(), function);
			}
		}
	}
	
	public Widget getWidget(String name) {
		if (this.widgetMap == null) {
			return null;
		}
		return this.widgetMap.get(name);
	}
	
	public void addAllWidgets(List<Widget> widgets) {
		if (this.widgets == null) {
			this.setWidgets(widgets);
		} else {
			this.widgets.addAll(widgets);
			for (Widget widget : widgets) {
				this.widgetMap.put(widget.getName(), widget);
			}
		}
	}
	
	public  void addAllLayouts(List<Layout> layout) {
		if (this.layouts == null) {
			this.setLayouts(layout);
		} else {
			this.layouts.addAll(layout);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	
	
	public CompileThreadPool getCompileThreadPool() {
        return compileThreadPool;
    }

    public void setCompileThreadPool(CompileThreadPool compileThreadPool) {
        this.compileThreadPool = compileThreadPool;
    }

    public List<PropertyResource> getPropertyResources() {
		return propertyResources;
	}

	public void setPropertyResources(List<PropertyResource> propertyResources) {
		this.propertyResources = propertyResources;
	}
	
	/**
	 * @return the blocks
	 */
	public List<Block> getBlocks() {
		return blocks;
	}
	
	/**
	 * @param blocks the blocks to set
	 */
	public void setBlocks(List<Block> blocks) {
		this.blocks = blocks;
		if (CollectionUtils.isNotEmpty(this.blocks)) {
			this.blockMap = new HashMap<String,Block>();
			for (Block block : this.blocks) {
				this.blockMap.put(block.getName(), block);
			}
		}
	}
	
	/**
	 * @return the functions
	 */
	public List<Function> getFunctions() {
		return functions;
	}
	
	/**
	 * @param functions the functions to set
	 */
	public void setFunctions(List<Function> functions) {
		this.functions = functions;
		if (CollectionUtils.isNotEmpty(this.functions)) {
			this.functionMap = new HashMap<String,Function>();
			for (Function function : this.functions) {
				this.functionMap.put(function.getName(), function);
			}
		}
	}

	/**
	 * @return the widgets
	 */
	public List<Widget> getWidgets() {
		return widgets;
	}

	/**
	 * @param widgets the widgets to set
	 */
	public void setWidgets(List<Widget> widgets) {
		this.widgets = widgets;
		if (CollectionUtils.isNotEmpty(this.widgets)) {
			this.widgetMap = new HashMap<String, Widget>();
			for (Widget widget : this.widgets) {
				this.widgetMap.put(widget.getName(), widget);
			}
		}
	}

	/**
	 * @return the layoutFilters
	 */
	public List<Layout> getLayouts() {
		return layouts;
	}

	/**
	 * @param layouts the layouts to set
	 */
	public void setLayouts(List<Layout> layouts) {
		this.layouts = layouts;
	}

	/**
	 * @return the conversionRelativeURL
	 */
	/*public final Boolean getConversionRelativeURL() {
		return conversionRelativeURL;
	}*/

	/**
	 * @param conversionRelativeURL the conversionRelativeURL to set
	 */
	/*public final void setConversionRelativeURL(Boolean conversionRelativeURL) {
		this.conversionRelativeURL = conversionRelativeURL;
	}*/

	/**
	 * return the urlDomainName
	 */
	/*public UrlDomainName getUrlDomainName() {
		return urlDomainName;
	}*/

	/**
	 * param urlDomainName the urlDomainName to set
	 */
	/*public void setUrlDomainName(UrlDomainName urlDomainName) {
		this.urlDomainName = urlDomainName;
	}*/

	/*public UrlVersion getUrlVersion() {
		return urlVersion;
	}

	public void setUrlVersion(UrlVersion urlVersion) {
		this.urlVersion = urlVersion;
	}*/

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
		TemplateConfig other = (TemplateConfig) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
