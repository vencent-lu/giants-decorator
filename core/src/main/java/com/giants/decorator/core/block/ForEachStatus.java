/**
 * 
 */
package com.giants.decorator.core.block;

import java.io.Serializable;

/**
 * @author vencent.lu
 *
 */
public class ForEachStatus implements Serializable {

	private static final long serialVersionUID = 8096439415737296939L;
	
	private Integer index;
	private Integer count;
	private Boolean first;
	private Boolean last;
		
	/**
	 * @param count the count to set
	 */
	public ForEachStatus(Integer count) {
		super();
		this.count = count;
	}

	public void updateStatus(Integer index) {
		this.index = index;
		if (this.count == 1) {
			this.first = true;
			this.last = true;
		} else if (this.index == 0) {
			this.first = true;
			this.last = false;
		} else if (this.index == this.count-1) {
			this.first = false;
			this.last = true;
		} else {
			this.first = false;
			this.last = false;
		}
	}

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}
	
	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}
	
	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}
	
	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}
	
	/**
	 * @return the first
	 */
	public Boolean getFirst() {
		return first;
	}
	
	/**
	 * @param first the first to set
	 */
	public void setFirst(Boolean first) {
		this.first = first;
	}
	
	/**
	 * @return the last
	 */
	public Boolean getLast() {
		return last;
	}
	
	/**
	 * @param last the last to set
	 */
	public void setLast(Boolean last) {
		this.last = last;
	}

}
