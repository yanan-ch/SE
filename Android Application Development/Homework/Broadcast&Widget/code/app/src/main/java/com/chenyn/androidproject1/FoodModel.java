package com.chenyn.androidproject1;

import java.io.Serializable;

public class FoodModel implements Serializable {
    private String foodName;
    private String circleContent;
    private String foodKind;
    private String nutriment;
    private String bgColor;
    private boolean ifFavor;
    private boolean ifStar;

    public FoodModel(String _foodName, String _circleContent, String _foodKind,
                 String _nutriment, String _bgColor) {
        foodName = _foodName;
        circleContent = _circleContent;
        foodKind = _foodKind;
        nutriment = _nutriment;
        bgColor = _bgColor;
        ifFavor = false;
        ifStar = false;
    }

    public void setFoodName(String _foodName) {
        foodName = _foodName;
    }
    public String getFoodName(){
        return foodName;
    }

    public void setCircleContent(String _circleContent) {
        circleContent = _circleContent;
    }
    public String getCircleContent() {
        return circleContent;
    }

    public void setFoodKind(String _foodKind) {
        foodKind = _foodKind;
    }
    public String getFoodKind() {
        return foodKind;
    }

    public void setNutriment(String _nutriment) {
        nutriment = _nutriment;
    }
    public String getNutriment() {
        return nutriment;
    }

    public void setBgColor(String _bgColor) {
        bgColor = _bgColor;
    }
    public String getBgColor() {
        return bgColor;
    }

    public void setIfFavor(boolean _ifFavor) {
        ifFavor = _ifFavor;
    }
    public boolean getIfFavor() {
        return ifFavor;
    }

    public void setIfStar(boolean _ifStar) {
        ifStar = _ifStar;
    }
    public boolean getIfStar() {
        return  ifStar;
    }
}
