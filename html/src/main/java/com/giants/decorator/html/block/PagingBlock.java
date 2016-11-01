/**
 * 
 */
package com.giants.decorator.html.block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.giants.common.tools.Page;
import com.giants.decorator.config.element.Block;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.block.AbstractBlock;
import com.giants.decorator.core.block.PageNumber;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public class PagingBlock extends AbstractBlock {

	private static final long serialVersionUID = -9026129459348540645L;

	/**
	 * 
	 * @param templateEngine
	 * @param key
	 * @param blockConf
	 * @param blockTemplate
	 * @throws TemplateAnalysisException
	 */
	public PagingBlock(TemplateEngine templateEngine, String key,
			Block blockConf, String blockTemplate)
			throws TemplateAnalysisException {
		super(templateEngine, key, blockConf, blockTemplate);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.block.AbstractBlock#parseOperateObject(java.util.Map, java.lang.Object)
	 */
	@Override
	protected Object parseOperateObject(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		if (this.getParameter("value") == null) {
			return dataObj;
		}		
		return this.getParameter("value").parse(globalVarMap, dataObj);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.block.AbstractBlock#parseBlock(java.util.Map, java.lang.Object, java.lang.Object)
	 */
	@Override
	protected String parseBlock(Map<String, Object> globalVarMap,
			Object dataObj, Object blockObj) throws TemplateException {
		Map<String, Object> additionalObject = new HashMap<String, Object>();
		additionalObject.put("pagingKey", System.nanoTime());
		Page<?> page = (Page<?>) blockObj;
		if (this.getParameter("pageActionUrl") != null) {
			page.setActionUrl((String) this.getParameter("pageActionUrl")
					.parse(globalVarMap, dataObj));
		}
		Object pageNumCountObj = null;
		if (this.getParameter("pageNumCount") != null) {
			pageNumCountObj = this.getParameter("pageNumCount").parse(
					globalVarMap, dataObj);
		}
		if (pageNumCountObj != null) {
			Long pageNumCount = Long.valueOf((Integer) pageNumCountObj);
			List<PageNumber> pageNumbers = new ArrayList<PageNumber>();
			if (pageNumCount == -1 || pageNumCount >= page.getTotalPage()) {
				for (long i = 1; i <= page.getTotalPage(); i++) {
					pageNumbers.add(new PageNumber(i));
				}
			} else {
				if (pageNumCount >= 5) {
					pageNumCount = pageNumCount - 4L;
				} else {
					pageNumCount = 1L;
				}
				Long startPageNum = pageNumCount % 2 == 1 ? page.getPageNo()
						- pageNumCount / 2 : page.getPageNo()
						- pageNumCount / 2 + 1;
				Long endPageNum = page.getPageNo() + pageNumCount / 2;
				if (startPageNum < 3) {
					endPageNum = endPageNum + 3 - startPageNum;
					startPageNum = 3L;					
				}
				if (endPageNum > page.getTotalPage()-2) {
					startPageNum = startPageNum - endPageNum+page.getTotalPage()-2;
					endPageNum = Long.valueOf(page.getTotalPage()-2);
				}
				pageNumbers.add(new PageNumber(1L));
				if (startPageNum == 3) {
					pageNumbers.add(new PageNumber(2L));
				} else {
					pageNumbers.add(new PageNumber(true));
				}
				for (long i = startPageNum; i <= endPageNum; i++) {
					pageNumbers.add(new PageNumber(i));
				}
				if (endPageNum == page.getTotalPage()-2) {
					pageNumbers.add(new PageNumber(Long.valueOf(page.getTotalPage()-1)));
				} else {
					pageNumbers.add(new PageNumber(true));
				}
				pageNumbers.add(new PageNumber(Long.valueOf(page.getTotalPage())));
			}
			additionalObject.put("pageNumbers", pageNumbers);			
		}
		return this.parseSingleton(globalVarMap, dataObj, blockObj,
				additionalObject);
	}

}
