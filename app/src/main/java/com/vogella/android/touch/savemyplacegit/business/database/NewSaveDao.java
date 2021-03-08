package com.vogella.android.touch.savemyplacegit.business.database;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.vogella.android.touch.savemyplacegit.business.model.NewSave;

import java.util.List;

@androidx.room.Dao
public interface NewSaveDao {

    @Query("SELECT*FROM newsave")
    LiveData<List<NewSave>> getAll();

    @Query("SELECT*FROM newsave WHERE id =:id")
    LiveData<NewSave> get(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NewSave newSave);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<NewSave> saves);

    @Update
    void update(NewSave newSave);

    @Delete
    void delete(NewSave... newSaves);
}
