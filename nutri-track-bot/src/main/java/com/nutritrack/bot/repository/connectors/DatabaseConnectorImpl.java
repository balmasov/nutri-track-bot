package com.nutritrack.bot.repository.connectors;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnectorImpl implements DatabaseConnector {

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private final String url;
    private final String user;
    private final String pass;

    @Inject
    public DatabaseConnectorImpl(@Named("url") String url,
                                 @Named("user") String user,
                                 @Named("pass") String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, pass);
    }

}
