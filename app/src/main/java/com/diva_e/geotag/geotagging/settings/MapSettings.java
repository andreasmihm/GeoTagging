package com.diva_e.geotag.geotagging.settings;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by jjan on 29.01.2018.
 */

public class MapSettings {

    private static MapSettings settings;

    public static MapSettings getSettings() {
        return settings != null ? settings : newSettings();
    }

    public static MapSettings newSettings() {
        settings = new MapSettings();
        return settings;
    }

    int type;

    private MapSettings(){
        type = GoogleMap.MAP_TYPE_NORMAL;
    }

    public int getType(){
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
