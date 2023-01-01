package com.epam.hospital.data_access_layer;

import com.epam.hospital.utils.ApplicationPropertiesLoader;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        Properties properties = ApplicationPropertiesLoader.load();
        config.setDriverClassName(properties.getProperty("dataSource.driverClassName"));
        config.setJdbcUrl(properties.getProperty("dataSource.jdbcUrl"));
        config.setUsername(properties.getProperty("dataSource.username"));
        config.setPassword(properties.getProperty("dataSource.password"));
        config.addDataSourceProperty("cachePrepStmts", properties.getProperty("dataSource.cachePrepStmts"));
        config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty("dataSource.prepStmtCacheSize"));
        config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty("dataSource.prepStmtCacheSqlLimit"));
        dataSource = new HikariDataSource(config);
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
