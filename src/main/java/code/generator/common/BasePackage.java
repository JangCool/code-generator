package code.generator.common;

import code.generator.elements.children.global.BasePackageElement;
import code.generator.elements.children.global.GlobalElement;
import lombok.Data;

@Data
public class BasePackage {
	
	/**
	 * Generator를 이용하여 controller클래스를 생성 할 package경로
	 */
	private String controller = Const.DEFAULT_PATH_CONTROLLER_PACKAGE;
	
	/**
	 * Generator를 이용하여 service클래스를 생성 할 package경로
	 */
	private String service = Const.DEFAULT_PATH_SERVICE_PACKAGE;

	/**
	 * Generator를 이용하여 repository클래스를 생성 할 package경로
	 */
	private String repository = Const.DEFAULT_PATH_REPOSITORY_PACKAGE;
	
	/**
	 * Generator를 이용하여 model클래스를 생성 할 package경로
	 */
	private String model = Const.DEFAULT_PATH_MODEL_PACKAGE;
	
	/**
	 * Generator를 이용하여 client클래스를 생성 할 package경로
	 */
	private String client = Const.DEFAULT_PATH_CLIENT_PACKAGE;

	
	public BasePackage(BasePackageElement basePackageElement) {
		
		if(basePackageElement == null) {
			return;
		}
		
		this.controller =basePackageElement.getController();
		this.service =basePackageElement.getService();
		this.repository =basePackageElement.getRepository();
		this.model = basePackageElement.getModel();
		this.client = basePackageElement.getClient();
			
		
	}
	
}