package code.generator.make;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.h2.util.StringUtils;

import code.generator.CodeGenerator;
import code.generator.common.Config;
import code.generator.common.Const;
import code.generator.common.Log;
import code.generator.elements.ConfigurationElement;
import code.generator.util.UtilsText;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateHashModel;

public abstract class BaseMake {

	protected ConfigurationElement configuration;

	protected Configuration freemarkerConfiguration;
	
	private String templatePath;
	
	private String sourcePath = Const.DEFAULT_PATH_SOURCES;
	
	private File directoryForTemplate;

	public BaseMake() {
		this(null);
	}
	
	public ConfigurationElement getConfiguration() {
		return configuration;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public BaseMake(ConfigurationElement configuration) {
		
		this.configuration = configuration;
		
		if (configuration.getGlobal() != null && configuration.getGlobal().getBasePath() != null ) {
			this.templatePath = configuration.getGlobal().getBasePath().getTemplate();
		}
		
		this.freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_28);
		freemarkerConfiguration.setDefaultEncoding("UTF-8");
		freemarkerConfiguration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		init();
	}
	
	public File getDirectoryForTemplate() {
		return directoryForTemplate;
	}

	public void setDirectoryForTemplate(File directoryForTemplate) throws Exception {
		this.directoryForTemplate = directoryForTemplate;
		freemarkerConfiguration.setDirectoryForTemplateLoading(directoryForTemplate);
	}
	
	
	public String getPropertyKey(String target) {
		if(UtilsText.getPropertyKey(target) != null) {
			return target = Config.getString(UtilsText.getPropertyKey(target));			
		}
		return target;
	}
	

	private void init() {
				
		try {
			if(templatePath != null) {
					setDirectoryForTemplate(new File(templatePath));
			}else {
				freemarkerConfiguration.setClassForTemplateLoading(CodeGenerator.class,"/");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 템플릿 파일을 이용하여 Local 경로에 파일을 생성한다.
	 * @param templateFileName
	 * @param folder
	 * @param path
	 * @param data
	 * @throws Exception
	 */
	public void writeTemplate(String templateFileName,String folder,String path ,Map<String, Object> data) throws Exception {
		
		BeansWrapper beansWrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_29).build();
		//Freemarker에서 정적 클래스를 호출하라면 아래와 같이 추가하여 준다.
		data.put("statics", beansWrapper.getStaticModels());
		
		Template template = freemarkerConfiguration.getTemplate(UtilsText.concat("template/",templateFileName,".ftl"));

		File makeTargetDirectory = new File(folder);
		
		boolean isDirectory = makeTargetDirectory.isDirectory();
		
		
		if(!isDirectory) {
			makeTargetDirectory.mkdirs();
		}

	    String fileName = UtilsText.rpad(path.substring(path.lastIndexOf(File.separator)+1, path.length()), 30);

		//Base 파일이 아닐 경우 이미 생성 되어 있으면  파일을 다시 쓰지 않고 넘어간다.
		if( !(new File(path).isFile() && !templateFileName.startsWith("Base")) ) {
			
			Writer file = new FileWriter (path);
			template.process(data, file, beansWrapper);
		    file.flush();
		    file.close();
		    
			Log.debug(UtilsText.rpad(fileName, 30) + " 파일이 생성 되었습니다.");

		}else if( (new File(path).isFile() && !templateFileName.startsWith("Base")) ) {
			Log.debug(UtilsText.rpad(fileName, 30) + " 파일이 이미 존재 합니다.");
		}
	}
	
	public abstract void generator() throws Exception;

	public abstract void generator(String name) throws Exception;

}
