package com.nutritrack.bot.service.keyboard;

import com.nutritrack.bot.config.MessageKey;
import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

public interface KeyboardService {

    SendMessage createCookingOrEatingKeyboard(CustomUpdate update, MessageKey key, Object... args);

    SendMessage createFoodListBoard(CustomUpdate update, List<Food> foods);

    void deleteReplyMarkupKeyBoard(CustomUpdate update, MessageKey key, Object... args);

    SendMessage createDeletionFoodKeyboard(CustomUpdate update, List<Food> foods, MessageKey key, Object... args);
}
