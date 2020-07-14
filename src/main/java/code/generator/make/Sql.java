package code.generator.make;

import java.util.List;
import java.util.Map;

import code.generator.common.Const;
import code.generator.common.Global;
import code.generator.elements.children.ColumnElement;
import code.generator.elements.children.TableElement;
import code.generator.elements.children.TablesElement;
import code.generator.exception.NotFoundPrimaryKey;
import code.generator.jdbc.DBInfo;
import code.generator.util.UtilsText;

public class Sql {

	/**
	 * SQL 컬럼 데이터 형식을 java 데이터 형식으로 변경한다.
	 * @param dataType SQL 컬럼 데이터 형식
	 * @return String
	 */
	public static String javaType(String dataType) {
		
		String java = null;
		dataType = dataType.toLowerCase();

		switch (dataType) {
			case "number"				: java = "java.lang.Long";				break; //oracle
			case "bigint"				: java = "java.lang.Long";				break;
			case "binary"				: java = "byte[]";						break;
			case "bit"	 				: java = "java.lang.Boolean";			break;
			case "char"	 				: java = "String";						break;
			case "date"					: java = "java.sql.Timestamp";			break;
			case "datetime"				: java = "java.sql.Timestamp";			break;
			case "datetime2"			: java = "java.sql.Timestamp";			break;
			case "decimal"				: java = "java.math.BigDecimal";		break;
			case "float"				: java = "java.lang.Double";			break;
			case "image"				: java = "byte[]";						break;
			case "int"					: java = "java.lang.Integer";			break;
			case "integer"				: java = "java.lang.Integer";			break;
			case "money"				: java = "java.math.BigDecimal";		break;
			case "nchar"				: java = "String";						break;
			case "ntext"				: java = "String";						break;
			case "numeric"				: java = "java.math.BigDecimal";		break;
			case "nvarchar"				: java = "String";						break;
			case "nvarchar2"			: java = "String";						break; //oracle
			case "nvarchar(max)"		: java = "String";						break;
			case "real"					: java = "java.lang.Float";				break;
			case "smalldatetime"		: java = "java.sql.Timestamp";			break;
			case "smallint"				: java = "java.lang.Short";				break;
			case "smallmoney"			: java = "java.math.BigDecimal";		break;
			case "text"					: java = "String";						break;
			case "mediumtext"			: java = "String";						break;
			case "longtext"				: java = "String";						break;
			case "time"					: java = "java.sql.Time";				break;
			case "timestamp"			: java = "byte[]";						break;
			case "tinyint"				: java = "java.lang.Short";				break;
			case "udt"					: java = "byte[]";						break;
			case "uniqueidentifier"		: java = "String";						break;
			case "varbinary"			: java = "byte[]";						break;
			case "varbinary(max)"		: java = "byte[]";						break;
			case "varchar"				: java = "String";						break;
			case "varchar2"				: java = "String";						break; //oracle
			case "varchar(max)"			: java = "String";						break;
			case "xml"					: java = "String";						break;
			case "blob"					: java = "byte[]";						break;
			case "clob"					: java = "byte[]";						break;

		}
		
		return java;
	}
	
