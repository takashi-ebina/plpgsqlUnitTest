package client;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import bean.SqlParam;

public interface ClientIf {
	public Connection getConnection() throws SQLException, ClassNotFoundException;
	public void closeConnection(Connection con) throws SQLException;
	Map<String, Object> callProc(Connection con, String sql, SqlParam ...param) throws SQLException;
}
