/**
 * 
 */
package com.giants.decorator.core.block;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.giants.common.beanutils.BeanUtils;
import com.giants.common.regex.Matcher;
import com.giants.common.regex.Pattern;
import com.giants.decorator.common.DecoratorConstants;
import com.giants.decorator.core.Block;
import com.giants.decorator.core.BlockStructure;
import com.giants.decorator.core.Element;
import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.BlockEndUndefinedException;
import com.giants.decorator.core.exception.analysis.BlockParameterNotSetException;
import com.giants.decorator.core.exception.analysis.BlockParameterUndefinedException;
import com.giants.decorator.core.exception.analysis.BlockStructureNotSetException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;
import com.giants.decorator.core.exception.parse.DataObjectConversionException;
import com.giants.decorator.core.variable.AbstractElement;

/**
 * @author vencent.lu
 *
 */
public abstract class AbstractBlock extends AbstractElement implements Block {

	private static final long serialVersionUID = 533401692548220018L;
	
	private com.giants.decorator.config.element.Block blockConf;
	private String blockTemplate;
	private Map<String, Parameter> parameterMap;
	private Map<String, Element> elements;
	private Map<String, BlockStructure> blockStructureMap;
	
	protected abstract Object parseOperateObject(
			Map<String, Object> globalVarMap, Object dataObj)
			throws TemplateException;

	protected abstract String parseBlock(Map<String, Object> globalVarMap,
			Object dataObj, Object blockObj) throws TemplateException;
		
	/**
	 * 
	 * @param templateEngine template engine
	 * @param key block key
	 * @param blockConf block config
	 * @param blockTemplate block template
	 * @throws TemplateAnalysisException template analysis exception
	 */
	public AbstractBlock(TemplateEngine templateEngine, String key,
			com.giants.decorator.config.element.Block blockConf,
			String blockTemplate) throws TemplateAnalysisException {
		this(templateEngine, key, blockConf, blockTemplate, true);
	}
	
	public AbstractBlock(TemplateEngine templateEngine, String key,
            com.giants.decorator.config.element.Block blockConf,
            String blockTemplate, boolean compile) throws TemplateAnalysisException {
        super(templateEngine, key, blockTemplate);
        this.blockConf = blockConf;
        if (compile) {
            this.compile();
        }     
    }
		
	private void addElement(Element element) {
		if (this.elements == null) {
			this.elements = new HashMap<String, Element>();
		}
		if (!this.elements.containsKey(element.getKey())){
			this.elements.put(element.getKey(), element);
		}
	}
	
	protected Element getElement(String key) {
		if (this.elements == null) {
			return null;
		}
		return this.elements.get(key);
	}
	
	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Block#compile()
	 */
	@Override
	public void compile() throws TemplateAnalysisException {
	    if (StringUtils.isNotEmpty(this.getContent())) {
	        this.blockTemplate = this.getContent();
	        if (this.elements != null) {
	            this.elements.clear();
	        }
	        if (this.parameterMap != null) {
	            this.parameterMap.clear();
	        }
	        this.analysisParameter();
	        this.analysisBlock();
	        this.analysisVariable();
	    }		
	}

