package code.generator.elements.protobuf.global;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.ToString;

@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class GlobalElement {
	
    @XmlElement(name = "base-path")
    private BasePathElement basePath;
    
}