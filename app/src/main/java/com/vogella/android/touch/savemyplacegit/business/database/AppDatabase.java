package com.vogella.android.touch.savemyplacegit.business.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.vogella.android.touch.savemyplacegit.business.model.NewSave;

@Database(entities = NewSave.class, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "app_database_git.db";
    private static AppDatabase database;

    public static AppDatabase getInstance(Context context){
        if(database == null)
        {
            database = Room.databaseBuilder(context,AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        }
        return database;
    }
    public abstract NewSaveDao getDao();
}
