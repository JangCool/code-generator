package code.generator;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import code.generator.elements.ConfigurationElement;
import code.generator.jdbc.ColumnsResultSet;
import code.generator.jdbc.DBConnection;
import code.generator.jdbc.DBInfo;

public class CodeGenerator {
	
	public static ConfigurationElement initParser() throws XMLStreamException {

		String xmlPath = System.getProperty("generator.path");
		
		if(xmlPath == null ||  xmlPath != null && xmlPath.equals("")){
			xmlPath = "generator".concat(File.separator).concat("generator.xml");
		}
		
		JAXBContext jaxbContext = null;
		ConfigurationElement elements = null;

		try {
		    jaxbContext = JAXBContext.newInstance(ConfigurationElement.class);              

			XMLInputFactory xif = XMLInputFactory.newFactory();
			xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
			XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(xmlPath));

		    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		    elements = (ConfigurationElement) jaxbUnmarshaller.unmarshal(xsr);
		    
		} catch (JAXBException e) {
		    e.printStackTrace();
		}
		 
		
		return elements;
	}

	public static void main(String[] args) {
		try {
			ConfigurationElement configurationElement = initParser();
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

			DBInfo dbInfo = new DBInfo(configurationElement.getJdbc());
			 
			DBConnection dbConn = new DBConnection(dbInfo);
			ColumnsResultSet processSql = new ColumnsResultSet(dbConn);
			dbConn.close();

		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
