package code.generator.make;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import code.generator.common.Global;
import code.generator.common.Log;
import code.generator.parser.XmlParser;
import code.generator.util.UtilsText;
import code.generator.vo.ControllerVO;

public class MakeController extends BaseMake{
	
	public MakeController() {
		super();
	}
	
	public MakeController(XmlParser xmlParser) {
		super(xmlParser);
	}

	@Override
	public void generator() throws Exception {
		generator(null);
	}

	@Override
	public void generator(String fileName) throws Exception {

		
		NodeList controllers = xmlParser.getDoc().getElementsByTagName("controllers");
		
   		int controllersLength = controllers.getLength();
   		if(controllersLength > 0 ) {
	   			
	   		Log.println("");
	   		Log.debug("==================================== Controller Generator ======================================");
	   		Log.debug("지금 Controller 파일 생성을 시작 합니다.  ");
	   		Log.debug("");
	   		Log.debug("path source = " + Global.getPath().getSource());
   		
   		}
   		
		for (int i = 0; i < controllersLength; i++) {
			
			Node nodeTables = controllers.item(i);
			Element elementTables = (Element) nodeTables;
			
	   		String pkg  = getPropertyKey(elementTables.getAttribute("package"));
	   		String suffixPkg  = getPropertyKey(elementTables.getAttribute("suffix-package"));
	   		String business   = getPropertyKey(elementTables.getAttribute("business"));
	   		String controllersType   = getPropertyKey(elementTables.getAttribute("type"));

	   		if(UtilsText.isBlank(pkg)) {
	   			pkg = Global.getControllerPkg();
	   		}

	  		ControllerVO cv = new ControllerVO();
			cv.setPkg(pkg);
	   		cv.setSuffixPkg(suffixPkg);
	   		cv.setBusiness(business);
	   		
	   		Log.debug("================================================================================================");
	   		Log.debug("business   = " + cv.getBusiness());
	   		Log.debug("package    = " + cv.getPkg());
	   		Log.debug("================================================================================================");

			
			NodeList childTables = elementTables.getElementsByTagName("controller");						
	   		int childTablesLength = childTables.getLength();
	   		
			for (int j = 0; j < childTablesLength; j++) {
				if(childTables.item(i).getNodeType() == Node.ELEMENT_NODE){
			  		Element element = (Element) childTables.item(j);
			  		
			   		String controllerName	= getPropertyKey(element.getAttribute("name"));
			   		String requestMapping	= getPropertyKey(element.getAttribute("request-mapping"));
			   		String type				= getPropertyKey(element.getAttribute("type"));
					String allInOne			= getPropertyKey(element.getAttribute("all-in-one"));

					if (!UtilsText.isBlank(fileName)) {
						controllerName = fileName;
					}

			   		if(UtilsText.isBlank(type)) {
			   			type = controllersType;
			   		}
			   		
					if(!UtilsText.isBlank(type) && "rest".equals(type)){
						type = "RestController";
					}else {
						type = "Controller";
					}
			   		
					String folder = UtilsText.concat(getPathSources().getAbsolutePath(),File.separator,cv.getPkg().replace(".", "/"));		
					String path = UtilsText.concat(folder,File.separator,controllerName,"Controller.java");
					
					if(!UtilsText.isBlank(requestMapping)) {
						cv.setRequestMapping(requestMapping);
					}else {
						if(!UtilsText.isBlank(cv.getBusiness())) {
							cv.setRequestMapping(cv.getBusiness().toLowerCase());
						}else {
							cv.setRequestMapping(controllerName.toLowerCase());
						}
					}
					
					boolean allInone = !UtilsText.isBlank(allInOne) && "true".equals(allInOne);

					String snakeCaseName = UtilsText.convertSnakeCaseToCamelcase(controllerName);

					Map<String,String> data = new HashMap<>();
					data.put("controllerName"		, controllerName);
					data.put("package"				, cv.getPkg());
					data.put("business"				, cv.getBusiness().toLowerCase());
					data.put("requestMapping"		, cv.getRequestMapping());
					data.put("snakeCaseName"		, snakeCaseName);
					data.put("annotation"			, type);
					
					writeTemplate((allInone) ? "Controller-all-in-one" : "Controller", folder, path, data);

					// 컨트럴로 옵션 all-in-one 값이 true 이면 jsp, js, service클래스까지 모두 생성.
					if (!UtilsText.isBlank(allInOne) && "true".equals(allInOne)) {
						makeService(controllerName, business, cv);
						makeJsp(snakeCaseName, business);
						makeJavascript(snakeCaseName, business);
					}
				}
			}
		}
	}

	public void makeService(String serviceName, String business, ControllerVO cv) throws Exception {
		
		String folder = UtilsText.concat(getPathSources().getAbsolutePath(),File.separator,cv.getPkg().replace(".", "/").replace("/controller", "/service"));	
		
		String path = UtilsText.concat(folder,File.separator,serviceName,"Service.java");
  		
		Map<String,String> data = new HashMap<>();
		data.put("serviceName"					, serviceName);
		data.put("package"						, cv.getPkg().replace(".controller", ".service"));
		data.put("proxyTargetProxy", "true");

		writeTemplate("Service", folder, path, data); 
	}

	public void makeJsp(String jspName, String business) throws Exception {
		String folder = UtilsText.concat(getPathViews().getAbsolutePath(), File.separator, business, File.separator, jspName);
		String path = UtilsText.concat(folder, File.separator, jspName, "-main.jsp");

		Map<String, String> data = new HashMap<>();
		data.put("jspName", jspName);
		data.put("business", business);
		data.put("target", business);
		data.put("resVer", "${resVer}");

		writeTemplate("Jsp-all-in-one", folder, path, data);
	}

	public void makeJavascript(String javascriptName, String business) throws Exception {
		String folder = UtilsText.concat(getPathJavascripts().getAbsolutePath(), File.separator, business,
				File.separator, javascriptName);
		String path = UtilsText.concat(folder, File.separator, javascriptName, "-main.js");

		Map<String, String> data = new HashMap<>();
		data.put("jspeName", javascriptName);
		data.put("target", business);

		writeTemplate("JavascriptGrid", folder, path, data);
	}
}
