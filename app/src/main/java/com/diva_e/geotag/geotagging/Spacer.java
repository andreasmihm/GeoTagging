package com.diva_e.geotag.geotagging;

/**
 * Created by jjan on 23.01.2018.
 */

public enum  Spacer {

    NEW_LINE("-/newLine/"),
    SPLIT("-/split/");
//    URL("-/url/=");

    String spacer;

    private Spacer(String spacer) {
        this.spacer = spacer;
    }

    public String toString() {
        return spacer;
    }
}
