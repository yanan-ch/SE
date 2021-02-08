package com.chenyn.miaowu.data.pet;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.chenyn.miaowu.data.AppRoomDatabase;

import java.util.List;

public class PetRepository {
    private PetDao mPetDao;
    private LiveData<List<Pet>> mAllPets;

    public PetRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        this.mPetDao = db.petDao();
        this.mAllPets = mPetDao.getAllPets();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Pet>> getAllPets() {
        return mAllPets;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertPet(final Pet Pet) {
        AppRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mPetDao.insertPet(Pet);
            }
        });
    }

    public void deletePet(final Pet Pet) {
        AppRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mPetDao.deletePet(Pet);
            }
        });
    }
}
