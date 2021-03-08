package com.vogella.android.touch.savemyplacegit.business.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.vogella.android.touch.savemyplacegit.business.model.NewSave;

import java.util.List;

public class DatabaseHelper {

    private NewSaveDao newSaveDao;

    public DatabaseHelper(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        newSaveDao = database.getDao();
    }

    public LiveData<List<NewSave>> getNewSaves() {
        return newSaveDao.getAll();
    }
    public LiveData<NewSave> getNewSave(int id) {
        return newSaveDao.get(id);
    }
    public void addNewSave(NewSave newSave) {
        newSaveDao.insert(newSave);
    }

    public void addNewSaves(List<NewSave> newSaves) {
        newSaveDao.insert(newSaves);
    }

    public void editNewSave(NewSave newSave) {
        newSaveDao.update(newSave);
    }

    public void deleteSave(NewSave newSave) {
        newSaveDao.delete(newSave);
    }
}
