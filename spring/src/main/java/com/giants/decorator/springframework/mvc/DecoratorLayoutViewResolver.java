/**
 * 
 */
package com.giants.decorator.springframework.mvc;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.html.DecoratorLayout;

/**
 * @author vencent.lu
 *
 */
public class DecoratorLayoutViewResolver extends AbstractTemplateViewResolver {
	
	private DecoratorLayout decoratorLayout;
	
	public DecoratorLayoutViewResolver() {
		setViewClass(requiredViewClass());
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.AbstractTemplateViewResolver#requiredViewClass()
	 */
	@Override
	protected Class<?> requiredViewClass() {
		return DecoratorLayoutView.class;
	}
	
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		DecoratorLayoutView decoratorLayoutView = (DecoratorLayoutView)super.buildView(viewName);
		decoratorLayoutView.setDecoratorLayout(this.decoratorLayout);
		return decoratorLayoutView;
	}

	/**
	 * @param templateEngine the templateEngine to set
	 */
	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.decoratorLayout = new DecoratorLayout(templateEngine);
	}	

}
