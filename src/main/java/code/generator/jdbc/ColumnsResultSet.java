package code.generator.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import code.generator.common.Const;
import code.generator.common.Log;
import code.generator.util.UtilsText;

public class ColumnsResultSet {

	private static final String SQL_ORACLE_TABLE_COLUMN = "SELECT ats.column_name, ats.data_type, ats.column_id as ordinal_position,( SELECT comments FROM   all_col_comments acc WHERE  acc.table_name = ats.table_name AND acc.column_name = ats.column_name ) as comments, DECODE(ac.CONSTRAINT_TYPE ,'P','PRI') AS PK FROM (SELECT A.TABLE_NAME, A.CONSTRAINT_TYPE, B.COLUMN_NAME , B.POSITION FROM ALL_CONSTRAINTS  A, ALL_CONS_COLUMNS B WHERE A.TABLE_NAME = ? AND A.CONSTRAINT_TYPE = 'P' AND A.OWNER = B.OWNER AND A.CONSTRAINT_NAME = B.CONSTRAINT_NAME ORDER BY B.POSITION) ac, all_tab_columns ats WHERE  ats.table_name = ? AND ac.column_name(+) = ats.column_name ORDER BY ats.column_id";

	private static final String SQL_MSSQL_TABLE_COLUMN = "SELECT col.column_name,data_type,ordinal_position,cn.comment as comments,CASE WHEN cb.column_name IS not NULL THEN 'Y' ELSE 'N' END AS auto_increment FROM information_schema.columns col LEFT OUTER JOIN (SELECT objname, value AS comment FROM Fn_listextendedproperty(NULL, 'SCHEMA', 'DBO', 'TABLE', ?, 'COLUMN', DEFAULT)) cn ON col.column_name = cn.objname COLLATE korean_wansung_ci_as LEFT OUTER JOIN (SELECT column_name FROM information_schema.columns WHERE  Columnproperty(Object_id(?), column_name, 'IsIdentity') = 1 AND table_name = ? ) cb ON col.column_name = cb.column_name COLLATE korean_wansung_ci_as WHERE  col.table_name = ? ORDER  BY ordinal_position ";

	private static final String SQL_MSSQL_TABLE_PK_COLUMN = "SELECT col.column_name, cn.comment as comments, (SELECT data_type FROM information_schema.columns dtcn WHERE dtcn.table_name=col.table_name AND dtcn.column_name=col.column_name) as data_type FROM information_schema.table_constraints tab,information_schema.constraint_column_usage col LEFT OUTER JOIN  (SELECT objname,value as comment FROM fn_listextendedproperty(NULL, 'SCHEMA', 'DBO', 'TABLE', ?, 'COLUMN', DEFAULT)) cn ON col.column_name=cn.objname collate Korean_Wansung_CI_AS WHERE col.constraint_name = tab.constraint_name AND col.table_name = tab.table_name AND constraint_type = 'PRIMARY KEY' AND col.table_Name = ?";

	private static final String SQL_MYSQL_TABLE_COLUMN = "SELECT column_name,data_type,column_key as pk, ordinal_position, column_comment as comments, CASE WHEN EXTRA = 'auto_increment' THEN 'Y' ELSE '' END AS auto_increment FROM information_schema.columns WHERE table_schema = SCHEMA() AND table_name = ? ORDER BY ordinal_position";

//	private static final String SQL_MYSQL_TABLE_PK_COLUMN = "SELECT * FROM (SELECT column_name,data_type,column_key as pk, ordinal_position, column_comment as comments, CASE WHEN EXTRA = 'auto_increment' THEN 'Y' ELSE '' END AS auto_increment FROM information_schema.columns WHERE table_schema = SCHEMA() AND table_name = ?) t WHERE t.pk = \"PRI\" ORDER BY ordinal_position";

	private static final String SQL_MARIA_TABLE_COLUMN = "SELECT column_name,data_type,column_key as pk, ordinal_position,column_comment as comments, CASE WHEN EXTRA = 'auto_increment' THEN 'Y' ELSE '' END AS auto_increment FROM information_schema.columns a WHERE table_schema = SCHEMA() AND table_name = ? ORDER BY ordinal_position";

	private static final String SQL_MARIA_TABLE_PK_COLUMN = "SELECT * FROM (SELECT column_name,data_type,column_key as pk, ordinal_position, column_comment as comments, CASE WHEN EXTRA = 'auto_increment' THEN 'Y' ELSE '' END AS auto_increment FROM information_schema.columns WHERE table_schema = SCHEMA() AND table_name = ?) t WHERE t.pk = \"PRI\" ORDER BY ordinal_position";

	private static final String SQL_H2DB_TABLE_COLUMN = "SELECT column_name,type_name as data_type,ordinal_position, remarks as comments, CASE WHEN sequence_name is not null THEN 'Y' ELSE '' END AS auto_increment FROM information_schema.columns WHERE table_schema = SCHEMA() AND table_name = ? ORDER BY ordinal_position";

