/**
 * 
 */
package com.giants.decorator.core.template;

import java.text.MessageFormat;

import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;

/**
 * @author vencent.lu
 *
 */
public class ContentTemplate extends AbstractTemplate {

	private static final long serialVersionUID = 2724388658671780186L;

	/**
	 * 
	 * @param templateEngine
	 * @param key
	 * @param content
	 * @throws TemplateAnalysisException
	 */
	public ContentTemplate(TemplateEngine templateEngine, String key,
			String content) throws TemplateAnalysisException {
		super(templateEngine, key, content, System.currentTimeMillis());
		this.logger.info(MessageFormat.format(
				"Create \"{0}\" template success !", key));
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Template#getModifyTime()
	 */
	@Override
	public long getModifyTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Template#compile()
	 */
	@Override
	public void compile() throws TemplateException {
		// TODO Auto-generated method stub

	}

}
