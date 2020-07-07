package code.generator;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import code.generator.common.Config;
import code.generator.common.Global;
import code.generator.common.Log;
import code.generator.elements.ConfigurationElement;
import code.generator.elements.children.ControllersElement;
import code.generator.parser.XmlParser;

public class CodeGenerator {
	
	public static XmlParser initParser() throws XMLStreamException {

		String xmlPath = System.getProperty("generator.path");
		
		if(xmlPath == null ||  xmlPath != null && xmlPath.equals("")){
			xmlPath = "generator".concat(File.separator).concat("generator.xml");
		}
		
		
		File xmlFile = new File(xmlPath);
		 
		JAXBContext jaxbContext = null;

		try {
		    jaxbContext = JAXBContext.newInstance(ConfigurationElement.class);              

			XMLInputFactory xif = XMLInputFactory.newFactory();
			xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
			XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(xmlPath));

		    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		    ConfigurationElement elements = (ConfigurationElement) jaxbUnmarshaller.unmarshal(xsr);
		    System.out.println(elements);
		    
//		    for (ControllersElement element : elements.getControllers()) {
//			    Log.debug(element.toString());
//				
//			}
		    
		} catch (JAXBException e) {
		    e.printStackTrace();
		}
		 
		
		XmlParser xp = new XmlParser(xmlPath);
			
		
		Config.loadConfiguration(xp);
		Global.init(xp);
		
		return xp;
	}

	public static void main(String[] args) {
		try {
			XmlParser xmlParser = initParser();
//			DBInfo dbInfo = new DBInfo(xmlParser);
//			 
//			DBConnection dbConn = new DBConnection(dbInfo);
//			ColumnsResultSet processSql = new ColumnsResultSet(dbConn);
//			  
//			
//			BaseMake processController = new MakeController(xmlParser);
//			processController.generator();
//			
//			BaseMake processService = new MakeService(xmlParser);
//			processService.generator();
//			
//			BaseMake processClient = new MakeClient(xmlParser);
//			processClient.generator();
//			
//			BaseMake processDao = new MakeDaoMapper(xmlParser,processSql);
//			processDao.generator();
//
//			BaseMake processJsp = new MakeJsp(xmlParser);
//			processJsp.generator();
//
//			BaseMake processJavascript = new MakeJavascript(xmlParser);
//			processJavascript.generator();
//
//			dbConn.close();

		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
