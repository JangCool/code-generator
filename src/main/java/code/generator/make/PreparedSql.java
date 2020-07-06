package code.generator.make;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import code.generator.common.Const;
import code.generator.common.Global;
import code.generator.jdbc.DBInfo;
import code.generator.util.UtilsText;
import code.generator.vo.RepositoryVO;

public class PreparedSql {

	private static String jdbcType(String dataType) {

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
			jdbc = "BINARY";
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

	public static String resultMap(String tableName, RepositoryVO repositoryVO, List<Map<String, String>> columns,
			List<Map<String, String>> pkColumns, NodeList columnNodeList) {

		StringBuilder sb = new StringBuilder();

		if (columns != null) {

			int i = 0;
			for (Map<String, String> column : columns) {

				String orgColumnName = column.get(Const.COLUMN_NAME).toUpperCase();
				String columnName = UtilsText.convert2CamelCase(orgColumnName);
				String dataType = column.get(Const.DATA_TYPE);

//				<id column="" jdbcType="" property=""/>
//		    	<result column="" jdbcType="" property=""/>

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

				if (i > 0) {
					sb.append("\t\t");
				}

				if (isPk) {
					sb.append(UtilsText.concat("<id column=\"", orgColumnName, "\" jdbcType=\"", jdbcType(dataType),
							"\" property=\"", columnName, "\"/>"));

				} else {
					sb.append(UtilsText.concat("<result column=\"", orgColumnName, "\" jdbcType=\"", jdbcType(dataType),
							"\" property=\"", columnName, "\"/>"));

				}

				sb.append("\n");

				i++;
			}
		}
		return sb.toString();

	}

	public static String selectByPrimaryKey(String tableName, RepositoryVO repositoryVO,
			List<Map<String, String>> columns, List<Map<String, String>> pkColumns, NodeList columnNodeList) {
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append("SELECT \n");
		sb.append("\t\t\t");
		sb.append("<include refid=\"select-columns\" />");
		sb.append("\n");
		sb.append("\t\t");
		sb.append("FROM \n");
		sb.append("\t\t\t");

		// table 명에 alias를 줄경우 테이블 명 AS 가칭 테이블명으로 변경한다.
		if (!UtilsText.isBlank(repositoryVO.getAlias())) {
			tableName = UtilsText.concat(tableName, " AS ", repositoryVO.getAlias());
		}

		sb.append(tableName).append("\n");

		if (pkColumns != null && pkColumns.size() > 0) {

			sb.append("\t\t");
			sb.append("WHERE \n");
			sb.append("\t\t\t");
			sb.append("<include refid=\"pk-columns\" />");

		}

		return sb.toString();
	}

	public static String pkColumns(String tableName, RepositoryVO repositoryVO, List<Map<String, String>> columns,
			List<Map<String, String>> pkColumns, NodeList columnNodeList) {
		StringBuilder sb = new StringBuilder();

		if (pkColumns != null && pkColumns.size() > 0) {

			int i = 0;
			for (Map<String, String> pkColumn : pkColumns) {

				String orgColumnName = pkColumn.get(Const.COLUMN_NAME).toUpperCase();

				if (!UtilsText.isBlank(repositoryVO.getAlias())) {
					orgColumnName = UtilsText.concat(repositoryVO.getAlias(), ".", orgColumnName);
				}

				if (i > 0) {
					sb.append(" AND ");
				}
				sb.append(orgColumnName).append(" = ").append(generatorColumn(pkColumn, columnNodeList));

				i++;
			}
		}

		return sb.toString();
	}

	public static String columns(String tableName, RepositoryVO repositoryVO, List<Map<String, String>> columns,
			List<Map<String, String>> pkColumns, NodeList columnNodeList) {

		StringBuilder sb = new StringBuilder();
		if (columns != null) {

			int i = 0;
			for (Map<String, String> column : columns) {

				String columnName = column.get(Const.COLUMN_NAME).toUpperCase();

				if (!UtilsText.isBlank(repositoryVO.getAlias())) {
					columnName = UtilsText.concat(repositoryVO.getAlias(), ".", columnName);
				}

				if (i == 0) {
					sb.append(columnName);
				} else {
					sb.append(", ").append(columnName);
				}

				i++;
			}
		}

		return sb.toString();
	}

	public static String select(String tableName, RepositoryVO repositoryVO, List<Map<String, String>> columns,
			List<Map<String, String>> pkColumns, NodeList columnNodeList) {
		return select(tableName, repositoryVO, columns, pkColumns, columnNodeList, null);
	}

	public static String select(String tableName, RepositoryVO repositoryVO, List<Map<String, String>> columns,
			List<Map<String, String>> pkColumns, NodeList columnNodeList, String lock) {

		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append("SELECT \n");
		sb.append("\t\t\t <include refid=\"select-columns\" />");
		sb.append("\n");
		sb.append("\t\t");
		sb.append("FROM \n");
		sb.append("\t\t\t");

		// table 명에 alias를 줄경우 테이블 명 AS 가칭 테이블명으로 변경한다.
		if (!UtilsText.isBlank(repositoryVO.getAlias())) {
			tableName = UtilsText.concat(tableName, " AS ", repositoryVO.getAlias());
		}

		sb.append(tableName);

		if (!UtilsText.isBlank(lock) && "select".equals(lock)) {
			sb.append(" WITH (NOLOCK)").append("\n");
		}

		sb.append("\n");
		whereSql(sb, repositoryVO, columns, columnNodeList, "S");

		return sb.toString();
	}

	public static String insert(String tableName, RepositoryVO repositoryVO, List<Map<String, String>> columns,
			List<Map<String, String>> pkColumns, NodeList columnNodeList) {

		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(UtilsText.concat("INSERT INTO ", tableName, "\n"));
		if (columns != null) {

			sb.append("\t\t\t ( ");

			int i = 0;
			boolean first = false;
			for (Map<String, String> column : columns) {

				String orgColumnName = column.get(Const.COLUMN_NAME).toUpperCase();
//				String columnName = UtilsText.convert2CamelCase(orgColumnName);
				String autoIncrement = column.get(Const.COLUMN_AUTO_INCREMENT);
				boolean isAutoIncrement = (!UtilsText.isBlank(column.get(Const.COLUMN_AUTO_INCREMENT))
						&& "Y".equals(autoIncrement));
				boolean isExclude = false;

				String[] excludeColumns = Global.getExcludeInsertColumn().toUpperCase().split("\\,");

				for (int j = 0; j < excludeColumns.length; j++) {
					if (orgColumnName.equals(excludeColumns[j].trim().toUpperCase())) {
						isExclude = true;
						break;
					}
				}

				if (isAutoIncrement || isExclude) {
					continue;
				}

				if (!first) {
					first = true;
					sb.append(orgColumnName);
				} else {
					sb.append(", ").append(orgColumnName);
				}

				i++;
			}
			sb.append(" ) \n");

		}

		sb.append("\n");
		sb.append("\t\t");
		sb.append("VALUES \n");
		sb.append("\t\t\t ");
		sb.append("( ");

		if (columns != null) {

			int i = 0;
			boolean first = false;
			for (Map<String, String> column : columns) {

				String orgColumnName = column.get(Const.COLUMN_NAME).toUpperCase();
				String columnName = UtilsText.convert2CamelCase(orgColumnName);
				String dataType = column.get("DATA_TYPE");
				String autoIncrement = column.get(Const.COLUMN_AUTO_INCREMENT);
				boolean isAutoIncrement = (!UtilsText.isBlank(column.get(Const.COLUMN_AUTO_INCREMENT))
						&& "Y".equals(autoIncrement));

				String[] excludeColumns = Global.getExcludeInsertColumn().toUpperCase().split("\\,");

				boolean isExclude = false;

				for (int j = 0; j < excludeColumns.length; j++) {
					if (orgColumnName.equals(excludeColumns[j].trim().toUpperCase())) {
						isExclude = true;
						break;
					}
				}

				if (isAutoIncrement || isExclude) {
					continue;
				}

				String bind = UtilsText.concat("#{", columnName, ", jdbcType=", jdbcType(dataType), "}");

				if ("VARCHAR".equals(jdbcType(dataType)) || "VARCHAR2".equals(jdbcType(dataType))) {

					StringBuilder insertEmpty = new StringBuilder();
					insertEmpty.append(UtilsText.concat("<choose> "));
					insertEmpty.append(UtilsText.concat("<when test=\"", columnName, " == null", " or ", columnName,
							" == ''\"> "));
					insertEmpty.append(" null ");
					insertEmpty.append(UtilsText.concat("</when>"));
					insertEmpty.append(UtilsText.concat("<otherwise>"));
					insertEmpty.append(UtilsText.concat("#{", columnName, ", jdbcType=", jdbcType(dataType), "} "));
					insertEmpty.append(UtilsText.concat("</otherwise>"));
					insertEmpty.append(UtilsText.concat("</choose>"));

					bind = insertEmpty.toString();
				} else {
					bind = UtilsText.concat("#{", columnName, ", jdbcType=", jdbcType(dataType), "}");
				}

				if ("date".equals(dataType) || "datetime".equals(dataType) || "datetime2".equals(dataType)) {

					String[] defaultDateColumns = Global.getRegDateColumn().toUpperCase().split("\\,");

					boolean isDefaultDate = false;
					for (int j = 0; j < defaultDateColumns.length; j++) {
						if (orgColumnName.equals(defaultDateColumns[j].toUpperCase())) {
							isDefaultDate = true;
							break;
						}
					}
					if (isDefaultDate) {
						bind = UtilsText.concat("<choose><when test=\"", columnName, " != null\">#{", columnName,
								", jdbcType=", jdbcType(dataType), "}</when><otherwise>",
								getDateTime(repositoryVO.getdBInfo()), "</otherwise></choose>");
					} else {
						bind = UtilsText.concat("<choose><when test=\"", columnName, " != null\">#{", columnName,
								", jdbcType=", jdbcType(dataType), "}</when><otherwise>null</otherwise></choose>");
					}

				}

				if (!first) {
					first = true;
					sb.append(bind);
				} else {
					sb.append(", ").append(bind);
				}

				i++;
			}
		}

		sb.append(" ) ");

		return sb.toString();
	}

	public static String getDateTime(DBInfo dbInfo) {

		if (dbInfo.isOracle()) {
			return "SYSDATE";
		} else if (dbInfo.isMssql()) {
			return "getdate()";
		} else if (dbInfo.isMysql() || dbInfo.isMaria()) {
			return "now(3)";
		}

		return null;
	}

	public static String update(String tableName, RepositoryVO repositoryVO, List<Map<String, String>> columns,
			List<Map<String, String>> pkColumns, NodeList columnNodeList) {

		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(UtilsText.concat("UPDATE ", tableName, "\n"));
		sb.append("\t\t");
		sb.append("<set> \n");

		if (columns != null) {

			int i = 0;
			for (Map<String, String> column : columns) {

				String orgColumnName = column.get(Const.COLUMN_NAME).toUpperCase();
				String columnName = UtilsText.convert2CamelCase(orgColumnName);
				String dataType = column.get("DATA_TYPE");

				boolean isExclude = false;

				String[] excludeColumns = Global.getExcludeUpdateColumn().toUpperCase().split("\\,");

				for (int j = 0; j < excludeColumns.length; j++) {
					if (orgColumnName.equals(excludeColumns[j].trim().toUpperCase())) {
						isExclude = true;
						break;
					}
				}

				if (isExclude) {
					continue;
				}

				if (!"VARCHAR".equals(jdbcType(dataType)) && !"VARCHAR2".equals(jdbcType(dataType))) {

					if ("date".equals(dataType) || "datetime".equals(dataType) || "datetime2".equals(dataType)) {

						String[] defaultDateColumns = repositoryVO.getModDateColumn().toUpperCase().split("\\,");

						boolean isDefaultDate = false;
						for (int j = 0; j < defaultDateColumns.length; j++) {
							if (orgColumnName.equals(defaultDateColumns[j].toUpperCase())) {
								isDefaultDate = true;
								break;
							}
						}
						if (isDefaultDate) {
							sb.append("\t\t\t").append(UtilsText.concat(orgColumnName, "=",
									getDateTime(repositoryVO.getdBInfo()), ", \n"));
						} else {
							sb.append("\t\t\t").append(UtilsText.concat("<if test=\"", columnName, " != null\"> \n"));
							sb.append("\t\t\t\t").append(orgColumnName).append(" = ").append(
									UtilsText.concat("#{", columnName, ", jdbcType=", jdbcType(dataType), "}, \n"));
							sb.append("\t\t\t").append(UtilsText.concat("</if> \n"));
						}
					} else {
						sb.append("\t\t\t").append(UtilsText.concat("<if test=\"", columnName, " != null\"> \n"));
						sb.append("\t\t\t\t").append(orgColumnName).append(" = ")
								.append(UtilsText.concat("#{", columnName, ", jdbcType=", jdbcType(dataType), "}, \n"));
						sb.append("\t\t\t").append(UtilsText.concat("</if> \n"));
					}

				} else {

					sb.append("\t\t\t").append(UtilsText.concat("<if test=\"", columnName, " != null\"> \n"));
					sb.append("\t\t\t\t").append(UtilsText.concat("<choose> \n"));
					sb.append("\t\t\t\t\t").append(UtilsText.concat("<when test=\"", columnName, " == ''\"> \n"));
					sb.append("\t\t\t\t\t\t").append(orgColumnName).append(" = null, \n");
					sb.append("\t\t\t\t\t").append(UtilsText.concat("</when> \n"));
					sb.append("\t\t\t\t\t").append(UtilsText.concat("<otherwise> \n"));
					sb.append("\t\t\t\t\t\t").append(orgColumnName).append(" = ")
							.append(UtilsText.concat("#{", columnName, ", jdbcType=", jdbcType(dataType), "}, \n"));
					sb.append("\t\t\t\t\t").append(UtilsText.concat("</otherwise> \n"));
					sb.append("\t\t\t\t").append(UtilsText.concat("</choose> \n"));
					sb.append("\t\t\t").append(UtilsText.concat("</if> \n"));
				}

				i++;
			}
		}
		sb.append("\t\t");
		sb.append("</set> \n");

		whereSqlPk(sb, repositoryVO, pkColumns, columnNodeList, "U");

		return sb.toString();
	}

	public static String delete(String tableName, RepositoryVO repositoryVO, List<Map<String, String>> columns,
			List<Map<String, String>> pkColumns, NodeList columnNodeList) {
		StringBuilder sb = new StringBuilder();
		sb.append("\t");
		sb.append(UtilsText.concat("DELETE FROM ", tableName, "\n"));

		whereSqlPk(sb, repositoryVO, pkColumns, columnNodeList, "D");

		return sb.toString();
	}

	private static void whereSqlPk(StringBuilder sb, RepositoryVO repositoryVO, List<Map<String, String>> pkColumns,
			NodeList columnNodeList, String crud) {

		if (pkColumns != null && pkColumns.size() > 0) {

			sb.append("\t\t");
			sb.append("WHERE \n");

			int i = 0;
			for (Map<String, String> pkColumn : pkColumns) {

				String orgColumnName = pkColumn.get(Const.COLUMN_NAME).toUpperCase();

				if ("S".equals(crud) && !UtilsText.isBlank(repositoryVO.getAlias())) {
					orgColumnName = UtilsText.concat(repositoryVO.getAlias(), ".", orgColumnName);
				}

				if (i == 0) {
					sb.append("\t\t\t ");
				} else {
					sb.append(" AND ");
				}
				sb.append(orgColumnName).append(" = ").append(generatorColumn(pkColumn, columnNodeList));

				i++;
			}
		}
	}

	private static void whereSql(StringBuilder sb, RepositoryVO repositoryVO, List<Map<String, String>> columns,
			NodeList columnNodeList, String crud) {

		if (columns != null && columns.size() > 0) {

			sb.append("\t\t");
			sb.append("<where> \n");

			int i = 0;
			for (Map<String, String> column : columns) {

				String orgColumnName = column.get(Const.COLUMN_NAME).toUpperCase();
				String columnName = UtilsText.convert2CamelCase(orgColumnName);
				String dataType = column.get("DATA_TYPE");

				if ("S".equals(crud) && !UtilsText.isBlank(repositoryVO.getAlias())) {
					orgColumnName = UtilsText.concat(repositoryVO.getAlias(), ".", columnName);
				}

				sb.append("\t\t\t").append(
						UtilsText.concat("<if test=\"", columnName, " != null and ", columnName, " != '' \"> \n"));

				if (i == 0) {
					sb.append("\t\t\t\t ");
				} else {
					sb.append("\t\t\t\t AND ");
				}
				sb.append(orgColumnName).append(" = ").append(generatorColumn(column, columnNodeList)).append("\n");
				sb.append("\t\t\t").append(UtilsText.concat("</if> \n"));

				i++;
			}
			sb.append("\t\t");
			sb.append("</where> ");

		}
	}

	private static String generatorColumn(Map<String, String> column, NodeList columnNodeList) {

		StringBuilder sb = new StringBuilder();

		String orgColumnName = column.get(Const.COLUMN_NAME).toLowerCase();
		String columnName = UtilsText.convert2CamelCase(orgColumnName);
		String dataType = column.get(Const.DATA_TYPE);

		sb.append(UtilsText.concat("#{", columnName));
		if (columnNodeList != null && columnNodeList.getLength() > 0) {

			int columnNodeListLength = columnNodeList.getLength();

			for (int i = 0; i < columnNodeListLength; i++) {
				if (columnNodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) columnNodeList.item(i);

					String name = element.getAttribute("name");
					String jdbcType = element.getAttribute("jdbcType");
					String typeHandler = element.getAttribute("typeHandler");

					if (!UtilsText.isBlank(name) && orgColumnName.equals(name.toLowerCase())) {

						if (!UtilsText.isBlank(jdbcType)) {
							sb.append(", jdbcType=").append(jdbcType);
						} else {
							sb.append(", jdbcType=").append(jdbcType(dataType));
						}

						if (!UtilsText.isBlank(typeHandler)) {
							sb.append(", typeHandler=").append(typeHandler);
						}
					}

				}
			}
		} else {
			sb.append(UtilsText.concat(", jdbcType=", jdbcType(dataType)));
		}

		sb.append("}");

		return sb.toString();

	}

	public static String getAutoIncrement(List<Map<String, String>> columns) {

		String autoIncrementColumn = null;

		for (Map<String, String> column : columns) {
			String orgColumnName = column.get(Const.COLUMN_NAME).toUpperCase();
			String columnName = UtilsText.convert2CamelCase(orgColumnName);
			String autoIncrement = column.get(Const.COLUMN_AUTO_INCREMENT);
			boolean isAutoIncrement = (!UtilsText.isBlank(column.get(Const.COLUMN_AUTO_INCREMENT))
					&& "Y".equals(autoIncrement));

			if (isAutoIncrement) {
				autoIncrementColumn = columnName;
				break;
			}

		}

		return autoIncrementColumn;

	}

	public static void main(String[] args) {
		String regDt = "REG_ID";

		System.out.println(regDt.split("\\,")[0]);
		System.out.println(regDt.split("\\,").length);

	}
}
