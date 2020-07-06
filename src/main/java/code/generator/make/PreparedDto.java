package code.generator.make;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.generator.common.Const;
import code.generator.util.UtilsText;

public class PreparedDto {

	private static String javaType(String dataType) {
		
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

	public static List<Map<String, String>> dto(String orgTableName, String packagePh, List<Map<String, String>> columns, List<Map<String, String>> pkColumns) {
		
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
			fieldMap.put("javaType", javaType(dataType));
			
			fieldList.add(fieldMap);
		}
		
		return fieldList;
	}
}
