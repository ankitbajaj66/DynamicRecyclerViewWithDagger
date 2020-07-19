package com.example.daggerexample.model;

public class FoodItem {

    private String foodName;
    private String date;

    public FoodItem(String foodName, String date) {
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