	private void analysisBlock() throws TemplateAnalysisException {
		Matcher beginMatcher = DecoratorConstants.TEMPLATE_BLOCK_BEGIN_PATTERN
				.matcher(this.blockTemplate);
		while (beginMatcher.find()) {
			String[] beginSegments = beginMatcher.group(1).split("\\s+");
			StringBuffer beginBlock = new StringBuffer();
			StringBuffer beginBlockRegex = new StringBuffer();
			for (int i=0;i<beginSegments.length;i++) {
				if (i != 0) {
					beginBlockRegex.append("\\s*");
					beginBlock.append(" ");
				}
				beginBlockRegex.append(beginSegments[i].replace("+", "\\+")
						.replace("-", "\\-").replace("*", "\\*")
						.replace("/", "\\/").replace("%", "\\%"));
				beginBlock.append(beginSegments[i]);
			}
			String beginBlockStr = beginBlock.toString()
					.replaceAll("\\s*\\:\\s*", ":")
					.replaceAll("\\s*\\=\\s*", "=")
					.replaceAll("\\s*\\,\\s*", ",")
					.replaceAll("\\s*\\.\\s*", ".")
					.replaceAll("\\s*\\!\\s*", "!")
					.replaceAll("\\s*\\=\\=\\s*", "==")
					.replaceAll("\\s*\\!\\=\\s*", "!=")
					.replaceAll("\\s*\\|\\|\\s*", "||")
					.replaceAll("\\s*\\&\\&\\s*", "&&")
					.replaceAll("\\s*\\>\\s*", ">")
					.replaceAll("\\s*\\<\\s*", "<")
					.replaceAll("\\s*\\>\\=\\s*", ">=")
					.replaceAll("\\s*\\<\\=\\s*", "<=")
					.replaceAll("\\(\\s*", "(").replaceAll("\\s*\\)", ")")
					.replaceAll("\\[\\s*", "[").replaceAll("\\s*\\]", "]")
					.replaceAll("\\s*#\\s*", "#")
					.replaceAll("\\s*\\+\\s*", "+")
					.replaceAll("\\s*\\-\\s*", "-")
					.replaceAll("\\s*\\*\\s*", "*")
					.replaceAll("\\s*\\/\\s*", "/")
					.replaceAll("\\s*\\%\\s*", "%");
			this.analysisBlock(beginBlockStr, beginBlockRegex.toString()
					.replace("$", "\\$").replace("(", "\\(")
					.replace(")", "\\)").replace("[", "\\[")
					.replace("]", "\\]").replace("|", "\\|"));
			beginMatcher = DecoratorConstants.TEMPLATE_BLOCK_BEGIN_PATTERN
					.matcher(this.blockTemplate);
		}
		
		if (this.blockConf != null
				&& this.blockConf.getBlockStructures() != null) {
			for (com.giants.decorator.config.element.BlockStructure blockStructureConf : this.blockConf
					.getBlockStructures()) {
				if (blockStructureConf.isRequired()
						&& (this.getBlockStructure(blockStructureConf.getName()) == null || this
								.getBlockStructure(blockStructureConf.getName())
								.getBlocks().isEmpty())) {
					throw new BlockStructureNotSetException(this, blockStructureConf.getBlockName());
				}
			}
		}
	}
	
	private void analysisBlock(String beginBlock, String beginBlockRegex) throws TemplateAnalysisException {
		String blockName = beginBlock.split("\\:")[0];
		com.giants.decorator.config.element.BlockStructure blockStructureConf = null;
		if (this.blockConf != null) {
			blockStructureConf = this.blockConf.getBlockStructure(blockName);
		}
		String blockRegex = DecoratorConstants.TEMPLATE_BLOCK_REGEX.replace(
				"{beginBlockRegex}", beginBlockRegex).replace("{blockName}", blockName);		
		Pattern pattern = Pattern.compile(blockRegex);
		Matcher matcher = pattern.matcher(this.blockTemplate);
		int i=0;
		int prevBeginTagNum = 0;
		while (matcher.find()) {
			String blockStr = matcher.group(0);
			String blockContent = matcher.group(1);
			int beginTagNum = this.statisticalBeginTagNum(blockName, blockContent);
			int noEndTagNum = beginTagNum - this.statisticalEndTagNum(blockName, blockContent);
			if (noEndTagNum==0){
				prevBeginTagNum = 0;
				Element element;
				if (i != 0) {
					StringBuffer repeatBlockBegin = new StringBuffer(
							"<!-- BEGIN ").append(beginBlock).append(" :#")
							.append(i).append(" -->");
					String repeatBlockStr = blockStr;
					repeatBlockStr = repeatBlockStr.replaceFirst(
							DecoratorConstants.TEMPLATE_BLOCK_FULL_BEGIN_REGEX
									.replace("{beginBlockRegex}",
											beginBlockRegex), repeatBlockBegin
									.toString());
					this.blockTemplate = this.blockTemplate.replace(blockStr,
							repeatBlockStr);
				} else {
					element = this.templateEngine.createElement(beginBlock,
							blockContent);
					this.addElement(element);
					this.blockTemplate = this.blockTemplate.replace(blockStr,
							element.getTemplateVariable());
					if (blockStructureConf != null) {
						BlockStructure blockStructure = this
								.getBlockStructure(blockStructureConf.getName());
						if (blockStructure == null) {
							blockStructure = new BlockStructure(
									blockStructureConf.getName(),
									blockStructureConf.isMultiton());
							this.addBlockStructure(blockStructure);
						}
						blockStructure.addBlock((Block)element);
					}
				}				
				i++;
			} else if (beginTagNum > prevBeginTagNum) {
				String blockNestRegex = DecoratorConstants.TEMPLATE_BLOCK_NEST_REGEX.replace("{blockName}", blockName);
				for (int j=0;j<noEndTagNum;j++) {
					blockRegex = blockRegex.replace("[\\S\\s]*?", blockNestRegex);
				}
				pattern = Pattern.compile(blockRegex);
				matcher = pattern.matcher(this.blockTemplate);
				prevBeginTagNum = beginTagNum;
			} else {
				throw new BlockEndUndefinedException(blockName, beginBlock,
						blockStr);
			}
		}
		if (i == 0) {
			throw new BlockEndUndefinedException(blockName, beginBlock,
					this.blockTemplate);
		}
	}
	
