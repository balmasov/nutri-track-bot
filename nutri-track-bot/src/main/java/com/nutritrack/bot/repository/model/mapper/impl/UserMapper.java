package com.nutritrack.bot.repository.model.mapper.impl;

import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.repository.model.mapper.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper implements ResultSetMapper<User> {

    private final static Logger logger = LoggerFactory.getLogger(UserMapper.class);

    @Override
    public List<User> map(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();

        while (rs.next()) {

            Long id = rs.getLong("id");
            String name = rs.getString("name");

            UserStep userStep = rs.getString("step") == null
                    ? null
                    : UserStep.get(rs.getString("step"));

            users.add(
                    new User.Builder()
                            .id(id)
                            .userName(name)
                            .step(userStep)
                            .build()
            );
        }

        return users;
    }
}
