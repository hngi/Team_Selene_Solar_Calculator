package com.example.solarcalculator.Utils;

import android.content.SharedPreferences;

public class SharedPref {
    private static final String LAST_SUN_ACCESS ="LAST_SUN_ACCESS";
    private SharedPreferences sharedPreferences;

    public SharedPref(SharedPreferences sharedPreferences) {
        //this.sharedPreferences = context.getSharedPreferences("SharedPref",Context.MODE_PRIVATE);
        this.sharedPreferences=sharedPreferences;

    }

    public void setLastSunAccess(int hour){
        sharedPreferences.edit().putInt(LAST_SUN_ACCESS,hour).apply();
    }

    public int getLastSunAccessInHour(){
        return sharedPreferences.getInt(LAST_SUN_ACCESS,1);
    }
}
