package code.generator.make;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import code.generator.common.Global;
import code.generator.common.Log;
import code.generator.exception.NoneTableNameException;
import code.generator.jdbc.ColumnsResultSet;
import code.generator.jdbc.DBInfo;
import code.generator.parser.XmlParser;
import code.generator.util.UtilsDate;
import code.generator.util.UtilsText;
import code.generator.vo.RepositoryVO;

public class MakeDaoMapper extends BaseMake {

	private ColumnsResultSet processSql;
	private XmlParser xmlParser;

	public MakeDaoMapper() {
		super();
	}

	public MakeDaoMapper(XmlParser xmlParser, ColumnsResultSet processSql) {
		super(xmlParser);
		this.processSql = processSql;
		this.xmlParser = xmlParser;
	}

	/**
	 * DB 에 접속 하여 해당 Table의 컬럼 정보를 가져 온다. 테이블이 있다면 컬럼 정보들을 가져 올 것이고 없다면 컬럼 정보를 가지고
	 * 오지 못한다. 이 조건으로 테이블 유무를 결정 한다.
	 * 
	 * @param tableName 테이블 이름
	 * @return boolean
	 */
	public boolean getTableColumns(String tableName) {

		boolean result = true;

		DBInfo dbInfo = processSql.getDbInfo();

		if (dbInfo.isOracle()) {
			processSql.callOracleColumn(tableName);
		} else if (dbInfo.isMssql()) {
			processSql.callMssqlColumn(tableName);
		} else if (dbInfo.isMysql()) {
			processSql.callMysqlColumn(tableName);
		} else if (dbInfo.isMaria()) {
			processSql.callMariaColumn(tableName);
		}

		if (processSql.getColumns() == null || processSql.getColumns() != null && processSql.getColumns().size() == 0) {
			result = false;
		}

		return result;
	}

	@Override
	public void generator() throws Exception {
		generator(null);
	}

