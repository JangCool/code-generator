package code.generator.make;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.generator.common.Const;
import code.generator.common.Global;
import code.generator.common.Log;
import code.generator.elements.ConfigurationElement;
import code.generator.elements.children.TableElement;
import code.generator.elements.children.TablesElement;
import code.generator.exception.NoneTableNameException;
import code.generator.jdbc.ColumnsResultSet;
import code.generator.jdbc.DBInfo;
import code.generator.util.UtilsDate;
import code.generator.util.UtilsText;

//package code.generator.make;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import code.generator.common.Global;
//import code.generator.common.Log;
//import code.generator.elements.ConfigurationElement;
//import code.generator.exception.NoneTableNameException;
//import code.generator.jdbc.ColumnsResultSet;
//import code.generator.jdbc.DBInfo;
//import code.generator.util.UtilsDate;
//import code.generator.util.UtilsText;
//import code.generator.vo.RepositoryVO;
//
public class MakeTable extends BaseMake {

	/**
	 * 각 DB의 컬럼 정보를 가지고 있다.
	 */
	private ColumnsResultSet columnsResultSet;

	public MakeTable(ConfigurationElement configurationElement, ColumnsResultSet columnsResultSet) {
		super(configurationElement);
		this.columnsResultSet = columnsResultSet;
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

		DBInfo dbInfo = columnsResultSet.getDbInfo();

		if (dbInfo.isOracle()) {
			columnsResultSet.callOracleColumn(tableName);
		} else if (dbInfo.isMssql()) {
			columnsResultSet.callMssqlColumn(tableName);
		} else if (dbInfo.isMysql()) {
			columnsResultSet.callMysqlColumn(tableName);
		} else if (dbInfo.isMaria()) {
			columnsResultSet.callMariaColumn(tableName);
		} else if (dbInfo.isH2()) {
			columnsResultSet.callH2DBColumn(tableName);
		}

		if (columnsResultSet.getColumns() == null || columnsResultSet.getColumns() != null && columnsResultSet.getColumns().size() == 0) {
			result = false;
		}

		return result;
	}

	@Override
	public void generator() throws Exception {
		generator(null);
	}

	@Override
	public void generator(String name) throws Exception {
		
		List<TablesElement> tablesList = getConfiguration().getTables();
		int tablesLength = tablesList.size();

		Log.println("");
		
		if (tablesLength == 0) {
			Log.debug("==================================== Repository Generator ======================================");
			Log.warning("<tables> 엘리먼트가 설정되어 있지 않습니다. ");

			return;
		}

		Log.debug(
				"==================================== Repository Generator ======================================");
		Log.debug("지금 Repository, Model, Mapper 파일 생성을 시작 합니다.  ");
		Log.debug("");
		Log.debug("path source = " + Global.getBasePath().getSource());
		Log.debug("path mapper = " + Global.getBasePath().getResources());
		Log.debug("");

		
		for (TablesElement tables : tablesList) {
			
			tables.setDBInfo(columnsResultSet.getDbInfo());
			
			// 지정된 값만 생성 한다. model, dao, mappers
			String only = tables.getOnly();
			
			
			Log.debug("================================================================================================");
			Log.debug("sqlSession		 = " + tables.getSqlSession());
			Log.debug("business   		 = " + tables.getBusiness());
			Log.debug("package    		 = " + tables.getDefaultPackage());
			Log.debug("suffix-package    = " + tables.getSuffixPackage());
			Log.debug("================================================================================================");
			
			
			List<TableElement> tableList = tables.getTable();
			
			for (TableElement table : tableList) {
				
				String orgTableName = table.getName();

				if (orgTableName == null) {
					throw new NoneTableNameException("테이블 명이 존재 하지 않습니다.");
				}
				
				boolean isTable = getTableColumns(orgTableName);
				
				// 테이블이 없을 경우 건너 뛴다.
				if (!isTable) {
					Log.debug(UtilsText.rpad(orgTableName, 30) + " 테이블이 존재 하지 않습니다.");
					continue;
				}
				
				
				if (UtilsText.isBlank(only)) {

					createRepository(tables, table);
					createModel(tables, table);
					// rename, prefix 는 쿼리 내용도 적용될 필요가 없다. 가공되지 않은 원본 테에블명 넘긴다.
					createMappers(tables, table);

				} else {
//					if ("model".equals(only)) {
//						createModel(gv, orgTableName, rename, fileName);
//					} else if ("dao".equals(only)) {
//						createDao(gv, orgTableName, rename, fileName);
//					} else if ("mapper".equals(only)) {
//						createMapper(gv, orgTableName, rename, fileName,
//								element.getElementsByTagName("column"));
//					}
				}
				
				
				
				// 호출된 컬럼 정보들을 제거 한다.
				columnsResultSet.clearColumns();
			}
			
		}
	}

