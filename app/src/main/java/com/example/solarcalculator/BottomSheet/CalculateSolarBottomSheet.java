package com.example.solarcalculator.BottomSheet;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.solarcalculator.Model.User;
import com.example.solarcalculator.R;
import com.example.solarcalculator.viewmodel.UserViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.solarcalculator.Model.SolarCalData;

public class CalculateSolarBottomSheet extends BottomSheetDialogFragment {
    private static final String NEW_INSTANCE_LIST_KEY = "com.example.solarcalculator.BottomSheet_NEW_INSTANCE_LIST_KEY";
    private static final String NEW_INSTANCE_SUNLIGHT_ACCESS_KEY = "com.example.solarcalculator.BottomSheet_NEW_INSTANCE_SUNLIGHT_ACCESS_KEY";
    private static final String NEW_INSTANCE_CURRENT_USER = "com.example.solarcalculator.BottomSheet_NEW_INSTANCE_CURRENT_USER";

    private List<SolarCalData> solarCalDataList;
    private int userSunlightAccessInHours;

    private long totalWatt = 0;
    private long totalWattHour = 0;
    private TextView totalWattHourTextView;
    private TextView recSolarPowerTextView;
    private TextView recPowerCapacityTextView;
    private TextView titleTextView;
    private TextView calcDisplayTextView;

    private UserViewModel viewModel;
    private User currentUser;


    public static CalculateSolarBottomSheet newInstance(List<SolarCalData> solarCalDataList, int userSunlightAccessInHours, User currentUser) {
        CalculateSolarBottomSheet bottomSheet = new CalculateSolarBottomSheet();
        Bundle args = new Bundle();
        args.putParcelableArrayList(NEW_INSTANCE_LIST_KEY, (ArrayList<? extends Parcelable>) solarCalDataList);
        args.putInt(NEW_INSTANCE_SUNLIGHT_ACCESS_KEY, userSunlightAccessInHours);
        args.putParcelable(NEW_INSTANCE_CURRENT_USER, currentUser);
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
        titleTextView = rootview.findViewById(R.id.cal_display_title_textview);
        calcDisplayTextView = rootview.findViewById(R.id.cal_display_textview);
        Button dismissBtn = rootview.findViewById(R.id.cal_display_dismiss_btn);

        if (getArguments() != null) {
            solarCalDataList = getArguments().getParcelableArrayList(NEW_INSTANCE_LIST_KEY);
            userSunlightAccessInHours = getArguments().getInt(NEW_INSTANCE_SUNLIGHT_ACCESS_KEY);
            currentUser = getArguments().getParcelable(NEW_INSTANCE_CURRENT_USER);
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
        totalWattHourTextView.setText(String.format("%s WattHrs", calTotalWattHour()));
        recSolarPowerTextView.setText(String.format("%s Watts", calRecSolarPowerNeeded()));
        recPowerCapacityTextView.setText(String.format("%s AmpHrs", calRecBatteryCapacity()));

        calcDisplayTextView.setText(String.format("%s %s", currentUser.getFirstName(), currentUser.getLastName()));
        titleTextView.setText(getString(R.string.your_solar_cal_text));
    }


    private void calTotalWattAndTotalWattHour() {
        for (SolarCalData solarCalData : solarCalDataList) {
            if(solarCalDataList.get(0)!=solarCalData){ // this if statement is done because of the lazy hack done to the adapter first list data
                totalWatt = totalWatt + (solarCalData.getVoltage() * solarCalData.getAmps());
                totalWattHour=totalWattHour + (solarCalData.getVoltage() * solarCalData.getAmps()*solarCalData.getHoursUsedDaily()*solarCalData.getQuantity());
            }
        }
    }

    private String calRecBatteryCapacity() {
        return String.valueOf(totalWattHour / 6);
    }


    private String calRecSolarPowerNeeded() {
        return String.valueOf(totalWattHour / userSunlightAccessInHours);
    }

    private String calTotalWattHour() {
        return String.valueOf(totalWattHour);
    }


}
