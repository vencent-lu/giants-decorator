/**
 * 
 */
package com.giants.decorator.core;

import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public interface Block extends Element {
		
	String getBlockTemplate();	
		
	void compile() throws TemplateAnalysisException;
	
}
