package com.chenyn.miaowu.data.Record;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "record_table")
public class Record {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "record_id")
    private int recordId;

    @NonNull
    @ColumnInfo(name = "record_date")
    private String recordDate;

    @NonNull
    @ColumnInfo(name = "record_content")
    private String recordContent;

    public Record(String recordDate, String recordContent) {
        this.recordDate = recordDate;
        this.recordContent = recordContent;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordDate(@NonNull String recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordContent(@NonNull String recordContent) {
        this.recordContent = recordContent;
    }

    public String getRecordContent() {
        return recordContent;
    }
}
