package com.example.solarcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.solarcalculator.Model.User;
import com.example.solarcalculator.viewmodel.UserViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "slike.Views.Login";
    public static final String USER_LOGIN_KEY_INTENT_EXTRA ="com.example.solarcalculator_USER_KEY";

    private TextInputLayout emailEditText, passwordEditText;
    private ProgressBar progressBar;
    private UserViewModel viewModel;
    private Handler handler;
    boolean isNowLoggedIn;
    int RC_SIGN_IN = 0;
    GoogleSignInButton googleSignInButton;
    GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;

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



        googleSignInButton = findViewById(R.id.google_signIn_btn);
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        showProgressbar();
        viewModel.getallUsers();
        //initViews();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Google Sign In Error", "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStart() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        super.onStart();
    }


    private void initViews() {
        emailEditText = findViewById(R.id.login_activity_email_TextInputLayout);
        passwordEditText = findViewById(R.id.login_activity_password_TextInputLayout);
        TextView registerHere = findViewById(R.id.login_activity_register_text);
        Button loginbtn = findViewById(R.id.login_activity_button);
        GoogleSignInButton signInButton = findViewById(R.id.google_signIn_btn);

        registerHere.setOnClickListener(this);
        loginbtn.setOnClickListener(this);
        signInButton.setOnClickListener(this);


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
            case R.id.google_signIn_btn:
               login();
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

//    main_menu_logout.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            signOut();
//        }
//    });
//}

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(LoginActivity.this,"Successfully signed out",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }
                });
    }

}
