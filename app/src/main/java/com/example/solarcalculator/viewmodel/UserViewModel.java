package com.example.solarcalculator.viewmodel;

import android.app.Application;

import com.example.solarcalculator.DataStore.Repository;
import com.example.solarcalculator.Model.User;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserViewModel extends AndroidViewModel {

    private final Repository repository;
    private MutableLiveData<User> currentUser=new MutableLiveData<>();

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
    }

    public Long insertUser(User user){
        return repository.insertUser(user);
    }

    public void updateUser(User user){
        repository.updateUser(user);
    }

    public void deleteUser(User user){
        repository.deleteUser(user);
    }

    public List<User> getallUsers(){
        return repository.getallUsers();
    }

    public void setCurrentUser(User user) {
        currentUser.setValue(user);
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }
}
