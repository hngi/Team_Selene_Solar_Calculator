package com.example.solarcalculator.BottomSheet;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.solarcalculator.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.solarcalculator.Model.SolarCalData;

public class CalculateSolarBottomSheet extends BottomSheetDialogFragment {
    public static final String NEW_INSTANCE_LIST_KEY = "com.example.solarcalculator.BottomSheet_NEW_INSTANCE_LIST_KEY";
    public static final String NEW_INSTANCE_SUNLIGHT_ACCESS_KEY = "com.example.solarcalculator.BottomSheet_NEW_INSTANCE_SUNLIGHT_ACCESS_KEY";

    private List<SolarCalData> solarCalDataList;
    private int userSunlightAccessInHours;

    private long totalWatt = 0;
    private long totalWattHour = 0;
    private TextView totalWattHourTextView;
    private TextView recSolarPowerTextView;
    private TextView recPowerCapacityTextView;

    public static CalculateSolarBottomSheet newInstance(List<SolarCalData> solarCalDataList, int userSunlightAccessInHours) {
        CalculateSolarBottomSheet bottomSheet = new CalculateSolarBottomSheet();
        Bundle args = new Bundle();
        args.putParcelableArrayList(NEW_INSTANCE_LIST_KEY, (ArrayList<? extends Parcelable>) solarCalDataList);
        args.putInt(NEW_INSTANCE_SUNLIGHT_ACCESS_KEY, userSunlightAccessInHours);
        bottomSheet.setArguments(args);
        return bottomSheet;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.calculation_display_bottomsheet_layout, container, false);
        totalWattHourTextView = rootview.findViewById(R.id.cal_display_total_watt);
        recSolarPowerTextView = rootview.findViewById(R.id.cal_display_rec_solar_power);
        recPowerCapacityTextView = rootview.findViewById(R.id.cal_rec_battery_capacity);
        Button dismissBtn = rootview.findViewById(R.id.cal_display_dismiss_btn);

        if (getArguments() != null) {
            solarCalDataList = getArguments().getParcelableArrayList(NEW_INSTANCE_LIST_KEY);
            userSunlightAccessInHours = getArguments().getInt(NEW_INSTANCE_SUNLIGHT_ACCESS_KEY);
            displaySolarData();
        }

        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return rootview;
    }

    private void displaySolarData() {
        calTotalWattAndTotalWattHour();
        totalWattHourTextView.setText(calTotalWattHour());
        recSolarPowerTextView.setText(calRecSolarPowerNeeded());
        recPowerCapacityTextView.setText(calRecBatteryCapacity());
    }


    private void calTotalWattAndTotalWattHour() {
        for (SolarCalData solarCalData : solarCalDataList) {
            totalWatt = totalWatt + (solarCalData.getVoltage() * solarCalData.getAmps());
            totalWattHour=totalWattHour + (solarCalData.getVoltage() * solarCalData.getAmps()*solarCalData.getHoursUsedDaily());
        }
    }

    private String calRecBatteryCapacity() {
        return String.valueOf(totalWatt / 12);
    }


    private String calRecSolarPowerNeeded() {
        return String.valueOf(totalWatt / userSunlightAccessInHours);
    }

    private String calTotalWattHour() {
        return String.valueOf(totalWattHour);
    }


}
