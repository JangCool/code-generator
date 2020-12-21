package code.generator.make;



public class TableOperation {
	
	/**
	 * SQL 컬럼 데이터 형식을 java 데이터 형식으로 변경한다.
	 * @param dataType SQL 컬럼 데이터 형식
	 * @return String
	 */
	public static String javaType(String dataType) {
//		System.out.println("javaType : " + dataType);

		String java = null;
		dataType = dataType.toLowerCase();

		switch (dataType) {
			case "number"				: java = "java.lang.Long";				break; //oracle
			case "bigint"				: java = "java.lang.Long";				break;
			case "binary"				: java = "byte[]";						break;
			case "bit"	 				: java = "java.lang.Boolean";			break;
			case "char"	 				: java = "String";						break;
			case "bpchar" 				: java = "String";						break;//postgresql
			case "date"					: java = "java.sql.Timestamp";			break;
			case "datetime"				: java = "java.sql.Timestamp";			break;
			case "datetime2"			: java = "java.sql.Timestamp";			break;
			case "decimal"				: java = "java.math.BigDecimal";		break;
			case "float"				: java = "java.lang.Double";			break;
			case "float4"				: java = "java.lang.Double";			break;
			case "float8"				: java = "java.lang.Double";			break;
			case "image"				: java = "byte[]";						break;
			case "int"					: java = "java.lang.Integer";			break;
			case "int2"					: java = "java.lang.Integer";			break;
			case "int4"					: java = "java.lang.Integer";			break;
			case "int8"					: java = "java.lang.Long";				break;
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
			case "time"					: java = "java.time.LocalTime";			break;
			case "timestamp"			: java = "java.time.LocalDateTime";		break;
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
//		System.out.println("jdbcType : " + dataType);

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
		case "bpchar":
			jdbc = "CHAR";
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
		case "float4":
			jdbc = "DOUBLE";
			break;
		case "float8":
			jdbc = "DOUBLE";
			break;
		case "image":
			jdbc = "LONGVARBINARY";
			break;
		case "int":
			jdbc = "INTEGER";
		case "int2":
			jdbc = "INTEGER";
		case "int4":
			jdbc = "INTEGER";
		case "int8":
			jdbc = "NUMERIC";
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
}