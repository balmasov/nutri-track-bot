package com.nutritrack.bot.repository.sql_service.impl;

import com.nutritrack.bot.repository.connectors.DatabaseConnector;
import com.nutritrack.bot.repository.model.mapper.ResultSetMapper;
import com.nutritrack.bot.repository.sql_service.ParamSetter;
import com.nutritrack.bot.repository.sql_service.SQLService;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SQLServiceImpl implements SQLService {

    private final Set<ParamSetter> setters;

    private final ResultSetMapper<?> mapper;

    private final DatabaseConnector dbConnector;

    @Inject
    public SQLServiceImpl(Set<ParamSetter> setters,
                          ResultSetMapper<?> mapper,
                          DatabaseConnector dbConnector) {
        this.setters = setters;
        this.mapper = mapper;
        this.dbConnector = dbConnector;
    }

    @Override
    public List<?> select(String query, List<?> args) {

        try(Connection connection = dbConnector.getConnection();
            PreparedStatement stmt = extracted(connection, query, args);
            ResultSet resultSet = stmt.executeQuery()) {

            return mapper.map(resultSet);

        } catch (Exception e) {
            throw new RuntimeException("Failed while trying to execute query. Exception: \n" + e);
        }
    }

    @Override
    public List<?> select(String query, Object param) {
        return select(query, Collections.singletonList(param));
    }

    @Override
    public void execute(String query, List<?> args) {
        try(Connection connection = dbConnector.getConnection();
            PreparedStatement stmt = extracted(connection, query, args)) {

            stmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException("Failed while trying to execute query. Exception: \n" + e);
        }
    }

    private PreparedStatement extracted(Connection connection, String query, List<?> args) {
        try {
            return getPreparedStatement(connection, query, args);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to prepare statement. Exception: \n" + e);
        }
    }

    private PreparedStatement getPreparedStatement(Connection connection, String query, List<?> args) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(query);
        int index = 1;
        for (Object param : args) {
            for (ParamSetter setter : setters) {
                if (setter.handles(param)) {
                    setter.setParam(stmt, index, param);
                    break;
                }
            }
            index++;
        }
        return stmt;
    }
}
