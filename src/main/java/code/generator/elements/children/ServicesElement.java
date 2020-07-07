package code.generator.elements.children;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
    
    @XmlAttribute(name = "proxy-target-proxy")
    private String proxyTargetProxy;
    

    @XmlElement(name = "service")
    private List<ServiceElement> service = null;
}
