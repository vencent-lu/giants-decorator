/**
 * 
 */
package com.giants.decorator.core.block;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.giants.decorator.config.element.Block;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public class ForEachBlock extends AbstractBlock {

	private static final long serialVersionUID = -1148957344520037063L;
		
	/**
	 * 
	 * @param templateEngine template engine
	 * @param key block key
	 * @param blockConf block configuration
	 * @param blockTemplate block template
	 * @throws TemplateAnalysisException template analysis exception
	 */
	public ForEachBlock(TemplateEngine templateEngine, String key,
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
		if (this.getParameter("items") == null) {
			return dataObj;
		}		
		return this.getParameter("items").parse(globalVarMap, dataObj);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.block.AbstractBlock#parseBlock(java.util.Map, java.lang.Object, java.lang.Object)
	 */
	@Override
	protected String parseBlock(Map<String, Object> globalVarMap,
			Object dataObj, Object blockObj) throws TemplateException {
		String varStatusName = null;
		ForEachStatus status = null;
		if (this.getParameter("varStatus") != null) {
			varStatusName = (String) this.getParameter("varStatus").parse(
					globalVarMap, dataObj);
		}
		StringBuffer outBlockCode = new StringBuffer();
		if (blockObj instanceof Collection<?> || blockObj instanceof Map<?,?>) {
			Collection<?> collectionObj;
			if (blockObj instanceof Collection<?>) {
				collectionObj = (Collection<?>)blockObj;
			} else {
				collectionObj = ((Map<?,?>)blockObj).values();
			}
			if (StringUtils.isNotEmpty(varStatusName)) {
				status = new ForEachStatus(collectionObj.size());
			}			
			int index = 0;
			for (Object object : collectionObj) {
				String parseSingleton;
				if (status == null) {
					parseSingleton = this.parseSingleton(globalVarMap, dataObj,
							object);
				} else {
					status.updateStatus(index);
					Map<String, Object> additionalObject = new HashMap<String, Object>();
					additionalObject.put(varStatusName, status);
					parseSingleton = this.parseSingleton(globalVarMap, dataObj,
							object, additionalObject);
				}
				if (parseSingleton != null) {
					outBlockCode.append(parseSingleton);
				}
				index++;
			}
			return outBlockCode.toString().trim();
		} else if (blockObj.getClass().isArray()) {
			Object[] arrayObject = (Object[]) blockObj;
			if (StringUtils.isNotEmpty(varStatusName)) {
				status = new ForEachStatus(arrayObject.length);
			}
			int index = 0;
			for (Object object : arrayObject) {
				String parseSingleton;
				if (status == null) {
					parseSingleton = this.parseSingleton(globalVarMap, dataObj,
							object);
				} else {
					status.updateStatus(index);
					Map<String, Object> additionalObject = new HashMap<String, Object>();
					additionalObject.put(varStatusName, status);
					parseSingleton = this.parseSingleton(globalVarMap, dataObj,
							object, additionalObject);
				}
				if (parseSingleton != null) {
					outBlockCode.append(parseSingleton);
				}
				index++;
			}
			return outBlockCode.toString().trim();
		} else {
			String parseSingleton;
			if (StringUtils.isNotEmpty(varStatusName)) {
				status = new ForEachStatus(1);
				status.updateStatus(0);
				Map<String, Object> additionalObject = new HashMap<String, Object>();
				additionalObject.put(varStatusName, status);
				parseSingleton = this.parseSingleton(globalVarMap, dataObj,
						blockObj, additionalObject);
			} else {
				parseSingleton = this.parseSingleton(globalVarMap, dataObj,
						blockObj);
			}				
			if (parseSingleton == null) {
				return null;
			}
			return parseSingleton.trim();
		}
	}

}