	/**
	 * dao 파일을 생성 한다.
	 * 
	 * @param tables          tables element 정보를 담고 있는 객체
	 * @param table           table element 정보를 담고 있는 객체
	 * @throws Exception
	 */
	private void createRepository(TablesElement tables, TableElement table) throws Exception {

		List<Map<String, String>> columns = columnsResultSet.getColumns();
		List<Map<String, String>> pkColumns = columnsResultSet.getPrimaryColumns();

		
		String fileName = table.getFileName();
		String defaultPackage = tables.getDefaultPackage();
		
		String field = UtilsText.convertFirstLowerCase(fileName);

		Map<String, Object> data = new HashMap<>();
		data.put("fileName", fileName);
		data.put("package", tables.getDefaultPackage());
		data.put("model", UtilsText.concat(replaceModelPackage(defaultPackage), ".", fileName));
		data.put("mapperid", UtilsText.concat(defaultPackage, ".", fileName));
		data.put("field", field);
		data.put("date", UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));
		data.put("sqlsession", UtilsText.capitalize(tables.getSqlSession()));
		data.put("name", table.getName());
		data.put("desc", table.getDesc());
		data.put("table", table);
		data.put("columns", columns);
		data.put("pkColumns", pkColumns);
		data.put("findByPrimaryKey", Sql.findByPrimaryKey(tables, table, columns, pkColumns));
		data.put("findAll", Sql.findAll(tables, table, columns, pkColumns));
		data.put("findBy", Sql.findBy(tables, table, columns, pkColumns));
		data.put("insert", Sql.insert(tables, table, columns, pkColumns));
		data.put("update", Sql.update(tables, table, columns, pkColumns));
		data.put("delete", Sql.delete(tables, table, columns, pkColumns));
		data.put("deleteAll", Sql.deleteAll(tables, table, columns, pkColumns));
		
		String folder = UtilsText.concat(new File(Global.getBasePath().getSource()).getAbsolutePath(), File.separator, defaultPackage.replace(".", "/"));
		String path = UtilsText.concat(folder, File.separator, fileName, "Dao.java");

		String baseFolder = UtilsText.concat(folder, File.separator, "base");
		String basePath = UtilsText.concat(baseFolder, File.separator, "Base", fileName, "Dao.java");

