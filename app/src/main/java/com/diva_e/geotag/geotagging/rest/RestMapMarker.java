package com.diva_e.geotag.geotagging.rest;

import com.diva_e.geotag.geotagging.marker.MapMarker;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by jjan on 26.01.2018.
 */

public class RestMapMarker {


    String title;
    String text;
    String link;

    double lat;
    double lng;


    String userid;


    public RestMapMarker() {
        super();
    }

    public RestMapMarker(String title, String text, String link, double lat, double lng, String userid) {
        super();
        this.title = title;
        this.text = text;
        this.link = link;
        this.lat = lat;
        this.lng = lng;
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getUserId() {
        return userid;
    }

    public void setUserId(String userid) {
        this.userid = userid;
    }

    public MapMarker toMapMarker(){
        return new MapMarker(new LatLng(lat, lng), title, text).setLink(link);
    }

}
