package code.generator.elements.children;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import code.generator.common.Global;
import code.generator.jdbc.DBInfo;
import code.generator.util.UtilsText;
import lombok.Setter;
import lombok.ToString;

@XmlRootElement(name = "controllers")
@XmlAccessorType(XmlAccessType.FIELD)
@Setter
@ToString
public class TablesElement {
	
	private DBInfo dBInfo;
	
    @XmlAttribute
    private String business;
    
    @XmlAttribute(name = "package")
    private String defaultPackage;
    
    @XmlAttribute(name = "suffix-package")
    private String suffixPackage;
    
    @XmlAttribute(name = "sqlsession")
    private String sqlSession;
    
    @XmlAttribute(name = "base-repository")
    private boolean baseRepository = true;

    @XmlAttribute(name = "base-model")
    private boolean baseModel = true;

    @XmlAttribute(name = "base-mappers")
    private boolean baseMappers = true;
    
    @XmlAttribute
    private String only;
    
    @XmlElement(name = "table")
    private List<TableElement> table = null;

    
	public String getBusiness() {
		return business;
	}

	public String getDefaultPackage() {
		return UtilsText.isBlank(this.defaultPackage) ? Global.getBasePackage().getRepository() : this.defaultPackage;
	}

	public String getSuffixPackage() {
		return suffixPackage;
	}

	public String getSqlSession() {
		
		return (this.sqlSession == null) ? Global.getSqlSession().getName() : this.sqlSession;
	}

	public boolean isBaseRepository() {
		return baseRepository;
	}

	public boolean isBaseModel() {
		return baseModel;
	}

	public boolean isBaseMappers() {
		return baseMappers;
	}

	public String getOnly() {
		return only;
	}

	public List<TableElement> getTable() {
		return table;
	}

	public DBInfo getDBInfo() {
		return dBInfo;
	}

	public void setDBInfo(DBInfo dBInfo) {
		this.dBInfo = dBInfo;
	}

    
    
    
}
