package com.dityatkin.gradient.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {
    private final String url;
    private final String user;
    private final String password;

    public DBConnector(String fileConfigName) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/" + fileConfigName));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        this.url = properties.getProperty("db.url");
        this.user = properties.getProperty("db.user");
        this.password = properties.getProperty("db.password");

    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }
}
