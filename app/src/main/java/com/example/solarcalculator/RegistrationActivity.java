package com.example.solarcalculator;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.solarcalculator.Model.User;
import com.example.solarcalculator.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import androidx.lifecycle.ViewModelProviders;

public class RegistrationActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "slike.Views.Register";

    private TextInputEditText fNameEditext, lNameEditext, emailEditText, passwordEditText;
    private ProgressBar progressBar;
    private UserViewModel viewModel;
    private Handler handler;
    private boolean isNowRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_scroll_view);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        initViews();

        //PreferenceManager.getDefaultSharedPreferences(this)

    }

    private void initViews() {
        fNameEditext = findViewById(R.id.registration_activity_fname);
        lNameEditext = findViewById(R.id.registration_activity_lname);
        emailEditText = findViewById(R.id.registration_activity_email);
        passwordEditText = findViewById(R.id.registration_activity_password);
        TextView loginHere = findViewById(R.id.registration_activity_login_text);
        Button registerBtn = findViewById(R.id.registration_activity_button);
        progressBar =findViewById(R.id.registration_activity_progressbar);


        loginHere.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

        handler = new Handler();

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registration_activity_login_text: gotoLoginActivity(this);
                break;
            case R.id.registration_activity_button: register();
                break;
        }
    }


    private void register(){
        showProgressbar();
        String fName = Objects.requireNonNull(fNameEditext.getText()).toString().trim();
        String lName = Objects.requireNonNull(lNameEditext.getText()).toString().trim();
        String email = Objects.requireNonNull(emailEditText.getText()).toString().trim();
        String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();


        if (!validateInputs(fName, lName, email, password)){
            hideProgressbar();
            return;
        }


        User newUser=new User(fName,lName,email);
        newUser.setPassword(password);
        Long insertedUserId = viewModel.insertUser(newUser);
        newUser.setId(insertedUserId);

        if(insertedUserId!=null&&insertedUserId!=-1&&!isNowRegistered){
            isNowRegistered=true;
            getSharePref().setLoggedUserId(insertedUserId);
            Log.d(TAG, "register: SharedPref LoggedIn UserId "+getSharePref().getLoggedUserId());
            openHandler(newUser);
        } else if(isNowRegistered){
            showToast("Registering Please wait");
            return;
        }else {
            showToast("Error Registering");
        }

    }

    private void openHandler(final User newUser) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgressbar();
                showToast("Registration Successful");
                openLoginActivity(RegistrationActivity.this,newUser);
                finish();
            }
        },3000);
    }




    private boolean validateInputs(String fname, String lname, String email, String password) {
        if(TextUtils.isEmpty(fname)){
            fNameEditext.setError("Required");
            return false;
        }

        if(TextUtils.isEmpty(lname)){
            lNameEditext.setError("Required");
            return false;
        }

        if(TextUtils.isEmpty(email)){
            emailEditText.setError("Required");
            return false;
        }

        if(TextUtils.isEmpty(password)){
            passwordEditText.setError("Required");
            return false;
        } else if(password.length()>15){
            passwordEditText.setError("Password too long");
            return false;
        }
        return true;
    }


    private void showProgressbar(){
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressbar(){
        progressBar.setVisibility(View.GONE);
    }
}
