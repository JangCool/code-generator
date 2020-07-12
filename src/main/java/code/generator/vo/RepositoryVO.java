package code.generator.vo;

import code.generator.common.Const;
import code.generator.common.Global;
import code.generator.elements.children.TablesElement;
import code.generator.jdbc.DBInfo;
import code.generator.util.UtilsText;

/**
 *
 * @since 2019.11.25 01:20
 * @author 장진철
 *
 *         <PRE>
 * -----------------------------------------------------------
 * ※ 개정 이력		
 * yyyy.MM.dd hh:mm - 이름    : 이력
 * -----------------------------------------------------------
 * 2019.11.25 01:20 - 장진철 : 최초 작성
 *         </PRE>
 */
public class RepositoryVO extends BaseVO {

	
	private TablesElement tablesElement;
	
	private TableVO tableVO;
	
	private DBInfo dBInfo;

	private String orgDefaultPackage;
	private String sqlSession;

	public RepositoryVO(TablesElement tablesElement) {
		
		this.tablesElement = tablesElement;

		super.setDefaultPackage(
			UtilsText.isBlank(tablesElement.getDefaultPackage()) ? 
				Global.getBasePackage().getRepository() : tablesElement.getDefaultPackage()
		);
		
		super.setBusiness(tablesElement.getBusiness());
		super.setSuffixPackage(tablesElement.getSuffixPackage());
		
		this.orgDefaultPackage = super.getDefaultPackage();
		this.sqlSession = (tablesElement.getSqlsession() == null) ? Global.getSqlSession().getName() : tablesElement.getSqlsession();
		
	}

	public TableVO getTableVO() {
		return tableVO;
	}

	public void setTableVO(TableVO tableVO) {
		this.tableVO = tableVO;
	}

	public String getOrgDefaultPackage() {
		return orgDefaultPackage;
	}
	
	/**
	 * Repository > Dao 파일 생성 시 Base파일을 포함하여 생성할지 여부
	 * 
	 * @return boolean
	 */
	public boolean isBaseRepository() {
		return tablesElement.isBaseRepository();
	}
	
	/**
	 * Model > Dto or Entity 파일 생성 시 Base파일을 포함하여 생성할지 여부
	 * 
	 * @return boolean
	 */
	public boolean isBaseModel() {
		return tablesElement.isBaseModel();
	}

	/**
	 * Mappers > Mapper 파일 생성 시 Base파일을 포함하여 생성할지 여부
	 * 
	 * @return boolean
	 */
	public boolean isBaseMappers() {
		return tablesElement.isBaseMappers();
	}

	public DBInfo getdBInfo() {
		return dBInfo;
	}

	public void setdBInfo(DBInfo dBInfo) {
		this.dBInfo = dBInfo;
	}

	public String getDefaultPackage() {

		String returnValue = super.getDefaultPackage();

		if (!UtilsText.isBlank(getBusiness())) {
			returnValue = returnValue.concat(".").concat(getBusiness());
		}

		if (!UtilsText.isBlank(getSuffixPackage())) {
			returnValue = returnValue.concat(".").concat(getSuffixPackage());
		}

		if (!UtilsText.isBlank(getSqlSession())) {
			returnValue = returnValue.concat(".").concat(getSqlSession());
		}

		return returnValue;
	}

	public String getMapperPackage() {

		String returnValue = "";

		if (!UtilsText.isBlank(getSqlSession())) {
			returnValue = returnValue.concat(".").concat(getSqlSession());
		}

		if (!UtilsText.isBlank(getBusiness())) {
			returnValue = returnValue.concat(".").concat(getBusiness());
		}

		return returnValue;
	}

	public String getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(String sqlSession) {
		this.sqlSession = sqlSession;
	}

}