	@Override
	public void generator(String newfileName) throws Exception {

		NodeList tables = xmlParser.getDoc().getElementsByTagName("tables");

		int tablesLength = tables.getLength();

		if (tablesLength > 0) {

			Log.println("");
			Log.debug(
					"==================================== Repository Generator ======================================");
			Log.debug("지금 Dao, Model, Mapper 파일 생성을 시작 합니다.  ");
			Log.debug("");
			Log.debug("path mapper = " + Global.getPath().getMapper());
			Log.debug("path source = " + Global.getPath().getSource());
		}

		for (int i = 0; i < tablesLength; i++) {

			Node nodeTables = tables.item(i);
			Element elementTables = (Element) nodeTables;

			String only = elementTables.getAttribute("only");

			NodeList childTables = elementTables.getElementsByTagName("table");
			int childTablesLength = childTables.getLength();

			RepositoryVO gv = new RepositoryVO();

			String tablesPkg = getPropertyKey(elementTables.getAttribute("package"));
			String tablesSuffixPkg = getPropertyKey(elementTables.getAttribute("suffix-package"));
			String business = getPropertyKey(elementTables.getAttribute("business"));
			String sqlSession = getPropertyKey(elementTables.getAttribute("sqlsession"));

			String baseDao = elementTables.getAttribute("base-dao");
			String baseDto = elementTables.getAttribute("base-dto");
			String baseMapper = elementTables.getAttribute("base-mapper");

			if (UtilsText.isBlank(tablesPkg)) {
				tablesPkg = Global.getDaoPkg();
			}

			if (!"N".equals(Global.getSqlSessionUseYn()) && UtilsText.isBlank(sqlSession)) {
				sqlSession = Global.getSqlSession();
			}

			gv.setBaseDao((UtilsText.isBlank(baseDao) ? true : "true".equals(baseDao)));
			gv.setBaseDto((UtilsText.isBlank(baseDao) ? true : "true".equals(baseDto)));
			gv.setBaseMapper((UtilsText.isBlank(baseDao) ? true : "true".equals(baseMapper)));

			gv.setOrgDaoPkg(tablesPkg);
			gv.setPkg(gv.getOrgDaoPkg());
			gv.setSuffixPkg(tablesSuffixPkg);
			gv.setBusiness(business);
			gv.setSqlSession(sqlSession);
			gv.setRegDateColumn(Global.getRegDateColumn());
			gv.setModDateColumn(Global.getModDateColumn());

			Log.debug(
					"================================================================================================");
			Log.debug("sqlSession		 = " + gv.getSqlSession());
			Log.debug("business   		 = " + gv.getBusiness());
			Log.debug("package    		 = " + gv.getPkg());
			Log.debug("suffix-package    = " + gv.getSuffixPkg());
			Log.debug(
					"================================================================================================");

			for (int j = 0; j < childTablesLength; j++) {
				if (childTables.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) childTables.item(j);

					if ("table".equals(element.getTagName())) {

						String orgTableName = element.getAttribute("name");

						if (orgTableName == null) {
							throw new NoneTableNameException("테이블 명이 존재 하지 않습니다.");
						}

						boolean isTable = getTableColumns(orgTableName);

						// 테이블이 없을 경우 건너 뛴다.
						if (!isTable) {
							Log.debug(UtilsText.rpad(orgTableName, 30) + " 테이블이 존재 하지 않습니다.");
							continue;
						}

						gv.setdBInfo(processSql.getDbInfo());

						gv.setDesc(getPropertyKey(element.getAttribute("desc")));

						gv.setAlias(getPropertyKey(element.getAttribute("alias")));

						gv.setPrefix(getPropertyKey(element.getAttribute("prefix")));
						if (!UtilsText.isBlank(element.getAttribute("prefix"))) {
							gv.setPrefix(gv.getPrefix().concat("_"));
						}
						gv.setSuffix(getPropertyKey(element.getAttribute("suffix")));
						if (!UtilsText.isBlank(element.getAttribute("suffix"))) {
							gv.setSuffix("_".concat(gv.getSuffix()));
						}

						String rename = getPropertyKey(element.getAttribute("rename"));

						if (UtilsText.isBlank(rename)) {
							rename = orgTableName;
						}

						if (!UtilsText.isBlank(gv.getPrefix())) {
							rename = UtilsText.concat(gv.getPrefix(), orgTableName);
						}
						if (!UtilsText.isBlank(rename)) {
							rename = UtilsText.concat(rename);
						}
						if (!UtilsText.isBlank(gv.getSuffix())) {
							rename = UtilsText.concat(orgTableName, gv.getSuffix());
						}

						gv.setLock(getPropertyKey(element.getAttribute("lock")));

						String fileName = UtilsText.convert2CamelCaseTable(rename,
								(rename.indexOf('_') < 0) ? true : false);

						if (UtilsText.isBlank(only)) {

							createDao(gv, orgTableName, rename, fileName);
							createModel(gv, orgTableName, rename, fileName);
							// rename, prefix 는 쿼리 내용도 적용될 필요가 없다. 가공되지 않은 원본 테에블명 넘긴다.
							createMapper(gv, orgTableName, element.getAttribute("name"), fileName,
									element.getElementsByTagName("column"));

						} else {
							if ("model".equals(only)) {
								createModel(gv, orgTableName, rename, fileName);
							} else if ("dao".equals(only)) {
								createDao(gv, orgTableName, rename, fileName);
							} else if ("mapper".equals(only)) {
								createMapper(gv, orgTableName, rename, fileName,
										element.getElementsByTagName("column"));
							}
						}

						// 호출된 컬럼 정보들을 제거 한다.
						processSql.clearColumns();
					}
				}
			}

		}

	}

	/**
	 * dao 파일을 생성 한다.
	 * 
	 * @param gv           생성에 필요한 정보를 담고 있는 GeneratorVO 객체
	 * @param orgTableName db에 존재하는 테이블명
	 * @param tableName    camelcase로 변형된 테이블 명.
	 * @throws Exception
	 */
	private void createDao(RepositoryVO gv, String orgTableName, String rename, String fileName) throws Exception {

		String field = UtilsText.convert2CamelCase(rename);

		Map<String, String> data = new HashMap<>();
		data.put("fileName", fileName);
		data.put("package", gv.getPkg());
		data.put("model", UtilsText.concat(replaceModelPackage(gv.getPkg()), ".", fileName));
		data.put("mapperid", UtilsText.concat(gv.getPkg(), ".", fileName));
		data.put("field", field);
		data.put("date", UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));
		data.put("sqlsession", UtilsText.capitalize(gv.getSqlSession()));
		data.put("tableName", orgTableName);
		data.put("desc", gv.getDesc());

		String folder = UtilsText.concat(getPathSources().getAbsolutePath(), File.separator,
				gv.getPkg().replace(".", "/"));
		String path = UtilsText.concat(folder, File.separator, fileName, "Dao.java");

		String baseFolder = UtilsText.concat(folder, File.separator, "base");
		String basePath = UtilsText.concat(baseFolder, File.separator, "Base", fileName, "Dao.java");

		if (gv.isBaseDao()) {
			writeTemplate("BaseDao", baseFolder, basePath, data);
			writeTemplate("Dao", folder, path, data);
		} else {
			writeTemplate("DaoNotExtend", folder, path, data);
		}

	}

	/**
	 * mapper 파일을 생성 한다.
	 * 
	 * @param gv           생성에 필요한 정보를 담고 있는 GeneratorVO 객체
	 * @param orgTableName db에 존재하는 테이블명
	 * @param tableName    camelcase로 변형된 테이블 명.
	 * @throws Exception
	 */
	public void createMapper(RepositoryVO repositoryVO, String orgTableName, String rename, String fileName,
			NodeList columnNodeList) throws Exception {

		List<Map<String, String>> columns = processSql.getColumns();
		List<Map<String, String>> pkColumns = processSql.getPrimaryColumns();

		Map<String, String> data = new HashMap<>();
		data.put("fileName", fileName);
		data.put("package", repositoryVO.getPkg());
		data.put("columns", PreparedSql.columns(orgTableName, repositoryVO, columns, pkColumns, columnNodeList));
		data.put("pkcolumns", PreparedSql.pkColumns(orgTableName, repositoryVO, columns, pkColumns, columnNodeList));
		data.put("selectByPrimaryKey",
				PreparedSql.selectByPrimaryKey(orgTableName, repositoryVO, columns, pkColumns, columnNodeList));
		data.put("select", PreparedSql.select(orgTableName, repositoryVO, columns, pkColumns, columnNodeList,
				repositoryVO.getLock()));
		data.put("insert", PreparedSql.insert(orgTableName, repositoryVO, columns, pkColumns, columnNodeList));
		data.put("insertKeyGenerator", PreparedSql.getAutoIncrement(columns));
		data.put("update", PreparedSql.update(orgTableName, repositoryVO, columns, pkColumns, columnNodeList));
		data.put("delete", PreparedSql.delete(orgTableName, repositoryVO, columns, pkColumns, columnNodeList));
		data.put("model", UtilsText.concat(replaceModelPackage(repositoryVO.getPkg()), ".", fileName));
		data.put("mapperid", UtilsText.concat(repositoryVO.getPkg(), ".", fileName, "Dao"));
		data.put("date", UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));
		data.put("tableName", orgTableName);
		data.put("desc", repositoryVO.getDesc());
		data.put("resultMap", PreparedSql.resultMap(orgTableName, repositoryVO, columns, pkColumns, columnNodeList));

		String folder = UtilsText.concat(getPathMappers().getAbsolutePath(), File.separator,
				repositoryVO.getMapperPkg().replace(".", "/"));
		String path = UtilsText.concat(folder, File.separator, File.separator, fileName, "Mapper.xml");

		String baseFolder = UtilsText.concat(folder, File.separator, "base");
		String basePath = UtilsText.concat(baseFolder, File.separator, "Base", fileName, "Mapper.xml");

		if (repositoryVO.isBaseMapper()) {
			writeTemplate("BaseMapper", baseFolder, basePath, data);
			writeTemplate("Mapper", folder, path, data);
		} else {
			writeTemplate("MapperNotExtend", folder, path, data);
		}
	}

	/**
	 * model 파일을 생성 한다.
	 * 
	 * @param gv           생성에 필요한 정보를 담고 있는 GeneratorVO 객체
	 * @param orgTableName db에 존재하는 테이블명
	 * @param tableName    camelcase로 변형된 테이블 명.
	 * @throws Exception
	 */
	public void createModel(RepositoryVO gv, String orgTableName, String rename, String fileName) throws Exception {

		List<Map<String, String>> columns = processSql.getColumns();
		List<Map<String, String>> pkColumns = processSql.getPrimaryColumns();

		String pkgModel = replaceModelPackage(gv.getPkg());

		Map<String, Object> data = new HashMap<>();
		data.put("fileName", fileName);
		data.put("package", pkgModel);
		data.put("model", PreparedDto.dto(orgTableName, pkgModel, columns, pkColumns));
		data.put("date", UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));
		data.put("tableName", orgTableName);
		data.put("desc", gv.getDesc());

		String folder = UtilsText.concat(getPathSources().getAbsolutePath(), File.separator,
				pkgModel.replace(".", "/"));
		String path = UtilsText.concat(folder, File.separator, fileName, ".java");

		String baseFolder = UtilsText.concat(folder, File.separator, "base");
		String basePath = UtilsText.concat(baseFolder, File.separator, "Base", fileName, ".java");

		if (gv.isBaseDto()) {
			writeTemplate("BaseDto", baseFolder, basePath, data);
			writeTemplate("Dto", folder, path, data);
		} else {
			writeTemplate("DtoNotExtend", folder, path, data);
		}

	}

	/**
	 * Dao를 만든 직후 Dto도 만들기 위해 패키지명 .dto로 변경 한다.
	 * 
	 * @param newPackage
	 * @return
	 */
	private static String replaceModelPackage(String newPackage) {

		newPackage = newPackage.replace(".repository", ".model");
		newPackage = newPackage.replace(".dao", ".model");
		return newPackage;
	}

	public static void main(String[] args) {

		String a = "kr.co.abcmart.dao.member";

		System.out.println(a.replace(".", "-"));
	}
}
