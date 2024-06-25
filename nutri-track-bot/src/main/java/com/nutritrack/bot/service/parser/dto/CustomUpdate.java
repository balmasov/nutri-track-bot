package com.nutritrack.bot.service.parser.dto;

import org.apache.commons.lang3.LocaleUtils;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Locale;

public class CustomUpdate extends Update {

    private User telegramUser;
    /**
     * Could be null if update has Message
     */
    private String callbackQueryData;
    /**
     * Could be null if update has CallbackQuery
     */
    private String userMsg;
    private Long chatId;

    private Locale locale;

    private CustomUpdate(Builder builder, Update update) {
        this.telegramUser = builder.user;
        this.userMsg = builder.userMsg;
        this.callbackQueryData = builder.callbackQueryData;
        this.chatId = builder.chatId;
        this.locale = builder.locale;

        // Copy fields from originalUpdate
        this.setUpdateId(update.getUpdateId());
        this.setMessage(update.getMessage());
        this.setEditedMessage(update.getEditedMessage());
        this.setChannelPost(update.getChannelPost());
        this.setEditedChannelPost(update.getEditedChannelPost());
        this.setInlineQuery(update.getInlineQuery());
        this.setChosenInlineQuery(update.getChosenInlineQuery());
        this.setCallbackQuery(update.getCallbackQuery());
        this.setShippingQuery(update.getShippingQuery());
        this.setPreCheckoutQuery(update.getPreCheckoutQuery());
        this.setPoll(update.getPoll());
        this.setPollAnswer(update.getPollAnswer());
    }


    public User getTelegramUser() {
        return telegramUser;
    }

    public void setTelegramUser(User telegramUser) {
        this.telegramUser = telegramUser;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    public String getCallbackQueryData() {
        return callbackQueryData;
    }

    public void setCallbackQueryData(String callbackQueryData) {
        this.callbackQueryData = callbackQueryData;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public static class Builder {
        private User user;
        private String userMsg;
        private String callbackQueryData;
        private Long chatId;
        private Locale locale;

        public Builder() {
        }

        public Builder telegramUser(User telegramUser) {
            this.user = telegramUser;
            return this;
        }

        public Builder userMsg(String userMsg) {
            this.userMsg = userMsg;
            return this;
        }

        public Builder callbackQueryData(String data) {
            this.callbackQueryData = data;
            return this;
        }

        public Builder chatId(Long chatId) {
            this.chatId = chatId;
            return this;
        }

        public Builder locale(String languageCode) {
            Locale locale;
            try {
                locale = LocaleUtils.toLocale(languageCode);
            } catch (Exception e) {
                locale = Locale.US;
            }
            this.locale = locale;
            return this;
        }

        public CustomUpdate build(Update update) {
            return new CustomUpdate(this, update);
        }
    }

    @Override
    public String toString() {
        return "CustomUpdate{" +
                "user=" + telegramUser +
                ", callbackQueryData='" + callbackQueryData + '\'' +
                ", userMsg='" + userMsg + '\'' +
                '}';
    }
}
