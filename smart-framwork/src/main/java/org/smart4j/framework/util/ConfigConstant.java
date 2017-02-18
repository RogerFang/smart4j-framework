package org.smart4j.framework.util;

/**
 * 提供相关配置项常量
 * Created by Roger on 2016/11/23.
 */
public interface ConfigConstant {

    String CONFIG_FILE = "smart.properties";

    String JDBC_DRIVER = "jdbc.driver";
    String JDBC_URL = "jdbc.url";
    String JDBC_USERNAME = "jdbc.username";
    String JDBC_PASSWORD = "jdbc.password";

    String APP_BASE_PACKAGE = "app.base_package";
    String APP_JSP_PATH = "app.jsp_path";
    String APP_ASSET_PATH = "app.asset_path";
    
    String APP_FILEUPLOAD_LIMIT = "app.fileupload.limit";
}