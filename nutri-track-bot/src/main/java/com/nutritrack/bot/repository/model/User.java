package com.nutritrack.bot.repository.model;

import java.math.BigInteger;

public class User {

    private BigInteger id;
    private String name;
    private UserStep step;

    private User () {}

    private User(Builder builder) {
        this.id = builder.id;
        this.name = builder.userName;
        this.step = builder.userStep;
    }

    public void setStep(UserStep userStep) {
        this.step = userStep;
    }

    public UserStep getStep() {
        return step;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Builder {
        private BigInteger id;
        private String userName;
        private UserStep userStep;

        public Builder() {
        }

        public Builder id(Long id) {
            this.id = BigInteger.valueOf(id);
            return this;
        }

        public Builder id(BigInteger id) {
            this.id = id;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder step(UserStep userStep) {
            this.userStep = userStep;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
