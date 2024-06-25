package com.nutritrack.bot.service.step.impl.deletion;

import com.nutritrack.bot.exception.WrongInputException;
import com.nutritrack.bot.repository.model.User;
import com.nutritrack.bot.repository.model.UserStep;
import com.nutritrack.bot.service.entity.food_service.FoodService;
import com.nutritrack.bot.service.entity.user_service.UserService;
import com.nutritrack.bot.service.keyboard.KeyboardService;
import com.nutritrack.bot.service.message.MessageService;
import com.nutritrack.bot.service.parser.dto.CustomUpdate;
import com.nutritrack.bot.service.step.StepService;
import com.nutritrack.bot.service.step.StepState;
import com.nutritrack.bot.util.NumberExtractHelper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.inject.Inject;

import static com.nutritrack.bot.config.MessageKey.*;

public class DeletionServiceImpl implements StepService, StepState {

    private final MessageService messageService;
    private final FoodService foodService;
    private final KeyboardService keyboardService;
    private final UserService userService;

    @Inject
    public DeletionServiceImpl(MessageService messageService,
                               FoodService foodService,
                               KeyboardService keyboardService,
                               UserService userService) {
        this.messageService = messageService;
        this.foodService = foodService;
        this.keyboardService = keyboardService;
        this.userService = userService;
    }

    //TODO cansel command should cansel deletion and return deletion food if user in the deletion process
    @Override
    public SendMessage doStep(User user, CustomUpdate update) {
        if (!update.hasCallbackQuery()) {
            throw new WrongInputException(messageService.getMessage(CHOSE_KEYBOARD_OPTION, update.getLocale()));
        }

        String callbackQueryData = update.getCallbackQueryData();

        return NumberExtractHelper.onlyDigits(callbackQueryData)
                ? deleteFoodItem(update, callbackQueryData, user)
                : deleteAll(update, user);
    }

    private SendMessage deleteAll(CustomUpdate update, User user) {
        foodService.deleteUserFood(user.getId());

        String userChoice = messageService.getMessage(DELETE_ALL_BUTTON, update.getLocale());
        keyboardService.deleteReplyMarkupKeyBoard(update, YOUR_CHOICE_WAS, userChoice);

        return allFoodWasDeleted(update, user);
    }

    //TODO It is better to change existing message than delete keyboard and sent new keyboard again
    private SendMessage deleteFoodItem(CustomUpdate update, String callbackQueryData, User user) {
        Long foodId = NumberExtractHelper.extractLong(callbackQueryData);

        String nameOfDeletedFood = foodService.deleteFood(foodId);
        keyboardService.deleteReplyMarkupKeyBoard(update, YOUR_CHOICE_WAS, nameOfDeletedFood);

        //TODO now user can use only cansel command to stop deletion FOOD_WAS_DELETED message
        return foodService.getUsersFood(user.getId())
                .filter(list -> !list.isEmpty())
                .map(foods -> keyboardService.createDeletionFoodKeyboard(update, foods, ONE_FOOD_WAS_DELETED, nameOfDeletedFood))
                .orElseGet(() -> allFoodWasDeleted(update, user));
    }

    private SendMessage allFoodWasDeleted(CustomUpdate update, User user) {
        userService.setStep(user, UserStep.STEEPLES);
        userService.update(user);

        return messageService.createSendMessage(update, ALL_FOOD_WAS_DELETED);
    }

    @Override
    public UserStep step() {
        return UserStep.DELETION;
    }
}
