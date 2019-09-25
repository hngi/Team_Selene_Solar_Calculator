package com.example.solarcalculator;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;

public class CalculateActivity extends AppCompatActivity {
    private NumberPicker num_picker;
    private String[] pickerVals;
    private PopupWindow popUp;
    private LinearLayout layout;
    private LinearLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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


        popUp = new PopupWindow(this);
        layout = new LinearLayout(this);
        mainLayout = new LinearLayout(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigth = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (heigth*.5));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;
        getWindow().setAttributes(params);

    }
}
