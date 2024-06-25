package com.nutritrack.bot.repository.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@FunctionalInterface
public interface ResultSetMapper<T> {

    List<T> map(ResultSet rs) throws SQLException;
}
