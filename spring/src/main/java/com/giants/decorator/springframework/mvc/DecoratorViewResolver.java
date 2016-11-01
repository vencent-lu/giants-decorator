/**
 * 
 */
package com.giants.decorator.springframework.mvc;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import com.giants.decorator.core.TemplateEngine;

/**
 * @author vencent.lu
 *
 */
public class DecoratorViewResolver extends AbstractTemplateViewResolver {
	
	private TemplateEngine templateEngine;

	public DecoratorViewResolver() {
		setViewClass(requiredViewClass());
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.AbstractTemplateViewResolver#requiredViewClass()
	 */
	@Override
	protected Class<?> requiredViewClass() {
		return DecoratorView.class;
	}

	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		DecoratorView decoratorView = (DecoratorView)super.buildView(viewName);
		decoratorView.setTemplateEngine(this.templateEngine);
		return decoratorView;
	}

	/**
	 * @param templateEngine the templateEngine to set
	 */
	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}	
	
}
