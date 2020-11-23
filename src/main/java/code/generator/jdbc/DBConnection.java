package code.generator.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import code.generator.common.Log;

public class DBConnection {

    private Connection connection;

    private DBInfo dbInfo;
    
    public DBConnection(DBInfo dbInfo) throws SQLException {
    	
    	this.dbInfo = dbInfo;
    	
        try {
            Class.forName(dbInfo.getDriver());
            this.connection = DriverManager.getConnection(dbInfo.getUrl(), dbInfo.getUsername(), dbInfo.getPassword());
        } catch (ClassNotFoundException ex) {
        	ex.printStackTrace();
        	Log.error("Database Connection Creation Failed : " + ex.getMessage());
        }
    }
    
    
    public DBInfo getDbInfo() {
		return dbInfo;
	}

	public Connection getConnection() {
        return connection;
    }
    
    public void close() {
    	
        if (connection == null) {
        	Log.warning("No connection found");
        } else {
            try {
                connection.close();
                connection = null;
            }
            catch (SQLException e) {
            	Log.error("Failed to close the connection"+ e);
            }
        }
    }
}
