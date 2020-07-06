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
import code.generator.vo.JspVO;

public class MakeJsp extends BaseMake{
	
	public MakeJsp() {
		super();
	}
	
	public MakeJsp(XmlParser xmlParser) {
		super(xmlParser);
	}

	@Override
	public void generator() throws Exception {
		generator(null);
	}

	@Override
	public void generator(String fileName) throws Exception {

		
		NodeList services = xmlParser.getDoc().getElementsByTagName("views");
		
   		int servicesLength = services.getLength();
   		if(servicesLength > 0 ) {
	
	   		Log.println("");
			Log.debug("==================================== JSP Generator ======================================");
			Log.debug("지금 JSP 파일 생성을 시작 합니다.  ");
	   		Log.debug("");
			Log.debug("path JSP = " + Global.getPath().getView());
   		}
   		
		for (int i = 0; i < servicesLength; i++) {
			
			Node nodeTables = services.item(i);
			Element elementTables = (Element) nodeTables;
			
			String pkg = getPropertyKey(elementTables.getAttribute("target"));
			String suffixPkg = getPropertyKey(elementTables.getAttribute("suffix-target"));
	   		String business				= getPropertyKey(elementTables.getAttribute("business"));

			JspVO cv = new JspVO();
			cv.setPkg(pkg);
			cv.setSuffixPkg(suffixPkg);
	   		cv.setBusiness(business);
	   		
	   		Log.debug("================================================================================================");
			Log.debug("business                     = " + cv.getBusiness());
			Log.debug("target                       = " + Global.getPath().getView() + "/" + cv.getPkg());
			Log.debug("suffix-target                = " + cv.getSuffixPkg());
	   		Log.debug("================================================================================================");

			
			NodeList childTables = elementTables.getElementsByTagName("jsp");
	   		int childTablesLength = childTables.getLength();
	   		
			for (int j = 0; j < childTablesLength; j++) {
				if(childTables.item(i).getNodeType() == Node.ELEMENT_NODE){
			  		Element element = (Element) childTables.item(j);
			  		
					String jspeName = getPropertyKey(element.getAttribute("name"));
			   		
					if (!UtilsText.isBlank(fileName)) {
						jspeName = fileName;
					}
			   	
					String folder = UtilsText.concat(getPathViews().getAbsolutePath(), File.separator, cv.getPkg());
					String path = UtilsText.concat(folder, File.separator, jspeName, ".jsp");

					Map<String,String> data = new HashMap<>();
					data.put("jspeName", jspeName);
					data.put("target", cv.getPkg());
					 
					writeTemplate("Jsp", folder, path, data);
				}
			}
		}
	}
}
