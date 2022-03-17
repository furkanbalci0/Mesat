package com.furkanbalci.mesat.database;

import androidx.annotation.NonNull;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class Database {

    private final HikariDataSource dataSource;

    public Database(String hostname, int port, String database, String username, String password) {

        this.dataSource = new HikariDataSource();

        this.dataSource.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        this.dataSource.addDataSourceProperty("serverName", hostname);
        this.dataSource.addDataSourceProperty("port", port);
        this.dataSource.addDataSourceProperty("databaseName", database);
        this.dataSource.addDataSourceProperty("user", username);
        this.dataSource.addDataSourceProperty("password", password);
    }

    public HikariDataSource getDataSource() {
        return this.dataSource;
    }

    public ResultSet selectQuery(@NonNull String query) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement select = connection.prepareStatement(query);
             ResultSet result = select.executeQuery()) {
            if (result.next()) {
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void executeQuery(@NonNull String query) {
        try (Connection connection = this.dataSource.getConnection();
             PreparedStatement select = connection.prepareStatement(query)) {
            select.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
