package code.generator.elements;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import code.generator.elements.jdbc.ControllersElement;
import code.generator.elements.jdbc.JavascriptsElement;
import code.generator.elements.jdbc.JdbcElement;
import code.generator.elements.jdbc.PropertyElement;
import code.generator.elements.jdbc.ServicesElement;
import code.generator.elements.jdbc.TablesElement;
import code.generator.elements.jdbc.ViewsElement;
import code.generator.elements.jdbc.global.GlobalElement;
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
	
    @XmlElement(name = "global")
    private GlobalElement global = null;
    
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
