package com.diva_e.geotag.geotagging.create;

import com.diva_e.geotag.geotagging.marker.MapMarker;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by jjan on 24.01.2018.
 */

public class MarkerCreate {

    private static MarkerCreate markerCreate;

    public static MarkerCreate getMarkerCreate() {
        return markerCreate != null ? markerCreate : newMarkerCreate();
    }

    public static MarkerCreate newMarkerCreate() {
        markerCreate = new MarkerCreate();
        return markerCreate;
    }

    LatLng position;
    String title;
    String[] text;

    private MarkerCreate() {

    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String... text) {
        this.text = text;
    }

    public MapMarker createMarker() {
        return position != null && title != null && text != null ? new MapMarker(position, title, text) : null;
    }

}
