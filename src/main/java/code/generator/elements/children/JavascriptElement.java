package code.generator.elements.children;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "javascript")
@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class JavascriptElement {
	
    @XmlAttribute
    private String name;
}