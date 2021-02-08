package com.chenyn.miaowu.data.Record;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.chenyn.miaowu.data.AppRoomDatabase;

import java.util.List;

public class RecordRepository {
    private RecordDao mRecordDao;
    private LiveData<List<Record>> mAllRecords;

    public RecordRepository(Application application) {
        AppRoomDatabase db = AppRoomDatabase.getDatabase(application);
        this.mRecordDao = db.recordDao();
        this.mAllRecords = mRecordDao.getAllRecords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Record>> getAllRecords() {
        return mAllRecords;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    public void insertRecord(final Record Record) {
        AppRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mRecordDao.insertRecord(Record);
            }
        });
    }

    public void deleteRecordById(final Record Record) {
        AppRoomDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mRecordDao.deleteRecordById(Record.getRecordId());
            }
        });
    }
}
