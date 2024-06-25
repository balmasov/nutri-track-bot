package com.nutritrack.bot.repository.sql_service.impl.setter;

import com.nutritrack.bot.repository.sql_service.ParamSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StringSetter implements ParamSetter {
    @Override
    public boolean handles(Object param) {
        return param instanceof String;
    }

    @Override
    public void setParam(PreparedStatement stmt, int index, Object param) throws SQLException {
        String strParam = (String) param;
        stmt.setString(index,  strParam.toLowerCase());
    }
}
