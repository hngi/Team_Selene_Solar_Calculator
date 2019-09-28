package com.example.solarcalculator;

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
import android.widget.TextView;

import com.example.solarcalculator.Adapter.SolarDataAdapter;
import com.example.solarcalculator.BottomSheet.CalculateSolarBottomSheet;
import com.example.solarcalculator.Model.SolarCalData;
import com.example.solarcalculator.Model.User;
import com.example.solarcalculator.viewmodel.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends BaseActivity implements View.OnClickListener, SolarDataAdapter.DataListListener {
    private static final String TAG = "solarcalculator";
    public static final String USER_KEY_INTENT_EXTRA ="com.example.solarcalculator_USER_KEY";


    ArrayList<SolarCalData> solarCalDataList;
    private SolarDataAdapter solarDataAdapter;

    //AddFile Dialog Views
    private TextInputEditText deviceNameEditText;
    private TextInputEditText deviceAmpsEditText;
    private TextInputEditText deviceVoltsEditText;
    private TextInputEditText deviceHoursEditText;
    private TextInputEditText deviceQtyEditText;
    private ProgressBar progressBar;
    private Handler handler;

    private FloatingActionButton calculateFab;
    private TextView noInputTextView;

    boolean addFileDialogCalledFromFab;

    private SolarCalData dataToEdit;
    private int dataToEditPosition;

    private UserViewModel viewModel;
    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);


        initViews();

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

    }

    private void initViews() {
        solarCalDataList = new ArrayList<>();
        solarDataAdapter = new SolarDataAdapter(solarCalDataList, this);
        solarDataAdapter.setOnDataListListener(this);

        noInputTextView = findViewById(R.id.main_activity_no_input_text);

        RecyclerView recyclerView = findViewById(R.id.main_activity_recyclerview);
        recyclerView.setAdapter(solarDataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        FloatingActionButton addFab = findViewById(R.id.main_activity_add_fab);
        calculateFab = findViewById(R.id.main_activity_calculate_fab);
        addFab.setOnClickListener(this);
        calculateFab.setOnClickListener(this);

        handler = new Handler();

        getLoggedInUserFromIntentExtra();
        generateFirstDummyDataForList();
    }

    private void getLoggedInUserFromIntentExtra() {
        if (getSharePref().getLoggedUserId() != -1 &&getIntent()!=null) {
            currentUser = getIntent().getParcelableExtra(USER_KEY_INTENT_EXTRA);
            noInputTextView.setText(String.format("Hello %s %s\nplease click the + icon to begin your solar calculation", currentUser.getFirstName(), currentUser.getFirstName()));
        }
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
                        .userId(currentUser.getId())
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
        Objects.requireNonNull(dialog.getWindow()).setLayout(700, 1400);
        
    }

    private void openAccessHourDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        View view=View.inflate(this,R.layout.number_picker_dialog,null);

        final NumberPicker numberPicker=view.findViewById(R.id.number_picker_dialog_num_picker);
        Button calculateBtn=view.findViewById(R.id.number_picker_btn);
        progressBar=view.findViewById(R.id.number_picker_progressbar);

        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
        numberPicker.setValue(getSharePref().getLastSunAccessInHour());
        numberPicker.clearFocus();

        builder.setView(view);
        final AlertDialog dialog=builder.create();

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressbar();
                getSharePref().setLastSunAccess(numberPicker.getValue());

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
        SolarCalData data = SolarCalData.getBuilder("Dummy")
                .amps(200).voltage(200).hoursUsedDaily(2).quantity(2).userId(currentUser.getId()).build();
        solarCalDataList.add(data);
    }


    @Override
    public void showFab() {
        calculateFab.show();
        noInputTextView.setVisibility(View.GONE);
    }

    @Override
    public void hideFab() {
        calculateFab.hide();
        noInputTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_clear_all:
                int deletedItems = solarDataAdapter.clearAllData();
                generateFirstDummyDataForList();
                if(deletedItems>0){
                    showSnackbar(deletedItems+" item(s) deleted");
                } else showSnackbar("You have no data to delete");
                return true;
            case R.id.main_menu_logout:
                logout();
                return true;
            case R.id.main_menu_delete_account:
                deleteAccount();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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

    private void deleteAccount() {
        viewModel.deleteUser(currentUser);
        showToast("Account Deleted Successfully");
        logout();
    }

    private void logout() {
        getSharePref().setLoggedUserId((long) -1);
        gotoLoginActivity(MainActivity.this);
        finish();
    }

}
