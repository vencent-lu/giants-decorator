/**
 * 
 */
package com.giants.decorator.springframework.engine;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @author vencent.lu
 *
 */
public class WebApplicationStaticThemeTemplateEngine extends
		WebApplicationThemeTemplateEngine {
	
	private ThemeService themeService;

	/* (non-Javadoc)
	 * @see com.giants.decorator.springframework.engine.WebApplicationTemplateEngine#onApplicationEvent(org.springframework.context.ApplicationEvent)
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		super.onApplicationEvent(event);
		if (event instanceof ContextRefreshedEvent) {
			this.selectTheme(this.themeService.findCurrentTheme());
		}		
	}

	/**
	 * @param themeService the themeService to set
	 */
	public void setThemeService(ThemeService themeService) {
		this.themeService = themeService;
	}

	public ThemeService getThemeService() {
		return themeService;
	}	

}
