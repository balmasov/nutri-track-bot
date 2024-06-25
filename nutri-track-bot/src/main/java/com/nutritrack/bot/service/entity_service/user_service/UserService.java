package com.nutritrack.bot.service.entity_service.user_service;

import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;

import java.math.BigInteger;
import java.util.Optional;

public interface UserService {

    User get(CustomUpdate update);

    void update(User user);

    User setStep(User user, UserStep step);

    Optional<User> get(BigInteger id);
}
