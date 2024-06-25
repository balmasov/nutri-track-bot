package com.nutritrack.bot.config;

public enum MessageKey {
    COOKING_OR_EATING("cooking_or_eating"),
    COOKING("cooking"),
    EATING("eating"),
    SELECT_FOOD_ITEM("select_food_item"),
    FOOD_INFO("food_info"),
    NAME_OF_FOOD_YOU_ARE_COOKING("name_of_food_you_are_cooking"),
    NO_FOOD_FOUND("no_food_found"),
    HOW_MANY_GRAMS_OF_RAW_FOOD("how_many_grams_of_raw_food"),
    HOW_MANY_GRAMS_OF_COOKED_FOOD("how_many_grams_of_cooked_food"),
    COOKED_FOOD_INFO("cooked_food_info"),
    HOW_MANY_YOU_EAT("how_many_you_eat"),
    YOU_NEED_X_GRAM("you_need_x_gram"),
    CANSEL_COMMAND_MSG("cansel_command_msg"),
    USER_ERR_MSG("user_err_msg"),
    SERVER_ERR_MSG("server_err_msg"),
    MSG_TO_DEV("msg_to_dev"),
    UPDATE_MSG("update_msg"),
    UNKNOWN_MSG_TYPE("unknown_msg_type"),
    NUMBER_FORMAT_WRONG_INPUT_GUESS("number_format_wrong_input_guess"),
    NUMBER_FORMAT_WRONG_INPUT("number_format_wrong_input"),
    START_COMMAND_MSG("start_command_msg"),
    YOUR_CHOICE_WAS("your_choice_was"),
    CHOSE_KEYBOARD_OPTION("chose_keyboard_option"),
    HELP_MESSAGE("help_message"),
    DELETE_ONE_OR_ALL_FOOD_MESSAGE("delete_food_message"),
    DELETE_ALL_BUTTON("delete_all"),
    ONE_FOOD_WAS_DELETED("food_was_deleted"),
    ALL_FOOD_WAS_DELETED("all_food_was_deleted");

    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;

    }
}
