package com.chenyn.androidproject1;

public class MessageEvent {
    private FoodModel foodModel;
    public MessageEvent(FoodModel foodModel) {
        this.foodModel = foodModel;
    }

    public FoodModel getFoodModel() {
        return this.foodModel;
    }

    public void setFoodModel(FoodModel foodModel) {
        this.foodModel = foodModel;
    }
}
