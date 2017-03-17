/**
 * 
 */
package com.giants.decorator.core.engine;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.giants.common.collections.CollectionUtils;
import com.giants.common.regex.Matcher;
import com.giants.decorator.common.DecoratorConstants;
import com.giants.decorator.config.TemplateConfig;
import com.giants.decorator.config.element.Block;
import com.giants.decorator.config.element.Function;
import com.giants.decorator.config.element.PropertyResource;
import com.giants.decorator.core.Element;
import com.giants.decorator.core.Parameter;
import com.giants.decorator.core.Template;
import com.giants.decorator.core.TemplateEngine;
import com.giants.decorator.core.block.BlockHandler;
import com.giants.decorator.core.block.DefaultBlock;
import com.giants.decorator.core.exception.TemplateException;
import com.giants.decorator.core.exception.analysis.BlockHandlerInitException;
import com.giants.decorator.core.exception.analysis.BlockUndefinedException;
import com.giants.decorator.core.exception.analysis.FunctionHandlerInitException;
import com.giants.decorator.core.exception.analysis.FunctionUndefinedException;
import com.giants.decorator.core.exception.analysis.ParameterIllegalException;
import com.giants.decorator.core.exception.analysis.TemplateAnalysisException;
import com.giants.decorator.core.function.DefaultFunction;
import com.giants.decorator.core.function.FunctionHandler;
import com.giants.decorator.core.parameter.ArithmeticParameter;
import com.giants.decorator.core.parameter.ConditionParameter;
import com.giants.decorator.core.parameter.DefiniteValueParameter;
import com.giants.decorator.core.parameter.FunctionParameter;
import com.giants.decorator.core.parameter.GlobalVariableParameter;
import com.giants.decorator.core.parameter.ListParameter;
import com.giants.decorator.core.parameter.MultiConditionParameter;
import com.giants.decorator.core.parameter.ObjectParameter;
import com.giants.decorator.core.parameter.VariableParameter;
import com.giants.decorator.core.variable.Variable;
import com.giants.xmlmapping.XmlDataModule;
import com.giants.xmlmapping.XmlMappingData;
import com.giants.xmlmapping.config.exception.XmlMapException;
import com.giants.xmlmapping.exception.XMLParseException;
import com.giants.xmlmapping.exception.XmlDataException;

/**
 * @author vencent.lu
 *
 */
public abstract class AbstractTemplateEngine implements TemplateEngine {
	
	protected final Logger   logger = LoggerFactory.getLogger(this.getClass());
	
	private String templateRelativePath;
	private String[] configFiles;
	
	protected String templateBasePath;
	private TemplateConfig templateConfig;
	private Map<String, String> propertiesMap;
	private final Map<String,Template> templateMap = new HashMap<String, Template>();
	private static Map<Class<?>, Object> tagHandlerObjectMap;
	
	private ThreadPoolExecutor compileThreadPool;
		
	protected abstract Class<?> getTemplateConfigClass();
	
	protected abstract Template createTemplate(String name)
			throws TemplateException;
			
	public AbstractTemplateEngine() {
		super();
	}
	
	/**
	 * @param basePath
	 * @param relativeBasePath
	 * @param configFile
	 * @throws XmlMapException
	 * @throws XmlDataException
	 * @throws XMLParseException
	 */
	public AbstractTemplateEngine(String basePath, String relativeBasePath,
			String... configFile) throws XmlMapException, XmlDataException,
			XMLParseException {
		super();
		this.initConfig(basePath, relativeBasePath, configFile);
	}
	
