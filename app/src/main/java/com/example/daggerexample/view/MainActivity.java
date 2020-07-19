package com.example.daggerexample.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.daggerexample.R;
import com.example.daggerexample.model.FoodItem;
import com.example.daggerexample.rv.FoodItemRecyclerView;
import com.example.daggerexample.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements FoodItemRecyclerView.OnFoodItemEnteredListener,LifecycleOwner {

    private RecyclerView recyclerView;
    private FoodItemRecyclerView mAdapter;
    private Button btn;
    private Button btnOrder;
    private MainViewModel viewModel;
    private MainActivity context;

    private List<FoodItem> foodItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();
        initRecyclerView();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addNewRow();
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.getFoodDetails();
            }
        });
    }

    private void initViews() {
        recyclerView = findViewById(R.id.rv);
        btn = findViewById(R.id.button2);
        btnOrder = findViewById(R.id.btnOrder);
        context = this;
        viewModel = ViewModelProviders.of(context).get(MainViewModel.class);
        viewModel.getFoodListMutableLiveData().observe(this, foodListObserver);
    }

    private void initRecyclerView() {
        mAdapter = new FoodItemRecyclerView(foodItemList, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        mAdapter.setInterfaceListener(this);
    }


    Observer<List<FoodItem>> foodListObserver = new Observer<List<FoodItem>>() {
        @Override
        public void onChanged(List<FoodItem> foodItems) {
            mAdapter.notifyData(foodItems);
        }
    };

    @Override
    public void onFoodItemEntered(String foodItem, int position) {
        viewModel.updateRowData(foodItem,position);
    }

    @Override
    public void onDateEntered(String date, int position) {
        viewModel.updateDate(date,position);
    }
}
