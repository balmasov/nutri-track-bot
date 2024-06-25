package com.nutritrack.bot.repository.sql_service.impl.setter;

import com.nutritrack.bot.repository.sql_service.ParamSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class NullSetter implements ParamSetter {
    @Override
    public boolean handles(Object param) {
        return Objects.isNull(param);
    }

    @Override
    public void setParam(PreparedStatement stmt, int index, Object param) throws SQLException {
        stmt.setNull(index, java.sql.Types.NULL);
    }
}