		if (tables.isBaseRepository()) {
			writeTemplate("BaseDao", baseFolder, basePath, data);
			writeTemplate("Dao", folder, path, data);
		} else {
			writeTemplate("DaoNotExtend", folder, path, data);
		}

	}
	
	/**
	 * model 파일을 생성 한다.
	 * 
	 * @param tables          tables element 정보를 담고 있는 객체
	 * @param table           table element 정보를 담고 있는 객체
	 * @throws Exception
	 */
	private void createModel(TablesElement tables, TableElement table) throws Exception {

		List<Map<String, String>> columns = columnsResultSet.getColumns();
		List<Map<String, String>> pkColumns = columnsResultSet.getPrimaryColumns();

		String fileName = table.getFileName();
		String defaultPackage = tables.getDefaultPackage();
		
		String modelPackage = replaceModelPackage(defaultPackage);
		

		Map<String, Object> data = new HashMap<>();
		data.put("fileName", fileName);
		data.put("package", modelPackage);
		data.put("model", dto(table, columns, pkColumns));
		data.put("date", UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));
		data.put("name", table.getName());
		data.put("desc", table.getDesc());

		String folder = UtilsText.concat(new File(Global.getBasePath().getSource()).getAbsolutePath(), File.separator, modelPackage.replace(".", "/"));
		String path = UtilsText.concat(folder, File.separator, fileName, ".java");

		String baseFolder = UtilsText.concat(folder, File.separator, "base");
		String basePath = UtilsText.concat(baseFolder, File.separator, "Base", fileName, ".java");

		if (tables.isBaseModel()) {
			writeTemplate("BaseDto", baseFolder, basePath, data);
			writeTemplate("Dto", folder, path, data);
		} else {
			writeTemplate("DtoNotExtend", folder, path, data);
		}

	}
	
	public static List<Map<String, String>> dto( TableElement table, List<Map<String, String>> columns, List<Map<String, String>> pkColumns) {
		
		List<Map<String, String>> fieldList = new ArrayList<>();
		
		for (Map<String, String> column : columns) {
			
			String orgColumnName = column.get("COLUMN_NAME").toUpperCase();
			String field = UtilsText.convert2CamelCase(orgColumnName);				
			String field2 = UtilsText.convert2CamelCaseTable(orgColumnName);				
			String dataType = column.get("DATA_TYPE");
			String comment = column.get(Const.COLUMN_COMMENT);
			
			Map<String,String> fieldMap = new HashMap<>();
			fieldMap.put("field", field);
			fieldMap.put("field2", field2);
			fieldMap.put("comment", comment);
			fieldMap.put("javaType", Sql.javaType(dataType));
			
			fieldList.add(fieldMap);
		}
		
		return fieldList;
	}


	/**
	 * mapper.xml 파일을 생성 한다.
	 * 
	 * @param tables          tables element 정보를 담고 있는 객체
	 * @param table           table element 정보를 담고 있는 객체
	 * @throws Exception
	 */
	private void createMappers(TablesElement tables, TableElement table) throws Exception {

		List<Map<String, String>> columns = columnsResultSet.getColumns();
		List<Map<String, String>> pkColumns = columnsResultSet.getPrimaryColumns();


		String fileName = table.getFileName();
		String defaultPackage = tables.getDefaultPackage();
		
		String modelPackage = replaceModelPackage(defaultPackage);
		
		
		Map<String, Object> data = new HashMap<>();
		data.put("fileName", fileName);
		data.put("package", defaultPackage);
		data.put("name", table.getName());
		data.put("desc", table.getDesc());
		data.put("mapperid", UtilsText.concat(defaultPackage, ".", fileName, "Dao"));
		data.put("model", UtilsText.concat(modelPackage, ".", fileName));
		data.put("date", UtilsDate.today(UtilsDate.DEFAULT_DATETIME_PATTERN));
		data.put("resultMap", Sql.resultMap(tables, table, columns, pkColumns));
		data.put("columns", Sql.selectColumns(tables, table, columns));
		data.put("pkcolumns", Sql.bindColumnPrimaryKey(tables, table, pkColumns));
		data.put("findBy", Sql.findBy(tables, table, columns, pkColumns, false));
		data.put("insert", Sql.insert(tables, table, columns, pkColumns, false));
		data.put("update", Sql.update(tables, table, columns, pkColumns, false));
		data.put("delete", Sql.delete(tables, table, columns, pkColumns, false));
		
		String folder = UtilsText.concat(new File(Global.getBasePath().getResources()).getAbsolutePath(), File.separator, tables.getMappersPath());
		String path = UtilsText.concat(folder, File.separator, File.separator, fileName, "Mapper.xml");

		String baseFolder = UtilsText.concat(folder, File.separator, "base");
		String basePath = UtilsText.concat(baseFolder, File.separator, "Base", fileName, "Mapper.xml");

		if (tables.isBaseMappers()) {
			writeTemplate("BaseMapper", baseFolder, basePath, data);
			writeTemplate("Mapper", folder, path, data);
		} else {
			writeTemplate("MapperNotExtend", folder, path, data);
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
//
//	public static void main(String[] args) {
//
//		String a = "kr.co.abcmart.dao.member";
//
//		System.out.println(a.replace(".", "-"));
//	}
}
