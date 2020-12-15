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

@XmlRootElement(name = "controllers")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@ToString
public class ControllersElement {
	
    @XmlAttribute
    private String business;
    
    @XmlAttribute(name = "package")
    private String defaultPackage;
    
    @XmlAttribute(name = "suffix-package")
    private String suffixPackage;
    
    @XmlAttribute
    private String type;
    
    @XmlElement(name = "controller")
    private List<ControllerElement> controller = null;
    
    
	public String getControllerPackage() {
		return (UtilsText.isBlank(this.defaultPackage) ? Global.getBasePackage().getRepository() : this.defaultPackage) +"."+ getSubPackage("controller");
	}
	
	public String getControllerPath() {
		return getControllerPackage().replace(".", "/");
	}
	
	public String getSubPackage(String type) {
		
		String subPackage = "" ;
		
		subPackage += getBusiness();
		subPackage += "."+ type;

		return subPackage;
	}
	
	public String getType() {
		
		if(UtilsText.isBlank(this.type)) {
			return "Controller";
		}
		return type;
	}
}
