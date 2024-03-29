package code.generator.make;

import java.util.List;
import java.util.Map;

import code.generator.common.Const;
import code.generator.common.Global;
import code.generator.elements.jdbc.ColumnElement;
import code.generator.elements.jdbc.TableElement;
import code.generator.elements.jdbc.TablesElement;
import code.generator.exception.NotFoundPrimaryKey;
import code.generator.jdbc.DBInfo;
import code.generator.util.UtilsText;

public class Sql {
	
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
				columnStr += "\t\t\t\t\t";

				if (i > 0) {
					columnStr += ", ";
				}
				
				columnStr += columnName;
		
				i++;
				columnStr += "\n";
			}
		}

		return columnStr;
	}
	
	public static String getDateTime(DBInfo dbInfo) {

		if (dbInfo.isOracle()) {
			return "SYSDATE";
		} else if (dbInfo.isMssql()) {
			return "getdate()";
		} else if (dbInfo.isMysql() || dbInfo.isMaria() || dbInfo.isH2()) {
			return "now(3)";
		} else if (dbInfo.isHyperSql() || dbInfo.isPostgreSql()) {
			return "now()";
		}
//		}else if(dbInfo.isH2()) {
//			return "CURRENT_TIMESTAMP()";
//		}

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
	private static String getTableName(TableElement table, DBInfo dbInfo) {
		
		String tableName = ((table.getAlias() != null) ? table.getName() + " " + table.getAlias() : table.getName()) + " ";
		
		if(dbInfo.isPostgreSql()) {
			return tableName.toLowerCase();
		}
		
		return tableName;
	}

	
	public static String bindColumnPrimaryKey(TablesElement tables, TableElement table, List<Map<String, String>> pkColumnsRs) throws Exception{

		if(pkColumnsRs == null || (pkColumnsRs != null && pkColumnsRs.size()  == 0)) {
			if(table.isRequiredPrimary()) {
				throw new NotFoundPrimaryKey("Primary Key 정보가 존재하지 않습니다. 필수로 지정하여 주세요.");				
			}
		}
		return bindColumn(tables, table, pkColumnsRs, true);
	}
	
	public static String bindColumn(TablesElement tables, TableElement table, List<Map<String, String>> columns, boolean isPrimaryKey) throws Exception{

		String bindColumn = "";
		
		int columnSize = columns.size();
		
		
		for (int i = 0; i < columnSize; i++) {
			
			Map<String, String> column = columns.get(i);
			String columnName = column.get(Const.COLUMN_NAME);
			String dataType = column.get(Const.DATA_TYPE);
			String val = UtilsText.convert2CamelCase(columnName);
			
//			System.out.println(dataType);
			boolean isTypeDate = ("DATE".equals(dataType.toUpperCase()) || "DATETIME".equals(dataType.toUpperCase()) || "DATETIME2".equals(dataType.toUpperCase())  || "TIMESTAMP".equals(dataType.toUpperCase()));

			if(!isPrimaryKey) {
				bindColumn += "\t\t\t\t";
				if(!isTypeDate) {
					bindColumn += "\t<if test=\' "+val+" != null and "+val+" != \\\"\\\" \'>\n";
				}else {
					bindColumn += "\t<if test=\' "+val+" != null \'>\n";
				}
			}
			
			bindColumn += "\t\t\t\t";
			bindColumn += " \t\t";

			if(i > 0) {
				bindColumn += " AND ";
			}
			
			bindColumn += getColumnName(table, columnName, false) + "=#{" + val;
			
			
			Map<String, ColumnElement> columnElementMap = table.getCacheColumnResultSetMap();
			
			if(columnElementMap != null && columnElementMap.get(columnName) != null) {
				
				ColumnElement columnElement  = columnElementMap.get(columnName);
				
				//jdbcType은 기본으로 설정한다.
				bindColumn += ", jdbcType="+ (UtilsText.isBlank(columnElement.getJdbcType()) ? TableOperation.jdbcType(dataType) : columnElement.getJdbcType());
			
				if (!UtilsText.isBlank(columnElement.getTypeHandler())) {
					bindColumn += ", typeHandler=" + columnElement.getTypeHandler();
				}
				
			} else {
				bindColumn += ", jdbcType=" + TableOperation.jdbcType(dataType);
			}
			
			bindColumn += "} \n";
			
			if(!isPrimaryKey) {
				bindColumn += "\t\t\t\t\t</if>\n";
			}
		}
	
		return bindColumn;

	}
	
	private static String bindColumn(TablesElement tables, TableElement table, List<Map<String, String>> columns) throws Exception{
		return bindColumn(tables, table, columns, false);
	}
	
	

	public static String findByPrimaryKey(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		
		String mapperSql = "";
		
		mapperSql += "@Select(\"\"\"\n";
		mapperSql += "\t<script> \n";		
		mapperSql += "\t\t\t\tSELECT \n";
		mapperSql += selectColumns(tables, table, columnsRs) +" ";
		mapperSql += "\t\t\t\t"+"FROM \n";
		mapperSql += "\t\t\t\t"+"\t" + getTableName(table, tables.getDBInfo()) +"\n";
		mapperSql += "\t\t\t\t"+"WHERE \n";
		mapperSql += "\t\t\t\t"+"\t<trim prefixOverrides=\\\"AND\\\"> \n";
		mapperSql += bindColumnPrimaryKey(tables, table, pkColumnsRs);
		mapperSql += "\t\t\t\t"+"\t</trim> \n";
		mapperSql += "\t</script> \n";
		mapperSql += "\t\"\"\")";
		
		
		return mapperSql;
	}
	
	public static String findAll(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		
		String mapperSql = "";
		
		mapperSql += "@Select(\"\"\"\n";
		mapperSql += "\t<script> \n";		
		mapperSql += "\t\t\t\tSELECT \n";
		mapperSql += selectColumns(tables, table, columnsRs);
		mapperSql += "\t\t\t\t"+"FROM \n";
		mapperSql += "\t\t\t\t"+"\t" + getTableName(table, tables.getDBInfo()) +"\n";
		mapperSql += "\t</script> \n";
		mapperSql += "\t\"\"\")";
		
		
		return mapperSql;
	}
	
	public static String findBy(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		return findBy(tables, table, columnsRs, pkColumnsRs, true);
	}
	public static String findBy(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs, boolean isAnnotation) throws Exception {
		
		String mapperSql = "";
		
		if(isAnnotation) {
			mapperSql += "@Select(\"\"\"\n";
			mapperSql += "\t<script> \n";			
		}
		mapperSql += "\t\t\t\tSELECT \n";
		mapperSql += selectColumns(tables, table, columnsRs);
		mapperSql += "\t\t\t\t"+"FROM \n";
		mapperSql += "\t\t\t\t"+"\t" + getTableName(table, tables.getDBInfo()) +"\n";
		mapperSql += "\t\t\t\t"+"WHERE \n";
		mapperSql += "\t\t\t\t"+"\t<trim prefixOverrides=\\\"AND\\\"> \n";
		mapperSql += bindColumn(tables, table, columnsRs);
		mapperSql += "\t\t\t\t"+"\t</trim> \n";

		if(isAnnotation) {
			mapperSql += "\t</script> \n";
			mapperSql += "\t\"\"\")";
		}
		
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
			boolean isTypeString = ("VARCHAR".equals(TableOperation.jdbcType(dataType)) || "VARCHAR2".equals(TableOperation.jdbcType(dataType)));
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
				tempBindColumn += ", jdbcType="+ (UtilsText.isBlank(columnElement.getJdbcType()) ? TableOperation.jdbcType(dataType) : columnElement.getJdbcType());
			
				if (!UtilsText.isBlank(columnElement.getTypeHandler())) {
					tempBindColumn += ", typeHandler=" + columnElement.getTypeHandler();
				}
				
			} else {
				tempBindColumn += ", jdbcType=" + TableOperation.jdbcType(dataType);
			}
			
			tempBindColumn += "} ";

			if (isTypeString) {
			
				bindColumn += "<choose> ";
				bindColumn += "<when test=\'" + val + " == null  or " + val +" == \\\"\\\"'> ";
				bindColumn += "null ";
				bindColumn += "</when> ";
				bindColumn += "<otherwise> ";
				bindColumn += tempBindColumn;
				bindColumn += "</otherwise> ";
				bindColumn += "</choose> ";
			
			}else if (isTypeDate) {
			
					String[] defaultDateColumns = {"REG_DATETIME","MOD_DATETIME"};
			
					boolean isDefaultDate = false;
					for (int j = 0; j < defaultDateColumns.length; j++) {
						if (orgColumnName.equals(defaultDateColumns[j].toUpperCase())) {
							isDefaultDate = true;
							break;
						}
					}
					if (isDefaultDate) {
						bindColumn +=  UtilsText.concat("<choose><when test='", val, " != null'>#{", val,", jdbcType=", TableOperation.jdbcType(dataType), "}</when><otherwise>",	getDateTime(tables.getDBInfo()), "</otherwise></choose>");
					} else {
						bindColumn += UtilsText.concat("<choose><when test='", val, " != null'>#{", val,", jdbcType=", TableOperation.jdbcType(dataType), "}</when><otherwise>null</otherwise></choose>");
					}
			} else {
				bindColumn += tempBindColumn;
			}
			
			
		}
	
		return bindColumn;

	}
	
	public static String insert(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		return insert(tables, table, columnsRs, pkColumnsRs, true);
	}
		
	public static String insert(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs, boolean isAnnotation) throws Exception {
		
		String mapperSql = "";
		
		if(isAnnotation) {
			mapperSql += "@Insert(\"\"\"\n";
			mapperSql += "\t<script> \n";			
		}
		
		mapperSql += "\t\t\t\t"+"INSERT INTO " + table.getName() +" \n";
		mapperSql += "\t\t\t\t"+"\t\t(" + insertColumns(tables, table, columnsRs) + ") \n";
		mapperSql += "\t\t\t\t"+"VALUES\n";
		mapperSql += "\t\t\t\t"+"\t\t(" + bindColumnOfInsert(tables, table, columnsRs) + ") \n";

		if(isAnnotation) {
			mapperSql += "\t</script> \n";
			mapperSql += "\t\"\"\")";
		}
		
		return mapperSql;
	}
	
	public static String updateByPrimaryKey(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		return updateByPrimaryKey(tables, table, columnsRs, pkColumnsRs, true);
	}
	
	public static String updateByPrimaryKey(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs, boolean isAnnotation) throws Exception {
		
		String mapperSql = "";
		
		if(isAnnotation) {
			mapperSql += "@Update(\"\"\"\n";
			mapperSql += "\t<script> \n";		
		}
		
		mapperSql += "\t\t\t\t" + "UPDATE " + table.getName() +" SET \n";
		mapperSql += "\t\t\t\t"+"\t<trim prefixOverrides=\\\",\\\"> \n";
		mapperSql += updateColumns(tables, table, columnsRs, pkColumnsRs);
		mapperSql += "\t\t\t\t"+"\t</trim> \n";
		mapperSql += "\t\t\t\t" + "WHERE \n";
		mapperSql += "\t\t\t\t"+"\t<trim prefixOverrides=\\\"AND\\\"> \n";
		mapperSql += bindColumnPrimaryKey(tables, table, pkColumnsRs);
		mapperSql += "\t\t\t\t"+"\t</trim> \n";

		if(isAnnotation) {
			mapperSql += "\t</script> \n";
			mapperSql += "\t\"\"\")";
		}
		return mapperSql;
	}

	public static String update(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		return update(tables, table, columnsRs, pkColumnsRs, true);
	}
	
	public static String update(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs, boolean isAnnotation) throws Exception {
		
		String mapperSql = "";
		
		if(isAnnotation) {
			mapperSql += "@Update(\"\"\"\n";
			mapperSql += "\t<script> \n";		
		}
		
		mapperSql += "\t\t\t\t" + "UPDATE " + table.getName() +" SET \n";
		mapperSql += "\t\t\t\t"+"\t<trim prefixOverrides=\\\",\\\"> \n";
		mapperSql += updateColumns(tables, table, columnsRs, pkColumnsRs);
		mapperSql += "\t\t\t\t"+"\t</trim> \n";
		mapperSql += "\t\t\t\t" + "WHERE \n";
		mapperSql += "\t\t\t\t"+"\t<trim prefixOverrides=\\\"AND\\\"> \n";
		mapperSql += bindColumn(tables, table, columnsRs);
		mapperSql += "\t\t\t\t"+"\t</trim> \n";

		if(isAnnotation) {
			mapperSql += "\t</script> \n";
			mapperSql += "\t\"\"\")";
		}
		return mapperSql;
	}

	private static String updateColumns(TablesElement tables, TableElement table, List<Map<String, String>> columns, List<Map<String, String>> pkColumns) {

		String bindColumn = "";
		
		int columnSize = columns.size();
		
		boolean isFirst = true;
		
		for (int i = 0; i < columnSize; i++) {
			
			Map<String, String> column = columns.get(i);
			String orgColumnName = column.get(Const.COLUMN_NAME);
			String columnName = orgColumnName;
			String dataType = column.get(Const.DATA_TYPE).toUpperCase();
			String val = UtilsText.convert2CamelCase(columnName);
			
			boolean isAutoIncrement = isAutoIncrement(column);
			boolean isExclude = isExcludeColumn(columnName, column);
			boolean isTypeString = ("VARCHAR".equals(TableOperation.jdbcType(dataType)) || "VARCHAR2".equals(TableOperation.jdbcType(dataType)));
			boolean isTypeDate = ("DATE".equals(dataType) || "DATETIME".equals(dataType) || "DATETIME2".equals(dataType)  || "TIMESTAMP".equals(dataType));

			columnName = getColumnName(table, columnName);
	
			if( isAutoIncrement || isExclude ) {
				continue;
			}
			
			boolean isPk = false;
			if (pkColumns != null && pkColumns.size() > 0) {

				for (Map<String, String> pkColumn : pkColumns) {

					String orgPkColumnName = pkColumn.get(Const.COLUMN_NAME).toUpperCase();

					if (orgColumnName.equals(orgPkColumnName)) {
						isPk = true;
						break;
					}
				}
			}
			
			//pk일 경우 업데이트 컬럼에 넣지 않는다.
			if(isPk) {
				continue;
			}
			
			bindColumn += "\t\t\t\t";
			bindColumn += "<if test=\'"+val+" != null\'>\n";
			
			bindColumn += "\t\t\t\t\t";

			if (!isFirst) {
				bindColumn += ", ";
			} else {
				isFirst = false;
			}
			
			String tempBindColumn = "";
			
			tempBindColumn += getColumnName(table, columnName, false) + "=#{" + val;
			
			Map<String, ColumnElement> columnElementMap = table.getCacheColumnResultSetMap();
			
			if(columnElementMap != null && columnElementMap.get(columnName) != null) {
				
				ColumnElement columnElement  = columnElementMap.get(columnName);
				
				//jdbcType은 기본으로 설정한다.
				tempBindColumn += ", jdbcType="+ (UtilsText.isBlank(columnElement.getJdbcType()) ? TableOperation.jdbcType(dataType) : columnElement.getJdbcType());
			
				if (!UtilsText.isBlank(columnElement.getTypeHandler())) {
					tempBindColumn += ", typeHandler=" + columnElement.getTypeHandler();
				}
				
			} else {
				tempBindColumn += ", jdbcType=" + TableOperation.jdbcType(dataType);
			}
			
			tempBindColumn += "} ";

			if (isTypeString) {
				bindColumn += "<choose> \n";
				bindColumn += "\t\t\t\t"+"\t\t<when test=\'" + val + " == null  or " + val +" == \\\"\\\"'> \n";
				bindColumn += "\t\t\t\t"+"\t\t\t "+ getColumnName(table, columnName, false) + "=null \n";
				bindColumn += "\t\t\t\t"+"\t\t</when> \n";
				bindColumn += "\t\t\t\t"+"\t\t<otherwise> \n";
				bindColumn += "\t\t\t\t"+"\t\t\t"+tempBindColumn+"\n";
				bindColumn += "\t\t\t\t"+"\t\t</otherwise> \n";
				bindColumn += "\t\t\t\t"+"\t</choose>";

			
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
					bindColumn += UtilsText.concat("<choose><when test='", val, " != null'>", getColumnName(table, columnName, false), "=#{", val,", jdbcType=", TableOperation.jdbcType(dataType), "}</when><otherwise>", getColumnName(table, columnName, false), "=",	getDateTime(tables.getDBInfo()), "</otherwise></choose>");
				} else {
					bindColumn += UtilsText.concat("<choose><when test='", val, " != null'>", getColumnName(table, columnName, false), "=#{", val,", jdbcType=", TableOperation.jdbcType(dataType), "}</when><otherwise>", getColumnName(table, columnName, false), "= null</otherwise></choose>");
				}
				
			} else {
				bindColumn += tempBindColumn;
			}
			
			bindColumn += "\n\t\t\t\t</if>\n";

			
		}
		return bindColumn;
	}

	public static String deleteByPrimaryKey(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		return deleteByPrimaryKey(tables, table, columnsRs, pkColumnsRs, true);
	}
	
	public static String deleteByPrimaryKey(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs, boolean isAnnotation) throws Exception {
		
		String mapperSql = "";
		
		if(isAnnotation) {
			mapperSql += "@Delete(\"\"\"\n";
			mapperSql += "\t<script> \n";		

		}
		
		mapperSql +=  "\t\t\t\t" +"DELETE FROM " + table.getName() +" \n";
		mapperSql +=  "\t\t\t\t" +"WHERE \n";
		mapperSql += "\t\t\t\t"+"\t<trim prefixOverrides=\\\"AND\\\"> \n";
		mapperSql += bindColumnPrimaryKey(tables, table, pkColumnsRs);
		mapperSql += "\t\t\t\t"+"\t</trim> \n";
		
		if(isAnnotation) {
			mapperSql += "\t</script> \n";
			mapperSql += "\t\"\"\")";
		}
		
		return mapperSql;
	}
	

	public static String delete(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs) throws Exception {
		return delete(tables, table, columnsRs, true);
	}
	
	public static String delete(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, boolean isAnnotation) throws Exception {
		
		String mapperSql = "";
		
		if(isAnnotation) {
			mapperSql += "@Delete(\"\"\"\n";
			mapperSql += "\t<script> \n";		
		}
		
		mapperSql +=  "\t\t\t\t" + "DELETE FROM " + table.getName() +" \n";
		mapperSql +=  "\t\t\t\t" + "WHERE \n";
		mapperSql += "\t\t\t\t"+"\t<trim prefixOverrides=\\\"AND\\\"> \n";
		mapperSql += bindColumn(tables, table, columnsRs);
		mapperSql += "\t\t\t\t"+"\t</trim> \n";
		
		if(isAnnotation) {
			mapperSql += "\t</script> \n";
			mapperSql += "\t\"\"\")";
		}
		
		return mapperSql;
	}
	
	public static String deleteAll(TablesElement tables, TableElement table, List<Map<String, String>> columnsRs, List<Map<String, String>> pkColumnsRs) throws Exception {
		
		String mapperSql = "";
		
		mapperSql += "@Delete(\"\"\"\n";
		mapperSql +=  "\t\t\t\t" +"DELETE FROM " + table.getName() +" ";
		mapperSql += "\"\"\")";
		
		return mapperSql;
	}
	
	
	public static String resultMap(TablesElement tables, TableElement table, List<Map<String, String>> columns, List<Map<String, String>> pkColumns) throws Exception {

		
		String resultMap = "";
		int columnSize = columns.size();
		
		for (int i = 0; i < columnSize; i++) {
			
			Map<String, String> column = columns.get(i);
			String orgColumnName = column.get(Const.COLUMN_NAME);
			String columnName = orgColumnName;
			String dataType = column.get(Const.DATA_TYPE).toUpperCase();
		
			boolean isPk = false;
			if (pkColumns != null && pkColumns.size() > 0) {

				for (Map<String, String> pkColumn : pkColumns) {

					String orgPkColumnName = pkColumn.get(Const.COLUMN_NAME).toUpperCase();

					if (orgColumnName.equals(orgPkColumnName)) {
						isPk = true;
						break;
					}
				}
				
				
				if (i > 0) {
					resultMap += "\t\t";
				}

				if (isPk) {
					resultMap += UtilsText.concat("<id column=\"", orgColumnName, "\" jdbcType=\"", TableOperation.jdbcType(dataType),	"\" property=\"", columnName, "\"/>");

				} else {
					resultMap += UtilsText.concat("<result column=\"", orgColumnName, "\" jdbcType=\"", TableOperation.jdbcType(dataType),	"\" property=\"", columnName, "\"/>");

				}
				resultMap += "\n";
			}
			
			
		}
		
		return resultMap;

	}

}
