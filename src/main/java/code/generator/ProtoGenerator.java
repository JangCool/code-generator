package code.generator;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;

import code.generator.common.ProtoGlobal;
import code.generator.elements.ProtoConfigurationElement;
import code.generator.make.Protobuf;

public class ProtoGenerator {
	
	public static ProtoConfigurationElement initParser() throws XMLStreamException {

		String xmlPath = System.getProperty("generator.proto-path");
		
		if(xmlPath == null ||  xmlPath != null && xmlPath.equals("")){
			xmlPath = "generator".concat(File.separator).concat("generator-proto.xml");
		}
		
		JAXBContext jaxbContext = null;
		ProtoConfigurationElement elements = null;

		try {
		    jaxbContext = JAXBContext.newInstance(ProtoConfigurationElement.class);              

			XMLInputFactory xif = XMLInputFactory.newFactory();
			xif.setProperty(XMLInputFactory.SUPPORT_DTD, false);
			XMLStreamReader xsr = xif.createXMLStreamReader(new StreamSource(xmlPath));

		    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		    elements = (ProtoConfigurationElement) jaxbUnmarshaller.unmarshal(xsr);
		    
		} catch (JAXBException e) {
		    e.printStackTrace();
		}
		 
		
		return elements;
	}

	public static void main(String[] args) {
		try {
			ProtoConfigurationElement configurationElement = initParser();
			System.out.println(configurationElement);
			
			//global값 설정
			ProtoGlobal.init(configurationElement.getGlobal());
			
			Protobuf processProtobuf = new Protobuf(configurationElement);
			processProtobuf.generator();
			
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
}
