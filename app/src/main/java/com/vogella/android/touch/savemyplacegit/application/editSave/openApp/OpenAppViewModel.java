package com.vogella.android.touch.savemyplacegit.application.editSave.openApp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.vogella.android.touch.savemyplacegit.business.database.DatabaseHelper;
import com.vogella.android.touch.savemyplacegit.business.model.NewSave;

import java.util.List;

public class OpenAppViewModel extends AndroidViewModel {

    private LiveData<List<NewSave>> saves;
    private DatabaseHelper databaseHelper;

    public OpenAppViewModel(@NonNull Application application) {
        super(application);
        databaseHelper = new DatabaseHelper(application.getApplicationContext());
    }

    LiveData<List<NewSave>> getSaves() {
        if(saves == null)
        {
            saves = databaseHelper.getNewSaves();
        }

        return saves;
    }

    void deleteTheSave(NewSave newSave) {databaseHelper.deleteSave(newSave);}

    void updateSave(NewSave newSave) {databaseHelper.editNewSave(newSave);}
}
