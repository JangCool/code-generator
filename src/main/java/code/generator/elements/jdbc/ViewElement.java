package code.generator.elements.jdbc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "view")
@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class ViewElement {
	
    @XmlAttribute
    private String name;
}