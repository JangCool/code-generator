package code.generator.elements.children;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import code.generator.util.UtilsText;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement(name = "table")
@XmlAccessorType (XmlAccessType.FIELD)
@Setter
@ToString
public class TableElement {
	
	
	private Map<String, ColumnElement> storeColumn;
	
	private String fileName;
	
    @XmlAttribute
    private String name;
    
    @XmlAttribute
    private String sqlsession;
    
    @XmlAttribute
    private String rename;
    
    @XmlAttribute
    private String desc;
    
    @XmlAttribute
    private String alias;
    
    @XmlAttribute
    private String suffix;
    
    @XmlAttribute
    private String prefix;
    
    @XmlAttribute
    private String lock;
    
    
    @XmlElement(name = "column")
    private List<ColumnElement> column = null;
	

	public String getFileName() {
		
		String otherFilename = (getRename() != null) ? getRename(): this.name;
		
		System.out.println(this.name);
		
		if (!UtilsText.isBlank(getPrefix())) {
			otherFilename = UtilsText.concat(getPrefix(), fileName);
		}
		
		if (!UtilsText.isBlank(getSuffix())) {
			otherFilename = UtilsText.concat(fileName, getSuffix());
		}

		return UtilsText.convert2CamelCaseTable(otherFilename, false);
	}

	public String getName() {
		return name;
	}


	public String getSqlsession() {
		return sqlsession;
	}


	public String getRename() {
		return rename;
	}


	public String getDesc() {
		return desc;
	}


	public String getAlias() {
		return alias;
	}


	public String getSuffix() {
		return (this.suffix != null) ?  "_" + this.suffix: null;
	}


	public String getPrefix() {
		return (this.prefix != null) ?  "_" + this.prefix: null;
	}


	public String getLock() {
		return lock;
	}


	public List<ColumnElement> getColumn() {
		return column;
	}
	
	public Map<String, ColumnElement> getCacheColumnResultSetMap() {
		
		if(column == null) {
			return null;
		} else if(storeColumn != null) {
			return storeColumn;
		} else {
			storeColumn = new HashMap<>();
		}
		
		for (ColumnElement columnElement : column) {
			if(columnElement.getName() != null)
			storeColumn.put(columnElement.getName().toUpperCase(), columnElement);
		}
		
		return storeColumn;
	}

    
}