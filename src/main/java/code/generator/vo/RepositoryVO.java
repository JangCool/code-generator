package code.generator.vo;

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

	private DBInfo dBInfo;

	private String orgDaoPkg;
	private String business;
	private String sqlSession;
	private String desc;
	private String alias;
	private String prefix;
	private String suffix;
	private String lock;

	private String regDateColumn;

	private String modDateColumn;

	private boolean baseDao;
	private boolean baseDto;
	private boolean baseMapper;

	public DBInfo getdBInfo() {
		return dBInfo;
	}

	public void setdBInfo(DBInfo dBInfo) {
		this.dBInfo = dBInfo;
	}

	public String getOrgDaoPkg() {
		return orgDaoPkg;
	}

	public void setOrgDaoPkg(String orgDaoPkg) {
		this.orgDaoPkg = orgDaoPkg;
	}

	public String getPkg() {

		String returnValue = super.getPkg();

		if (!UtilsText.isBlank(getBusiness())) {
			returnValue = returnValue.concat(".").concat(getBusiness());
		}

		if (!UtilsText.isBlank(getSuffixPkg())) {
			returnValue = returnValue.concat(".").concat(getSuffixPkg());
		}

		if (!UtilsText.isBlank(getSqlSession())) {
			returnValue = returnValue.concat(".").concat(getSqlSession());
		}

		return returnValue;
	}

	public String getMapperPkg() {

		String returnValue = "";

		if (!UtilsText.isBlank(getSqlSession())) {
			returnValue = returnValue.concat(".").concat(getSqlSession());
		}

		if (!UtilsText.isBlank(getBusiness())) {
			returnValue = returnValue.concat(".").concat(getBusiness());
		}

		return returnValue;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getSqlSession() {
		return sqlSession;
	}

	public void setSqlSession(String sqlSession) {
		this.sqlSession = sqlSession;
	}

//	public String getRename() {
//		return rename;
//	}
//
//	public void setRename(String rename) {
//		this.rename = rename;
//	}

	public String getDesc() {
		return desc;
	}

	public String getRegDateColumn() {
		return regDateColumn;
	}

	public void setRegDateColumn(String regDateColumn) {
		this.regDateColumn = regDateColumn;
	}

	public String getModDateColumn() {
		return modDateColumn;
	}

	public void setModDateColumn(String modDateColumn) {
		this.modDateColumn = modDateColumn;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getLock() {
		return lock;
	}

	public void setLock(String lock) {
		this.lock = lock;
	}

	public boolean isBaseDao() {
		return baseDao;
	}

	public void setBaseDao(boolean baseDao) {
		this.baseDao = baseDao;
	}

	public boolean isBaseDto() {
		return baseDto;
	}

	public void setBaseDto(boolean baseDto) {
		this.baseDto = baseDto;
	}

	public boolean isBaseMapper() {
		return baseMapper;
	}

	public void setBaseMapper(boolean baseMapper) {
		this.baseMapper = baseMapper;
	}

}
