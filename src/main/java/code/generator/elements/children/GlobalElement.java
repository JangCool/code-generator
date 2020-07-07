package code.generator.elements.children;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class GlobalElement {
	
    @XmlAttribute
    private String url;
    
    @XmlAttribute(name = "dirver-class")
    private String driverClass;
    
    @XmlAttribute
    private String username;
    
    @XmlAttribute
    private String password;
    
}