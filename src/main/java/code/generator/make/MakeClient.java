//package code.generator.make;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import code.generator.common.Global;
//import code.generator.common.Log;
//import code.generator.parser.XmlParser;
//import code.generator.util.UtilsText;
//import code.generator.vo.ClientVO;
//
//public class MakeClient extends BaseMake{
//	
//	public MakeClient() {
//		super();
//	}
//	
//	public MakeClient(XmlParser xmlParser) {
//		super(xmlParser);
//	}
//
//	@Override
//	public void generator() throws Exception {
//		generator(null);
//	}
//
//	@Override
//	public void generator(String fileName) throws Exception {
//
//		
//		NodeList clients = xmlParser.getDoc().getElementsByTagName("clients");
//		
//   		int clientsLength = clients.getLength();
//   		if(clientsLength > 0 ) {
//	   			
//	   		Log.println("");
//	   		Log.debug("==================================== Client Generator ======================================");
//	   		Log.debug("지금 Client 파일 생성을 시작 합니다.  ");
//	   		Log.debug("");
//	   		Log.debug("path source = " + Global.getPath().getSource());
//   		
//   		}
//   		
//		for (int i = 0; i < clientsLength; i++) {
//			
//			Node nodeTables = clients.item(i);
//			Element elementTables = (Element) nodeTables;
//			
//	   		String pkg  = getPropertyKey(elementTables.getAttribute("package"));
//	   		String suffixPkg  = getPropertyKey(elementTables.getAttribute("suffix-package"));
//	   		String business   = getPropertyKey(elementTables.getAttribute("business"));
//
//	   		if(UtilsText.isBlank(pkg)) {
//	   			pkg = Global.getClientPkg();
//	   		}
//
//	  		ClientVO cv = new ClientVO();
//			cv.setPkg(pkg);
//	   		cv.setSuffixPkg(suffixPkg);
//	   		cv.setBusiness(business);
//	   		
//	   		Log.debug("================================================================================================");
//	   		Log.debug("business   = " + cv.getBusiness());
//	   		Log.debug("package    = " + cv.getPkg());
//	   		Log.debug("================================================================================================");
//
//			
//			NodeList childTables = elementTables.getElementsByTagName("client");						
//	   		int childTablesLength = childTables.getLength();
//	   		
//			for (int j = 0; j < childTablesLength; j++) {
//				if(childTables.item(i).getNodeType() == Node.ELEMENT_NODE){
//			  		Element element = (Element) childTables.item(j);
//			  		
//			   		String name				= getPropertyKey(element.getAttribute("name"));
//			   		String clientName		= getPropertyKey(element.getAttribute("client-name"));
//			   		String clientUrl		= getPropertyKey(element.getAttribute("client-url"));
//			   		
//					if (!UtilsText.isBlank(fileName)) {
//						name = fileName;
//					}
//			   		
//					String folder = UtilsText.concat(getPathSources().getAbsolutePath(),File.separator,cv.getPkg().replace(".", "/"));		
//					String path = UtilsText.concat(folder,File.separator,name,"Client.java");
//					
//			  		
//					Map<String,String> data = new HashMap<>();
//					data.put("name"					, name);
//					data.put("package"				, cv.getPkg());
//					data.put("clientName"			, clientName);
//					data.put("clientUrl"			, clientUrl);
//					
//					writeTemplate("Client", folder, path, data);
//
//				}
//			}
//		}
//	}
//}
