package com.example.solarcalculator.DataStore;

import com.example.solarcalculator.Model.SolarCalData;
import com.example.solarcalculator.Model.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DataDao {

    @Insert
    Long insertData(SolarCalData data);

    @Update
    void updateData(SolarCalData data);

    @Delete
    void deleteData(SolarCalData data);

    @Query("DELETE FROM solarcaldata WHERE userId=:userId")
    void deleteAllData(long userId);

    @Query("SELECT * FROM solarcaldata")
    LiveData<List<SolarCalData>> getAllData();
}
