package com.chenyn.miaowu.data.Record;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecordDao {
    @Insert
    void insertRecord(Record record);

    @Delete()
    void deleteRecord(Record record);

    @Query("DELETE FROM record_table WHERE record_id = :recordId")
    void deleteRecordById(int recordId);

    @Query("DELETE FROM record_table")
    void deleteAllRecords();

    @Query("SELECT * FROM record_table")
    LiveData<List<Record>> getAllRecords();
}
