package com.example.solarcalculator.viewmodel;

import android.app.Application;

import com.example.solarcalculator.DataStore.Repository;
import com.example.solarcalculator.Model.SolarCalData;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DataViewModel extends AndroidViewModel {

    private final Repository repository;

    public DataViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public Long insertData(SolarCalData data){
        return repository.insertData(data);
    }

    public void updateData(SolarCalData data){
        repository.updateData(data);
    }

    public void deleteData(SolarCalData data){
        repository.deleteData(data);
    }

    public void deleteAllData(long userId){
        repository.deleteAllData(userId);
    }
    public LiveData<List<SolarCalData>> getAllData(){
        return repository.getSolarCalList();
    }

}
