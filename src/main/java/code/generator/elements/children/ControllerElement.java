package code.generator.elements.children;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import code.generator.util.UtilsText;
import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "controller")
@XmlAccessorType (XmlAccessType.FIELD)
@Getter
@ToString
public class ControllerElement {
	
    @XmlAttribute
    private String name;
    
    @XmlAttribute(name = "request-mapping")
    private String requestMapping;

    @XmlAttribute
    private String type;
    
    @XmlAttribute(name = "all-in-one")
    private boolean allInOne;

	public String getRequestMapping() {
	
		if(UtilsText.isBlank(requestMapping)) {
			return name;
		}
		
		return requestMapping;
	}

	public String getType() {
		if(UtilsText.isBlank(this.type)) {
			return "Controller";
		}
		
		return type;
	}
    
    
    
}