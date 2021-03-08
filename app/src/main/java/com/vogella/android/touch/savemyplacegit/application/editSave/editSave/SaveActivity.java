package com.vogella.android.touch.savemyplacegit.application.editSave.editSave;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.vogella.android.touch.savemyplacegit.R;

public class SaveActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    final int LOCATION_PERMISSION_REQUEST = 1;

    private SaveFragment fragment;
    private Button saveBtn;

    private FusedLocationProviderClient client;
    LocationCallback callback;

    public static final String KEY_MODE = "KEY_MODE";
    public static final String KEY_ID = "KEY_ID";
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_LAT = "KEY_LAT";
    public static final String KEY_LON = "KEY_LON";

    private double lat;
    private double lon;

    private ProgressBar progressBar;
    private Handler handler = new Handler();
    private boolean locationOn = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        saveBtn = findViewById(R.id.save_btn);
        progressBar = findViewById(R.id.progress_bar_save_activity);

        //Location permission
        if(Build.VERSION.SDK_INT>= 23)
        {
            int hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if(hasLocationPermission != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            }
            else
            {
                startLocation();
            }
        }
        else
        {
            startLocation();
        }

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_save_activity);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.back_arrow_24_white);

        TextView toolbarTitle = toolbar.findViewById(R.id.save_activity_toolbar_title);
        toolbarTitle.setText(getSupportActionBar().getTitle());
        getSupportActionBar().setTitle(null);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font.ttf");
        toolbarTitle.setTypeface(typeface);


        //set fragment
        if(savedInstanceState == null) {
            fragment = new SaveFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_save_container, fragment);
            transaction.commit();
        }

        //save button
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while(locationOn == false) {
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                fragment.saveNewSave(lat, lon);
                            }
                        });

                    }
                }).start();
            }

        });
    }

    //Home button
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
        }
        return true;
    }

    //Permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == LOCATION_PERMISSION_REQUEST)
        {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "sorry cant work without permission", Toast.LENGTH_SHORT).show();
            }
            else {
                startLocation();
            }
        }
    }

    //Location
    private void startLocation()
    {

        client = LocationServices.getFusedLocationProviderClient(this);
        callback = new LocationCallback()
        {

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);

                if(!locationAvailability.isLocationAvailable())
                {
                    locationOn = false;

                    AlertDialog.Builder builder = new AlertDialog.Builder(SaveActivity.this);
                    builder.setTitle("Location problem").setMessage("Either the location is off or there is a network problem ")
                            .setPositiveButton("Turn location on", SaveActivity.this).setNegativeButton("Try again", SaveActivity.this).show();
                }

            }

            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Location lastLocation = locationResult.getLastLocation();

                lat = lastLocation.getLatitude();
                lon = lastLocation.getLongitude();

                locationOn = true;
            }
        };

        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(1000);
        request.setFastestInterval(2000);

        client.requestLocationUpdates(request,callback,null);

    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if(which == DialogInterface.BUTTON_POSITIVE)
        {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.removeLocationUpdates(callback);
    }
}
