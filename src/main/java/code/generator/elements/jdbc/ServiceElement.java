package code.generator.elements.jdbc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "service")
@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class ServiceElement {
	
    @XmlAttribute
    private String name;
}