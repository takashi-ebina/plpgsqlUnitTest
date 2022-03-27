package client.postgres;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import bean.SqlParam;
import client.Client;
import constant.SystemConstant.ParameterMode;



public class PostgresClient extends Client {
	
	@Override
	public Connection getConnection() throws SQLException, ClassNotFoundException {
		try {
			Class.forName("org.postgresql.Driver");
			return super.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public Map<String, Object> callProc(Connection con, String sql, SqlParam ...param) throws SQLException {
		String procedureParam = "";
		for (int i = 1; i <= param.length; i++) {
			procedureParam = procedureParam + "?";
			if (i == param.length) {
				break;
			}
			procedureParam = procedureParam + ",";
		}
		String execsql = "call " + sql + "(" + procedureParam + ")";
		// プロシージャ呼び出し
		try (CallableStatement proc = con.prepareCall(execsql);) {
			
			setParam(proc, param);
			proc.executeUpdate();
			
			Map<String, Object> resultMap = new HashMap<>();
			for (int i = 0; i < param.length; i++) {
				if (param[i].getParamMode() == ParameterMode.OUT || param[i].getParamMode() == ParameterMode.INOUT) {
					resultMap.put(param[i].getParamName(), proc.getObject(i + 1));
				}
			}
			return resultMap;
		} 
	}
	
	@Override
	protected void setParam(CallableStatement proc, SqlParam[] param) throws SQLException {
		for (int i = 0; i < param.length; i++) {
			switch (param[i].getParamMode()) {
				case IN:
					proc.setObject(i + 1, param[i].getParamData(), param[i].getParamDataType());
					break;
				case OUT:
					// OUTはプロシージャでは対応していない
					proc.registerOutParameter(i + 1, param[i].getParamDataType());
					proc.setObject(i + 1, param[i].getParamData(), param[i].getParamDataType());
					break;
				case INOUT:
					proc.registerOutParameter(i + 1, param[i].getParamDataType());
					proc.setObject(i + 1, param[i].getParamData(), param[i].getParamDataType());
					break;
				default:
					break;
			}
		}
	}
}
