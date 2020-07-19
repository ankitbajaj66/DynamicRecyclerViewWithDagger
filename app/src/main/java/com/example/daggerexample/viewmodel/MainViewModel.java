package com.example.daggerexample.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.daggerexample.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel{

    private MutableLiveData<List<FoodItem>> mutableLiveDataFoodItemList;
    private List<FoodItem> foodItemList;
    private FoodItem mFoodItem;
    private static final String TAG = "MainViewModel";

    public MainViewModel() {
        mutableLiveDataFoodItemList = new MutableLiveData<>();
        init();
    }

    public MutableLiveData<List<FoodItem>> getFoodListMutableLiveData() {
        return mutableLiveDataFoodItemList;
    }

    private void init(){
        populateList();
        mutableLiveDataFoodItemList.setValue(foodItemList);
    }

    private void populateList(){
        foodItemList = new ArrayList<>();
        mFoodItem = new FoodItem("","");
        foodItemList.add(mFoodItem);
    }

    public void addNewRow() {
        mFoodItem = new FoodItem("", "");
        foodItemList.add(mFoodItem);
        mutableLiveDataFoodItemList.setValue(foodItemList);
    }

    public void updateRowData(String foodItem, int position) {
        foodItemList.get(position).setFoodName(foodItem);
    }

    public void getFoodDetails() {
        StringBuilder foodDisplay = new StringBuilder("Food name --- \n");
        for(FoodItem food : foodItemList) {
            if(!food.getFoodName().isEmpty() && food.getDate()!=null) {
                foodDisplay.append(food.getFoodName());
                foodDisplay.append(" : " + food.getDate());
                foodDisplay.append("\n");
            }
        }
        Log.e(TAG, "getFoodDetails: "+foodDisplay );
    }

    public void updateDate(String date, int position) {
        foodItemList.get(position).setDate(date);
    }
}