	private void analysisParameter()
			throws TemplateAnalysisException {
		if (this.blockConf == null) {
			return;
		}
		Matcher paramMatcher = DecoratorConstants.TEMPLATE_BLOCK_PARAMETER_PATTERN
				.matcher(this.getKey());
		while (paramMatcher.find()) {
			String paramName = paramMatcher.group(1).trim();
			String paramValue = paramMatcher.group(2).trim();
			com.giants.decorator.config.element.Parameter paramConf = this.blockConf
					.getParameter(paramName);
			if (paramConf == null) {
				if (paramName.equals("var")) {
					paramConf = new com.giants.decorator.config.element.Parameter();
					paramConf.setName(paramName);
					paramConf.setType(String.class);
				} else {
					throw new BlockParameterUndefinedException(
							this.blockConf.getName(), paramName, this.getKey(),
							this.getContent());
				}
			}
			if (paramValue.startsWith("#[") && paramValue.endsWith("]")) {
				paramValue = paramValue.substring(2, paramValue.length()-1);
			}
			this.addParameter(this.templateEngine.createParameter(paramConf,
					paramValue, this));			
		}
		if (this.blockConf.getParameters() != null) {
			for (com.giants.decorator.config.element.Parameter paramConf : this.blockConf
					.getParameters()) {
				if (!paramConf.isAllowNull()
						&& this.getParameter(paramConf.getName()) == null) {
					throw new BlockParameterNotSetException(blockConf.getName(),
							paramConf.getName(), this.getKey(), this.getContent());
				}
			}
		}
	}
	
	private int statisticalBeginTagNum(String blockName,String blockContent) {
		int beginTagNum = 0;
		Pattern blockBeginPattern = Pattern.compile(DecoratorConstants.TEMPLATE_BLOCK_BEGIN_REGEX.replace("{blockName}", blockName));
		Matcher blockBeginMatcher = blockBeginPattern.matcher(blockContent);
		while (blockBeginMatcher.find()) {
			beginTagNum++;
		}
		return beginTagNum;
	}
	
	private int statisticalEndTagNum(String blockName,String blockContent) {
		int endTagNum = 0;
		Pattern blockEndPattern = Pattern.compile(DecoratorConstants.TEMPLATE_BLOCK_END_REGEX.replace("{blockName}", blockName));
		Matcher blockEndMatcher = blockEndPattern.matcher(blockContent);
		while (blockEndMatcher.find()) {
			endTagNum++;
		}
		return endTagNum;
	}
		
	private void analysisVariable() throws TemplateAnalysisException {
		String blockTemplateStr = this.blockTemplate;
		Matcher varMatcher = DecoratorConstants.TEMPLATE_VARIABLE_PATTERN
				.matcher(this.blockTemplate);
		while (varMatcher.find()) {
			String key = varMatcher.group(1);
			int leftBracketCount = StringUtils.countMatches(key, "{");
			int rightBracketCount = StringUtils.countMatches(key, "}");
			if (leftBracketCount > rightBracketCount) {
				int nestCount = leftBracketCount-rightBracketCount;
				String variableRegex = DecoratorConstants.TEMPLATE_VARIABLE_REGEX;
				for (int i=0; i < nestCount; i++) {
					variableRegex = variableRegex.replace("[\\S\\s]*?", DecoratorConstants.TEMPLATE_VARIABLE_NEST_REGEX);
				}
				varMatcher = Pattern.compile(variableRegex).matcher(blockTemplateStr);
			} else {
				key = key.trim()
						.replaceAll("\\s*\\:\\s*", ":")
						.replaceAll("\\s*\\,\\s*", ",")
						.replaceAll("\\s*\\.\\s*", ".")
						.replaceAll("\\!\\s*", "!")
						.replaceAll("\\(\\s*", "(")
						.replaceAll("\\s*\\)", ")")
						.replaceAll("\\{\\s*", "{")
						.replaceAll("\\s*\\}", "}")
						.replaceAll("\\[\\s*", "[")
						.replaceAll("\\s*\\]", "]")
						.replaceAll("\\s*\\+\\s*", "+")
						.replaceAll("\\s*\\-\\s*", "-")
						.replaceAll("\\s*\\*\\s*", "*")
						.replaceAll("\\s*\\/\\s*", "/")
						.replaceAll("\\s*\\%\\s*", "%");
				Element element = this.templateEngine.createElement(key, null);
				this.blockTemplate = this.blockTemplate.replace(varMatcher.group(),
						element.getTemplateVariable());
				this.addElement(element);
				blockTemplateStr = blockTemplateStr.replace(varMatcher.group(), "");
			}
		}
	}
	
