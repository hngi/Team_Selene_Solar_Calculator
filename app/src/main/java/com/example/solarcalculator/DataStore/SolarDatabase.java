package com.example.solarcalculator.DataStore;

import android.content.Context;

import com.example.solarcalculator.Model.SolarCalData;
import com.example.solarcalculator.Model.User;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {User.class, SolarCalData.class},version = 2,exportSchema = false)
public abstract class SolarDatabase extends RoomDatabase {
    private static SolarDatabase INSTANCE;

    public static synchronized SolarDatabase getInstance(Context context){
        if(INSTANCE==null){
            return Room.databaseBuilder(context, SolarDatabase.class,"chat_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();
    public abstract DataDao dataDao();
}

