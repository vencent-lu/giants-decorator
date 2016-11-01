/**
 * 
 */
package com.giants.decorator.springframework.mvc;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.giants.analyse.profiler.ExecutionTimeProfiler;
import com.giants.decorator.html.DecoratorLayout;

/**
 * @author vencent.lu
 *
 */
public class DecoratorLayoutView extends DecoratorView {
	
	private DecoratorLayout decoratorLayout;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.AbstractTemplateView#renderMergedTemplateModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExecutionTimeProfiler.enter("process decorator layout");
		response.getWriter().println(
				this.decoratorLayout.renderView(this.getUrl(), request,
						this.createGlobalVarMap(model, request), model));
		ExecutionTimeProfiler.release();
	}

	/**
	 * @param decoratorLayout the decoratorLayout to set
	 */
	public void setDecoratorLayout(DecoratorLayout decoratorLayout) {
		this.decoratorLayout = decoratorLayout;
	}

	
}
