package com.nutritrack.bot.repository.sql_service.impl.setter;

import com.nutritrack.bot.repository.sql_service.ParamSetter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BigIntegerSetter implements ParamSetter {
    @Override
    public boolean handles(Object param) {
        return param instanceof BigInteger;
    }

    @Override
    public void setParam(PreparedStatement stmt, int index, Object param) throws SQLException {
        if (handles(param)) {
            BigInteger bigIntegerValue = (BigInteger) param;
            stmt.setBigDecimal(index, new BigDecimal(bigIntegerValue));
        } else {
            throw new IllegalArgumentException("Parameter is not a BigInteger: " + param);
        }
    }
}
