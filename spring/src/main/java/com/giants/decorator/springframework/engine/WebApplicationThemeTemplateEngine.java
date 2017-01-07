/**
 * 
 */
package com.giants.decorator.springframework.engine;

import com.giants.decorator.html.Theme;
import com.giants.decorator.html.ThemeTemplateEngine;

/**
 * @author vencent.lu
 *
 */
public class WebApplicationThemeTemplateEngine extends
		WebApplicationTemplateEngine implements ThemeTemplateEngine {

	@Override
	public void selectTheme(Theme theme) {
		((ThemeTemplateEngine) this.getHtmlTemplateEngine()).selectTheme(theme);
	}

	@Override
	public Theme getCurrentTheme() {
		return ((ThemeTemplateEngine) this.getHtmlTemplateEngine()).getCurrentTheme();
	}

}
