package com.example.solarcalculator;

import android.os.Bundle;
import android.widget.Toast;

import com.example.solarcalculator.Model.SolarCalData;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "solarcalculator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //How To Use Model
        SolarCalData solarCalData = SolarCalData.getBuilder("TV")
                .amps(200)
                .voltage(35)
                .hoursUsedDaily(12)
                .quantity(7)
                .build();


    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