	private void addParameter(Parameter parameter) {
		if (this.parameterMap == null) {
			this.parameterMap = new HashMap<String, Parameter>();
		}
		this.parameterMap.put(parameter.getName(), parameter);
	}
	
	protected Parameter getParameter(String name) {
		if (this.parameterMap == null) {
			return null;
		}
		return this.parameterMap.get(name);
	}
	
	private void addBlockStructure(BlockStructure blockStructure) {
		if (this.blockStructureMap == null) {
			this.blockStructureMap = new HashMap<String, BlockStructure>();
		}
		this.blockStructureMap.put(blockStructure.getName(), blockStructure);
	}
	
	protected BlockStructure getBlockStructure(String name) {
		if (this.blockStructureMap == null) {
			return null;
		}
		return this.blockStructureMap.get(name);
	}
	
	
	
	/**
	 * @return the parameterMap
	 */
	public Map<String, Parameter> getParameterMap() {
		return parameterMap;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Block#getHtmlTemplate()
	 */
	@Override
	public String getBlockTemplate() {
		return this.blockTemplate;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.variable.AbstractElement#getTemplateVariable()
	 */
	@Override
	public String getTemplateVariable() {
		return new StringBuffer("$block(").append(this.getKey()).append(')')
				.toString();
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Element#parseForObject(java.util.Map, java.lang.Object)
	 */
	@Override
	public Object parseForObject(Map<String, Object> globalVarMap,
			Object dataObj) throws TemplateException {
		Object blockObj = this.parseOperateObject(globalVarMap, dataObj);
		if (blockObj != null) {	
			return this.parseBlock(globalVarMap, dataObj, blockObj);
		} else {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.giants.decorator.core.Element#parseForObject(java.lang.Object)
	 */
	@Override
	public Object parseForObject(Object dataObj) throws TemplateException {
		return this.parseForObject(null, dataObj);
	}

	protected String parseSingleton(Map<String, Object> globalVarMap,
			Object dataObj, Object blockObj) throws TemplateException {
		return this.parseSingleton(globalVarMap, dataObj, blockObj, null);
	}
	
	protected String parseSingleton(Map<String, Object> globalVarMap,
			Object dataObj, Object blockObj,
			Map<String, Object> additionalObject) throws TemplateException {
		blockObj = this.dataObjectProcessing(globalVarMap, dataObj, blockObj,
				additionalObject);
		String outBlockCode = this.getBlockTemplate();
		if (this.elements != null) {
			for (Element element : this.elements.values()) {
				if (!this.isBlockStructure(element)) {
					outBlockCode = outBlockCode.replace(
							element.getTemplateVariable(),
							element.parse(globalVarMap, blockObj));
				}
			}
		}
		return outBlockCode;
	}
	
	private boolean isBlockStructure(Element element) {
		if (this.blockStructureMap == null || !(element instanceof Block)) {
			return false;
		}
		for (BlockStructure blockStructure : this.blockStructureMap.values()) {
			if (blockStructure.getBlocks().contains(element)) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	protected Object dataObjectProcessing(Map<String, Object> globalVarMap,
			Object dataObj, Object blockObj,
			Map<String, Object> additionalObject) throws TemplateException {
		if (this.getParameter("var") != null || MapUtils.isNotEmpty(additionalObject)) {
			Map<String, Object> varMapObj = new HashMap<String, Object>();
			if (this.getParameter("var") != null) {
				varMapObj.put(
						(String) this.getParameter("var").parse(globalVarMap,
								dataObj), blockObj);
			} else {
				if (blockObj instanceof Map) {
					varMapObj.putAll((Map<String, Object>)blockObj);
				} else {
					try {
						BeanUtils.copyBeanPropertiesToMap(varMapObj, blockObj);
					} catch (Exception e) {
						throw new DataObjectConversionException(this.getKey(),
								this, dataObj, e);
					}
				}
			}
			if (MapUtils.isNotEmpty(additionalObject)) {
				varMapObj.putAll(additionalObject);
			}
			if (dataObj instanceof Map) {
				varMapObj.putAll((Map<String, Object>) dataObj);
			} else {
				try {
					BeanUtils.copyBeanPropertiesToMap(varMapObj, dataObj);
				} catch (Exception e) {
					throw new DataObjectConversionException(this.getKey(),
							this, dataObj, e);
				}
			}
			return varMapObj;
		} else {
			return blockObj;
		}
	}
	
}
