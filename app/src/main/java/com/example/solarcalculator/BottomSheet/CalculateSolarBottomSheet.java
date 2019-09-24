package com.example.solarcalculator.BottomSheet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.solarcalculator.Model.SolarCalData;
import com.example.solarcalculator.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CalculateSolarBottomSheet extends BottomSheetDialogFragment {
    public static final String NEW_INSTANCE_LIST_KEY ="com.example.solarcalculator.BottomSheet_NEW_INSTANCE_LIST_KEY";
    public static final String NEW_INSTANCE_SUNLIGHT_ACCESS_KEY ="com.example.solarcalculator.BottomSheet_NEW_INSTANCE_SUNLIGHT_ACCESS_KEY";

    private List<SolarCalData> solarCalDataList;
    private int userSunlightAccessInHours;

    private long totalWatt=0;

    public static CalculateSolarBottomSheet newInstance(ArrayList<SolarCalData> solarCalDataList, int userSunlightAccessInHours){
        CalculateSolarBottomSheet bottomSheet=new CalculateSolarBottomSheet();
        Bundle args=new Bundle();
        args.putParcelableArrayList(NEW_INSTANCE_LIST_KEY, solarCalDataList);
        args.putInt(NEW_INSTANCE_SUNLIGHT_ACCESS_KEY,userSunlightAccessInHours);
        bottomSheet.setArguments(args);
        return bottomSheet;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.calculation_display_bottomsheet_layout,container,false);
        TextView totalWattTextView=rootview.findViewById(R.id.cal_display_total_watt);
        TextView recSolarPowerTextView=rootview.findViewById(R.id.cal_display_rec_solar_power);
        TextView recPowerCapacityTextView=rootview.findViewById(R.id.cal_rec_battery_capacity);

        if(getArguments()!=null){
            solarCalDataList =getArguments().getParcelable(NEW_INSTANCE_LIST_KEY);
            userSunlightAccessInHours =getArguments().getInt(NEW_INSTANCE_SUNLIGHT_ACCESS_KEY);
            displaySolarData();
        }

        return rootview;
    }

    private void displaySolarData() {
        for (SolarCalData solarCalData : solarCalDataList){
            totalWatt=totalWatt+(solarCalData.getVoltage()*solarCalData.getAmps());
        }
    }





    //TODO method unfinished
    private int calWatt(SolarCalData solarCalData){
        return 0;
    }

    //TODO method unfinished
    private String calBatteryCapacity(SolarCalData solarCalData){
        return null;//String.valueOf(solarCalData.getVoltage()*solarCalData.g)
    }

    //TODO method unfinished
    private String calSolarPowerNeeded(SolarCalData solarCalData){
        return null;
    }

    //TODO method unfinished
    private String calTotalWatt(SolarCalData solarCalData){
        return null;
    }


}
