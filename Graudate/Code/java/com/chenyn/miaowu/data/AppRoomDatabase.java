package com.chenyn.miaowu.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.chenyn.miaowu.data.Record.Record;
import com.chenyn.miaowu.data.Record.RecordDao;
import com.chenyn.miaowu.data.pet.Pet;
import com.chenyn.miaowu.data.pet.PetDao;
import com.chenyn.miaowu.data.post.Post;
import com.chenyn.miaowu.data.post.PostDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Post.class, Pet.class, Record.class}, version = 1, exportSchema = false)
public abstract class AppRoomDatabase extends RoomDatabase {
    public abstract PostDao postDao();
    public abstract PetDao petDao();
    public abstract RecordDao recordDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile AppRoomDatabase INSTANCE;
    private static final String DB_NAME = "RoomDatabase";
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppRoomDatabase.class, DB_NAME)
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    // Populate the database in the background.
                    // If you want to start with more words, just add them.
                    PostDao dao = INSTANCE.postDao();
                    dao.deleteAllPosts();

                    Post post = new Post("2019/01/18 22:22:22", "Hello!", "");
                    dao.insertPost(post);
                }
            });
        }
    };
}
