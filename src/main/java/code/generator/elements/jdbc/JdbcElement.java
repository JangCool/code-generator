package code.generator.elements.jdbc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class JdbcElement {
	
    @XmlAttribute
    private String url;
    
    @XmlAttribute(name = "driver-class")
    private String driverClass;
    
    @XmlAttribute
    private String username;
    
    @XmlAttribute
    private String password;
    
}