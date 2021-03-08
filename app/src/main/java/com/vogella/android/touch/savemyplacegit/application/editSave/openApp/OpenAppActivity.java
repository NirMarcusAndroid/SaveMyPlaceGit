package com.vogella.android.touch.savemyplacegit.application.editSave.openApp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vogella.android.touch.savemyplacegit.R;
import com.vogella.android.touch.savemyplacegit.application.editSave.editSave.SaveActivity;
import com.vogella.android.touch.savemyplacegit.application.editSave.editSave.SaveFragment;

public class OpenAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fab
        FloatingActionButton addFab = findViewById(R.id.fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(OpenAppActivity.this, SaveActivity.class);
                intent.putExtra(SaveActivity.KEY_MODE, SaveFragment.Mode.ADD_SAVE);
                startActivity(intent);

            }
        });

        if(savedInstanceState == null)
        {
            OpenAppFragment fragment = new OpenAppFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.root_container, fragment);
            transaction.commit();
        }

        //Toolbar

        Toolbar toolbar = findViewById(R.id.toolbar_open_app_activity);
        setSupportActionBar(toolbar);

        TextView toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarTitle.setText(getSupportActionBar().getTitle());
        getSupportActionBar().setTitle(null);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font.ttf");
        toolbarTitle.setTypeface(typeface);


    }
}
