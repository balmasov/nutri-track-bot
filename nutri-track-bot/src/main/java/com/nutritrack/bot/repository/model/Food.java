package com.nutritrack.bot.repository.model;

import java.math.BigInteger;
import java.util.Objects;

public class Food {

    private Long id;
    private FoodState state;
    private String name;
    private Double rawWeight;
    private Double cookedWeight;
    private BigInteger userId;

    private Food() {
    }

    private Food(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.rawWeight = builder.rawWeight;
        this.cookedWeight = builder.cookedWeight;
        this.state = builder.state;
        this.userId = builder.userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRawWeight() {
        return rawWeight;
    }

    public void setRawWeight(Double rawWeight) {
        this.rawWeight = rawWeight;
    }

    public Double getCookedWeight() {
        return cookedWeight;
    }

    public void setCookedWeight(Double cookedWeight) {
        this.cookedWeight = cookedWeight;
    }

    public FoodState getState() {
        return state;
    }

    public void setState(FoodState state) {
        this.state = state;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public static class Builder {
        private Long id;
        private String name;
        private Double rawWeight;
        private Double cookedWeight;
        private FoodState state;
        private BigInteger userId;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder rawWeight(Double rawWeight) {
            this.rawWeight = rawWeight;
            return this;
        }

        public Builder cookedWeight(Double cookedWeight) {
            this.cookedWeight = cookedWeight;
            return this;
        }

        public Builder state(FoodState state) {
            this.state = state;
            return this;
        }

        public Builder userId(BigInteger userId) {
            this.userId = userId;
            return this;
        }

        public Food build() {
            return new Food(this);
        }
    }

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", state=" + state +
                ", name='" + name + '\'' +
                ", rawWeight=" + rawWeight +
                ", cookedWeight=" + cookedWeight +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Food food)) return false;
        return Objects.equals(id, food.id) && state == food.state && Objects.equals(name, food.name) && Objects.equals(rawWeight, food.rawWeight) && Objects.equals(cookedWeight, food.cookedWeight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, state, name, rawWeight, cookedWeight);
    }
}
