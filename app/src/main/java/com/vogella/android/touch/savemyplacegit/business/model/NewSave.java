package com.vogella.android.touch.savemyplacegit.business.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NewSave {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String saveName;
    private double lat;
    private double lon;

   /* public NewSave(String saveName, float lat, float lon) {
        this.saveName = saveName;
        this.lat = lat;
        this.lon = lon;
    }*/
    // public NewSave() {}

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
