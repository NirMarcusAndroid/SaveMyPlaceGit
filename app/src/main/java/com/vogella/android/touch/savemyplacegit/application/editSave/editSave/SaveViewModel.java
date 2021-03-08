package com.vogella.android.touch.savemyplacegit.application.editSave.editSave;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.vogella.android.touch.savemyplacegit.business.database.DatabaseHelper;
import com.vogella.android.touch.savemyplacegit.business.model.NewSave;

public class SaveViewModel extends AndroidViewModel {
    private DatabaseHelper databaseHelper;

    public SaveViewModel(@NonNull Application application) {
        super(application);

        databaseHelper = new DatabaseHelper(application.getApplicationContext());
    }

    void addNewSave(NewSave newSave) {
        databaseHelper.addNewSave(newSave);
    }

    void updateSave(NewSave newSave) {
        databaseHelper.editNewSave(newSave);
    }

    void deleteSave(NewSave newSave) {
        databaseHelper.deleteSave(newSave);
    }
}
