package com.nutritrack.bot.service.entity_service.user_service.impl;

import com.nutritrack.bot.repository.UserRepo;
import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.entity_service.user_service.UserService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;

    @Inject
    public UserServiceImpl(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User get(CustomUpdate update) {

        if (update.hasMessage() && update.getMessage().isCommand()) {
            return createNewUser(update.getTelegramUser(), UserStep.COMMAND);
        }

        return userRepository.getUser(update.getTelegramUser().getId())
                .orElseGet(() -> {
                    User newUser = createNewUser(update.getTelegramUser(), UserStep.STEEPLES);
                    userRepository.save(newUser);
                    return newUser;
                });
    }

    @Override
    public Optional<User> get(BigInteger id) {
        return userRepository.getUser(id);
    }

    @Override
    public void update(User user) {
        if (Objects.isNull(user) || Objects.isNull(user.getId())) {
            throw new IllegalArgumentException("User or userId is null");
        }
        userRepository.update(user);
    }

    @Override
    public User setStep(User user, UserStep step) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (step == null) {
            throw new IllegalArgumentException("UserStep cannot be null");
        }

        user.setStep(step);
        return user;
    }

    private User createNewUser(org.telegram.telegrambots.meta.api.objects.User user, UserStep step) {
        return new User.Builder()
                .id(user.getId())
                .userName(user.getUserName())
                .step(step)
                .build();
    }
}
