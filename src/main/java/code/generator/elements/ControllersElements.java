package code.generator.elements;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement(name = "controllers")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@ToString
public class ControllersElements {
	
    @XmlAttribute
    private String business;
    
    @XmlAttribute(name = "package")
    private String pkg;
    
    @XmlAttribute(name = "suffix-package")
    private String suffixPkg;
    
    @XmlAttribute
    private String type;
    

    @XmlElement(name = "controller")
    private List<ControllerElements> controller = null;
}
