/**
 * 
 */
package com.giants.decorator.springframework.mvc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.AbstractTemplateView;

import com.giants.analyse.profiler.ExecutionTimeProfiler;
import com.giants.decorator.core.Template;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.html.exception.NotHtmlTemplateException;
import com.giants.decorator.html.template.HtmlTemplate;
import com.giants.decorator.html.validation.Error;
import com.giants.decorator.html.validation.ValidationResult;

/**
 * @author vencent.lu
 *
 */
public class DecoratorView extends AbstractTemplateView {
	
	private TemplateEngine templateEngine;

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.view.AbstractTemplateView#renderMergedTemplateModel(java.util.Map, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExecutionTimeProfiler.enter("process decorator template");
		Template template = this.templateEngine.loadTemplate(this.getUrl());
		if (!(template instanceof HtmlTemplate)) {
			throw new NotHtmlTemplateException(template);
		}
		response.getWriter().println(
				((HtmlTemplate)template).execute(
						request,
						this.createGlobalVarMap(model, request), model));// TODO 实例化判断
		ExecutionTimeProfiler.release();
	}
	
	protected Map<String, Object> createGlobalVarMap(Map<String, Object> model,
			HttpServletRequest request) {
		Map<String, Object> globalVarMap = new HashMap<String, Object>();
		this.initValidation(globalVarMap, model);
		return globalVarMap;
	}
		
	protected void initValidation(Map<String, Object> globalVarMap, Map<String, Object> model) {
		RequestContext requestContext = (RequestContext) model.get("springMacroRequestContext");
		Iterator<String> keys = model.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			if (key.startsWith("org.springframework.validation.BindingResult.")) {
				BeanPropertyBindingResult bindingResult = (BeanPropertyBindingResult)model.get(key);
				ValidationResult validationResult = null;
				List<ObjectError> errors = bindingResult.getAllErrors();
				for (ObjectError error : errors) {
					if (validationResult == null) {
						validationResult = new ValidationResult();
					}
					String errorMsg = requestContext.getMessage(error);
					errorMsg = StringUtils.isNotEmpty(errorMsg) ? errorMsg : error.getDefaultMessage();
					if (error instanceof FieldError) {
						validationResult.addError(new Error(((FieldError)error).getField(), errorMsg));
					} else {
						validationResult.addError(new Error(error.getObjectName(), errorMsg, false));
					}					
				}
				if (validationResult != null) {
					globalVarMap
							.put(new StringBuffer("validation_")
									.append(key
											.replace(
													"org.springframework.validation.BindingResult.",
													"")).append("_result")
									.toString(), validationResult);
				}
			}
		}
	}

	/**
	 * @param templateEngine the templateEngine to set
	 */
	public void setTemplateEngine(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}	

}