	/* (non-Javadoc)
	 * @see com.giants.decorator.core.TemplateEngine#initConfig(java.lang.String, java.lang.String, java.lang.String[])
	 */
	@Override
	public void initConfig(String basePath, String relativeBasePath,
			String... configFile) throws XmlMapException, XmlDataException,
			XMLParseException {
		if (this.templateBasePath == null) {			
			if (configFile != null) {
				this.configFiles = (String[]) ArrayUtils.add(configFile, 0,
						"META-INF/giants-decorator-template.xml");
			} else {
				this.configFiles = new String[]{"META-INF/giants-decorator-template.xml"};
			}
			XmlMappingData xmlMappingData = new XmlMappingData(
					this.getTemplateConfigClass());
			xmlMappingData.loadXmls(this.configFiles);
			XmlDataModule<?> xmlDataModule = xmlMappingData
					.getDataModule(this.getTemplateConfigClass());
			TemplateConfig defaultConfig = (TemplateConfig)xmlDataModule.get("default");
			if (defaultConfig != null) {
				for (Object templateConfigObj : xmlDataModule.getAll()) {
					TemplateConfig templateConfig = (TemplateConfig)templateConfigObj;
					if (!templateConfig.equals(defaultConfig)) {
						this.addTemplateConfigItems(defaultConfig, templateConfig);
					}
				}
			}
			this.templateConfig = defaultConfig;
			
			if (StringUtils.isNotEmpty(basePath)) {
				if (StringUtils.isNotEmpty(relativeBasePath)) {
					this.templateBasePath = new StringBuffer(basePath).append('/')
							.append(relativeBasePath).toString();
				} else {
					this.templateBasePath = basePath;
				}
				this.templateRelativePath = relativeBasePath;
			}			
			
            this.compileThreadPool = new ThreadPoolExecutor(this.templateConfig.getCompileThreadPool()
                    .getCorePoolSize(), this.templateConfig.getCompileThreadPool().getMaximumPoolSize(),
                    this.templateConfig.getCompileThreadPool().getKeepAliveTime(), TimeUnit.SECONDS,
                    new ArrayBlockingQueue<Runnable>(this.templateConfig.getCompileThreadPool().getQueueSize()),
                    new CompileThreadFactory(this.templateConfig.getCompileThreadPool().getStackSize()));
            
            this.loadProperties();
			
			this.logger.info(MessageFormat
					.format("Decorator Initialization configuration  success ! \n template Path:{0} \n configuration files:{1}",
							templateBasePath, Arrays.toString(configFiles)));
		}
	}
	
	
	
