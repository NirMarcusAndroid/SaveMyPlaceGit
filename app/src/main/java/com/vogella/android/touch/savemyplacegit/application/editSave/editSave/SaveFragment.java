package com.vogella.android.touch.savemyplacegit.application.editSave.editSave;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.vogella.android.touch.savemyplacegit.R;
import com.vogella.android.touch.savemyplacegit.business.model.NewSave;

import static com.vogella.android.touch.savemyplacegit.application.editSave.editSave.SaveActivity.KEY_ID;
import static com.vogella.android.touch.savemyplacegit.application.editSave.editSave.SaveActivity.KEY_LAT;
import static com.vogella.android.touch.savemyplacegit.application.editSave.editSave.SaveActivity.KEY_LON;
import static com.vogella.android.touch.savemyplacegit.application.editSave.editSave.SaveActivity.KEY_MODE;
import static com.vogella.android.touch.savemyplacegit.application.editSave.editSave.SaveActivity.KEY_NAME;

public class SaveFragment extends Fragment {

    private Mode mode;
    private EditText saveEt;
    private NewSave newSave;
    private SaveViewModel viewModel;



    public enum Mode{

        ADD_SAVE
    }
    public SaveFragment() { }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SaveViewModel.class);
        newSave = new NewSave();

        Bundle extras = getActivity().getIntent().getExtras();
        if(extras != null)
        {
            mode = (Mode)extras.getSerializable(KEY_MODE);
            newSave.setId(extras.getInt(KEY_ID));
            newSave.setSaveName(extras.getString(KEY_NAME));
            newSave.setLat(extras.getDouble(KEY_LAT));
            newSave.setLon(extras.getDouble(KEY_LON));
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_save, container,false);
        saveEt = view.findViewById(R.id.new_save_et);
        return view;
    }

    public void saveNewSave(double lat, double lon) {
        if(!saveEt.getText().toString().isEmpty() ) {

            newSave.setSaveName(saveEt.getText().toString());
            newSave.setLat(lat);
            newSave.setLon(lon);
            if (mode == Mode.ADD_SAVE) {
                viewModel.addNewSave(newSave);
            }

            getActivity().finish();
        }
    }


}
