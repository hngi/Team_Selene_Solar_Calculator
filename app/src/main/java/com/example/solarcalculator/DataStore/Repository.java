package com.example.solarcalculator.DataStore;

import android.content.Context;

import com.example.solarcalculator.Model.SolarCalData;
import com.example.solarcalculator.Model.User;

import java.util.List;

import androidx.lifecycle.LiveData;

public class Repository {
    private final LiveData<List<SolarCalData>> solarCalList;
    private UserDao userDao;
    private DataDao dataDao;


    public Repository(Context context) {
        SolarDatabase solarDatabase = SolarDatabase.getInstance(context);
        userDao = solarDatabase.userDao();
        dataDao = solarDatabase.dataDao();
        solarCalList = dataDao.getAllData();
    }

    //Getters for User
    public Long insertUser(User user){
        return userDao.insertUser(user);
    }

    public void updateUser(User user){
        userDao.updateUser(user);
    }

    public void deleteUser(User user){
        userDao.deleteUser(user);
    }

    public List<User> getallUsers(){
        return userDao.getallUsers();
    }




    //Getters for SolarCalData
    public Long insertData(SolarCalData data){
        return dataDao.insertData(data);
    }

    public void updateData(SolarCalData data){
        dataDao.updateData(data);
    }

    public void deleteData(SolarCalData data){
        dataDao.deleteData(data);
    }

    public void deleteAllData(long userId){
        dataDao.deleteAllData(userId);
    }

    public LiveData<List<SolarCalData>> getSolarCalList() {
        return solarCalList;
    }
}
