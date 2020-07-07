package code.generator.elements;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import code.generator.elements.children.ControllersElement;
import code.generator.elements.children.JavascriptsElement;
import code.generator.elements.children.JdbcElement;
import code.generator.elements.children.PropertyElement;
import code.generator.elements.children.ServicesElement;
import code.generator.elements.children.TablesElement;
import code.generator.elements.children.ViewsElement;
import lombok.Getter;
import lombok.ToString;

@XmlRootElement(name = "configuration")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
@ToString
public class ConfigurationElement {
	
	@XmlElementWrapper(name = "properties")
    @XmlElement(name = "property")
    private List<PropertyElement> property = null;
    
    @XmlElement(name = "jdbc")
    private JdbcElement jdbc = null;
    
    @XmlElement(name = "controllers")
    private List<ControllersElement> controllers = null;
    
    @XmlElement(name = "services")
    private List<ServicesElement> services = null;
    
    @XmlElement(name = "views")
    private List<ViewsElement> views = null;
    
    @XmlElement(name = "javascripts")
    private List<JavascriptsElement> javascripts = null;

    @XmlElement(name = "tables")
    private List<TablesElement> tables = null;
}
