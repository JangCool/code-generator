package code.generator.elements.protobuf.global;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import code.generator.common.Global;
import lombok.Getter;
import lombok.ToString;

@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class BasePathElement {
	
    @XmlAttribute
    private String protoc;
    
    @XmlAttribute(name = "java_out")
    private String javaOut;
    
    @XmlAttribute(name = "js_out")
    private String jsOut;
    
}		