	@Override
    public com.giants.decorator.core.Block compileTemplateBlock(final com.giants.decorator.core.Block templateBlock)
            throws TemplateAnalysisException {
        Future<com.giants.decorator.core.Block> future = this.compileThreadPool.submit(new TemplateBlockCallable(
                templateBlock));
        try {
            com.giants.decorator.core.Block block = future.get();
            return block;
        } catch (InterruptedException | ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Error) {
                throw (Error)cause;
            } else if (cause instanceof RuntimeException) {
                throw (RuntimeException)cause;
            } else {
                throw (TemplateAnalysisException)cause;
            }
        }
    }

    private void loadProperties() {
		if (CollectionUtils.isNotEmpty(this.templateConfig.getPropertyResources())) {
			Map<String, PropertiesFile> propertiesFileMap = new HashMap<String, PropertiesFile>();
			for (PropertyResource propertyResource : this.templateConfig.getPropertyResources()) {
				String[] fileNameArray = propertyResource.getValue().trim().split(",");
				for (String fileName : fileNameArray) {
					fileName = fileName.trim();
					propertiesFileMap.put(fileName, new PropertiesFile(
							new File(
									new StringBuffer(this.templateBasePath)
											.append("/").append(fileName)
											.toString())));
				}
			}
			if (MapUtils.isNotEmpty(propertiesFileMap)) {
				LoadPropertiesRunnable loadPropertiesRunnable = new LoadPropertiesRunnable(
						this, propertiesFileMap.values());
				Thread thread = new Thread(loadPropertiesRunnable);
				thread.start();
			}
		}
	}
	
	protected void addTemplateConfigItems(TemplateConfig destTemplateConfig,
			TemplateConfig origTemplateConfig) {
	    if (origTemplateConfig.getCompileThreadPool() != null) {
	        destTemplateConfig.copyCompileThreadPool(origTemplateConfig.getCompileThreadPool());
	    }
		if (origTemplateConfig.getPropertyResources() != null) {
			destTemplateConfig.addAllPropertyResources(origTemplateConfig
					.getPropertyResources());
		}
		if (origTemplateConfig.getBlocks() != null) {
			destTemplateConfig.addAllBlocks(origTemplateConfig.getBlocks());
		}
		if (origTemplateConfig.getFunctions() != null) {
			destTemplateConfig.addAllFunctions(origTemplateConfig
					.getFunctions());
		}
		if (origTemplateConfig.getWidgets() != null) {
			destTemplateConfig.addAllWidgets(origTemplateConfig.getWidgets());
		}
		if (origTemplateConfig.getLayouts() != null) {
			destTemplateConfig.addAllLayouts(origTemplateConfig.getLayouts());
		}		
	}
	
	protected String buildTemplateName(String name) {
		return name;
	}
	
	protected void prugeTemplates() {
		if (this.templateMap.isEmpty()){
			return;
		}
		this.templateMap.clear();
		System.gc();
	}
	
	private static  Object getTagHandlerInstance(Class<?> tagHandlerClass)
			throws IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		if (tagHandlerObjectMap == null) {
			tagHandlerObjectMap = new HashMap<Class<?>, Object>();
		}
		Object instance = tagHandlerObjectMap.get(tagHandlerClass);
		if (instance == null) {
			instance = tagHandlerClass.getConstructor().newInstance();
			tagHandlerObjectMap.put(tagHandlerClass, instance);
		}
		return instance;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.TemplateEngine#loadTemplate(java.lang.String)
	 */
	@Override
	public Template loadTemplate(String name) throws TemplateException {
		name = this.buildTemplateName(name);
		Template template = this.templateMap.get(name);
		if (template == null) {
			template = this.createTemplate(name);
			this.templateMap.put(name, template);
		}
		return template;
	}
	
	/* (non-Javadoc)
	 * @see com.giants.decorator.core.TemplateEngine#removeTemplate(java.lang.String)
	 */
	@Override
	public void removeTemplate(String name) {
		this.templateMap.remove(name);
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.TemplateEngine#createElement(java.lang.String, java.lang.String)
	 */
	@Override
	public Element createElement(String key, String content)
			throws TemplateAnalysisException {
		if (content == null) {
			if (!DecoratorConstants.TEMPLATE_FUNCTION_PATTERN.matches(key)) {
				return new Variable(this, key);
			} else {
				Matcher matcher = DecoratorConstants.TEMPLATE_FUNCTION_NAME_PATTERN.matcher(key);
				matcher.find();
				String functionName = matcher.group(1);
				Function function = this.templateConfig.getFunction(functionName);
				if (function != null) {
					try {
						Class<?> handlerClass = function.getTagHandlerClass();
						if (handlerClass.getInterfaces().length != 0
								&& handlerClass.getInterfaces()[0] == FunctionHandler.class) {
							return new DefaultFunction(this, key, function,
									(FunctionHandler) getTagHandlerInstance(handlerClass));
						} else {
							return (Element) handlerClass.getConstructor(
									TemplateEngine.class, String.class,
									Function.class).newInstance(this, key,
									function);
						}
					} catch (Exception e) {
						throw new FunctionHandlerInitException(functionName,
								function.getTagHandlerClass(), key,
								content, e);
					}
				} else {
					throw new FunctionUndefinedException(functionName, key,
							content);
				}
			}
		} else {
			String blockName = key.split("\\:")[0].trim();
			Block block = templateConfig.getBlock(blockName);
			if (block != null) {
				try {
					Class<?> handlerClass = block.getTagHandlerClass();
					if (handlerClass.getInterfaces().length != 0
							&& handlerClass.getInterfaces()[0] == BlockHandler.class) {
						return new DefaultBlock(this, key, block, content,
								(BlockHandler) getTagHandlerInstance(handlerClass));
					} else {
						return (Element) handlerClass.getConstructor(
								TemplateEngine.class, String.class, Block.class, String.class)
								.newInstance(this, key, block, content);
					}
				} catch (Exception e) {
					throw new BlockHandlerInitException(blockName,
							block.getTagHandlerClass(), key, content, e);
				}
			} else {
				throw new BlockUndefinedException(blockName, key, content);
			}
		}
	} 

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.TemplateEngine#createParameter(com.giants.decorator.config.element.Parameter, java.lang.String, com.giants.decorator.core.Element)
	 */
	@Override
	public Parameter createParameter(
			com.giants.decorator.config.element.Parameter paramConf,
			String paramValue, Element element)
			throws TemplateAnalysisException {
		if (paramValue == null) {
			return new DefiniteValueParameter(paramConf, element, "null");		
		}
		//paramValue = paramValue.trim();		
		if (paramValue.equals("null")) {
			return new DefiniteValueParameter(paramConf, element, "null");
		} else if (DecoratorConstants.TEMPLATE_LIST_PARAMETER_PATTERN
				.matches(paramValue)) {
			return new ListParameter(paramConf, element, paramValue);
		} else if (DecoratorConstants.TEMPLATE_DEFINITE_VALUE_PARAM_PATTERN
				.matches(paramValue)) {
			return new DefiniteValueParameter(paramConf, element, paramValue);
		} else if (DecoratorConstants.TEMPLATE_OBJECT_PARAM_PATTERN
				.matches(paramValue)) {
			return new ObjectParameter(paramConf, element, paramValue);
		} else if (DecoratorConstants.TEMPLATE_GLOBAL_VARIABLE_PARAMETER_PATTERN
				.matches(paramValue)) {
			return new GlobalVariableParameter(paramConf, element, paramValue);
		} else if (DecoratorConstants.TEMPLATE_VARIABLE_PARAMETER_PATTERN
				.matches(paramValue)) {
			return new VariableParameter(paramConf, element, paramValue);
		} else if (DecoratorConstants.TEMPLATE_FUNCTION_PARAMETER_PATTERN
				.matches(paramValue)) {
			return new FunctionParameter(paramConf, element, paramValue);
		} else if (DecoratorConstants.TEMPLATE_ARITHMETIC_PARAMETER_PATTERN
				.matches(paramValue)) {
			return new ArithmeticParameter(paramConf, element, paramValue);
		} else if (DecoratorConstants.TEMPLATE_CONDITION_PARAMETER_PATTERN
				.matches(paramValue)) {
			return new ConditionParameter(paramConf, element, paramValue);
		} else if (DecoratorConstants.TEMPLATE_MULTI_CONDITION_PARAMETER_PATTERN
				.matches(paramValue)) {
			return new MultiConditionParameter(paramConf, element, paramValue);
		} else {
			if (paramConf == null) {
				paramConf = new com.giants.decorator.config.element.Parameter();
			}
			throw new ParameterIllegalException(paramConf, paramValue);
		}
	}
		
	/* (non-Javadoc)
	 * @see com.giants.decorator.core.TemplateEngine#getTemplateConfig()
	 */
	@Override
	public TemplateConfig getTemplateConfig() {
		return this.templateConfig;
	}

	/* (non-Javadoc)
	 * @see com.giants.decorator.core.TemplateEngine#getTemplateRelativePath()
	 */
	@Override
	public String getTemplateRelativePath() {
		return this.templateRelativePath;
	}
	
	/**
	 * @return the configFiles
	 */
	public String[] getConfigFiles() {
		return this.configFiles;
	}
	
	public Template getTemplate(String name) {
		return this.templateMap.get(name);
	}
	
	public void putTemplate(String name, Template template) {
		this.templateMap.put(name, template);
	}

	@Override
	public void setProperty(String key, String value) {
		if (this.propertiesMap == null) {
			this.propertiesMap = new HashMap<String, String>();
		}
		this.propertiesMap.put(key, value);
	}

	@Override
	public String getProperty(String key) {
		if (this.propertiesMap == null) {
			return null;
		}
		return this.propertiesMap.get(key);
	}
	
	private class CompileThreadFactory implements ThreadFactory {
	    protected final Logger   logger = LoggerFactory.getLogger(this.getClass());
	    
	    private final long threadStackSize;
	    private final ThreadGroup group;
	    private final AtomicInteger threadNumber = new AtomicInteger(1);
	    
	    private CompileThreadFactory(long threadStackSize) {
	        this.threadStackSize = threadStackSize;
	        SecurityManager s = System.getSecurityManager();
	        group = (s != null) ? s.getThreadGroup() :
	                              Thread.currentThread().getThreadGroup();
	    }

	    @Override
	    public Thread newThread(Runnable r) {
            String name = new StringBuilder("TemplateCompileThread-").append(threadNumber.getAndIncrement()).toString();
            Thread thread = new Thread(group, r, name, this.threadStackSize);
            this.logger
                    .info(MessageFormat.format("Create compiled template thread : {0}", name));
	        return thread;
	    }

	}
	
	private class TemplateBlockCallable implements Callable<com.giants.decorator.core.Block> {
	    protected final Logger   logger = LoggerFactory.getLogger(this.getClass());
	    
	    private com.giants.decorator.core.Block templateBlock;
	    
        private TemplateBlockCallable(com.giants.decorator.core.Block templateBlock) {
            super();
            this.templateBlock = templateBlock;
        }

        @Override
        public com.giants.decorator.core.Block call() throws Exception {
            this.templateBlock.compile();
            this.logger.info(MessageFormat.format("{0} compile template \"{1}\" sucess !", Thread.currentThread().getName(),
                    this.templateBlock.getKey()));
            return this.templateBlock;
        }
	    
	}

}
