package code.generator.elements.children;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "column")
@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class ColumnElement {
	
    @XmlAttribute
    private String name;
    
    @XmlAttribute
    private String alias;
    
    @XmlAttribute
    private String jdbcType;
    
    @XmlAttribute
    private String typeHandler;

    @XmlAttribute
    private String type;
    
    @XmlAttribute
    private String typeValue;
    
    
}