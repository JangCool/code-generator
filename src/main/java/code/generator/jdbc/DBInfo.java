package code.generator.jdbc;

import code.generator.elements.children.JdbcElement;
import code.generator.exception.JDBCException;

public class DBInfo {

	private static final String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String MSSQL_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	private static final String MARIA_DRIVER = "org.mariadb.jdbc.Driver";
	private static final String H2_DRIVER = "org.h2.Driver";
	private static final String HYPERSQL_DRIVER = "org.hsqldb.jdbcDriver";
	private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
	
	
	private String driver;
	private String url;
	private String username;
	private String password;
	
	
	public DBInfo(JdbcElement jdbcElement) throws Exception {
		
		this.url = jdbcElement.getUrl();
		this.driver = jdbcElement.getDriverClass();
		this.username = jdbcElement.getUsername();
		this.password = jdbcElement.getPassword();
		
		validate();
	}

	
	private void validate() throws Exception{
		
		if(this.url == null || "".equals(this.url) ) {
			throw new JDBCException("jdbc url 값이 존재하지 않습니다.");
		}else if (this.driver == null || "".equals(this.driver) ) {
			throw new JDBCException("jdbc driverClass 값이 존재하지 않습니다.");
		}else if (this.driver == null || "".equals(this.driver) ) {
			throw new JDBCException("jdbc username 값이 존재하지 않습니다.");
		}else if (this.driver == null || "".equals(this.driver) ) {
			throw new JDBCException("jdbc username 값이 존재하지 않습니다.");
		}
	}
	
	public String getDriver() {
		return driver;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public boolean isOracle() {
		return (ORACLE_DRIVER.equals(driver));
	}

	public boolean isMssql() {
		return (MSSQL_DRIVER.equals(driver));
	}

	public boolean isMysql() {
		return (MYSQL_DRIVER.equals(driver));
	}
	
	public boolean isMaria() {
		return (MARIA_DRIVER.equals(driver));
	}
	
	public boolean isH2() {
		return (H2_DRIVER.equals(driver));
	}
	
	public boolean isHyperSql() {
		return (HYPERSQL_DRIVER.equals(driver));
	}
	
	public boolean isPostgreSql() {
		return (POSTGRESQL_DRIVER.equals(driver));
	}
	
}