	/**
	 * SQL 컬럼 데이터 형식을 Mybatis에서 사용하는 데이터 형식으로 변경한다.
	 * @param dataType SQL 컬럼 데이터 형식
	 * @return String
	 */
	public static String jdbcType(String dataType) {

		String jdbc = null;
		dataType = dataType.toLowerCase();

		switch (dataType) {
		case "bigint":
			jdbc = "BIGINT";
			break;
		case "binary":
			jdbc = "BINARY";
			break;
		case "bit":
			jdbc = "BIT";
			break;
		case "char":
			jdbc = "CHAR";
			break;
		case "date":
			jdbc = "DATE";
			break;
		case "datetime":
			jdbc = "TIMESTAMP";
			break;
		case "datetime2":
			jdbc = "TIMESTAMP";
			break;
		case "decimal":
			jdbc = "DECIMAL";
			break;
		case "double":
			jdbc = "DOUBLE";
			break;
		case "float":
			jdbc = "DOUBLE";
			break;
		case "image":
			jdbc = "LONGVARBINARY";
			break;
		case "int":
			jdbc = "INTEGER";
		case "integer":
			jdbc = "INTEGER";
			break;
		case "money":
			jdbc = "DECIMAL";
			break;
		case "nchar":
			jdbc = "NCHAR";
			break;
		case "ntext":
			jdbc = "LONGNVARCHAR";
			break;
		case "number":
			jdbc = "NUMERIC";
			break;
		case "numeric":
			jdbc = "NUMERIC";
			break;
		case "nvarchar":
			jdbc = "NVARCHAR";
			break;
		case "nvarchar2":
			jdbc = "NVARCHAR";
			break;
		case "nvarchar(max)":
			jdbc = "NVARCHAR";
			break;
		case "real":
			jdbc = "REAL";
			break;
		case "smalldatetime":
			jdbc = "TIMESTAMP";
			break;
		case "smallint":
			jdbc = "SMALLINT";
			break;
		case "smallmoney":
			jdbc = "DECIMAL";
			break;
		case "text":
			jdbc = "LONGVARCHAR";
			break;
		case "mediumtext":
			jdbc = "LONGVARCHAR";
			break;
		case "longtext":
			jdbc = "LONGVARCHAR";
			break;
		case "time":
			jdbc = "TIME";
			break;
		case "timestamp":
			jdbc = "TIMESTAMP";
			break;
		case "tinyint":
			jdbc = "TINYINT";
			break;
		case "udt":
			jdbc = "VARBINARY";
			break;
		case "uniqueidentifier":
			jdbc = "CHAR";
			break;
		case "varbinary":
			jdbc = "VARBINARY";
			break;
		case "varbinary(max)":
			jdbc = "VARBINARY";
			break;
		case "varchar":
			jdbc = "VARCHAR";
			break;
		case "varchar2":
			jdbc = "VARCHAR";
			break;
		case "varchar(max)":
			jdbc = "VARCHAR";
			break;
		case "blob":
			jdbc = "BLOB";
			break;
		case "clob":
			jdbc = "CLOB";
			break;
		}

		return jdbc;
	}
	
	private static StringBuilder newStringBuilder() {
		return new StringBuilder();
	}
	
	public static String selectColumns(TablesElement tables, TableElement table, List<Map<String, String>> columns) {
		
		String columnStr = "";
		if (columns != null) {
		
			int i = 0;
			for (Map<String, String> column : columns) {
		
				String columnName = column.get(Const.COLUMN_NAME);
				
				columnName = getColumnName(table, columnName);
		
				if (i > 0) {
					columnStr += ", ";
				}
				
				columnStr += columnName;
		
				i++;
			}
		}

		return columnStr;
	}
	
	public static String getDateTime(DBInfo dbInfo) {

		if (dbInfo.isOracle()) {
			return "SYSDATE";
		} else if (dbInfo.isMssql()) {
			return "getdate()";
		} else if (dbInfo.isMysql() || dbInfo.isMaria()) {
			return "now(3)";
		}else if(dbInfo.isH2()) {
			return "CURRENT_TIMESTAMP()";
		}

		return null;
	}
	
	/**
	 *<pre>
	 * 컬럼명 약칭을 지정 했을 경우  약칭을 조합하여 반환한다.
	 * ex) alias = t 값으로 지정 될 경우, t.column명
	 *     alias = 없을 경우               , column명
	 * 
	 * @param tableAlias 테이블명 약어
	 * @param columnAlias 컬럼명 약어
	 * @param columnName 컬럼명
	 * @param disabledColumnAlias 컬럼명 alias 적용 여부
	 * @return
	 * </pre>
	 */	
	private static String getColumnName(TableElement table, String columnName, boolean disabledColumnAlias) {
		ColumnElement columnElement  = (table.getCacheColumnResultSetMap() != null) ? table.getCacheColumnResultSetMap().get(columnName) : null;

		String tempColumn = (table.getAlias() != null) ? table.getAlias() + "." + columnName : columnName;
		
		if( columnElement != null && columnElement.getAlias() != null && disabledColumnAlias == true ) {
			tempColumn += " AS "+ columnElement.getAlias();
		}
		
		return tempColumn;
	}
	
	/**
	 *<pre>
	 * 컬럼명 약칭을 지정 했을 경우  약칭을 조합하여 반환한다.
	 * ex) alias = t 값으로 지정 될 경우, t.column명
	 *     alias = 없을 경우               , column명
	 * 
	 * @param tableAlias 테이블명 약어
	 * @param columnAlias 컬럼명 약어
	 * @param columnName 컬럼명
	 * @return
	 * </pre>
	 */	
	private static String getColumnName(TableElement table, String columnName) {
		return getColumnName(table, columnName, true);
	}
	
