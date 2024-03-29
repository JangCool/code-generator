package code.generator.elements.protobuf;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import code.generator.util.UtilsText;
import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "protobuf")
@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class ProtobufElement {
	
    @XmlAttribute
    private String name;
    
}