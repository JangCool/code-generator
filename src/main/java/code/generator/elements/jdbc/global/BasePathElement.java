package code.generator.elements.jdbc.global;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import code.generator.common.Global;
import lombok.Getter;
import lombok.ToString;

@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class BasePathElement {
	
    @XmlAttribute
    private String template;
    
    @XmlAttribute
    private String source;
    
    @XmlAttribute
    private String resources;
    
    @XmlAttribute
    private String views;
    
    @XmlAttribute
    private String javascripts;
    
}