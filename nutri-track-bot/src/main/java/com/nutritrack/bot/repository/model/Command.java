package com.nutritrack.bot.repository.model;

public enum Command {

    CANCEL,
    START,
    HELP,
    DELETE;

    public static Command get(String value) {
        for (Command enumConstant : values()) {
            if (enumConstant.name().equalsIgnoreCase(value)) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException("No enum constant " + value);
    }
}
