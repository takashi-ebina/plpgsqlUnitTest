package constant;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SystemConstant {
	public static final String JDBC_PROPERTIES_FILE_PATH = "/lib/properties/JDBC.properties";
	
	public static final Map<String, String> JDBC_PROPERTIES = new HashMap<>();
	static {
		Properties properties = new Properties();
        //プロパティファイルのパスを指定する
        String strpass = System.getProperty("user.dir") + SystemConstant.JDBC_PROPERTIES_FILE_PATH;
        try {
            InputStream istream = new FileInputStream(strpass);
            properties.load(istream);            
        } catch (IOException e) {
            e.printStackTrace();
        }      

        for(Map.Entry<Object, Object> e : properties.entrySet()) {
        	SystemConstant.JDBC_PROPERTIES.put(e.getKey().toString(), e.getValue().toString());
        }
	}
	
	public static enum ParameterMode {
		IN,
		OUT,
		INOUT;
	}
	
	public static final String POSTGRES_JDBC_DRIVER = "org.postgresql.Driver";
}
