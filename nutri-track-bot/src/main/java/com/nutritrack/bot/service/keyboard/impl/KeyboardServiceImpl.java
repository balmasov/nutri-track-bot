package com.nutritrack.bot.service.keyboard.impl;

import com.nutritrack.bot.config.MessageKey;
import com.nutritrack.bot.repository.model.Food;
import com.nutritrack.bot.service.keyboard.KeyboardService;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.telegram.CountCaloriesBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.nutritrack.bot.config.MessageKey.*;

public class KeyboardServiceImpl implements KeyboardService {

    private final MessageService msgService;
    private final CountCaloriesBot bot;

    public KeyboardServiceImpl(MessageService msgService,
                               CountCaloriesBot bot) {
        this.msgService = msgService;
        this.bot = bot;
    }

    @Override
    public SendMessage createCookingOrEatingKeyboard(CustomUpdate update, MessageKey key, Object... args) {
        SendMessage message = msgService.createSendMessage(update, key, args);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        row.add(msgService.getMessage(COOKING, update.getLocale()));
        row.add(msgService.getMessage(EATING, update.getLocale()));

        // Add the first row to the keyboard
        keyboard.add(row);
        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);

        // Optional settings
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);
        keyboardMarkup.setOneTimeKeyboard(true);

        message.setReplyMarkup(keyboardMarkup);

        return message;
    }

    @Override
    public SendMessage createFoodListBoard(CustomUpdate update, List<Food> foods) {
        SendMessage message = msgService.createSendMessage(update, SELECT_FOOD_ITEM);

        InlineKeyboardMarkup foodKeyboardMarkup = getFoodKeyboardMarkup(update, foods);
        message.setReplyMarkup(foodKeyboardMarkup);

        return message;
    }

    @Override
    public void deleteReplyMarkupKeyBoard(CustomUpdate update, MessageKey key, Object... args) {
        EditMessageText editMessage = msgService.createEditMessage(update, key, args);
        editMessage.setReplyMarkup(null);

        bot.execute(editMessage);
    }

    @Override
    public SendMessage createDeletionFoodKeyboard(CustomUpdate update, List<Food> foods, MessageKey key, Object... args) {
        SendMessage sendMessage = msgService.createSendMessage(update, key, args);

        InlineKeyboardMarkup foodKeyboardMarkup = getFoodKeyboardMarkup(update, foods);
        sendMessage.setReplyMarkup(foodKeyboardMarkup);

        foodKeyboardMarkup.getKeyboard()
                .add(List.of(createDeleteAllButton(update)));

        return sendMessage;
    }

    private InlineKeyboardMarkup getFoodKeyboardMarkup(CustomUpdate update, List<Food> foods) {
        return new InlineKeyboardMarkup(getFoodsListsButtons(update, foods));
    }

    private List<List<InlineKeyboardButton>> getFoodsListsButtons(CustomUpdate update, List<Food> foods) {
        String message = msgService.getMessage(FOOD_INFO, update.getLocale());
        return foods.stream()
                .map(food -> createFoodInfoButton(food, message))
                .collect(Collectors.toList());
    }

    private List<InlineKeyboardButton> createFoodInfoButton(Food food, String message) {
        InlineKeyboardButton foodInfoButton = new InlineKeyboardButton();
        foodInfoButton.setText(String.format(message, food.getName(), food.getRawWeight(), food.getCookedWeight()));
        foodInfoButton.setCallbackData(food.getId().toString());
        return List.of(foodInfoButton);
    }

    private InlineKeyboardButton createDeleteAllButton(CustomUpdate update) {
        InlineKeyboardButton deleteAllButton = new InlineKeyboardButton();
        deleteAllButton.setText(msgService.getMessage(DELETE_ALL_BUTTON, update.getLocale()));
        deleteAllButton.setCallbackData("delete all:" + update.getTelegramUser().getId());
        return deleteAllButton;
    }
}
