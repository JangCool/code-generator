package code.generator.make;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.generator.common.Global;
import code.generator.common.Log;
import code.generator.elements.ConfigurationElement;
import code.generator.elements.jdbc.ControllerElement;
import code.generator.elements.jdbc.ControllersElement;
import code.generator.util.UtilsText;

public class MakeController extends BaseMake{
	
	public MakeController() {
		super();
	}
	
	public MakeController(ConfigurationElement configurationElement) {
		super(configurationElement);
	}

	@Override
	public void generator() throws Exception {
		generator(null);
	}

	@Override
	public void generator(String fileName) throws Exception {

		
		List<ControllersElement> controllersList = getConfiguration().getControllers();
		if(controllersList == null) {
			return;
		}
		
   		int controllersListSize = controllersList.size();
   		if(controllersListSize > 0 ) {
	   			
	   		Log.println("");
	   		Log.debug("==================================== Controller Generator ======================================");
	   		Log.debug("지금 Controller 파일 생성을 시작 합니다.  ");
	   		Log.debug("");
	   		Log.debug("path source = " + Global.getBasePath().getSource());
   		
   		}
   		
		for (int i = 0; i < controllersListSize; i++) {
			
			ControllersElement controllers = controllersList.get(i);
			List<ControllerElement> controllerList = controllers.getController();
			
	   		Log.debug("================================================================================================");
	   		Log.debug("business						= " + controllers.getBusiness());
	   		Log.debug("package						= " + controllers.getControllerPackage());
	   		Log.debug("suffix-package 				= " + controllers.getSuffixPackage());
	   		Log.debug("type							= " + controllers.getType());
	   		Log.debug("================================================================================================");
			
			
			for (ControllerElement controller : controllerList) {
				
		   		String controllerName	= controller.getName();
		   		String controllerType	= controllers.getType();
		   		String requestMapping	= controller.getRequestMapping();
				String snakeCaseName 	= UtilsText.convertSnakeCaseToCamelcase(controllerName);
				boolean isAllInOne 		= controller.isAllInOne();

				if(!controllers.getType().equals(controller.getType())){
					controllerType	= ("rest".equals(controller.getType()) ? "RestController" : "Controller");
				}
				
				String folder = UtilsText.concat(new File(Global.getBasePath().getSource()).getAbsolutePath(), File.separator, controllers.getControllerPath());
				String path = UtilsText.concat(folder,File.separator,controllerName,"Controller.java");
				
				Map<String, Object> data = new HashMap<>();
				data.put("controllerName"		, controllerName);
				data.put("package"				, controllers.getControllerPackage());
				data.put("business"				, controllers.getBusiness());
				data.put("requestMapping"		, requestMapping);
				data.put("snakeCaseName"		, snakeCaseName);
				data.put("annotation"			, controllerType);
				
				writeTemplate((isAllInOne) ? "Controller-all-in-one" : "Controller", folder, path, data);
				
				// 컨트럴로 옵션 all-in-one 값이 true 이면 jsp, js, service클래스까지 모두 생성.
				if (isAllInOne) {
					makeService(controllerName, controllers.getBusiness(), controllers);
//					makeJsp(snakeCaseName, business);
//					makeJavascript(snakeCaseName, business);
				}

			}
		}
	}

	public void makeService(String serviceName, String business, ControllersElement controllers) throws Exception {
		
		String folder = UtilsText.concat(Global.getBasePath().getSource(),File.separator,controllers.getControllerPath().replace("/controller", "/service"));	
		
		String path = UtilsText.concat(folder,File.separator,serviceName,"Service.java");
  		
		Map<String,Object> data = new HashMap<>();
		data.put("serviceName"					, serviceName);
		data.put("package"						, controllers.getControllerPackage().replace(".controller", ".service"));
		data.put("proxyTargetProxy", "true");

		writeTemplate("Service", folder, path, data); 
	}
//
//	public void makeJsp(String jspName, String business) throws Exception {
//		String folder = UtilsText.concat(getPathViews().getAbsolutePath(), File.separator, business, File.separator, jspName);
//		String path = UtilsText.concat(folder, File.separator, jspName, "-main.jsp");
//
//		Map<String, String> data = new HashMap<>();
//		data.put("jspName", jspName);
//		data.put("business", business);
//		data.put("target", business);
//		data.put("resVer", "${resVer}");
//
//		writeTemplate("Jsp-all-in-one", folder, path, data);
//	}
//
//	public void makeJavascript(String javascriptName, String business) throws Exception {
//		String folder = UtilsText.concat(getPathJavascripts().getAbsolutePath(), File.separator, business,
//				File.separator, javascriptName);
//		String path = UtilsText.concat(folder, File.separator, javascriptName, "-main.js");
//
//		Map<String, String> data = new HashMap<>();
//		data.put("jspeName", javascriptName);
//		data.put("target", business);
//
//		writeTemplate("JavascriptGrid", folder, path, data);
//	}
}