	/**
	 * 테이블 명을 가져온다. alias값이 있으면 alias를 설정한다.
	 * @param table
	 * @return
	 */
	private static String getTableName(TableElement table) {
		return ((table.getAlias() != null) ? table.getName() + " " + table.getAlias() : table.getName()) + " ";
	}

	
	private static String bindColumnPrimaryKey(TablesElement tables, TableElement table, List<Map<String, String>> pkColumnsRs) throws Exception{

		if(pkColumnsRs == null || (pkColumnsRs != null && pkColumnsRs.size()  == 0)) {
			throw new NotFoundPrimaryKey("Primary Key 정보가 존재하지 않습니다. 필수로 지정하여 주세요.");
		}
		return bindColumn(tables, table, pkColumnsRs, true);
	}
	
	private static String bindColumn(TablesElement tables, TableElement table, List<Map<String, String>> columns, boolean isPrimaryKey) throws Exception{

		String bindColumn = "";
		
		int columnSize = columns.size();
		
		
		for (int i = 0; i < columnSize; i++) {
			
			Map<String, String> column = columns.get(i);
			String columnName = column.get(Const.COLUMN_NAME);
			String dataType = column.get(Const.DATA_TYPE);
			String val = UtilsText.convert2CamelCase(columnName);
			
			if(!isPrimaryKey) {
				bindColumn += "<if test=\' "+val+" != null and "+val+" != \\\"\\\" \'>";
			}
			
			if(i > 0) {
				bindColumn += " AND ";
			}
			
			bindColumn += getColumnName(table, columnName, false) + "=#{" + val;
			
			
			Map<String, ColumnElement> columnElementMap = table.getCacheColumnResultSetMap();
			
			if(columnElementMap != null && columnElementMap.get(columnName) != null) {
				
				ColumnElement columnElement  = columnElementMap.get(columnName);
				
				//jdbcType은 기본으로 설정한다.
				bindColumn += ", jdbcType="+ (UtilsText.isBlank(columnElement.getJdbcType()) ? jdbcType(dataType) : columnElement.getJdbcType());
			
				if (!UtilsText.isBlank(columnElement.getTypeHandler())) {
					bindColumn += ", typeHandler=" + columnElement.getTypeHandler();
				}
				
			} else {
				bindColumn += ", jdbcType=" + jdbcType(dataType);
			}
			
			bindColumn += "} ";
			
			if(!isPrimaryKey) {
				bindColumn += "</if>";
			}
		}
	
		return bindColumn;

	}
	
	private static String bindColumn(TablesElement tables, TableElement table, List<Map<String, String>> columns) throws Exception{
		return bindColumn(tables, table, columns, false);
	}
	
	

	public static String findByPrimaryKey(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		
		String mapperSql = "";
		
		mapperSql += "@Select(\"";
		mapperSql += "SELECT ";
		mapperSql += selectColumns(tables, table, columnsRs) +" ";
		mapperSql += "FROM ";
		mapperSql += getTableName(table);
		mapperSql += "WHERE ";
		mapperSql += bindColumnPrimaryKey(tables, table, pkColumnsRs);

		mapperSql += "\")";
		
		
		return mapperSql;
	}
	
	public static String findAll(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		
		String mapperSql = "";
		
		mapperSql += "@Select(\"";
		mapperSql += "SELECT ";
		mapperSql += selectColumns(tables, table, columnsRs) +" ";
		mapperSql += "FROM ";
		mapperSql += getTableName(table);
		mapperSql += "\")";
		
		
		return mapperSql;
	}
	
	public static String findBy(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		
		String mapperSql = "";
		
		mapperSql += "@Select(\"";
		mapperSql += "<script> ";
		mapperSql += "SELECT ";
		mapperSql += selectColumns(tables, table, columnsRs) +" ";
		mapperSql += "FROM ";
		mapperSql += getTableName(table);
		mapperSql += "WHERE ";
		mapperSql += bindColumn(tables, table, columnsRs);
		mapperSql += "</script> ";
		mapperSql += "\")";
		return mapperSql;
	}
	
	/**
	 * 컬럼이 자동 채번 컬럼인지 판별한다.
	 * @param column column 정보
	 * @return boolean
	 */
	public static boolean isAutoIncrement(Map<String, String> column) {
		String autoIncrement = column.get(Const.COLUMN_AUTO_INCREMENT);
		return (!UtilsText.isBlank(autoIncrement) && "Y".equals(autoIncrement));
	}
	