	private static final String SQL_H2DB_TABLE_CONSTRAINTS = "SELECT COLUMN_LIST FROM INFORMATION_SCHEMA.CONSTRAINTS WHERE  table_schema = SCHEMA() AND CONSTRAINT_TYPE='PRIMARY KEY' AND TABLE_NAME=?";
	
	private static final String SQL_HYPERSQL_TABLE_COLUMN = "SELECT column_name, type_name AS DATA_TYPE, ordinal_position, (SELECT comment FROM information_schema.system_comments sc WHERE sc.OBJECT_NAME = table_name AND SC.COLUMN_NAME = COLUMN_NAME) as comments, CASE WHEN is_autoincrement = 'YES' THEN 'Y' ELSE '' END AS auto_increment FROM information_schema.system_columns WHERE table_schem = 'PUBLIC' AND table_name = ? ORDER BY ordinal_position";

	private static final String SQL_HYPERSQL_TABLE_CONSTRAINTS_PK = "SELECT POSITION('PK' IN CONSTRAINT_NAME) AS POSITION, issc.column_name, issc.type_name AS DATA_TYPE, (SELECT comment FROM information_schema.system_comments sc WHERE sc.object_name = kcu.table_name AND sc.column_name = kcu.column_name) as comments FROM information_schema.key_column_usage kcu,information_schema.system_columns issc WHERE kcu.table_schema = 'PUBLIC' AND kcu.table_name = ? AND kcu.table_name = issc.table_name AND kcu.table_schema = issc.table_schem  AND kcu.column_name = issc.column_name AND POSITION('PK' IN kcu.constraint_name) > 0 ORDER BY ordinal_position";


	private DBConnection connection;

	private List<Map<String, String>> columns;

	private List<Map<String, String>> primaryColumns;

	private DBInfo dbInfo;

	public DBInfo getDbInfo() {
		return dbInfo;
	}

	public List<Map<String, String>> getColumns() {
		return columns;
	}

	public List<Map<String, String>> getPrimaryColumns() {
		return primaryColumns;
	}

	public ColumnsResultSet(DBConnection connection) {
		this.connection = connection;
		this.dbInfo = connection.getDbInfo();
	}

