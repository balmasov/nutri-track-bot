package com.nutritrack.bot.repository.sql_service.impl.setter;

import com.nutritrack.bot.repository.sql_service.ParamSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LongSetter implements ParamSetter {
    @Override
    public boolean handles(Object param) {
        return param instanceof Long;
    }

    @Override
    public void setParam(PreparedStatement stmt, int index, Object param) throws SQLException {
        stmt.setLong(index, (Long) param);
    }
}
