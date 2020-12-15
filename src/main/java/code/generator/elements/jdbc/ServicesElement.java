package code.generator.elements.jdbc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import code.generator.common.Global;
import code.generator.util.UtilsText;
import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "services")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@ToString
public class ServicesElement {
	
    @XmlAttribute
    private String business;
    
    @XmlAttribute(name = "package")
    private String defaultPackage;
    
    @XmlAttribute(name = "suffix-package")
    private String suffixPackage;
    
    @XmlAttribute(name = "proxy-target-class")
    private boolean proxyTargetClass = true;
    

    @XmlElement(name = "service")
    private List<ServiceElement> service = null;
    
	public String getServicePackage() {
		return (UtilsText.isBlank(this.defaultPackage) ? Global.getBasePackage().getRepository() : this.defaultPackage) +"."+ getSubPackage("service");
	}
	
	public String getServicePath() {
		return getServicePackage().replace(".", "/");
	}
	
	
	public String getSubPackage(String type) {
		
		String subPackage = "" ;
		
		subPackage += getBusiness();
		subPackage += "."+ type;

		return subPackage;
	}
}
