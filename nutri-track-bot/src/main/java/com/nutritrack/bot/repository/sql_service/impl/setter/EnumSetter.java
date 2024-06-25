package com.nutritrack.bot.repository.sql_service.impl.setter;

import com.nutritrack.bot.repository.sql_service.ParamSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EnumSetter implements ParamSetter {
    @Override
    public boolean handles(Object param) {
        return param instanceof Enum<?>;
    }

    @Override
    public void setParam(PreparedStatement stmt, int index, Object param) throws SQLException {
        stmt.setString(index, ((Enum<?>) param).name());
    }
}
