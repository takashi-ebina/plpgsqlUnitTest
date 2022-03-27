package plpgsqlUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bean.SqlParam;
import constant.SystemConstant.ParameterMode;



class SampleTest extends BaseTest {

	private final Logger logger = LogManager.getLogger(SampleTest.class);
	
	@BeforeAll
	public static void GlobalSetUp() throws Exception {

	}
	@BeforeEach
	public void setUp() {
		try {
			con = pclient.getConnection();
			dbcon = new DatabaseConnection(con, "test_plpgsql");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (DatabaseUnitException e) {
			e.printStackTrace();
		}
	}
	
	@AfterEach
	public void termDown() {
		try {
			pclient.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Test
	void testcase1() {
		logger.info(super.getTestClassName() + "開始！！！");
		try {
	        // CSV用データセット作成
	        IDataSet dataset = new CsvDataSet(
	        		new File("data/" + super.getTestClassName() + "/" + super.getTestCaseName()));

	        DatabaseOperation.DELETE.execute(dbcon, dataset);
	        DatabaseOperation.INSERT.execute(dbcon, dataset);
			
	        // PROCEDURE実行
			Map<String, Object> resultMap = pclient.callProc(con, "test_plpgsql.sampleTest", 
					new SqlParam("p_col1", Types.NUMERIC, 1, ParameterMode.IN),
					new SqlParam("updateCount", Types.NUMERIC, 45, ParameterMode.INOUT));
			
			// 検証実施
			Assertions.assertEquals(BigDecimal.valueOf(1), resultMap.get("updateCount"));
			
			logger.info(super.getTestClassName() + "終了！！！");
		} catch(Exception e) {
			e.printStackTrace();
			fail("予期しないエラーでテストが失敗しました。");
		} finally {
			
		}
	}
	
}
