package com.nutritrack.bot.repository;

import com.nutritrack.bot.repository.model.User;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepo {

    void save(User user);

    void update(User user);

    Optional<User> getUser(BigInteger userId);

    Optional<User> getUser(Long userId);
}
