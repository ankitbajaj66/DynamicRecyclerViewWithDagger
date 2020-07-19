package com.example.daggerexample.rv;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private View mView;
    private int _day;
    private int _month;
    private int _birthYear;
    private OnDateChangedInterface mListener;

    public interface OnDateChangedInterface{
        void onDateEntered(String date);
    }

    public  DatePickerFragment(View view, OnDateChangedInterface listener) {
        mView = view;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialogDatePicker = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog_MinWidth,this, year, month, day);
        dialogDatePicker.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialogDatePicker;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        _birthYear = year;
        _month = month;
        _day = dayOfMonth;
        updateDisplay();
    }

    private void updateDisplay() {

        StringBuilder dateString = new StringBuilder();
        dateString.append(_day).append("/").append(_month + 1).append("/").append(_birthYear).append(" ");
        ((EditText) mView).setText(dateString);

        mListener.onDateEntered(String.valueOf(dateString));

    }
}
