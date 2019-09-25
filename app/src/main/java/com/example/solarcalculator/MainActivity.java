package com.example.solarcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import Model.SolarCalData;

public class MainActivity extends AppCompatActivity {

    ArrayList<SolarCalData> sList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        SolarCalData data = SolarCalData.getBuilder("Dummy").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data1 = SolarCalData.getBuilder("Refigirator").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data2 = SolarCalData.getBuilder("Laptop").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data3 = SolarCalData.getBuilder("Sound System").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data4 = SolarCalData.getBuilder("Pressing Iron").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data5 = SolarCalData.getBuilder("Dummy").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data6 = SolarCalData.getBuilder("Refigirator").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data7 = SolarCalData.getBuilder("Laptop").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data8 = SolarCalData.getBuilder("Sound System").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data9 = SolarCalData.getBuilder("Pressing Iron").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data10 = SolarCalData.getBuilder("Dummy").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data11 = SolarCalData.getBuilder("Refigirator").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data12 = SolarCalData.getBuilder("Laptop").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data13 = SolarCalData.getBuilder("Sound System").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data14 = SolarCalData.getBuilder("Pressing Iron").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data15 = SolarCalData.getBuilder("Dummy").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data16 = SolarCalData.getBuilder("Refigirator").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data17 = SolarCalData.getBuilder("Laptop").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data18 = SolarCalData.getBuilder("Sound System").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        SolarCalData data19 = SolarCalData.getBuilder("Pressing Iron baxkbdikbb bibsicb").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        sList.add(data);
        sList.add(data1);
        sList.add(data2);
        sList.add(data3);
        sList.add(data4);
        sList.add(data5);
        sList.add(data6);
        sList.add(data7);
        sList.add(data8);
        sList.add(data9);
        sList.add(data10);
        sList.add(data11);
        sList.add(data12);
        sList.add(data13);
        sList.add(data14);
        sList.add(data15);
        sList.add(data16);
        sList.add(data17);
        sList.add(data18);
        sList.add(data19);

        CustomAdapter customAdapter = new CustomAdapter(sList, this);

        recyclerView.setAdapter(customAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
