package com.example.solarcalculator;

import android.os.Bundle;
import android.widget.Toast;

import com.example.solarcalculator.BottomSheet.CalculateSolarBottomSheet;
import com.example.solarcalculator.Model.SolarCalData;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "solarcalculator";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }




    private void openCalculationBottomSheet(List<SolarCalData> solarCalDataList, int userSunlightAccessInHours){
        CalculateSolarBottomSheet bottomSheet=CalculateSolarBottomSheet.newInstance(solarCalDataList,userSunlightAccessInHours);
        bottomSheet.setCancelable(false);
        bottomSheet.show(getSupportFragmentManager(),null);
    }

    private void showToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
