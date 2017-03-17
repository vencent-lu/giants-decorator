/**
 * 
 */
package com.giants.decorator.html.block;

import org.apache.commons.lang.StringUtils;

import com.giants.common.regex.Matcher;
import com.giants.decorator.common.DecoratorConstants;
import com.giants.decorator.config.element.Block;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;
import com.giants.decorator.html.HtmlTag;

/**
 * @author vencent
 *
 */
public class HtmlContentTemplateBlock extends HtmlTemplateBlock {
    private static final long serialVersionUID = 3388959181859479353L;

    public HtmlContentTemplateBlock(TemplateEngine templateEngine, String key, Block blockConf, String blockTemplate)
            throws TemplateAnalysisException {
        super(templateEngine, key, blockConf, blockTemplate);
    }
    
    @Override
    public void compile() throws TemplateAnalysisException {
        String content = this.getContent();
        content = this.analysisHtmlHead(content);
        content = this.analysisHtmlBody(content);
        this.setContent(content);
        super.compile();
    }
    
    private String analysisHtmlHead(String template) throws TemplateAnalysisException {
        Matcher headMatcher = DecoratorConstants.TEMPLATE_HTML_HEAD_PATTERN
                .matcher(template);
        if (headMatcher.find()) {
            String headContent = headMatcher.group(1);
            template = template.replace(
                    headContent,
                    new StringBuffer("<!-- BEGIN tag : head -->\n").append(
                            headContent).append("<!-- END tag -->\n"));
            this.headTag = new HtmlTag("head");
            Matcher tagMatcher = DecoratorConstants.TEMPLATE_HTML_TAG_PATTERN.matcher(headContent);
            while (tagMatcher.find()) {
                String tagName = tagMatcher.group(6);
                if (tagName == null) {
                    tagName = tagMatcher.group(3);
                }
                String tagBody = tagMatcher.group(1);               
                String tagContent = tagMatcher.group(4);                
                HtmlTag tag = new HtmlTag(tagName);
                if (tagContent !=null && StringUtils.isNotEmpty(tagContent.trim())) {
                    tag.setContent(this.templateEngine
                            .createElement(
                                    new StringBuffer("tag:").append(tagName)
                                            .toString(), tagContent));
                    tag.setTagName(new StringBuffer(tag.getTagName()).append("-body").toString());
                }
                Matcher attMatcher = DecoratorConstants.TEMPLATE_HTML_TAG_ATT_PATTERN.matcher(tagBody);
                while (attMatcher.find()) {
                    String attName = attMatcher.group(1);
                    String attValue = attMatcher.group(3);
                    Matcher varMatcher = DecoratorConstants.TEMPLATE_VARIABLE_PATTERN
                            .matcher(attValue);
                    if (varMatcher.find()) {
                        StringBuffer varStrBuff = new StringBuffer(varMatcher.group(1));
                        tag.addAttribute(
                                attName,
                                this.templateEngine.createElement(
                                        varStrBuff.toString(), null));
                    } else {
                        tag.addAttribute(attName, this.templateEngine
                                .createElement(
                                        new StringBuffer("\"").append(attValue)
                                                .append("\"").toString(), null));
                    }
                }
                this.headTag.addChildrenTag(tag);
            }
        }
        return template;
    }
    
    private String analysisHtmlBody(String template) {
        Matcher bodyMatcher = DecoratorConstants.TEMPLATE_HTML_BODY_PATTERN
                .matcher(template);
        if (bodyMatcher.find()) {
            String bodyContent = bodyMatcher.group(1);
            template = template.replace(
                    bodyContent,
                    new StringBuffer("<!-- BEGIN tag : body -->\n").append(
                            bodyContent).append("<!-- END tag -->\n"));
        }
        return template;
    }

}
