package com.example.solarcalculator;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
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
import com.example.solarcalculator.viewmodel.DataViewModel;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends BaseActivity implements View.OnClickListener, SolarDataAdapter.DataListListener {
    private static final String TAG = "solarcalculator";
    public static final String USER_KEY_INTENT_EXTRA ="com.example.solarcalculator_USER_KEY";


    private SolarDataAdapter solarDataAdapter;
    private List<SolarCalData> dataList;


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

    private UserViewModel userViewModel;
    private User currentUser;
    private DataViewModel dataViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar=findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);


        initViews();


    }

    private void initViews() {
        handler = new Handler();
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        dataViewModel = ViewModelProviders.of(this).get(DataViewModel.class);

        dataList =new ArrayList<>();
        solarDataAdapter = new SolarDataAdapter(dataViewModel);
        solarDataAdapter.setOnDataListListener(this);

        noInputTextView = findViewById(R.id.main_activity_no_input_text);

        RecyclerView recyclerView = findViewById(R.id.main_activity_recyclerview);
        recyclerView.setAdapter(solarDataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton addFab = findViewById(R.id.main_activity_add_fab);
        calculateFab = findViewById(R.id.main_activity_calculate_fab);
        addFab.setOnClickListener(this);
        calculateFab.setOnClickListener(this);

        getLoggedInUserFromIntentExtra();
        populateData();
    }

    private void populateData() {
        dataViewModel.getAllData().observe(this, new Observer<List<SolarCalData>>() {
            @Override
            public void onChanged(List<SolarCalData> solarCalDataList) {
                dataList.clear();
                for(SolarCalData data:solarCalDataList){
                    if(data.getUserId()==currentUser.getId()){
                        dataList.add(data);
                    }
                }
                solarDataAdapter.summitList(dataList);
            }
        });
    }

    private void getLoggedInUserFromIntentExtra() {
        if (getSharePref().getLoggedUserId() != -1 &&getIntent()!=null) {
            currentUser = getIntent().getParcelableExtra(USER_KEY_INTENT_EXTRA);
            setNoItemText();
            userViewModel.setCurrentUser(currentUser);
        }
    }

    private void setNoItemText() {
        noInputTextView.setText(String.format("Hello %s %s\nplease click the + icon to begin your solar calculation", currentUser.getFirstName(), currentUser.getLastName()));
    }


    private void openCalculationBottomSheet(List<SolarCalData> solarCalDataList, int userSunlightAccessInHours){
        CalculateSolarBottomSheet bottomSheet = CalculateSolarBottomSheet.newInstance(solarCalDataList, userSunlightAccessInHours,currentUser);
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

                String msg = "";
                if(addFileDialogCalledFromFab){
                    solarDataAdapter.addData(data);
                    msg = "New appliance added";
                } else {
                    data.setId(dataToEdit.getId());
                    solarDataAdapter.modifyData(data);
                    msg = "Edited Successfully";
                }


                final String finalMsg = msg;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressbar();
                        dialog.dismiss();
                        showSnackbar(finalMsg);
                        dataToEdit=null;
                    }
                },500);
            }
        });

        dialog.show();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Objects.requireNonNull(dialog.getWindow()).setLayout(650, 1200);
        
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
        Objects.requireNonNull(dialog.getWindow()).setLayout(650, 1200);
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
    public void listHasOneItem() {
        noInputTextView.setVisibility(View.VISIBLE);
        noInputTextView.setText(getString(R.string.use_green_icon_text));
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
                long deletedItems = solarDataAdapter.clearAllData(currentUser.getId());
                setNoItemText();
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
                int dataToEditPosition = item.getGroupId();
                dataToEdit = solarDataAdapter.getDataAtPosition(dataToEditPosition);
                addFileDialogCalledFromFab=false;
                openAddFileDialog();
                break;
            case 113:
                solarDataAdapter.deleteData(solarDataAdapter.getDataAtPosition(item.getGroupId()));
                showSnackbar("Item deleted");
                break;

        }
        return super.onContextItemSelected(item);
    }

    private void deleteAccount() {
        //TODO should have a confirmation dialog
        solarDataAdapter.clearAllData(currentUser.getId());
        userViewModel.deleteUser(currentUser);
        showToast("Account Deleted Successfully");
        logout();
    }

    private void logout() {
        getSharePref().setLoggedUserId((long) -1);
        gotoLoginActivity(MainActivity.this);
        finish();
    }

}
