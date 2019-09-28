package com.example.solarcalculator.DataStore;

import android.content.Context;

import com.example.solarcalculator.Model.User;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {User.class},version = 1,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase INSTANCE;

    public static synchronized UserDatabase getInstance(Context context){
        if(INSTANCE==null){
            return Room.databaseBuilder(context, UserDatabase.class,"chat_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public abstract UserDao userDao();
}

