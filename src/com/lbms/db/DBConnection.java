package com.lbms.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DBConnection {

    private static final HikariDataSource dataSource;

    static {
        try {
            Properties props = new Properties();
            InputStream is = DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream("db.properties");

            if (is == null) {
                throw new RuntimeException("db.properties not found");
            }

            props.load(is);

            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.user"));
            config.setPassword(props.getProperty("db.password"));

            // Pool settings
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(10000);

            dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize DB pool", e);
        }
    }

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
