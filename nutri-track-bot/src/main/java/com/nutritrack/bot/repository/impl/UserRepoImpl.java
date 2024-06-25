package com.nutritrack.bot.repository.impl;

import com.nutritrack.bot.repository.SqlQuery;
import com.nutritrack.bot.repository.UserRepo;
import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.sql_service.SQLService;

import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigInteger;
import java.util.*;

import static com.nutritrack.bot.repository.SqlQuery.*;

public class UserRepoImpl implements UserRepo {

    @Named("UserSQLService")
    private final SQLService sqlService;
    private final Map<SqlQuery, String> sqlQueries;

    @Inject
    public UserRepoImpl(SQLService sqlService,
                        Map<SqlQuery, String> sqlQueries) {
        this.sqlService = sqlService;
        this.sqlQueries = sqlQueries;
    }

    @Override
    public void save(User user) {
        sqlService.execute(sqlQueries.get(SAVE_USER), Arrays.asList(user.getId(), user.getName(), user.getStep().toString()));
    }

    @Override
    public void update(User user) {
        sqlService.execute(sqlQueries.get(UPDATE_USER), Arrays.asList(user.getName(), user.getStep(), user.getId()));
    }

    @Override
    public Optional<User> getUser(BigInteger userId) {
        return findUser(userId);
    }

    @Override
    public Optional<User> getUser(Long userId) {
        return findUser(BigInteger.valueOf(userId));
    }

    private Optional<User> findUser(BigInteger userId) {
        List<User> user = (List<User>) sqlService.select(sqlQueries.get(GET_USER), Collections.singletonList(userId));

        if (user.isEmpty()) {
            return Optional.empty();
        }

        return Optional.ofNullable(user.get(0));
    }
}
