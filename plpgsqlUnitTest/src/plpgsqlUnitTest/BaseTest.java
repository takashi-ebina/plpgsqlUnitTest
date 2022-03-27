package plpgsqlUnitTest;

import java.sql.Connection;

import org.dbunit.database.IDatabaseConnection;

import client.postgres.PostgresClient;

public abstract class BaseTest {

	/** DBUnitのテスター */
	protected static IDatabaseConnection dbcon = null;
	protected static Connection con = null;
	protected static PostgresClient pclient = new PostgresClient();
	
	public static String getTestCaseName() {
		return Thread.currentThread().getStackTrace()[2].getMethodName();
	}
	
	public static String getTestClassName() {
		String str = Thread.currentThread().getStackTrace()[2].getClassName();
		// 完全修飾名から、クラス名を抜き出して返却
		return str.substring(str.length() - str.lastIndexOf("."));
	}
}