	/**
	 * 컬럼이 자동 채번 컬럼인지 판별한다.
	 * @param column column 정보
	 * @return boolean
	 */
	public static boolean isExcludeColumn(String columnName, Map<String, String> column) {
		
		
		if(Global.getExcludeColumn() == null) {
			return false;
		}
		
		if(UtilsText.isBlank(Global.getExcludeColumn().getInsert())) {
			return false;
		}
		
		boolean isExclude = false;
		String[] excludeColumns = Global.getExcludeColumn().getInsert().toUpperCase().split("\\,");
		
		for (int j = 0; j < excludeColumns.length; j++) {
			if (columnName.equals(excludeColumns[j].trim().toUpperCase())) {
				isExclude = true;
				break;
			}
		}
		
		return isExclude;
	}

	public static String insertColumns(TablesElement tables, TableElement table, List<Map<String, String>> columns) {
		
		String columnStr = "";
		if (columns != null) {
		
			int i = 0;
			for (Map<String, String> column : columns) {
		
				String columnName = column.get(Const.COLUMN_NAME);
				boolean isAutoIncrement = isAutoIncrement(column);
				boolean isExclude = isExcludeColumn(columnName, column);

				columnName = getColumnName(table, columnName);
		
				if( isAutoIncrement || isExclude ) {
					continue;
				}
				
				if (i > 0) {
					columnStr += ", ";
				}
				
				columnStr += columnName;
		
				i++;
			}
		}

		return columnStr;
	}
	

	
	private static String bindColumnOfInsert(TablesElement tables, TableElement table, List<Map<String, String>> columns) throws Exception{

		String bindColumn = "";
		
		int columnSize = columns.size();
		
		
		for (int i = 0; i < columnSize; i++) {
			
			Map<String, String> column = columns.get(i);
			String orgColumnName = column.get(Const.COLUMN_NAME);
			String columnName = orgColumnName;
			String dataType = column.get(Const.DATA_TYPE).toUpperCase();
			String val = UtilsText.convert2CamelCase(columnName);
			
			boolean isAutoIncrement = isAutoIncrement(column);
			boolean isExclude = isExcludeColumn(columnName, column);
			boolean isTypeString = ("VARCHAR".equals(jdbcType(dataType)) || "VARCHAR2".equals(jdbcType(dataType)));
			boolean isTypeDate = ("DATE".equals(dataType) || "DATETIME".equals(dataType) || "DATETIME2".equals(dataType)  || "TIMESTAMP".equals(dataType));
			
			columnName = getColumnName(table, columnName);
	
			if( isAutoIncrement || isExclude ) {
				continue;
			}
			
			
			if (i > 0) {
				bindColumn += ", ";
			}
			
			String tempBindColumn = "";
			
			tempBindColumn += "#{" + val;
			
			Map<String, ColumnElement> columnElementMap = table.getCacheColumnResultSetMap();
			
			if(columnElementMap != null && columnElementMap.get(columnName) != null) {
				
				ColumnElement columnElement  = columnElementMap.get(columnName);
				
				//jdbcType은 기본으로 설정한다.
				tempBindColumn += ", jdbcType="+ (UtilsText.isBlank(columnElement.getJdbcType()) ? jdbcType(dataType) : columnElement.getJdbcType());
			
				if (!UtilsText.isBlank(columnElement.getTypeHandler())) {
					tempBindColumn += ", typeHandler=" + columnElement.getTypeHandler();
				}
				
			} else {
				tempBindColumn += ", jdbcType=" + jdbcType(dataType);
			}
			
			tempBindColumn += "} ";

			if (isTypeString) {
			
				bindColumn += "<choose> ";
				bindColumn += "<when test=\'" + columnName + " == null  or " + columnName +" == \\\"\\\"'> ";
				bindColumn += "null ";
				bindColumn += "</when> ";
				bindColumn += "<otherwise> ";
				bindColumn += tempBindColumn;
				bindColumn += "</otherwise> ";
				bindColumn += "</choose> ";
			
			}else if (isTypeDate) {
			
					String[] defaultDateColumns = {"REG_DTIME","MODIFY_DTIME"};
			
					boolean isDefaultDate = false;
					for (int j = 0; j < defaultDateColumns.length; j++) {
						if (orgColumnName.equals(defaultDateColumns[j].toUpperCase())) {
							isDefaultDate = true;
							break;
						}
					}
					if (isDefaultDate) {
						bindColumn +=  UtilsText.concat("<choose><when test='", columnName, " != null'>#{", columnName,", jdbcType=", jdbcType(dataType), "}</when><otherwise>",	getDateTime(tables.getDBInfo()), "</otherwise></choose>");
					} else {
						bindColumn += UtilsText.concat("<choose><when test='", columnName, " != null'>#{", columnName,", jdbcType=", jdbcType(dataType), "}</when><otherwise>null</otherwise></choose>");
					}
			} else {
				bindColumn += tempBindColumn;
			}
			
			
		}
	
		return bindColumn;

	}
	