	public void callOracleColumn(String tableName) {

		clearColumns();

		try {

			PreparedStatement pstmt = connection.getConnection().prepareStatement(SQL_ORACLE_TABLE_COLUMN);

			pstmt.setString(1, tableName);
			pstmt.setString(2, tableName);

			ResultSet rs = pstmt.executeQuery();

			setColumnsResultSet(rs, "PRI");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void callMssqlColumn(String tableName) {

		clearColumns();
		PreparedStatement pstmt = null;
		PreparedStatement pkPstmt = null;
		ResultSet rs = null;
		ResultSet pkRs = null;
		try {

			pstmt = connection.getConnection().prepareStatement(SQL_MSSQL_TABLE_COLUMN);
			pstmt.setString(1, tableName);
			pstmt.setString(2, tableName);
			pstmt.setString(3, tableName);
			pstmt.setString(4, tableName);

			rs = pstmt.executeQuery();

			setColumnsResultSet(rs);

			pkPstmt = connection.getConnection().prepareStatement(SQL_MSSQL_TABLE_PK_COLUMN);
			pkPstmt.setString(1, tableName);
			pkPstmt.setString(2, tableName);

			pkRs = pkPstmt.executeQuery();

			setPKColumnsResultSet(pkRs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(pkRs);
			DBUtil.close(pkPstmt);
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
	}

	public void callMysqlColumn(String tableName) {

		clearColumns();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.getConnection().prepareStatement(SQL_MYSQL_TABLE_COLUMN);
			pstmt.setString(1, tableName);

			rs = pstmt.executeQuery();

			setColumnsResultSet(rs, "PRI");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
	}

	public void callMariaColumn(String tableName) {

		clearColumns();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			pstmt = connection.getConnection().prepareStatement(SQL_MARIA_TABLE_COLUMN);
			pstmt.setString(1, tableName);

			rs = pstmt.executeQuery();

			setColumnsResultSet(rs, "PRI");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
	}
	
	public void callH2DBColumn(String tableName) {

		clearColumns();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		PreparedStatement constPstmt = null;
		ResultSet constRs = null;

		PreparedStatement pkPstmt = null;
		ResultSet pkRs = null;
		
		
		try {
			pstmt = connection.getConnection().prepareStatement(SQL_H2DB_TABLE_COLUMN);
			pstmt.setString(1, tableName);

			rs = pstmt.executeQuery();

			setColumnsResultSet(rs);
			

			constPstmt = connection.getConnection().prepareStatement(SQL_H2DB_TABLE_CONSTRAINTS);
			constPstmt.setString(1, tableName);

			constRs = constPstmt.executeQuery();
			
			if(constRs.next()) {		
			
				String columnList = constRs.getString("COLUMN_LIST");
				
				if(!UtilsText.isBlank(columnList)) {
					
					String[] columnArr = columnList.split("\\,");
					
					StringBuilder query = new StringBuilder();
					
					query.append("SELECT column_name,type_name as data_type,ordinal_position, remarks as comments, CASE WHEN sequence_name is not null THEN 'Y' ELSE '' END AS auto_increment FROM information_schema.columns WHERE table_schema = SCHEMA() AND table_name = ?  AND column_name in (");
					
					if(columnArr != null && columnArr.length > 0) {
						
						int i = 0 ;
						int columnArrLength = columnArr.length;
						
						for (String columnName : columnArr) {
							
							if(i == 0 ) {
								query.append("?");							
							}else {
								query.append(",?");
							}
							
							i++;
						}
	
						query.append(") ORDER BY ordinal_position");
						
						pkPstmt = connection.getConnection().prepareStatement(query.toString());
						
						int c = 1;
						pkPstmt.setString(c++, tableName);	

						for (String column : columnArr) {
							pkPstmt.setString(c++, column);	
						}
						
						pkRs = pkPstmt.executeQuery();
	
					}
	
					setPKColumnsResultSet(pkRs);
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
			DBUtil.close(constRs);
			DBUtil.close(constPstmt);
			DBUtil.close(pkRs);
			DBUtil.close(pkPstmt);
		}
	}

	public void callHyperSqlColumn(String tableName) {

		clearColumns();
		PreparedStatement pstmt = null;
		PreparedStatement pkPstmt = null;
		ResultSet rs = null;
		ResultSet pkRs = null;

		try {
			pstmt = connection.getConnection().prepareStatement(SQL_HYPERSQL_TABLE_COLUMN);
			pstmt.setString(1, tableName);

			rs = pstmt.executeQuery();

			setColumnsResultSet(rs);
			
			pkPstmt = connection.getConnection().prepareStatement(SQL_HYPERSQL_TABLE_CONSTRAINTS_PK);
			pkPstmt.setString(1, tableName);

			pkRs = pkPstmt.executeQuery();

			setPKColumnsResultSet(pkRs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(rs);
			DBUtil.close(pstmt);
		}
	}
	

	private void setColumnsResultSet(ResultSet rs) throws SQLException {
		setColumnsResultSet(rs, null);
	}

	private void setColumnsResultSet(ResultSet rs, String keyType) throws SQLException {

		List<Map<String, String>> tempColumns = new ArrayList<>();
		List<Map<String, String>> tempPrimaryColumns = new ArrayList<>();

		while (rs.next()) {

			Map<String, String> column = new HashMap<>();

			column.put(Const.COLUMN_NAME, rs.getString(Const.COLUMN_NAME));
			column.put(Const.DATA_TYPE, rs.getString(Const.DATA_TYPE));
			column.put(Const.COLUMN_COMMENT,
					UtilsText.isBlank(rs.getString(Const.COLUMN_COMMENT)) ? "" : rs.getString(Const.COLUMN_COMMENT));

			if (!getDbInfo().isOracle()) {
				column.put(Const.COLUMN_AUTO_INCREMENT, rs.getString(Const.COLUMN_AUTO_INCREMENT));
			} else {
				column.put(Const.COLUMN_AUTO_INCREMENT, null);
			}

			if (keyType != null) {

				String key = rs.getString("PK");
				if (keyType.equals(key)) {
					tempPrimaryColumns.add(column);
				}

			}

			tempColumns.add(column);
		}

		this.columns = tempColumns;
		this.primaryColumns = tempPrimaryColumns;
	}

	private void setPKColumnsResultSet(ResultSet rs) throws SQLException {

		List<Map<String, String>> tempPrimaryColumns = new ArrayList<>();

		while (rs.next()) {
			Map<String, String> column = new HashMap<>();
			Log.debug("PK column : "
					+ rs.getString("COLUMN_NAME").concat(", type : ").concat(rs.getString("DATA_TYPE")));

			column.put(Const.COLUMN_NAME, rs.getString(Const.COLUMN_NAME));
			column.put(Const.DATA_TYPE, rs.getString(Const.DATA_TYPE));
			column.put(Const.COLUMN_COMMENT,
					UtilsText.isBlank(rs.getString(Const.COLUMN_COMMENT)) ? "" : rs.getString(Const.COLUMN_COMMENT));

			tempPrimaryColumns.add(column);
		}

		this.primaryColumns = tempPrimaryColumns;
	}

	public void clearColumns() {
		this.columns = null;
		this.primaryColumns = null;
		;
	}
}
