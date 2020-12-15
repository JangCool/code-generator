package code.generator.elements.jdbc;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "views")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@ToString
public class ViewsElement {
	
    @XmlAttribute
    private String business;
    
    @XmlAttribute(name = "target")
    private String target;
    
    @XmlAttribute(name = "suffix-target")
    private String suffixTarget;
    
    @XmlAttribute(name = "type")
    private String type;

    

    @XmlElement(name = "view")
    private List<ViewElement> view = null;
}
