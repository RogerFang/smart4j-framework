package org.smart4j.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.helper.ConfigHelper;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Roger on 2016/11/30.
 */
public final class DBUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBUtil.class);

    // 用于放置数据库连接的局部线程变量
    private static ThreadLocal<Connection> connContainer = new ThreadLocal<>();

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = connContainer.get();
        if (conn == null) {
            try {
                Class.forName(ConfigHelper.getJdbcDriver());
                conn = DriverManager.getConnection(ConfigHelper.getJdbcUrl(), ConfigHelper.getJdbcUsername(), ConfigHelper.getJdbcPassword());
            } catch (Exception e) {
                LOGGER.error("database get connection error", e);
                throw new RuntimeException(e);
            } finally {
                connContainer.set(conn);
            }
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection() {
        Connection conn = connContainer.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                LOGGER.error("database close connection error", e);
                throw new RuntimeException(e);
            } finally {
                connContainer.remove();
            }
        }
    }

    /**
     * 开启事务
     */
    public static void beginTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.setAutoCommit(false);
            } catch (Exception e) {
                LOGGER.error("database begin transaction error", e);
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 提交事务
     */
    public static void commitTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.commit();
            } catch (Exception e) {
                LOGGER.error("database commit transaction error", e);
                throw new RuntimeException(e);
            } finally {
                closeConnection();
            }
        }
    }

    /**
     * 回滚事务
     */
    public static void rollbackTransaction() {
        Connection conn = getConnection();
        if (conn != null) {
            try {
                conn.rollback();
            } catch (Exception e) {
                LOGGER.error("database rolleback transaction error", e);
                throw new RuntimeException(e);
            } finally {
                closeConnection();
            }
        }
    }
}
