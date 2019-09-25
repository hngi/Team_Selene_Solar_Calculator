package com.example.solarcalculator;

/*
 * Created by Utibe Etim (@Ut_et)
 * */

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.NumberPicker;

public class CalculateActivity extends AppCompatActivity {
    private NumberPicker num_picker;
    private String[] pickerVals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculate);

        num_picker = findViewById(R.id.num_picker);
        num_picker.setMaxValue(9);
        num_picker.setMinValue(0);
        pickerVals  = new String[] {"1", "2", "3", "4", "5","6", "7", "8", "9", "10"};
        num_picker.setDisplayedValues(pickerVals);

        num_picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                int valuePicker1 = num_picker.getValue();
                Log.d("picker value", pickerVals[valuePicker1]);
            }
        });

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigth = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (heigth*.7));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);

    }
}
