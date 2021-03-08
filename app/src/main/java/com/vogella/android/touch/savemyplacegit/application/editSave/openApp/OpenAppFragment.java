package com.vogella.android.touch.savemyplacegit.application.editSave.openApp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vogella.android.touch.savemyplacegit.R;
import com.vogella.android.touch.savemyplacegit.business.model.NewSave;

import java.util.List;

public class OpenAppFragment extends Fragment {

    NewSaveAdapter newSaveAdapter;

    RecyclerView recyclerView;

    private OpenAppViewModel viewModel;

    private List<NewSave> saves;

    public OpenAppFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(OpenAppViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_open_app, container, false);
        recyclerView = view.findViewById(R.id.recycler);

        viewModel.getSaves().observe(getViewLifecycleOwner(), new Observer<List<NewSave>>() {
            @Override
            public void onChanged(List<NewSave> newSaves) {

                if(newSaves == null || newSaves.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                }
                else {
                    saves = newSaves;
                    showSaves(newSaves);
                }
            }
        });

        return view;
    }

    private void showSaves(List<NewSave> saveList)
    {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newSaveAdapter = new NewSaveAdapter(saveList, getContext());
        recyclerView.setHasFixedSize(true);

        newSaveAdapter.setListener(new NewSaveAdapter.newSaveListener() {

            @Override
            public void onNewSaveClicked(int position, View view) {

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + saves.get(position).getLat() + "," + saves.get(position).getLon() + "?q=" + saves.get(position).getLat() + "," + saves.get(position).getLon() ));
                startActivity(intent);
            }

            @Override
            public void onCloseButtonClicked(int position, View view) {

                viewModel.deleteTheSave(saves.get(position));
                newSaveAdapter.notifyItemRemoved(position);
            }

            @Override
            public void onEditButtonClicked(int position, View view) {

                viewModel.updateSave(saves.get(position));
                newSaveAdapter.notifyItemChanged(position);

            }
        });

        recyclerView.setAdapter(newSaveAdapter);
    }


}