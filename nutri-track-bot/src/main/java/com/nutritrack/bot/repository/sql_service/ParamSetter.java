package com.nutritrack.bot.repository.sql_service;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ParamSetter {
    boolean handles(Object param);
    void setParam(PreparedStatement stmt, int index, Object param) throws SQLException;

}
