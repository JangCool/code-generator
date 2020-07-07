package code.generator.elements.children.global;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class GlobalElement {
	
    @XmlElement(name = "sql-session")
    private SqlSessionElement sqlSession;
    
    @XmlElement(name = "base-package")
    private BasePackageElement basePackage;
    
    @XmlElement(name = "base-path")
    private BasePathElement basePath;
    
    @XmlElement(name = "exclude-column")
    private ExcludeColumnElement excludeColumn;
    
}