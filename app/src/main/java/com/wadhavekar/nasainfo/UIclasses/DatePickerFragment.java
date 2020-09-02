package com.wadhavekar.nasainfo.UIclasses;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.wadhavekar.nasainfo.UIclasses.APOD.SET_DATE;
import static com.wadhavekar.nasainfo.UIclasses.APOD.SHARED_PREF;

public class DatePickerFragment extends DialogFragment  {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        SharedPreferences sp = getContext().getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        String date = sp.getString(SET_DATE,"2020-08-31");
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Calendar cal = Calendar.getInstance();
        int yearCal = cal.get(Calendar.YEAR);
        int monthCal = cal.get(Calendar.MONTH);
        int dayCal = cal.get(Calendar.DAY_OF_MONTH);
        try {
            c.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener) getActivity(),year,month,day);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());


        return dialog;
    }

}
