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
import com.google.android.material.textfield.TextInputLayout;

import androidx.lifecycle.ViewModelProviders;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "slike.Views.Login";

    private TextInputLayout emailEditText, passwordEditText;
    private ProgressBar progressBar;
    private UserViewModel viewModel;
    private Handler handler;
    boolean isNowLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        initViews();


        UserViewModel viewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        if (getSharePref().getLoggedUserId() != -1) {
            for (User looggedUser : viewModel.getallUsers()) {
                if(looggedUser.getId().equals(getSharePref().getLoggedUserId())) {
                    openMainActivity(LoginActivity.this,looggedUser);
                    finish();
                }
            }
        }


    }

    private void initViews() {
        emailEditText = findViewById(R.id.login_activity_email_TextInputLayout);
        passwordEditText = findViewById(R.id.login_activity_password_TextInputLayout);
        TextView registerHere = findViewById(R.id.login_activity_register_text);
        Button loginbtn = findViewById(R.id.login_activity_button);


        registerHere.setOnClickListener(this);
        loginbtn.setOnClickListener(this);

        progressBar = findViewById(R.id.login_activity_progressbar);
        handler = new Handler();

        viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_activity_button:
                login();
                break;
            case R.id.login_activity_register_text:
                gotoRegisterActivity(LoginActivity.this);
                break;
        }
    }


    private void login() {
        showProgressbar();
        String email = emailEditText.getEditText().getText().toString().trim();
        String password = passwordEditText.getEditText().getText().toString().trim();


        if (!validateEmailPassword(email, password)) {
            hideProgressbar();
            isNowLoggedIn =false;
            return;
        }

        for(User user:viewModel.getallUsers()){
            if(user.getEmail().equals(email)&&user.getPassword().equals(password)&&!isNowLoggedIn){
                isNowLoggedIn =true;
                getSharePref().setLoggedUserId(user.getId());
                Log.d(TAG, "register: SharedPref LoggedIn UserId "+getSharePref().getLoggedUserId());
                openMainActivityOnDelay(user);
                return;
            }else if(isNowLoggedIn){
                showToast("Please wait, Logging you in");
                return;
            }
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgressbar();
                showToast("Wrong email or password");
                isNowLoggedIn =false;
            }
        },2000);

    }

    private void openMainActivityOnDelay(final User user) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                openMainActivity(LoginActivity.this,user);
                finish();
            }
        },3000);
    }


    private boolean validateEmailPassword(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Required");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Required");
            return false;
        } else if (password.length() > 15) {
            passwordEditText.setError("Password too long");
            return false;
        }
        return true;
    }


    private void showProgressbar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressbar() {
        progressBar.setVisibility(View.GONE);
    }

}
