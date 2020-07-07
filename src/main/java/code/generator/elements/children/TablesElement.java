package code.generator.elements.children;

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
public class TablesElement {
	
    @XmlAttribute
    private String business;
    
    @XmlAttribute(name = "package")
    private String defaultPackage;
    
    @XmlAttribute(name = "suffix-package")
    private String suffixPackage;
    
    @XmlAttribute
    private String sqlsession;
    
    @XmlAttribute(name = "base-repository")
    private String baseRepository;

    @XmlAttribute(name = "base-model")
    private String baseModel;

    @XmlAttribute(name = "base-mappers")
    private String baseMapper;
    
    @XmlAttribute
    private String only;
    

    @XmlElement(name = "table")
    private List<TableElements> table = null;
}
