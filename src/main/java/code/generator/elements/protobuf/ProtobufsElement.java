package code.generator.elements.protobuf;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import code.generator.common.Global;
import code.generator.util.UtilsText;
import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "protobufs")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@ToString
public class ProtobufsElement {
	
    @XmlElement(name = "protobuf")
    private List<ProtobufElement> protobuf = null;
	
    @XmlAttribute
    private boolean java;
    
    @XmlAttribute
    private boolean js;
}
