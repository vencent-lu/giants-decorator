/**
 * 
 */
package com.giants.decorator.core.block;

/**
 * @author vencent.lu
 *
 */
public class PageNumber {
	
	private Boolean ellipsis = false;
	private Long pageNo;
	
	/**
	 * @param pageNo
	 */
	public PageNumber(Long pageNo) {
		super();
		this.pageNo = pageNo;
	}

	/**
	 * @param ellipsis
	 */
	public PageNumber(boolean ellipsis) {
		super();
		this.ellipsis = ellipsis;
	}
	
	public Boolean getEllipsis() {
		return ellipsis;
	}

	public void setEllipsis(Boolean ellipsis) {
		this.ellipsis = ellipsis;
	}

	public Long getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(Long pageNo) {
		this.pageNo = pageNo;
	}

}
