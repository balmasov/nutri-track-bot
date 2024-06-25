package com.nutritrack.bot.repository.model.mapper.impl;

import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.repository.model.FoodState;
import com.nutritrack.bot.repository.model.mapper.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FoodMapper implements ResultSetMapper<Food> {
    private final static Logger logger = LoggerFactory.getLogger(FoodMapper.class);

    @Override
    public List<Food> map(ResultSet rs) throws SQLException {
        List<Food> result = new ArrayList<>();

        while (rs.next()) {

            Long id = getLong(rs, "id");
            Double cookedWeight = getDouble(rs, "cooked_weight");
            String name = getString(rs, "name");
            Double rawWeight = getDouble(rs, "raw_weight");
            String state = getString(rs, "state");
            BigInteger userId = Optional.ofNullable(getBigDecimal(rs, "user_id"))
                    .map(BigDecimal::toBigInteger)
                    .orElse(null);

            result.add(
                    new Food.Builder()
                            .id(id)
                            .name(name)
                            .rawWeight(rawWeight)
                            .cookedWeight(cookedWeight)
                            .state(state != null ? FoodState.valueOf(state) : null)
                            .userId(userId)
                            .build()
            );
        }

        return result;
    }

    private String getString(ResultSet rs, String columnName) {
        try {
            return rs.getString(columnName);
        } catch (SQLException e) {
            return null;
        }
    }

    private Long getLong(ResultSet rs, String columnName) {
        try {
            return rs.getLong(columnName);
        } catch (SQLException e) {
            return null;
        }
    }

    private Double getDouble(ResultSet rs, String columnName) {
        try {
            return rs.getDouble(columnName);
        } catch (SQLException e) {
            return null;
        }
    }

    private BigDecimal getBigDecimal(ResultSet rs, String columnName) {
        try {
            return rs.getBigDecimal(columnName);
        } catch (SQLException e) {
            return null;
        }
    }
}
