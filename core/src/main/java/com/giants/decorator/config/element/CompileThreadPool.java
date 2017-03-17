/**
 * 
 */
package com.giants.decorator.config.element;

import java.io.Serializable;

import com.giants.xmlmapping.annotation.XmlAttribute;
import com.giants.xmlmapping.annotation.XmlEntity;

/**
 * @author vencent.lu
 *
 */
@XmlEntity
public class CompileThreadPool implements Serializable {
    private static final long serialVersionUID = -6973585252814471441L;
    
    @XmlAttribute
    private Integer corePoolSize;
    
    @XmlAttribute
    private Integer maximumPoolSize;
    
    @XmlAttribute
    private Long keepAliveTime;
    
    @XmlAttribute
    private Integer queueSize;
    
    @XmlAttribute
    private Long stackSize;

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public Long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }

    public Long getStackSize() {
        return stackSize;
    }

    public void setStackSize(Long stackSize) {
        this.stackSize = stackSize;
    }    

}
