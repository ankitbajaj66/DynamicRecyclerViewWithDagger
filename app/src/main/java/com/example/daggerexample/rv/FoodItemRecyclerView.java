package com.example.daggerexample.rv;

import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.daggerexample.R;
import com.example.daggerexample.model.FoodItem;
import com.example.daggerexample.view.MainActivity;

import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class FoodItemRecyclerView extends RecyclerView.Adapter<FoodItemRecyclerView.ViewHolder> {

    private List<FoodItem> foodList;
    private OnFoodItemEnteredListener foodItemInterface;
    private Context mContext;
    private static final String TAG = "FoodItemRecyclerView";

    public FoodItemRecyclerView(List<FoodItem> foodList, Context context) {
        this.foodList = foodList;
        this.mContext = context;
    }

    public interface OnFoodItemEnteredListener {
        void onFoodItemEntered(String foodItem, int position);
        void onDateEntered(String date, int position);
    }

    public void setInterfaceListener(OnFoodItemEnteredListener foodItemInterface){
        this.foodItemInterface = foodItemInterface;
    }

    @NonNull
    @Override
    public FoodItemRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final FoodItemRecyclerView.ViewHolder holder, final int position) {
        holder.edtFoodName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager)mContext.getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(holder.edtFoodName.getWindowToken(), 0);
                foodItemInterface.onFoodItemEntered(v.getText().toString(),position);
                return true;
            }
        });
        holder.edtDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment(v, new DatePickerFragment.OnDateChangedInterface() {
                    @Override
                    public void onDateEntered(String date) {
                        Log.e(TAG, "onDateEntered: "+date );
                        foodItemInterface.onDateEntered(date,position);
                    }
                });
                newFragment.show(((MainActivity)mContext).getSupportFragmentManager(), "datePicker");
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText edtFoodName, edtDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edtFoodName = itemView.findViewById(R.id.edtFoodName);
            edtDate = itemView.findViewById(R.id.edtDate);
        }
    }

    public void notifyData(List<FoodItem> newFoodList) {
        this.foodList = newFoodList;
        notifyItemInserted(foodList.size() - 1);
    }
}