	public static String insert(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		
		String mapperSql = "";
		
		mapperSql += "@Insert(\"";
		mapperSql += "<script> ";
		mapperSql += "INSERT INTO " + table.getName() +" (";
		mapperSql += insertColumns(tables, table, columnsRs) +") ";
		mapperSql += "VALUES (";
		mapperSql += bindColumnOfInsert(tables, table, columnsRs)+") ";
		mapperSql += "</script> ";
		mapperSql += "\")";
		
		return mapperSql;
	}
	
	public static String update(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		
		String mapperSql = "";
		
		mapperSql += "@Update(\"";
		mapperSql += "<script> ";
		mapperSql += "UPDATE " + table.getName() +" SET ";
		mapperSql += updateColumns(tables, table, columnsRs) +") ";
		mapperSql += "WHERE ";
		mapperSql += bindColumnPrimaryKey(tables, table, pkColumnsRs)+") ";
		mapperSql += "</script> ";
		mapperSql += "\")";
		
		return mapperSql;
	}

	private static String updateColumns(TablesElement tables, TableElement table, List<Map<String, String>> columns) {
//		if (!"VARCHAR".equals(jdbcType(dataType)) && !"VARCHAR2".equals(jdbcType(dataType))) {
//
//			if ("date".equals(dataType) || "datetime".equals(dataType) || "datetime2".equals(dataType)) {
//
//				String[] defaultDateColumns = repositoryVO.getModDateColumn().toUpperCase().split("\\,");
//
//				boolean isDefaultDate = false;
//				for (int j = 0; j < defaultDateColumns.length; j++) {
//					if (orgColumnName.equals(defaultDateColumns[j].toUpperCase())) {
//						isDefaultDate = true;
//						break;
//					}
//				}
//				if (isDefaultDate) {
//					sb.append("\t\t\t").append(UtilsText.concat(orgColumnName, "=",
//							getDateTime(repositoryVO.getdBInfo()), ", \n"));
//				} else {
//					sb.append("\t\t\t").append(UtilsText.concat("<if test=\"", columnName, " != null\"> \n"));
//					sb.append("\t\t\t\t").append(orgColumnName).append(" = ").append(
//							UtilsText.concat("#{", columnName, ", jdbcType=", jdbcType(dataType), "}, \n"));
//					sb.append("\t\t\t").append(UtilsText.concat("</if> \n"));
//				}
//			} else {
//				sb.append("\t\t\t").append(UtilsText.concat("<if test=\"", columnName, " != null\"> \n"));
//				sb.append("\t\t\t\t").append(orgColumnName).append(" = ")
//						.append(UtilsText.concat("#{", columnName, ", jdbcType=", jdbcType(dataType), "}, \n"));
//				sb.append("\t\t\t").append(UtilsText.concat("</if> \n"));
//			}
//
//		} else {
//
//			sb.append("\t\t\t").append(UtilsText.concat("<if test=\"", columnName, " != null\"> \n"));
//			sb.append("\t\t\t\t").append(UtilsText.concat("<choose> \n"));
//			sb.append("\t\t\t\t\t").append(UtilsText.concat("<when test=\"", columnName, " == ''\"> \n"));
//			sb.append("\t\t\t\t\t\t").append(orgColumnName).append(" = null, \n");
//			sb.append("\t\t\t\t\t").append(UtilsText.concat("</when> \n"));
//			sb.append("\t\t\t\t\t").append(UtilsText.concat("<otherwise> \n"));
//			sb.append("\t\t\t\t\t\t").append(orgColumnName).append(" = ")
//					.append(UtilsText.concat("#{", columnName, ", jdbcType=", jdbcType(dataType), "}, \n"));
//			sb.append("\t\t\t\t\t").append(UtilsText.concat("</otherwise> \n"));
//			sb.append("\t\t\t\t").append(UtilsText.concat("</choose> \n"));
//			sb.append("\t\t\t").append(UtilsText.concat("</if> \n"));
//		}
		

		String bindColumn = "";
		
		int columnSize = columns.size();
		
		
		for (int i = 0; i < columnSize; i++) {
			
			Map<String, String> column = columns.get(i);
			String orgColumnName = column.get(Const.COLUMN_NAME);
			String columnName = orgColumnName;
			String dataType = column.get(Const.DATA_TYPE).toUpperCase();
			String val = UtilsText.convert2CamelCase(columnName);
			
			boolean isAutoIncrement = isAutoIncrement(column);
			boolean isExclude = isExcludeColumn(columnName, column);
			boolean isTypeString = ("VARCHAR".equals(jdbcType(dataType)) || "VARCHAR2".equals(jdbcType(dataType)));
			boolean isTypeDate = ("DATE".equals(dataType) || "DATETIME".equals(dataType) || "DATETIME2".equals(dataType)  || "TIMESTAMP".equals(dataType));

			columnName = getColumnName(table, columnName);
	
			if( isAutoIncrement || isExclude ) {
				continue;
			}
			
			bindColumn += "<if test=\'"+val+" != null\'>";
			
			if (i > 0) {
				bindColumn += ", ";
			}
			
			String tempBindColumn = "";
			
			tempBindColumn += getColumnName(table, columnName, false) + "=#{" + val;
			
			Map<String, ColumnElement> columnElementMap = table.getCacheColumnResultSetMap();
			
			if(columnElementMap != null && columnElementMap.get(columnName) != null) {
				
				ColumnElement columnElement  = columnElementMap.get(columnName);
				
				//jdbcType은 기본으로 설정한다.
				tempBindColumn += ", jdbcType="+ (UtilsText.isBlank(columnElement.getJdbcType()) ? jdbcType(dataType) : columnElement.getJdbcType());
			
				if (!UtilsText.isBlank(columnElement.getTypeHandler())) {
					tempBindColumn += ", typeHandler=" + columnElement.getTypeHandler();
				}
				
			} else {
				tempBindColumn += ", jdbcType=" + jdbcType(dataType);
			}
			
			tempBindColumn += "} ";

			if (isTypeString) {
				bindColumn += "<choose> ";
				bindColumn += "<when test=\'" + columnName + " == null  or " + columnName +" == \\\"\\\"'> ";
				bindColumn += "null ";
				bindColumn += "</when> ";
				bindColumn += "<otherwise> ";
				bindColumn += tempBindColumn;
				bindColumn += "</otherwise> ";
				bindColumn += "</choose> ";

			
			}else if (isTypeDate) {
			

				String[] defaultDateColumns = {"REG_DTIME","MODIFY_DTIME"};
		
				boolean isDefaultDate = false;
				for (int j = 0; j < defaultDateColumns.length; j++) {
					if (orgColumnName.equals(defaultDateColumns[j].toUpperCase())) {
						isDefaultDate = true;
						break;
					}
				}
				if (isDefaultDate) {
					bindColumn +=  UtilsText.concat("<choose><when test='", columnName, " != null'>#{", columnName,", jdbcType=", jdbcType(dataType), "}</when><otherwise>",	getDateTime(tables.getDBInfo()), "</otherwise></choose>");
				} else {
					bindColumn += UtilsText.concat("<choose><when test='", columnName, " != null'>#{", columnName,", jdbcType=", jdbcType(dataType), "}</when><otherwise>null</otherwise></choose>");
				}
				
			} else {
				bindColumn += tempBindColumn;
			}
			
			bindColumn += "</if>";

			
		}
		return bindColumn;
	}

	public static String delete(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		
		String mapperSql = "";
		
		mapperSql += "@Delete(\"";
		mapperSql += "DELETE FROM " + table.getName() +" ";
		mapperSql += "WHERE ";
		mapperSql += bindColumnPrimaryKey(tables, table, pkColumnsRs);
		mapperSql += "\")";
		
		return mapperSql;
	}
	
	public static String deleteAll(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		
		String mapperSql = "";
		
		mapperSql += "@Delete(\"";
		mapperSql += "DELETE FROM " + table.getName() +" ";
		mapperSql += "\")";
		
		return mapperSql;
	}
}
