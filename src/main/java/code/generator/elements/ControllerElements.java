package code.generator.elements;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "controller")
@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class ControllerElements {
	
    @XmlAttribute
    private String name;
    
    @XmlAttribute(name = "request-mapping")
    private String requestMapping;

    @XmlAttribute
    private String type;
    
    @XmlAttribute(name = "all-in-one")
    private String allInOne;
    
    
}