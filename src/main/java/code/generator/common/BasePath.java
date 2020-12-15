package code.generator.common;

import code.generator.elements.jdbc.global.BasePathElement;
import lombok.Data;

@Data
public class BasePath {
	
	/**
	 * Generator에서 이용할 템플릿 경로
	 */
	private String template = Const.DEFAULT_PATH_TEMPLATE;
	
	/**
	 * Generator를 이용하여 classapth에  위치할 Java파일 경로
	 */
	private String source = Const.DEFAULT_PATH_SOURCES;

	/**
	 * Generator를 이용하여 classapth에  위치할 정적파일 경로
	 */
	private String resources = Const.DEFAULT_PATH_RESOURCES;
	
	/**
	 * Generator를 이용하여 jsp, freemarker등의 view 파일을 생성할 경로
	 */
	private String views = Const.DEFAULT_PATH_VIEWS;
	
	/**
	 * Generator를 이용하여 javascript 업무 파일을 생성할 경로
	 */
	private String javascripts = Const.DEFAULT_PATH_JAVASCRIPTS;

	
	public BasePath(BasePathElement basePathElement) {
		
		if(basePathElement == null) {
			return;
		}
		
		this.template = basePathElement.getTemplate();
		this.source = basePathElement.getSource();
		this.resources = basePathElement.getResources();
		this.views = basePathElement.getViews();
		this.javascripts = basePathElement.getJavascripts();
			
		
	}
	
}