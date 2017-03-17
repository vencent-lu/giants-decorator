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
import com.giants.decorator.html.config.HtmlTemplateConfig;

/**
 * @author vencent.lu
 *
 */
public class HtmlAutoCompileTemplateBlock extends HtmlTemplateBlock {
    private static final long serialVersionUID = -4792904985843944728L;

    public HtmlAutoCompileTemplateBlock(TemplateEngine templateEngine, String key, Block blockConf, String blockTemplate)
            throws TemplateAnalysisException {
        super(templateEngine, key, blockConf, blockTemplate);
    }

    @Override
    public void compile() throws TemplateAnalysisException {
        FindHeadBody find = new FindHeadBody();
        String content = this.getContent();
        content = this.analysisHtmlHead(content, find);
        content = this.analysisHtmlBody(content, find);
        if (!find.isFind()) {
            if (StringUtils.isNotEmpty(this.templateEngine
                    .getTemplateRelativePath())
                    && ((HtmlTemplateConfig) this.templateEngine
                            .getTemplateConfig()).getConversionRelativeURL()) {
                content = this.addUrlFunction(content);
            }
        }
        this.setContent(content);
        super.compile();
    }
    
    private String analysisHtmlHead(String template, FindHeadBody find) throws TemplateAnalysisException {
        Matcher headMatcher = DecoratorConstants.TEMPLATE_HTML_HEAD_PATTERN
                .matcher(template);
        Boolean conversionRelativeURL = ((HtmlTemplateConfig) this.templateEngine
                .getTemplateConfig()).getConversionRelativeURL();
        if (headMatcher.find()) {
            find.setFind(true);
            String headContent = headMatcher.group(1);
            if (StringUtils.isNotEmpty(this.templateEngine
                    .getTemplateRelativePath())
                    && conversionRelativeURL) {
                template = template.replace(
                        headContent,
                        new StringBuffer("<!-- BEGIN tag : head -->\n").append(
                                this.addUrlFunction(headContent)).append(
                                "<!-- END tag -->\n"));
            } else {
                template = template.replace(headContent, new StringBuffer(
                        "<!-- BEGIN tag : head -->\n").append(headContent)
                        .append("<!-- END tag -->\n"));
            }
            this.headTag = new HtmlTag("head");
            Matcher tagMatcher = DecoratorConstants.TEMPLATE_HTML_TAG_PATTERN.matcher(headContent);
            while (tagMatcher.find()) {
                String tagName = tagMatcher.group(6);
                if (tagName == null) {
                    tagName = tagMatcher.group(3);
                }
                String tagBody = tagMatcher.group(1);
                String tagContent = tagMatcher.group(4);
                if (tagContent !=null) {
                    tagBody = tagBody.replace(tagContent, "");
                }
                if (StringUtils.isNotEmpty(this.templateEngine.getTemplateRelativePath())
                        && conversionRelativeURL) {
                    tagBody = this.addUrlFunctionOfHeadTag(tagBody);
                }                               
                HtmlTag tag = new HtmlTag(tagName);
                if (tagContent !=null && StringUtils.isNotEmpty(tagContent.trim())) {
                    if (StringUtils.isNotEmpty(this.templateEngine.getTemplateRelativePath()) 
                            && conversionRelativeURL) {
                        tagContent = this.addUrlFunction(tagContent);
                    }
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
    
    private String analysisHtmlBody(String template, FindHeadBody find) {
        Matcher bodyMatcher = DecoratorConstants.TEMPLATE_HTML_BODY_PATTERN
                .matcher(template);
        if (bodyMatcher.find()) {
            find.setFind(true);
            String bodyContent = bodyMatcher.group(1);
            if (StringUtils.isNotEmpty(this.templateEngine.getTemplateRelativePath()) 
                    && ((HtmlTemplateConfig) this.templateEngine
                            .getTemplateConfig()).getConversionRelativeURL()) {
                template = template.replace(
                        bodyContent,
                        new StringBuffer("<!-- BEGIN tag : body -->\n").append(
                                this.addUrlFunction(bodyContent)).append(
                                "<!-- END tag -->\n"));
            } else {
                template = template.replace(bodyContent, new StringBuffer(
                        "<!-- BEGIN tag : body -->\n").append(bodyContent)
                        .append("<!-- END tag -->\n"));
            }
        }
        return template;
    }
    
    private String addUrlFunction(String htmlContent) {
        Matcher urlMatcher = DecoratorConstants.TEMPLATE_HTML_URL_PATTERN.matcher(htmlContent);
        Matcher styleUrlMatcher = DecoratorConstants.TEMPLATE_HTML_STYLE_URL_PATTERN.matcher(htmlContent);
        while (urlMatcher.find()) {
            String patternUrl = urlMatcher.group();
            String url = urlMatcher.group(2);
            char quotes = patternUrl.charAt(0);
            StringBuffer urlFunBf = new StringBuffer();
            urlFunBf.append(quotes).append("\\${url('&temp&").append(url).append("','")
                    .append(this.getKey()).append("')}").append(quotes);
            htmlContent = htmlContent.replaceFirst(
                    this.conversionUrlRegex(patternUrl), urlFunBf.toString());
        }
        while (styleUrlMatcher.find()) {
            String url = styleUrlMatcher.group(1);
            StringBuffer urlFunBf = new StringBuffer();
            urlFunBf.append("url(").append("\\${url('&temp&").append(url).append("','")
                    .append(this.getKey()).append("')}").append(")");
            htmlContent = htmlContent.replaceFirst(
                    this.conversionUrlRegex(styleUrlMatcher.group()),
                    urlFunBf.toString());
        }
        return htmlContent.replace("&temp&", "");
    }
    
    private String addUrlFunctionOfHeadTag(String htmlTagBody) {
        Matcher urlMatcher = DecoratorConstants.TEMPLATE_HTML_URL_PATTERN.matcher(htmlTagBody);     
        while (urlMatcher.find()) {
            String patternUrl = urlMatcher.group();
            String url = urlMatcher.group(2);
            char quotes = patternUrl.charAt(0);
            StringBuffer urlFunBf = new StringBuffer();
            urlFunBf.append(quotes).append("\\${url('&temp&").append(url).append("','")
                    .append(this.getKey()).append("')}").append(quotes);
            htmlTagBody = htmlTagBody.replaceFirst(
                    this.conversionUrlRegex(patternUrl), urlFunBf.toString());
        }
        return htmlTagBody.replace("&temp&", "");
    }
    
    private String conversionUrlRegex(String patternUrl) {
        return patternUrl.replace(".", "\\.").replace("/", "\\/")
                .replace("(", "\\(").replace(")", "\\)").replace("-", "\\-");
    }
    
    private class FindHeadBody {
        private boolean find = false;

        private boolean isFind() {
            return find;
        }

        private void setFind(boolean find) {
            this.find = find;
        }
        
    }
}
