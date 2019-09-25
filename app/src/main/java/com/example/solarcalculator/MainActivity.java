package com.example.solarcalculator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.solarcalculator.BottomSheet.CalculateSolarBottomSheet;
import com.example.solarcalculator.BottomSheet.CustomAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import com.example.solarcalculator.Model.SolarCalData;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "solarcalculator";
    ArrayList<SolarCalData> sList = new ArrayList<>();
    private Button mCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        SolarCalData data = SolarCalData.getBuilder("Dummy").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        sList.add(data);

        CustomAdapter customAdapter = new CustomAdapter(sList, this);

        recyclerView.setAdapter(customAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);


        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalculateDialog();
            }
        });


    }

    private void openCalculateDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.calculate_dialog, null);
        mCalculate = mView.findViewById(R.id.cal_btn);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("HELLO");
            }
        });
    }
    private void openFabDialog() {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.calculate_dialog, null);
        mCalculate = mView.findViewById(R.id.cal_btn);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();
        mCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("HELLO");
            }
        });
    }


    private void openCalculationBottomSheet(List<SolarCalData> solarCalDataList, int userSunlightAccessInHours){
        CalculateSolarBottomSheet bottomSheet = CalculateSolarBottomSheet.newInstance(solarCalDataList, userSunlightAccessInHours);
        bottomSheet.setCancelable(false);
        bottomSheet.show(getSupportFragmentManager(),null);

    }

    private void showToast(String msg){Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();}
}
