/**
 * 
 */
package com.giants.decorator.core.template;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;

import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;

/**
 * @author vencent.lu
 *
 */
public class AutoCompileTemplate extends AbstractTemplate {

	private static final long serialVersionUID = 3109517161322501264L;
	
	private TplEntity tplEntity;

	/**
	 * 
	 * @param templateEngine
	 * @param tplEntity
	 * @throws TemplateException
	 */
	public AutoCompileTemplate(TemplateEngine templateEngine,
			TplEntity tplEntity) throws TemplateException {
		super(templateEngine, tplEntity.getName(), tplEntity.loadContent(),
				tplEntity.lastModifiedTime());
		this.tplEntity = tplEntity;
		this.logger.info(MessageFormat.format(
				"Create \"{0}\" template success !", tplEntity.getName()));
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Template#getModifyTime()
	 */
	@Override
	public long getModifyTime() {
		return this.tplEntity.lastModifiedTime();
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Template#compile()
	 */
	@Override
	public void compile() throws TemplateException {
		try {
			long modifyTime = this.getModifyTime();
			synchronized(this) {
				if (modifyTime != this.compileTime) {
					String template = this.tplEntity.loadContent();
					if (StringUtils.isNotEmpty(template)) {
						this.compileTime = modifyTime;
						this.analysis(template);
						this.logger.info(MessageFormat.format(
								"\"{0}\" template afresh analysis !", this.getKey()));
					}
				}
			}		
		} catch (TemplateException e) {
			this.templateEngine.removeTemplate(this.getKey());
			throw e;
		}
	}

}
