package com.example.solarcalculator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.solarcalculator.BottomSheet.CalculateSolarBottomSheet;
import com.example.solarcalculator.Adapter.SolarDataAdapter;
import com.example.solarcalculator.Utils.SharedPref;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.example.solarcalculator.Model.SolarCalData;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, SolarDataAdapter.DataListListener {
    private static final String TAG = "solarcalculator";
    ArrayList<SolarCalData> solarCalDataList;
    private SolarDataAdapter solarDataAdapter;
    private SharedPref sharedPref;

    //AddFile Dialog Views
    private TextInputEditText deviceNameEditText;
    private TextInputEditText deviceAmpsEditText;
    private TextInputEditText deviceVoltsEditText;
    private TextInputEditText deviceHoursEditText;
    private TextInputEditText deviceQtyEditText;
    private ProgressBar progressBar;
    private Handler handler;

    private FloatingActionButton calculateFab;

    boolean addFileDialogCalledFromFab;

    private SolarCalData dataToEdit;
    private int dataToEditPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);


        initViews();



    }

    private void initViews() {
        solarCalDataList = new ArrayList<>();
        solarDataAdapter = new SolarDataAdapter(solarCalDataList, this);
        solarDataAdapter.setOnDataListListener(this);

        RecyclerView recyclerView = findViewById(R.id.main_activity_recyclerview);
        recyclerView.setAdapter(solarDataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedPref = new SharedPref(getSharedPreferences("SunHour", Context.MODE_PRIVATE));

        FloatingActionButton addFab = findViewById(R.id.main_activity_add_fab);
        calculateFab = findViewById(R.id.main_activity_calculate_fab);
        addFab.setOnClickListener(this);
        calculateFab.setOnClickListener(this);

        handler = new Handler();
        generateFirstDummyDataForList();
    }



    private void openCalculationBottomSheet(List<SolarCalData> solarCalDataList, int userSunlightAccessInHours){
        CalculateSolarBottomSheet bottomSheet = CalculateSolarBottomSheet.newInstance(solarCalDataList, userSunlightAccessInHours);
        bottomSheet.setCancelable(false);
        bottomSheet.show(getSupportFragmentManager(),null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_activity_add_fab:
                addFileDialogCalledFromFab=true;
                openAddFileDialog();
                break;
            case R.id.main_activity_calculate_fab:
                openAccessHourDialog();
                break;
        }
    }

    private void openAddFileDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=View.inflate(this,R.layout.add_data_layout,null);

        deviceNameEditText = view.findViewById(R.id.addfile_device_name_editText);
        deviceAmpsEditText = view.findViewById(R.id.addfile_device_amps_editText);
        deviceVoltsEditText = view.findViewById(R.id.addfile_device_volts_editText);
        deviceHoursEditText = view.findViewById(R.id.addfile_device_hour_editText);
        deviceQtyEditText = view.findViewById(R.id.addfile_device_qty_editText);
        progressBar = view.findViewById(R.id.addfile_progressbar);
        Button addApplianceBtn=view.findViewById(R.id.addfile_btn);

        builder.setView(view);
        final AlertDialog dialog=builder.create();

        if(!addFileDialogCalledFromFab){
            if(dataToEdit==null){
                showSnackbar("Something is wrong somewhere");
                return;
            }
            deviceNameEditText.setText(dataToEdit.getDeviceName());
            deviceAmpsEditText.setText(String.valueOf(dataToEdit.getAmps()));
            deviceVoltsEditText.setText(String.valueOf(dataToEdit.getVoltage()));
            deviceHoursEditText.setText(String.valueOf(dataToEdit.getHoursUsedDaily()));
            deviceQtyEditText.setText(String.valueOf(dataToEdit.getQuantity()));
        }

        addApplianceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressbar();
                String deviceName = Objects.requireNonNull(deviceNameEditText.getText()).toString().trim();
                String deviceAmps = Objects.requireNonNull(deviceAmpsEditText.getText()).toString().trim();
                String deviceVolts = Objects.requireNonNull(deviceVoltsEditText.getText()).toString().trim();
                String deviceHours = Objects.requireNonNull(deviceHoursEditText.getText()).toString().trim();
                String deviceQty = Objects.requireNonNull(deviceQtyEditText.getText()).toString().trim();

                if (!validateInput(deviceName, deviceAmps, deviceVolts,deviceHours,deviceQty)) {
                    hideProgressbar();
                    return;
                }
                SolarCalData data = SolarCalData.getBuilder(deviceName)
                        .amps(Integer.parseInt(deviceAmps))
                        .voltage(Integer.parseInt(deviceVolts))
                        .hoursUsedDaily(Integer.parseInt(deviceHours))
                        .quantity(Integer.parseInt(deviceQty))
                        .build();
                if(addFileDialogCalledFromFab){
                    solarDataAdapter.addData(data);
                } else {
                    solarDataAdapter.modifyData(data,dataToEditPosition);
                }


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressbar();
                        dialog.dismiss();
                        showSnackbar("New appliance added");
                        dataToEdit=null;
                    }
                },500);
            }
        });

        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Objects.requireNonNull(dialog.getWindow()).setLayout(800, 1400);
    }

    private void openAccessHourDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=View.inflate(this,R.layout.number_picker_dialog,null);

        final NumberPicker numberPicker=view.findViewById(R.id.number_picker_dialog_num_picker);
        Button calculateBtn=view.findViewById(R.id.number_picker_btn);
        progressBar=view.findViewById(R.id.number_picker_progressbar);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(sharedPref.getLastSunAccessInHour());
        numberPicker.clearFocus();

        builder.setView(view);
        final AlertDialog dialog=builder.create();

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressbar();
                sharedPref.setLastSunAccess(numberPicker.getValue());

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressbar();
                        openCalculationBottomSheet(solarDataAdapter.getAllData(),numberPicker.getValue());
                        dialog.dismiss();
                    }
                },500);
            }
        });


        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Objects.requireNonNull(dialog.getWindow()).setLayout(700, 1400);
    }

    private boolean validateInput(String deviceName, String deviceAmps, String deviceVolts, String deviceHour, String deviceQty) {

        if (TextUtils.isEmpty(deviceName)) {
            deviceNameEditText.setError("Required Field");
            return false;
        }

        if (TextUtils.isEmpty(deviceAmps)) {
            deviceAmpsEditText.setError("Required Field");
            return false;
        }

        if (TextUtils.isEmpty(deviceVolts)) {
            deviceVoltsEditText.setError("Required Field");
            return false;
        }

        if (TextUtils.isEmpty(deviceHour)) {
            deviceHoursEditText.setError("Required Field");
            return false;
        }

        if (TextUtils.isEmpty(deviceQty)) {
            deviceQtyEditText.setError("Required Field");
            return false;
        }

        return true;
    }


    private void showSnackbar(String msg){
        Snackbar.make(findViewById(R.id.main_activity_root),msg,Snackbar.LENGTH_SHORT).show();

    }

    private void showProgressbar(){
        if(progressBar!=null){
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void hideProgressbar(){
        if(progressBar!=null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private void generateFirstDummyDataForList() {
        SolarCalData data = SolarCalData.getBuilder("Dummy").amps(200).voltage(200).hoursUsedDaily(2).quantity(2).build();
        solarCalDataList.add(data);
    }


    @Override
    public void showFab() {
        calculateFab.show();
    }

    @Override
    public void hideFab() {
        calculateFab.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_action_clear_all){
            int deletedItems = solarDataAdapter.clearAllData();
            generateFirstDummyDataForList();
            showSnackbar(deletedItems+" item(s) deleted");
            return true;
        } else
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case 112:
                dataToEditPosition=item.getGroupId();
                dataToEdit = solarDataAdapter.getDataAtPosition(dataToEditPosition);
                addFileDialogCalledFromFab=false;
                openAddFileDialog();
                break;
            case 113:
                solarDataAdapter.removeDataAtPosition(item.getGroupId());
                showSnackbar("Item deleted");
                break;

        }
        return super.onContextItemSelected(item);
    }
}
