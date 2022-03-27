package client;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import bean.SqlParam;
import constant.SystemConstant;

public abstract class Client implements ClientIf {
	
	@Override
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		try {
			Connection con = DriverManager.getConnection(
					SystemConstant.JDBC_PROPERTIES.get("url"),
					SystemConstant.JDBC_PROPERTIES.get("user"),
					SystemConstant.JDBC_PROPERTIES.get("password"));
			con.setAutoCommit(false);
			return con;
		} catch (SQLException e) {
			throw e;
		} 
	}

	@Override
	public void closeConnection(Connection con) throws SQLException {
		try {
			if (con != null) {
				con.rollback();
				con.close();
			}
		} catch (SQLException e) {
			throw e;
		}
	}

	protected abstract void setParam(CallableStatement proc, SqlParam[] param) throws SQLException;

}
