package com.example.solarcalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.solarcalculator.DataStore.SharedPref;
import com.example.solarcalculator.Model.User;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private SharedPref sharePref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharePref=SharedPref.getINSTANCE(this);
    }

    public SharedPref getSharePref() {
        return sharePref;
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected void openMainActivity(Context context, User currentUser) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(MainActivity.USER_KEY_INTENT_EXTRA,currentUser);
        startActivity(intent);
    }

    protected void gotoRegisterActivity(Context context) {
        startActivity(new Intent(context, RegistrationActivity.class));
    }

    protected void gotoLoginActivity(Context context) {
        startActivity(new Intent(context, LoginActivity.class));
    }




}
