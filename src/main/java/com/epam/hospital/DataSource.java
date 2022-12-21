package com.epam.hospital;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class DataSource {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        Properties properties = new Properties();
        try (InputStream inputStream = DataSource.class.getResourceAsStream("/db.properties")) {
            properties.load(new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
