package code.generator.elements.children;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "table")
@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class TableElements {
	
    @XmlAttribute
    private String name;
    
    @XmlAttribute
    private String sqlsession;
    
    @XmlAttribute
    private String rename;
    
    @XmlAttribute
    private String desc;
    
    @XmlAttribute
    private String alias;
    
    @XmlAttribute
    private String suffix;
    
    @XmlAttribute
    private String prefix;
    
    @XmlAttribute
    private String lock;
    
    
    @XmlElement(name = "column")
    private List<ColumnElement> column = null;
}