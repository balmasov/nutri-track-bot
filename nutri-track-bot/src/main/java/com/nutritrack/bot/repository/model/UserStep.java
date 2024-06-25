package com.nutritrack.bot.repository.model;

public enum UserStep {

    STEEPLES,
    COOKING,
    RAW_FOOD_WEIGHING,
    COOKED_FOOD_WEIGHING,
    COOKING_CALCULATION,
    EATING,
    EATING_NAMING,
    EATING_CALCULATION,
    COMMAND,
    DELETION;

    public static UserStep get(String value) {
        for (UserStep enumConstant : values()) {
            if (enumConstant.name().equalsIgnoreCase(value)) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException("No enum constant " + value);
    }
}
